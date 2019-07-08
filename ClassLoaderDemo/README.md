

# **ClassLoader加载器**


### **JDK中的三种ClassLoader**

| 类加载器 | 描述|
| :--- |:--- |
| Bootstrap ClassLoader | 是用C++语言写的，它是在Java虚拟机启动后初始化的，它主要负责加载`%JAVA_HOME%/jre/lib,-Xbootclasspath`参数指定的路径以及`%JAVA_HOME%/jre/classes`中的类|
|Extension ClassLoader | 用来进行扩展类的加载，一般对应的是jre\lib\ext目录中的类 |
| AppClassLoader | 加载classpath指定的类，是最常使用的一种加载器 |








通过一个案例,进一步了解：
```Java
public class Test {
    public static void main(String[] arg) {
         //获取Test类的类加载器
        ClassLoader appClassLoader = Test.class.getClassLoader();
        System.out.println(" Test类的加载器是：" + appClassLoader.getClass().getSimpleName());
       //获取AppClassLoader的父类
        ClassLoader extClassLoader=appClassLoader.getParent();
        System.out.println(" Test类的加载器的父类是：" + extClassLoader.getClass().getSimpleName());
        //获取ExtClassLoader的parent
        ClassLoader  bootstrapClassLoader=extClassLoader.getParent();
        System.out.println(" Test类的加载器的父类的父类是：" + bootstrapClassLoader);
    }
}
```
控制台输出结果：
```Java
 Test类的加载器是：AppClassLoader
 Test类的加载器的父类是：ExtClassLoader
 Test类的加载器的父类的父类是：null
```
从以上可知，加载Test类是AppClassLoader。AppClassLoader的父类是ExtClassLoader。ExtClassLoader的父类BootstrapClassLoder，但却为null?由于Bootstrap Loader是用C++语言写的，依java的观点来看，逻辑上并不存在Bootstrap Loader的类实体。

三者的关系图如下：




### **ClassLoader 委托模型机制**:

**ClassLoader的加载机制**：
>当类加载器有载入类的需求时，会先请示其Parent，让Parent使用其提供的路径帮忙载入。若Parent找不到，那么才自己依照自己的搜索路径搜索类。

查看ClassLoader的源码,进一步了解：
```
public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
}
```
继续查看：
```Java
protected Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException{
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    if (parent != null) {
                       //关键点1：先从父的ClassLoader中查找。
                        c = parent.loadClass(name, false);
                    } else {
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }
                //关键点2：若parent中没找到，调用findClass()在本身中查找。             
                if (c == null) {
                    long t1 = System.nanoTime();
                    c = findClass(name);
                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

```
从上可知：

- ClassLoader加载机制是委派双亲机制，会先parent先去查找对应的类，若是没有找到，则在本身中查找。
- 自定义ClassLoader的子类，需要继承ClassLoader外，还需要重写findClass()

###  **自定义ClassLoader**

既然JVM已经提供了默认的类加载器，为什么还要定义自已的类加载器呢？

因为Java中提供的默认ClassLoader，只加载指定目录下的jar和class，如果我们想加载其它位置的类或jar时，比如：我要加载网络上的一个class文件，通过动态加载到内存之后，要调用这个类中的方法实现我的业务逻辑。在这样的情况下，默认的ClassLoader就不能满足我们的需求了，所以需要定义自己的ClassLoader。




**1. 先创建本地插件的源文件**：
```Java
package com.xingen.classloader.plugin;

public class PluginTest {
    public PluginTest() {
    }

    public void print() {
        System.out.append("Plugin加载,自定义ClassLoader加载class文件");
    }
}
```
进一步编译得到对应的PluginTest.class类文件，作为插件。

**2. 自定义ClassLoader类**:

继承ClassLoader,重写findClass()方法。 
```Java
public class HotClassLoader extends ClassLoader {
    private static final ClassLoader ParentClassLoader = ClassLoader.getSystemClassLoader();
    private String filePath;

    public HotClassLoader(String filePath) {
        super(ParentClassLoader);
        this.filePath = filePath;
    }

    /**
     * 重写父类的findClass（）
     * <p>
     * loadClass()中已经实现搜索类的算法，
     * 当loadClass()搜索不到就会调用findClass()
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("HotClassLoader 执行 findClass() ");
        byte[] bytes = StreamUtils.fileToByteArray(new File(filePath));
        //将byte生成Class对象
        Class<?> mClass = defineClass(name, bytes, 0, bytes.length);
        return mClass;
    }
}

```

根据插件路径，将插件加载进去，从插件中查找类


**3. 加载插件中的类**：

```Java
private static void testClassLoader() {
        String dir = System.getProperty("user.dir");
        File pluginFile = new File(dir + File.separator + "plugin" + File.separator + "PluginTest.class");
        //额外的PluginTest类的路径
        final String filePath = pluginFile.getAbsolutePath();
        try {
            //创建ClassLoader子类对象
            HotClassLoader hotClassLoader = new HotClassLoader(filePath);
            //包名下的类名
            final String className = "com.xingen.classloader.plugin.PluginTest";
            //将该类加载到JVM
            Class<?> objectClass = hotClassLoader.loadClass(className);
            if (objectClass == null) {
                System.out.println(" 加载plugin中的类失败");
            } else {
                //反射调用额外路径下类中的方法
                Method printMethod = objectClass.getDeclaredMethod("print");
                printMethod.setAccessible(true);
                printMethod.invoke(objectClass.newInstance(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
控制台，输出结果：
```
HotClassLoader 执行 findClass() 
Plugin加载,自定义ClassLoader加载class文件
```

