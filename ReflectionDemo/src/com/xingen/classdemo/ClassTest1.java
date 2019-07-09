package com.xingen.classdemo;

/**
 * Created by ${新根} on 2018/2/14 0014.
 * 博客：http://blog.csdn.net/hexingen
 * <p>
 * <p>
 *
 *  代码演示：三种方式构建Class类对象
 *
 * 在Java中，Object是所有类的超类，所有类的对象实际上也是java.lang,Class类的实例
 * <p>
 * 因此，所有的对象都可以转变成Class类型表示。
 */
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
