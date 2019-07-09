
反射的好处（为什么需要反射机制）：

>通过反射机制可以获取到一个类的完整信息，例如：所有（包含private修饰）属性和方法，包信息等。
>
>换句话说，Class本身表示一个类的本身，通过Class可以完整获取一个类中的完整结构，包含此类中的方法定义，属性定义等。

反射的核心概念：

>一切的操作都是讲使用Object完成，类或者数组的引用是可以用Object进行接收。

这里，个人的理解是,对象的多态：`Object object= 任何引用类型的实例对象`

**介绍Class类**
---

在Java中，可以通过Class类获取到任何一个类中完整的信息。

一个很重要的概念：

> 在Java中，Object是所有类的超类，所有类的对象实际上也是java.lang,Class类的实例。  因此，所有的对象都可以转变成Class类型表示。

后面一句话，个人的理解是通过Class<?> 中类型使用`?`占位符表示，可以是任何一种具体的类型，也就是说Class<?>对象通过`newInstance()`可以创建任何一种类型的实例对象。



**Class类常用的API**:

- forName() : 传入完整的"包.类"名称实例化Class对象
- getConstructors() : 得到一个类中的全部构造方法
- getConstructor(Class<?>...parameterTypes)：获取到指定参数类型的(public修饰的)构造方法

- getDeclaredConstructor(Class<?>...parameterTypes)：获取到指定参数类型的构造方法，包含private修饰和public修饰

- getDeclaredFields() : 获得某个类的单独定义全部的字段（即包括public、private和proteced等修饰符的全部属性），但是不包括父类的申明字段或者实现接口中的字段。

- getFields() : 获得某个类的所有的公共（public）的字段，包括父类中的公共字段或者实现接口的公共属性和类本身定义的公共属性。 

- getMethods() : 获取到本类中全部public修饰的方法，包含父类中公共方法和覆盖重写的方法和自己本身定义的公共方法。

- getMethod(String name,Class...parameterType) : 获取到指定名字，指定方法参数类型的公共方法。

- getSuperclass() : 获取到父类的Class

- getInterfaces() : 获取（实现的全部接口对应的）Class数组。

- newInstance() : 实例化Class<?>中定义类型的实例化对象。

- getComponentType() : 获取数组类型的Class.

- isArray() : 判断此Class是否是一个数组。

- getName() : 获取到一个类完整"包.类"名称


**创建Class类对象的方式有三种**：

- `对象.getClass()`方式

- `类名.Class `方式
 
- `Class.forName( 类的包名 )` 方式


**案例实战**

创建一个测试类：

```
package com.xingen.classdemo;

public class ClassTest1 {

    public static ClassTest1 newInstance() {
        return new ClassTest1();
    }

    public static void createClassInstance1() {
        //对象.getClass() 方式获取Class对象
        Class<?> mClass = ClassTest1.newInstance().getClass();
        //输出类所在的包路径
        System.out.println(" 通过对象.getClass（）方式， 反射出类所在的包路径： " + mClass.getName());
    }

    public static void createClassInstance2() {
        //类名.class 方式获取Class对象
        Class<?> mClass = ClassTest1.class;
        //输出类所在的包路径
        System.out.println(" 通过类名.Class 方式，反射出  类所在的包路径： " + mClass.getName());
    }

    public static void createClassInstance3() {
        //Class.forName 方式获取Class对象
        Class<?> mClass = null;
        try {
            mClass = Class.forName("com.xingen.classdemo.ClassTest1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //输出类所在的包路径
        System.out.println(" 通过Class.forName 方式，反射出  类所在的包路径： " + mClass.getName());
    }
}
```
创建`main()`的程序入口的Client类：

```JAVA
package com.xingen.classdemo;

public class Client {
    
    public static void main(String[] args) {
      createClassInstance();
    }
    
       /**
     * 创建Class类实例对象的三种方式
     */
    private static void createClassInstance() {
        ClassTest1.createClassInstance1();
        ClassTest1.createClassInstance2();
        ClassTest1.createClassInstance3();

    }
}

```

运行结果如下 , 控制台输出以下日志：
```
 
 通过对象.getClass（）方式， 反射出类所在的包路径： com.xingen.classdemo.ClassTest1

 通过类名.Class 方式，反射出  类所在的包路径： com.xingen.classdemo.ClassTest1
 
 通过Class.forName 方式，反射出  类所在的包路径： com.xingen.classdemo.ClassTest1
 
```



