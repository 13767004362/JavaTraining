package com.xingen.classloader;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by ${新根} on 2018/12/4.
 * 博客：http://blog.csdn.net/hexingen
 */
public class TestClient {
    public static void main(String[] args) {
        testClassLoader();
    }
    private static void testClassLoader() {
        String dir = System.getProperty("user.dir");
        File pluginFile = new File(dir + File.separator + "plugin" + File.separator + "PluginTest.class");
        //额外的PluginTest类的路径
        final String filePath = pluginFile.getAbsolutePath();
        try {
            //创建ClassLoader子类对象
            HotClassLoader hotClassLoader = new HotClassLoader(filePath);
            //包名下的类名
            final String className = "com.xingen.classloader.plugin.PluginTest";
            //将该类加载到JVM
            Class<?> objectClass = hotClassLoader.loadClass(className);
            if (objectClass == null) {
                System.out.println(" 加载plugin中的类失败");
            } else {
                //反射调用额外路径下类中的方法
                Method printMethod = objectClass.getDeclaredMethod("print");
                printMethod.setAccessible(true);
                printMethod.invoke(objectClass.newInstance(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
