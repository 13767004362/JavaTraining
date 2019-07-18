package com.xingen.sixprinciples;

import javafx.util.Pair;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

/**
 * author: xinGen
 * date : 2019/7/18
 * blog: https://blog.csdn.net/hexingen
 * <p>
 * 迪米特原则： 只与直接朋友通讯，避免耦合度
 */
public class LeastKnowledgePrinciple {

    public  static void test(){
        // 面试者角色，定下目标薪资，从网站上筛选
        RecruitmentWeb recruitmentWeb=new RecruitmentWeb();
        double targetPrice=12000;
        recruitmentWeb.filter(targetPrice);
    }
    /**
     *  招聘公司的信息
     */
    public static class CompanyBean {
        private Interval interval;
        private String name;
        public CompanyBean setInterval(double start,double end) {
            this.interval = new Interval();
            this.interval.start=start;
            this.interval.end=end;
            return this;
        }
        public String getName() {
            return name;
        }
        public CompanyBean setName(String name) {
            this.name = name;
            return this;
        }
    }
    /**
     * 用于记录工资区间
     */
    public static class Interval {
        public double start, end;
    }
    /**
     *  招聘网站
     */
    public static class RecruitmentWeb{
        private List<CompanyBean> companyList=new ArrayList<>();
        public RecruitmentWeb() {
            for (int i=0;i<3;++i){
                companyList.add(new CompanyBean().setInterval(i*5000.1,(i+1)*5000).setName("公司"+(i+1)));
            }
        }

        /**
         * 避免调用者与CompanyBean 直接操作，避免耦合
         *
         * @param price
         */
        public void filter(double price){
            for (CompanyBean bean:companyList){
                if (isSuitable(bean,price)){
                    System.out.println("期望薪资"+price+",符合的公司是"+bean.name);
                    break;
                }
            }
        }
        private  boolean isSuitable(CompanyBean companyBean, double price){
               return  (price>companyBean.interval.start)&&(price<=companyBean.interval.end);
        }
    }


}
