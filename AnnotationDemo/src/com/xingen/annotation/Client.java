package com.xingen.annotation;

import com.xingen.annotation.custom.TestCustomAnnotation;
import com.xingen.annotation.deprecated.DeprecatedClass;
import com.xingen.annotation.override.Developer;
import com.xingen.annotation.override.JavaDeveloper;
import com.xingen.annotation.suppresswarnings.SuppressWarningsClass;

public class Client {

    public static void main(String[] args) {
        testOverrideAnnotation();
        testDeprecatedAnnotation();
        testSuppressWarningsAnnotation();
        testCustomAnnotation();
        testTargetAnnotation();
        testInheritedAnnotation();
    }

    /**
     * 测试@Override注解
     */
    public static void testOverrideAnnotation() {
        Developer developer = JavaDeveloper.create();
        developer.work();
    }

    /**
     * 测试@Deprecated注解
     */
    public static void testDeprecatedAnnotation() {
        DeprecatedClass instance = DeprecatedClass.create();
        instance.deprecated();
        System.out.println("输出一个废弃属性 " + instance.DeprecatedField);
    }

    /**
     * 测试@SuppressWarnings注解
     */
    public static void testSuppressWarningsAnnotation() {
        SuppressWarningsClass.testSuppressWarnings();
    }

    /**
     * 测试自定义Annotation注解
     */
    public static void testCustomAnnotation() {
        TestCustomAnnotation.testClassGetAnnotationMessage(TestCustomAnnotation.TAG, TestCustomAnnotation.class);
    }

    /**
     * 测试@Target注解
     */
    public static void testTargetAnnotation() {
        TestCustomAnnotation.testTargetAnnotation();
    }

    /**
     * 测试@Inherited
     */
    public static void testInheritedAnnotation() {
        TestCustomAnnotation.ChildTestCustomAnnotation.testInheritedAnnotation(TestCustomAnnotation.ChildTestCustomAnnotation.TAG, TestCustomAnnotation.ChildTestCustomAnnotation.class);
    }


}
