package com.xingen.annotation.custom;

import java.lang.annotation.*;

/**
 * 自定义Annotation的结构：
 *
 * public @interface Annotation名字 {
 *
 *     数据类型  变量名();
 *
 * }
 * <p>
 * 理解：
 * <p>
 * 一个自定义的Annotation , 使用@interface注解修饰，相当于继承java.lang.Annotation接口。
 */
@Retention(value = RetentionPolicy.RUNTIME) //指定该Annotation在执行时候起作用，会保留在源文件(.java )和类文件(.class)中，也会加载进入JVM中。
@Inherited                                   //用于注解所在类的子类，可以继承该注解
@Target(value ={ ElementType.TYPE })          //指定Annotation使用范围，这里指定修饰类，接口，枚举中。
public @interface CustomAnnotation {
    /**
     * 定义一个字符串数组类型的属性
     *
     * @return
     */
    String[] value();

    /**
     * 定义了一个变量的默认值，在使用该注解的时候，可以不指定该属性的值。
     *
     * @return
     */
    String key() default "设置key的默认值";

    /**
     * 使用enum枚举来限制Annotation注解中属性类型，设置范围值
     *
     * @return
     */
    CustomEnum enumField() default CustomEnum.yellow;
}
