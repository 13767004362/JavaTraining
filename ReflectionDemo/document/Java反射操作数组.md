
引用类型包含类，接口，数组，前面介绍了两者，接下来介绍反射数组。

反射对数组操作，是通过Array类来完成的，这里对该类简单介绍。

**Array类常用API**:

- get(Object array,int index):获取数组中指定位置的内容。

- newInstance(Class<?> componenType,int length):根据指定类型和指定长度，开辟一个新的数组

- set(Object array,int index,Object value):修改数组中指定位置的内容


**案例实战**
---

**需求**：

创建一个数组，通过反射获取数组的类型，然后创建一个新长度的新数组，拷贝旧数组的内容。

**思路如下**：

- 先创建一个具体类型的数组

- 通过`数组对象.getClass().getComponentType()`获取到数组对应的Class对象。

- 反射获取数组的信息，例如，通过` Array.getLength()`获取数组长度。

- 接下来，通过` Array.newInstance(Class<?> componentType, int length)`创建新长度的新数组。

- 最后,通过`System.arraycopy(Object src,  int  srcPos,Object dest, int destPos, int length)`拷贝旧数组的内容。

接下来，开始编码实战。

**1. 创建一个实体类**

```
package com.xingen.classdemo;

import java.lang.reflect.Array;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 *
 * 代码演示：反射数组，利用Array来创建新数组和获取指定位置的数据。
 */
public class ClassTest4 {

    private static final String MESSAGE = "反射访问数组";
    private String msg;

    public ClassTest4() {
        this.setMsg(MESSAGE);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static ClassTest4 newInstance() {
        return new ClassTest4();
    }

   
}

```
**2. 反射数组的操作**
```
 public static void testArray() {
        try {
            ClassTest4[] instanceArray = new ClassTest4[]{ClassTest4.newInstance()};
            //获取数组的Class对象
            Class<?> arrayClass = instanceArray.getClass().getComponentType();
            System.out.println("反射数组的类型 " + arrayClass.getName() + " 数组长度：" + Array.getLength(instanceArray) + " 第一个数组实体内容是： "+((ClassTest4)Array.get(instanceArray,0)).getMsg());
            
            //开辟新的数组
            ClassTest4[] newInstanceArray = (ClassTest4[]) Array.newInstance(arrayClass, instanceArray.length + 1);
            Class<?> newArrayClass = newInstanceArray.getClass().getComponentType();
            System.out.println("反射新数组的类型 " + newArrayClass.getName() + " 数组长度：" + Array.getLength(newInstanceArray));
           
            //拷贝数组内容
            System.arraycopy(instanceArray, 0, newInstanceArray, 0, Array.getLength(instanceArray)); 
            System.out.println("将旧的数组内容拷贝到新数组中， 第一个数组实体内容是： "+((ClassTest4)Array.get(newInstanceArray,0)).getMsg());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

**3. 通过程序`main()`入口，运行程序**：
```
public class Client {

    public static void main(String[] args) {
        useClassArray();
    }
    
    /**
     * 使用Array反射操作数组
     */
    public static void useClassArray() {
        ClassTest4.testArray();
    }
}
    
```
**4. 控制台输出结果**：
```
反射数组的类型 com.xingen.classdemo.ClassTest4 数组长度：1 第一个数组实体内容是： 反射访问数组

反射新数组的类型 com.xingen.classdemo.ClassTest4 数组长度：2

将旧的数组内容拷贝到新数组中， 第一个数组实体内容是： 反射访问数组
```

