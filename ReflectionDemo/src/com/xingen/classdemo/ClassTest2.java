package com.xingen.classdemo;

import java.lang.reflect.Constructor;

/**
 * Created by ${新根} on 2018/2/14 0014.
 * 博客：http://blog.csdn.net/hexingen
 * <p>
 * <p>
 * 代码演示：通过反射调用无参数构造方法和有参数构造器
 */
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
     *
     * @param name
     * @param work
     * @return
     */
   public static ClassTest2 createInstance(String name, String work) {
        ClassTest2 test = null;
        try {
            Class<ClassTest2> mClass = ClassTest2.class;
            Constructor<ClassTest2> constructor = mClass.getDeclaredConstructor(String.class, String.class);
            constructor.setAccessible(true);
            test = constructor.newInstance(name, work);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return test;
    }
}
