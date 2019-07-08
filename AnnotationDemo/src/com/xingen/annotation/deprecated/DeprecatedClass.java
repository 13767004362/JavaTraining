package com.xingen.annotation.deprecated;

import com.sun.tracing.dtrace.DependencyClass;

/**
 * @Deprecated注解 可以修饰在属性，类，方法中，
 * 用于声明不建议使用，但仍然可以使用。
 *
 */
@Deprecated
public class DeprecatedClass {
    @Deprecated
    public  final  String DeprecatedField="DeprecatedField";

    @Deprecated
    public  void deprecated(){
        System.out.println("调用的是一个废弃方法");
    }
    public static  DeprecatedClass create(){
        return new DeprecatedClass();
    }
}
