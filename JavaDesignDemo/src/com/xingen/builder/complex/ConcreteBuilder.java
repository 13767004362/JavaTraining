package com.xingen.builder.complex;

public class ConcreteBuilder implements Buidler {
    private Person person;
    public ConcreteBuilder() {
        this.person=new Person();
    }
    @Override
    public void setName(String name) {
     this.person.setName(name);
    }
    @Override
    public void setAge(int age) {
         this.person.setAge(age);
    }
    @Override
    public Person builder() {
        return this.person;
    }
}
