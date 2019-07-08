package com.xingen.classloader;

import sun.plugin2.applet.Applet2ClassLoader;

import java.io.File;

/**
 * Created by ${新根} on 2018/12/4.
 * 博客：http://blog.csdn.net/hexingen
 * <p>
 * 自定义ClassLoader
 * <p>
 * 1.继续ClassLoader
 * 2.重写findClass（）
 */
public class HotClassLoader extends ClassLoader {
    private static final ClassLoader ParentClassLoader = ClassLoader.getSystemClassLoader();
    private String filePath;

    public HotClassLoader(String filePath) {
        super(ParentClassLoader);
        this.filePath = filePath;
    }

    /**
     * 重写父类的findClass（）
     * <p>
     * loadClass()中已经实现搜索类的算法，
     * 当loadClass()搜索不到就会调用findClass()
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("HotClassLoader 执行 findClass() ");
        byte[] bytes = StreamUtils.fileToByteArray(new File(filePath));
        //将byte生成Class对象
        Class<?> mClass = defineClass(name, bytes, 0, bytes.length);
        return mClass;
    }
}
