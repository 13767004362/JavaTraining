package com.xingen.classdemo.genericity;

public abstract class GenericityBaseClass<R> {

    protected  Class<?> rClass;

    abstract   R increase(R r);

    public Class<?> getRClass() {
        return rClass;
    }
}
