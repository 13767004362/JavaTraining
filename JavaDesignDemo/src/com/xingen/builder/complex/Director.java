package com.xingen.builder.complex;

public class Director {

    public Person construct(Buidler buidler,String name,int age){
        buidler.setAge(age);
         buidler.setName(name);
         return buidler.builder();
    }
}
