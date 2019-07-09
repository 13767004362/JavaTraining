package com.xingen.classdemo.proxy;

/**
 * Created by ${新根} on 2018/2/16 0016.
 * 博客：http://blog.csdn.net/hexingen
 *
 *  真实的被代理者，案例演示：海外购
 */
public class ForeignProduct implements Product{
    @Override
    public String buy(double money) {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("花费");
        stringBuffer.append(money);
        stringBuffer.append("美元代购国外产品");
        return stringBuffer.toString();
    }
    public static ForeignProduct newInstance(){
        return new ForeignProduct();
    }
}
