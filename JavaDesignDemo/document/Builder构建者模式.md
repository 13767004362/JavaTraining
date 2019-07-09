**介绍**：
 >将(复杂对象的构建)过程和对象的表示分隔开来，进一步解耦过程和部件。
 
**好处**：
 >采用构建者模式，可以使用能够同样的构建过程，创建出拥有不同部件的复杂对象。
 
**构建者模式中角色**：
 
- **Product**: 被构建的复杂对象。

- **Builder(构建者)**:一个抽象出构建不同部件的方法的接口。
 
- **ConcreteBuilder**: 实现具体的构建部件，且提供一个返回被Product对象的方法。

- **Director(指导者)**: 通过一个使用Builder接口的对象 ，指导将不同部件组装，构建出复杂对象的过程。

**完整四个角色的Buidler模式**
---

案例：创建一个人对象，构建不同的属性，包含名字和年龄。

**1. Product对象**：
```
public class Person {
    private int age;
    private String name;
    public Person() {
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("姓名：");
        stringBuffer.append(name);
        stringBuffer.append("\n");
        stringBuffer.append("年龄：");
        stringBuffer.append(age);
        return stringBuffer.toString();
    }
}
```

**2. Builder接口**：
```
public interface Buidler {
    void setName(String name);
    void setAge(int age);
    Person builder();
}
```
**3. Builder实现类之ConcreteBuilder**：
```
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
```
**4. Director(指导者)**:
```
public class Director {
    public Person construct(Buidler buidler,String name,int age){
        buidler.setAge(age);
         buidler.setName(name);
         return buidler.builder();
    }
}
```
**Client端口调用该模式**：

```
public class Client {
    public static void main(String[] args) {
        System.out.println("<--------------完整的构建者模式-------------->");
        Buidler buidler = new ConcreteBuilder();
        Director director = new Director();
        Person person = director.construct(buidler, "新根", 23);
        System.out.println(person.toString());
    }
}
```

控制台的输出结果：
```
<--------------完整的构建者模式-------------->
姓名：新根
年龄：23
```



**简洁的Builder模式**
---
案例：构建一个工作者对象，构建不同的属性，包含名字和职业。
```
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
```
Client端使用该模式：
```
public class Client {
    public static void main(String[] args) {
        //.........................
        System.out.println("<-------------简洁的构建者模式--------------->");
        Worker.Builder workBuilder = new Worker.Builder();
        Worker worker = workBuilder.setName("xinGen").setWorkName("Android Developer").builder();
        System.out.println(worker.toString());
    }
}
```
控制台的输出结果：
```
<-------------简洁的构建者模式--------------->
姓名：xinGen
工作：Android Developer
```
