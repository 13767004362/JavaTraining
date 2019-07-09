package com.xingen.builder.concise;

/**
 * 简洁的构建者模式：
 *
 *  将构建者中四个角色写入一个类中，
 *  一个Builder既充当构建者角色，又充当指导者角色。
 */
public class Worker {
    private String name;
    private String workName;

    public Worker() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public static class Builder {
        private Worker worker;
        public Builder() {
            this.worker = new Worker();
        }
        public Builder setName(String name) {
            this.worker.setName(name);
            return this;
        }
        public Builder setWorkName(String workName) {
            this.worker.setWorkName(workName);
            return this;
        }
        public Worker builder() {
            return worker;
        }
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("姓名：");
        stringBuffer.append(name);
        stringBuffer.append("\n");
        stringBuffer.append("工作：");
        stringBuffer.append(workName);
        return stringBuffer.toString();
    }
}
