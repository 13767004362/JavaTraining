package com.xingen.strategy;

/**
 * author: xinGen
 * date : 2019/7/18
 * blog: https://blog.csdn.net/hexingen
 */
public class JavaWorkStrategy implements WorkStrategy {
    @Override
    public void work() {
        System.out.println(" 使用Java语言 开发工作 ");
    }
}
