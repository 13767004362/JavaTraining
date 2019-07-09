package com.xingen.classdemo.genericity;

import java.lang.reflect.Type;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 *
 * 通过Type获取对象class
 */
public class TypeUtils {
    private static final String TYPE_NAME_PREFIX = "class ";

    public static String getClassName(Type type) {
        if (type==null) {
            return "";
        }
        String className = type.toString();
        if (className.startsWith(TYPE_NAME_PREFIX)) {
            className = className.substring(TYPE_NAME_PREFIX.length());
        }
        return className;
    }

    public static Class<?> getClass(Type type)
            throws ClassNotFoundException {
        String className = getClassName(type);
        if (className==null || className.isEmpty()) {
            return null;
        }
        return Class.forName(className);
    }
}
