package com.xingen.sixprinciples;

import java.io.Closeable;
import java.io.PrintStream;

/**
 * author: xinGen
 * date : 2019/7/18
 * blog: https://blog.csdn.net/hexingen
 * <p>
 * 接口隔离原则
 */
public class InterfaceSegregationPrinciple {
    public static void test() {
        PrintStream printStream = IOUtils.createStream();
        final String content = "接口隔离原则：客户端调用，接口最小化";
        try {
            printStream.write(content.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(printStream);
        }
    }

    public static class IOUtils {
        /***
         *  接口最小化的场景：
         *
         *   Closeable是最小接口，只有close关闭方法。
         *
         * @param closeable
         */
        public static void close(Closeable closeable) {
            try {
                if (closeable != null) {
                    closeable.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         *  打印流
         * @return
         */
        public static PrintStream createStream() {
            return System.out;
        }
    }
}
