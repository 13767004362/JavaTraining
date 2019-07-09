

前面介绍了，反射调用类的构造方法来创建类的实例对象。一个类的结构包含方法(构造，静态,非静态)和属性（静态和非静态）。按照循环渐进的方式，接下来，介绍反射类中属性和普通的方法。



在这里简单介绍，反射调用属性和方法会用到的新类，Method类和Field类。

**Method类的常用API**：

- getModifiers() : 获取方法的修饰符
- getName() : 获取到方法的名称
- getParameterTypes() : 获取到方法的全部参数类型
- getReturnType() : 获取到方法的返回值类型
- getException():获取方法中全部抛出的异常
- invoke(Object obj,Object ... args):反射调用类中的方法

**Field类的常用API**:

- get(Object obj):获取到属性的具体内容

- set(Object ,Object value):设置指定属性的具体内容

- getModifiers() : 获取属性的修饰符

- isAccessible():判断属性是否可以被外部访问

- setAccessible(boolean flag):设置这属性可以被外部访问。


**案例实战**

---

**1. 定义一个接口**：

定义一些行为，作为抽象方法。用于测试，反射调用实现类（覆盖重写的）方法。
```
package com.xingen.classdemo;

public interface ClassTestInterface {
    void testMethod(String name,String work);
}
```

**2. 构建一个父类**：

添加一些属性，进行封装。用于测试，反射调用父类私有属性。

```
package com.xingen.classdemo;

import java.lang.reflect.Constructor;

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
    public ClassTest2(String name, String work) {
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

}
```

**3. 创建一个子类**：

该子类的作用有以下几点：

- 继承父类

- 实现若干接口，覆盖重写抽象方法，例如：` testMethod(String name, String work) `

- 定义自己本身的私有属性，例如：`age`字段

```
package com.xingen.classdemo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassTest3 extends ClassTest2 implements ClassTestInterface {

    private int age;

    public ClassTest3() {
    }

    @Override
    public void testMethod(String name, String work) {
        this.setName(name);
        this.setWork(work);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (getName() != null) {
            stringBuffer.append("name: ");
            stringBuffer.append(getName());
            stringBuffer.append("\n");
        }
        if (getWork() != null) {
            stringBuffer.append("work: ");
            stringBuffer.append(getWork());
            stringBuffer.append("\n");
        }
        if (getAge() > 0) {
            stringBuffer.append("age: ");
            stringBuffer.append(getAge());
        }
        return stringBuffer.toString();
    }
    
}

```
**4. 各种常用的反射场景**：

**4.1 案例之反射获取父类的信息**：

先获取到Class对象，然后调用`getSuperclass()`获取到父类的Class对象。
众所众知，单继承，多实现，因此父类只有一个。这里，输出父类所属于的包信息。

```
    /**
     * 获取父类的信息
     */
    public static void testSuperClass() {
        try {
            Class<?> mClass = ClassTest3.class;
            //获取继承的父类
            Class<?> superClass = mClass.getSuperclass();
            System.out.println("获取继承父类的包路径：\n" + superClass.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
输出结果是：
```
获取继承父类的包路径：
com.xingen.classdemo.ClassTest2
```

**4.2 案例之反射获取实现接口的信息**：

先获取到Class对象，然后调用`getInterfaces()`获取到全部实现接口的Class数组。因ClassTest3实现了一个接口，所以这里的数组索引值是0。接口的Class对象可以获取到接口中的完整信息，这里输出接口所属于的包信息。

```
    /**
     * 获取实现接口的信息
     */
    public static void testInterface() {
        try {
            Class<?> mClass = ClassTest3.class;
            /**
             * 获得全部实现的接口：Class<?>[] getInterfaces()得到的数组中，接口对象顺序和这个对象所表示的类中implements子句中接口名的顺序，是一致的。
             */
            Class<?>[] interfaceArray = mClass.getInterfaces();
            Class<?> interfaces = interfaceArray[0];
            System.out.println("获取实现接口的包路径： \n" + interfaces.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
输出结果是：
```
获取实现接口的包路径： 
com.xingen.classdemo.ClassTestInterface
```
**4.3 案例之反射调用覆盖重写的方法**

先获取Class对象，然后通过`getMethod() `获取到指定的需要调用的方法。接下来，处理是否需要添加访问权限`setAccessible(true)`，最后通过`invoke()`进行方法调用。
```
    /**
     * 测试覆盖重写的方法
     */
    public static void testSuperMethod() {
        try {
            Class<ClassTest3> mClass = ClassTest3.class;
            ClassTest3 instance = mClass.newInstance();
            Method method = mClass.getMethod("testMethod", String.class, String.class);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            method.invoke(instance, "xinGen", "Android Lirary Developer");
            System.out.println("反射访问覆盖重写的方法：\n " + instance.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
输出结果是：
```
反射访问覆盖重写的方法：
name: xinGen
work: Android Lirary Developer
```


**4.4 案例之反射调用本类定义的私有属性**

先获取到Class对象，然后通过`getDeclaredField()`获取到本类定义的私有属性，再调用`setAccessible()`赋予访问权限，最后调用`set()`对私有属性内容修改。

```
     /**
     * 测试调用本身定义的私有属性
     */
    public static void testSelfField() {
        try {
            Class<ClassTest3> mClass = ClassTest3.class;
            ClassTest3 instance = mClass.newInstance();
            Field field = mClass.getDeclaredField("age");
            field.setAccessible(true);
            field.set(instance, 24);
            System.out.println("反射访问本类中Private修饰的属性：\n " + instance.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
输出结果是：
```
反射访问本类中Private修饰的属性：
age: 24
```

**4.5 案例之反射调用父类中私有属性**

先获取到Class对象，然后通过`getSuperclass()`获取到父类的Class对象，再调用`getDeclaredField()`获取到父类的私有属性，最后调用`set()`，对私有属性进行内容修改。这里，最后输出修改后的内容。
```
  /**
     * 测试调用父类私有属性
     */
    public static void testSuperField() {
        try {
            Class<ClassTest3> mClass = ClassTest3.class;
            ClassTest3 instance = mClass.newInstance();
            Field field = mClass.getSuperclass().getDeclaredField("name");
            field.setAccessible(true);
            field.set(instance, "xinGen");
            System.out.println("反射访问父类中Private修饰的属性：\n " + instance.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
输出结果是：
```
反射访问父类中Private修饰的属性：
name: xinGen
```

**5. 运行`main()`入口程序**

```
package com.xingen.classdemo;

public class Client {

    public static void main(String[] args) {
        useClassFieldAndMethod();
    }
    /**
     * 使用Class类：读写属性和方法
     */
    public static void useClassFieldAndMethod() {
        ClassTest3.testInterface();
        ClassTest3.testSuperClass();
        ClassTest3.testSuperMethod();
        ClassTest3.testSelfField();
        ClassTest3.testSuperField();
    }
}
```
---

**反射调用属性的归纳总结**：

1. 先判断该属性是否public修饰
2. 若是，则使用getField()获取。
3. 反之，则判断是否是类本身单独定义属性。
4. 若是，则使用getDeclaredField（）去获取。
5. 反之，则先通过getSuperclass()获取到父类的Class对象，再去调用getDeclaredField()去获取。

同理，反射调用方法和调用属性的思路类似。

