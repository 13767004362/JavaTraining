package com.xingen.sixprinciples;

/**
 * author: xinGen
 * date : 2019/7/18
 * blog: https://blog.csdn.net/hexingen
 * <p>
 * 里氏替换
 */
public class RichterReplacementPrinciples {

    public  static void test(){
        ITCompany company=new ITCompany();
        company.work(new JavaDeveloper());
        company.work(new AndroidDeveloper());
    }
    /**
     *  定义一个抽象类,用于描述开发岗位，职责和工作产量。
     */
    public static abstract class Developer {
        abstract String develop();
        public void work() {
              System.out.println(develop());
        }
    }

    /**
     * 定义Java后台开发岗位
     */
    public static  class  JavaDeveloper extends Developer{
        @Override
        String develop() {
            return "Java 后台开发，部署服务器上";
        }
    }

    /**
     *  定义android开发岗位
     */
    public static class  AndroidDeveloper extends Developer{
        @Override
        String develop() {
            return "Android 客户端开发,运行在Android设备上";
        }
    }
    /**
     *  定义一个公司的角色
     */
    public static class ITCompany{
        public  void work(Developer developer){
              // 自由切换子类，并不会影响
             developer.work();
        }
    }


}
