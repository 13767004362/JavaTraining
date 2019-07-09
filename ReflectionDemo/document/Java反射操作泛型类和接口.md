

### **先了解下Type接口以及4个子接口和一个实现类**


Type接口：

>是所有类型的父接口，它有4个子接口和一个实现类。


### **Class类**

用途：表示原始类型。


### **ParameterizedType接口**：

用途： 用于表示参数化类型,例如：List<String>这种带有泛型的类型。

1. 获取泛型父类
```
  Class<?> mClass;
  Type[] types = mClass.getGenericSuperclass();
  ParameterizedType parameterizedType=(ParameterizedType) types[0];
```

2. 获取泛型接口

```
  Class<?> mClass;
  Type[] types = mClass.getGenericInterfaces();
  ParameterizedType parameterizedType=(ParameterizedType) types[0];
```


**常用的3个方法**：

- `Type[] getActualTypeArguments()` :  当前泛型表达式中，用来填充泛型变量<T,R,K>的列表。例如List<String>的泛型变量是String。 

- `Type getRawType()`   :  声明当前泛型表达式的类或者接口的Class对象。例如List<String>的原始类型为List。 

- `getOwerType()` : 返回的是类型所属的类型，例如存在A<T>，其中定义了内部类InnerA<T>，则InnerA<T>所属的类型是A<T>，如果是顶层类型则返回null。

### **TypeVariable接口**

用途：表示类型变量,例如List<T>中的T就是类型变量。

常用方法：

- `Type[] getBounds()`:获取类型变量的上边界。若未声明,默认为Object。例如List<T extends Number>中T的上边界就是Number。

- `D getGenericDeclaration()` :获取声明该类型变量的原始类型。例如List<T>中原始类型是List。

### GenericArrayType接口

用途：表示数组类型的类型。例如,Integer[]。

常用方法：

- `Type getGenericComponentType()` : 获取数组的组成元素类型。


###  **WildcardType接口**

**用途**：用于表示通配符类型,例如List<? extends Number> ,这里的`? extends Number`是通配符类型。

常用的2个方法

- `Type[] getUpperBounds()` : 返回类型变量的上边界。
- `Type[] getLowerBounds()` : 返回类型变量的下边界。

**资源参考**：

- https://blog.csdn.net/harvic880925/article/details/50085595
- https://blog.csdn.net/u011983531/article/details/80295479


**实战案例**
---

通过反射获取到抽象类或者接口中泛型信息的操作也是很常见的。实际上开发中，解析后台数据的Json数据，生成对应的泛型实体类，会用到反射获取泛型信息的操作。


**大致思路**:

- `getGenericInterfaces()`获取到泛型接口的Type数组。
- `getGenericSuperclass()`:获取泛型父类的Tyoe
- `getActualTypeArguments()`获取到泛型接口的类型变量。


**1. 定义一个泛型的接口**：
```
package com.xingen.classdemo.genericity;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 *
 * 参考：https://www.cnblogs.com/whitewolf/p/4355541.html
 */
public interface GenericityInterface<K,T> {

    T doThing(K k);

}
```
**2. 定义一个泛型父类**：
```
public abstract class GenericityBaseClass<R> {

    protected  Class<?> rClass;

    abstract   R increase(R r);

    public Class<?> getRClass() {
        return rClass;
    }
}
```

**3. 定义一个实现类，用于获取指定具体的类型**

```
package com.xingen.classdemo.genericity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 * <p>
 * 反射获取泛型信息
 */
public class GenericityInterfaceImp extends GenericityBaseClass<Integer> implements GenericityInterface<String, Bean> {
    //泛型中的两个类型
    private Class<?> kClass, tClass;

    public GenericityInterfaceImp() {
        getGenericityMessage(GenericityInterfaceImp.class);
    }

    @Override
    Integer increase(Integer integer) {
        return integer + integer;
    }

    @Override
    public Bean doThing(String s) {
        return new Bean(s);
    }

    public void getGenericityMessage(Class<?> mClass) {
        parseGenericityClass(mClass);
        parseGenericityInerface(mClass);
    }

    private void parseGenericityClass(Class<?> mClass) {
        try {
            // 获取泛型类
            Type intefaceType = mClass.getGenericSuperclass();
            if (intefaceType == null) return;
            if (!(intefaceType instanceof ParameterizedType)) {
                return;
            }
            //获取到实际的泛型中参数类型
            Type[] parameterizedType = ((ParameterizedType) intefaceType).getActualTypeArguments();
            rClass = TypeUtils.getClass(parameterizedType[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseGenericityInerface(Class<?> mClass) {
        try {
            //获取泛型接口
            Type[] intefaceTypeArray = mClass.getGenericInterfaces();
            if (intefaceTypeArray == null && intefaceTypeArray.length == 0) {
                return;
            }
            //获取实现的第一个接口类型
            Type type = intefaceTypeArray[0];
            if (!(type instanceof ParameterizedType)) {
                //这是Object类型
                return;
            }
            //获取到实际的泛型中参数类型
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            kClass = TypeUtils.getClass(parameterizedType[0]);
            tClass = TypeUtils.getClass(parameterizedType[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Class<?> getkClass() {
        return kClass;
    }

    public Class<?> gettClass() {
        return tClass;
    }

    public static GenericityInterfaceImp newInstance() {
        return new GenericityInterfaceImp();
    }


}


```
**3. 定义一个工具类**：用于Type和Class之间的转换。
```
package com.xingen.classdemo.genericity;

import java.lang.reflect.Type;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 *
 * 通过Type获取对象class
 */
public class TypeUtils {
    private static final String TYPE_NAME_PREFIX = "class ";

    public static String getClassName(Type type) {
        if (type==null) {
            return "";
        }
        String className = type.toString();
        if (className.startsWith(TYPE_NAME_PREFIX)) {
            className = className.substring(TYPE_NAME_PREFIX.length());
        }
        return className;
    }

    public static Class<?> getClass(Type type)
            throws ClassNotFoundException {
        String className = getClassName(type);
        if (className==null || className.isEmpty()) {
            return null;
        }
        return Class.forName(className);
    }
}
```
**4. 定义一个实体类**：用于指定泛型中实际类型
```
public class Bean {
    private String work;

    public Bean() {
    }

    public Bean(String work) {
        this.work = work;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}
```

**5. 程序入口`main()`,运行**:
```
public class Client {

    public static void main(String[] args) {
        useGenericity();
    }
    
    /**
     * 反射泛型信息
     */
    public static  void useGenericity(){
        GenericityInterfaceImp imp=GenericityInterfaceImp.newInstance();
        System.out.println("反射接口中泛型参数： K是 "+imp.getkClass().getName()+" T 是："+imp.gettClass().getName()+" R 是："+imp.getRClass());
    }
}
```
**6. 运行结果如下**：
```
反射接口中泛型参数： K是 java.lang.String T 是：com.xingen.classdemo.genericity.Bean R 是：class java.lang.Integer

```


