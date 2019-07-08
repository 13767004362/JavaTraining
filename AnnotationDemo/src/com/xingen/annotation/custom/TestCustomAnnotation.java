package com.xingen.annotation.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@CustomAnnotation(value = {"测试自定义Annotation,且传递一个数组值给它"}, enumField = CustomEnum.red)    //使用自定义的Annotation
public class TestCustomAnnotation {

    public static final String TAG = TestCustomAnnotation.class.getSimpleName();

    /**
     * 反射获取Annotation中完整信息
     */
    public static void testClassGetAnnotationMessage(String tag, Class<?> mClass) {
        try {
            //获取类上修饰的全部Annotation
       /*     Annotation[] annotationArray = mClass.getAnnotations();
            for (Annotation annotation : annotationArray) {
                System.out.println(tag + " 反射获取到本类中修饰全部的Annotation ,for循环输出类中使用的注解 " + annotation);
            }*/

            //获取指定的Annotation
            if (mClass.isAnnotationPresent(CustomAnnotation.class)) {
                CustomAnnotation customAnnotation = mClass.getAnnotation(CustomAnnotation.class);
                String value = customAnnotation.value()[0];
                String key = customAnnotation.key();
                CustomEnum enumField = customAnnotation.enumField();
                System.out.println(tag + " 反射获取到指定的Annotation ,输出 CustomAnnotation 注解 中定义的属性：" + " value = " + value + " ，key = " + key + " ,enumField = " + enumField);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testTargetAnnotation() {
        try {
            Class<CustomAnnotation> mClass = CustomAnnotation.class;
     /*       Annotation[] annotationArray = mClass.getAnnotations();
            for (Annotation annotation : annotationArray) {
                System.out.println(" 反射获取到@CustomAnnotation中修饰全部的Annotation ,for循环输出类中使用的注解 " + annotation);
            }*/
            //获取指定的Annotation
            if (mClass.isAnnotationPresent(Target.class)) {
                Target target = mClass.getAnnotation(Target.class);
                ElementType[] elementTypeArray = target.value();
                System.out.println("反射获取到@CustomAnnotation中修饰的target " + elementTypeArray[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 定义一个子类，用于测试@Inherited注解
     */
    public static class ChildTestCustomAnnotation extends TestCustomAnnotation {
        public static final String TAG = ChildTestCustomAnnotation.class.getSimpleName();

        public static void testInheritedAnnotation(String tag, Class<?> mClasss) {
            TestCustomAnnotation.testClassGetAnnotationMessage(tag, mClasss);
        }
    }


}
