package com.xingen.classdemo.genericity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 * <p>
 * 反射获取泛型信息
 */
public class GenericityInterfaceImp extends GenericityBaseClass<Integer> implements GenericityInterface<String, Bean> {
    //泛型中的两个类型
    private Class<?> kClass, tClass;

    public GenericityInterfaceImp() {
        getGenericityMessage(GenericityInterfaceImp.class);
    }

    @Override
    Integer increase(Integer integer) {
        return integer + integer;
    }

    @Override
    public Bean doThing(String s) {
        return new Bean(s);
    }

    public void getGenericityMessage(Class<?> mClass) {
        parseGenericityClass(mClass);
        parseGenericityInerface(mClass);
    }

    private void parseGenericityClass(Class<?> mClass) {
        try {
            // 获取泛型类
            Type intefaceType = mClass.getGenericSuperclass();
            if (intefaceType == null) return;
            if (!(intefaceType instanceof ParameterizedType)) {
                return;
            }
            //获取到实际的泛型中参数类型
            Type[] parameterizedType = ((ParameterizedType) intefaceType).getActualTypeArguments();
            rClass = TypeUtils.getClass(parameterizedType[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseGenericityInerface(Class<?> mClass) {
        try {
            //获取泛型接口
            Type[] intefaceTypeArray = mClass.getGenericInterfaces();
            if (intefaceTypeArray == null && intefaceTypeArray.length == 0) {
                return;
            }
            //获取实现的第一个接口类型
            Type type = intefaceTypeArray[0];
            if (!(type instanceof ParameterizedType)) {
                //这是Object类型
                return;
            }
            //获取到实际的泛型中参数类型
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            kClass = TypeUtils.getClass(parameterizedType[0]);
            tClass = TypeUtils.getClass(parameterizedType[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Class<?> getkClass() {
        return kClass;
    }

    public Class<?> gettClass() {
        return tClass;
    }

    public static GenericityInterfaceImp newInstance() {
        return new GenericityInterfaceImp();
    }


}
