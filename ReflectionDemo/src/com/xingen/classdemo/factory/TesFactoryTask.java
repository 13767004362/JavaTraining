package com.xingen.classdemo.factory;

import com.xingen.classdemo.Client;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class TesFactoryTask implements Runnable {

    @Override
    public void run() {
        InputStream inputStream = null;
        try {
            // 单个/代表当前目录下,详情参考：http://riddickbryant.iteye.com/blog/436693
            String filePath = "/load.properties";
            Properties properties = new Properties();
            inputStream = Client.class.getResourceAsStream(filePath);
            //加载属性文件
            properties.load(inputStream);
            //根据属性文件进行，创建实例
            Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String values = properties.getProperty(key);
                Worker worker = Factory.newInstance(values);
                worker.work();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void test(){
        new Thread(new TesFactoryTask()).start();
    }
}
