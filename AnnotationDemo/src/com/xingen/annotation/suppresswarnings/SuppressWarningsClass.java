package com.xingen.annotation.suppresswarnings;

/**
 *  @SuppressWarnings注解 用来抑制警告
 *
 *  例如：
 *
 *  @SuppressWarnings(value = {"unchecked", "deprecation"})，同时两种，抑制检查和不建议使用的警告
 *
 */
@Deprecated
public class SuppressWarningsClass<T> {
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @SuppressWarnings(value = {"unchecked", "deprecation"})
    public static void testSuppressWarnings() {
        SuppressWarningsClass instance = new SuppressWarningsClass();
        instance.setT("测试@SuppressWarnings注解 ,这里压制 unchecked警告和 deprecation警告 ");
        System.out.println(instance.getT());
    }

}
