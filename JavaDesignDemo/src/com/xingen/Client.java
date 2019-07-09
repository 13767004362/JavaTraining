package com.xingen;

import com.xingen.builder.complex.Buidler;
import com.xingen.builder.complex.ConcreteBuilder;
import com.xingen.builder.complex.Director;
import com.xingen.builder.complex.Person;
import com.xingen.builder.concise.Worker;

public class Client {
    public static void main(String[] args) {

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
