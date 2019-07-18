package com.xingen.sixprinciples;

import com.sun.org.apache.xpath.internal.operations.And;

/**
 * author: xinGen
 * date : 2019/7/18
 * blog: https://blog.csdn.net/hexingen
 * <p>
 * 依赖颠倒原则
 */
public class DependenceInversionPrinciple {


    public  static void test(){
        ITCompany company=new ITCompany();
        // 顾客先下单，后台系统
        company.buyType(Developer.java).product();
        // 顾客继续下单，做客户端
        company.buyType(Developer.android).product();
    }
    /**
     * 定义一个it公司角色
     */
    public static class ITCompany {
        // 调用者，依赖抽象，不依赖实现类。
        private Developer developer;
        public ITCompany buyType(int type) {
            switch (type) {
                case Developer.android:
                    this.developer = new AndroidDeveloper();
                    break;
                case Developer.java:
                    this.developer = new JavaDeveloper();
                    break;
            }
            return this;
        }
        public void setDeveloper(Developer developer) {
            this.developer = developer;
        }

        public void product() {
            if (developer==null)return;
            this.developer.develop();
        }
    }

    /**
     * 定义一个开发色的接口
     */
    public interface Developer {

        void develop();

        int android = 1, java = 2;
    }

    /**
     * 定义后台开发角色，开发后台系统
     */
    public static class JavaDeveloper implements Developer {
        @Override
        public void develop() {
            System.out.println("Java软件工程师接到需求，开发后台系统");
        }
    }

    /**
     *  定义Android开发角色，做客户端
     */
    public static class AndroidDeveloper implements Developer {
        @Override
        public void develop() {
            System.out.println("Android软件工程师接到需求，开发客户端");
        }
    }


}
