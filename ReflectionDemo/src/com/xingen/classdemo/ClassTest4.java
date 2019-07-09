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
}
