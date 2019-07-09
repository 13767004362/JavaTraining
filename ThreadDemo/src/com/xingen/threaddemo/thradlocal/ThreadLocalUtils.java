package com.xingen.threaddemo.thradlocal;

/**
 * Created by ${新根} on 2018/12/23.
 * 博客：http://blog.csdn.net/hexingen
 */
public class ThreadLocalUtils {
    private  static final ThreadLocal<DataBean> beanThreadLocal=new ThreadLocal<DataBean>(){
        @Override
        protected DataBean initialValue() {
           DataBean bean= DataBean.newInstance();
           System.out.println(" ThreadLocal "+" initialValue() ,创建bean对象："+bean);
            return  bean;
        }
    };
    public static  final  DataBean getDataBean(){
        return  beanThreadLocal.get();
    }
}
