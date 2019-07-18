package com.xingen;

import com.xingen.builder.complex.Buidler;
import com.xingen.builder.complex.ConcreteBuilder;
import com.xingen.builder.complex.Director;
import com.xingen.builder.complex.Person;
import com.xingen.builder.concise.Worker;
import com.xingen.strategy.JavaWorkStrategy;
import com.xingen.strategy.Worker1;

public class Client {
    public static void main(String[] args) {

           testStrategy();
    }

    /**
     * 测试策略模式
     */
    private static void testStrategy(){
       Worker1 worker= Worker1.create();
       // 自由切换不同的策略
       worker.setWorkStrategy(new JavaWorkStrategy());
       worker.work();
    }


    /**
     * 测试Builder模式
     */
    private static void testBuilder(){
        System.out.println("<--------------完整的构建者模式-------------->");
        Buidler buidler = new ConcreteBuilder();
        Director director = new Director();
        Person person = director.construct(buidler, "新根", 23);
        System.out.println(person.toString());
        System.out.println("<-------------简洁的构建者模式--------------->");
        Worker.Builder workBuilder = new Worker.Builder();
        Worker worker = workBuilder.setName("xinGen").setWorkName("Android Developer").builder();
        System.out.println(worker.toString());
    }
}
