**反射之创建类对象**

通过反射可以创建Class<?>中"?"对应的类型的实例对象，众所众知，创建类对象，会调用构造方法，构造器可以有多个，默认构造方法，多参数构造方法等。

这里演示，通过反射创建默认构造方法的实例对象，和带参数的构造方法的实例对象。



案例实战
---

**1. 反射访问默认构造方法，创建类实例对象**

大概思路如下：

- 先在类中构建一个默认的构造方法
- 然后获取到Class对象
- 通过`newInstance() `获取到类实例对象。

**2. 反射访问Private修饰的带参数构造方法，创建类实例对象** 

大概思路如下：

- 先在类中创建一个private修饰的带参数的构造方法，
- 然后获取Class对象
- 通过` getDeclaredConstructor(Class<?>... parameterTypes)：`获取到指定参数类型的构造方法
- 接下来调用`setAccessible(true) `绕过private修饰符检查
- 最后调用`newInstance()`传入相关参数，获取到类实例对象。

这里使用到了Constructor类，介绍一下其API:

- getModifiers():获取构造方法的修饰符，private或者public等。

- getParameterTypes()：获取到构造方法中参数的类型
- toString():获取到构造方法中信息
- newInstance(Object ... initargs):传递参数，创建实例化对象

**3. 编写代码如下,进行验证**

```
package com.xingen.classdemo;
public class ClassTest2 {
    private String name;
    private String work;

    /**
     * 构建一个默认的构造方法
     */
    public ClassTest2() {
    }

    /**
     * 构建有参数构造方法
     *
     * @param name
     * @param work
     */
     private ClassTest2(String name, String work) {
        this.name = name;
        this.work = work;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("name: ");
        stringBuffer.append(getName());
        stringBuffer.append("\n");
        stringBuffer.append("work: ");
        stringBuffer.append(getWork());
        return stringBuffer.toString();
    }

    /**
     * 通过Class类的newInstance()创建一个类的实例对象，
     * 该类必须要要有一个默认的构造方法，
     * 反之，会抛出一个 java.lang.InstantiationException
     */
    public static ClassTest2 createDefaultInstance() {
        ClassTest2 test = null;
        try {
            Class<ClassTest2> mClass = ClassTest2.class;
            test = mClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test;
    }

    /**
     * 反射一个带有参数的构造方法，传入指定参数
     * @param name
     * @param work
     * @return
     */
    public static  ClassTest2 createInstance(String name,String work){
        ClassTest2 test = null;
        try {
            Class<ClassTest2> mClass = ClassTest2.class;
            Constructor<ClassTest2> constructor=  mClass.getDeclaredConstructor(String.class,String.class);
            //设置允许访问，防止private修饰的构造方法
            constructor.setAccessible(true);
            test=  constructor.newInstance(name,work);
        }catch (Exception e){
            e.printStackTrace();
        }
        return test;
    }

}

```
调用程序主入口，运行测试。
```
package com.xingen.classdemo;

public class Client {

    public static void main(String[] args) {
        
        useClassInstance();
        
        useClassInstance("xinGen","Java Developer");
    }
    
    /**
     * 使用Class类实例对象：去创建另外一个类的对象
     */
    public  static  void useClassInstance(){
        ClassTest2 test2=ClassTest2.createDefaultInstance();
        test2.setName("xinGen");
        test2.setWork("Android Library Developer");
        System.out.println("使用反射创建一个类的对象 \n"+test2.toString());
    }

    /**
     * 使用Class类实例对象：去创建另外一个类的对象,且传入构造参数
     *
     * @param name
     * @param work
     */
    public static  void useClassInstance(String name,String work){
        ClassTest2 test2=  ClassTest2.createInstance(name,work);
        System.out.println("使用反射创建一个类的对象 \n"+test2.toString());
    }
}
```
控制台输出结果：
```
使用反射创建一个类的对象 
name: xinGen
work: Android Library Developer

使用反射创建一个类的对象 
name: xinGen
work: Java Developer
```


---

**一点思考**：

通过反射可以绕过检查是否被Private修饰，访问到构造方法，因此也可以突破常见的单例模式，因此需要针对性处理。单例模式如何预防反射和反序列化攻击，请找度娘。