package com.xingen.genericdemo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * author: xinGen
 * date : 2019/7/12
 * blog: https://blog.csdn.net/hexingen
 */
public class GenericDemo4 {


    /**
     *  使用 <? extends  Developer> 通配符，作为上限 。
     * @param developerList  传入参数必须是 Developer 和及其子类
     */
    private static void print1(List<? extends Developer> developerList){
        Iterator<? extends Developer>  iterator=developerList.iterator();
        while (iterator.hasNext()){
            // 明确超父类是Developer,可以多态方式接收Developer子类对象。
            Developer developer=   iterator.next();
            System.out.println ( "通配符上限测试："+developer.work());
        }
    }

    /**
     * 使用 <? super AndroidDeveloper> 通配符，作为下限 。
     * @param developerList AndroidDeveloper和及其父类
     */
    public static void print2(List<? super AndroidDeveloper> developerList){
        Iterator<? super AndroidDeveloper>  iterator=developerList.iterator();
        while (iterator.hasNext()){
              // 因不缺定具体的父类，只能有最原始父类Object去多态接收。
               Object object= iterator.next();
               // 向下强制类型转换,指定父类对象去接受。
               if (object instanceof Developer){
                  System.out.println ( "通配符下限测试："+((Developer) object).work());
               }
        }
    }

    /**
     *  测试上限
     */
    public static void test3() {
        List< JavaDeveloper> developerList1 = new ArrayList<>();
        developerList1.add(Developer.createJavaDeveloper());
        developerList1.add(Developer.createAndroidDeveloper());
        List< CDeveloper> developerList2 = new ArrayList<>();
        developerList2.add(Developer.createCDeveloper());
        print1(developerList1);
        print1(developerList2);
    }

    /**
     * 测试下限
     */
    public static void test4(){
        List<Developer> developerList1 = new ArrayList<>();
        developerList1.add(Developer.createJavaDeveloper());
        developerList1.add(Developer.createAndroidDeveloper());
        print2(developerList1);
    }



    public interface Developer {
        String  work();
        static JavaDeveloper createJavaDeveloper() {
            return new JavaDeveloper();
        }

        static CDeveloper createCDeveloper() {
            return new CDeveloper();
        }
        static  AndroidDeveloper createAndroidDeveloper(){
            return new AndroidDeveloper();
        }
    }

    public static class JavaDeveloper implements Developer {
        @Override
        public String work() {
            return "Java程序，使用Java语言开发";
        }
    }
    public static class AndroidDeveloper extends JavaDeveloper{
        @Override
        public String work() {
            return "Android程序，使用Java语言开发";
        }
    }
    public static class CDeveloper implements Developer {
        @Override
        public String work() {
            return  "C程序，使用C语言开发";
        }
    }
}
