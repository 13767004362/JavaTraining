# **Generic泛型**

理解：
>一种类型安全机制

好处：
1. 将运行时出现的问题ClassCastException，转移到编译时期，提高了安全             
2. 避免了强制类型转换的麻烦。

与强制类型转换比较：

向上类型转换是安全的，向下类型转换是可能出错(ClassCastException异常)。


### **泛型的用法**：

- 定义泛型方法,当不同方法操作不同类型，且类型不明确时，就将泛型定义在方法上 。例如：`public <T> void  get(T  t){  }`
- 定义泛型类,整个类中泛型都有效。 
- 定义泛型接口


**定义泛型类和泛型方法**

定义一个泛型类和泛型方法：
```
public class GenericDemo1<T> {
    private T t;
    public GenericDemo1 setT(T t) {
        this.t = t;
        return this;
    }
    public void print(){
        System.out.println("定义泛型类,指定泛型变量的值："+t+" ,指定的泛型变量是"+t.getClass().getName());
    }
    // 定义泛型静态方法
    public static <K> GenericDemo1<K> create(){
        return new GenericDemo1<>();
    }
}
```
静态方法不可访问类上的泛型，当静态方法操作的应用类型不明确时，可以将泛型定义在静态方法上。


测试：
```
    /**
     * 测试泛型类
     */
    public static void test1(){
        GenericDemo1<String> generic1=  GenericDemo1.create();
        generic1.setT("字符串").print();
        GenericDemo1<Integer> generic2= GenericDemo1.create();
        generic2.setT(100).print();
    }
```
输出结果：
```
定义泛型类,指定泛型变量的值：字符串 ,指定的泛型变量是java.lang.String
定义泛型类,指定泛型变量的值：100 ,指定的泛型变量是java.lang.Integer
```


**定义泛型接口**：

先定义一个泛型接口：
```
public interface GenericInterface<T> {
    void  print(T t);
}
```

定义一个实现类：
```
public class GenericDemo2<K> implements GenericInterface<K> {
    @Override
    public void print(K k) {
        System.out.println("继承泛型接口,指定泛型变量的值："+k+" ,指定的泛型变量是"+k.getClass().getName());
    }
    public static <K> GenericDemo2<K> create(){
        return new GenericDemo2();
    }

}
```
测试：
```
    /**
     *  测试泛型接口
     */
    public static void test2(){
        GenericInterface<String> generic1=GenericDemo2.create();
        generic1.print(" 字符串123");
    }
```

输出结果：
```
继承泛型接口,指定泛型变量的值： 字符串123 ,指定的泛型变量是java.lang.String
```


###  **泛型的通配符**

通配符 `?` 不确定泛型参数的具体类型时，可以使用通配符进行对象定义。至于其他字母是已知模板类型,例如`T 、K`。

```
< T > 等同于 < T extends Object>
< ? > 等同于 < ? extends Object>
```

使用`?`通配符定义一个不缺定类型的List。
```
public class GenericDemo3 {
    /**
     *  使用List<?>去接收不缺定类型的集合。
     *  不能调用add()去添加不缺定类型的数据。
     * @param dataList
     */
   private static void print(List<?> dataList) {
        Iterator<?> interator = dataList.iterator();
        while (interator.hasNext()) {
            System.out.println("使用？通配符,定义不缺定类型的List,输出存储的值：" + interator.next());
        }
    }
}
```
测试：
```
    public static void test3() {
        List<String> stringList=new ArrayList<>();
        stringList.add("字符串456");
        List<Integer> integerList=new ArrayList<>();
        integerList.add(110);
        print(stringList);
        print(integerList);
    }
```
输出结果：
```
使用？通配符,定义不缺定类型的List,输出存储的值：字符串456
使用？通配符,定义不缺定类型的List,输出存储的值：110
```

注意点：
```
List<?> dataList;
dataList.add() //错误语法
```
**使用List<?>不缺定类型的集合,不能调用add()去添加不缺定类型的数据**。



接下来,再来了解一下泛型的上限和下限。

| 通配符 | 描述 |
| :--- | :--- |
| `? extends E`通配符 | 可以接受E类型、或者E类型的子类,即上限 |
| `? super   E`通配符 | 可以接收E类型、或者E类型的父类,即下限|



先定义一个接口和三个子类：
```
 public interface Developer {
        void work();

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
        public void work() {
            System.out.println("Java程序，使用Java语言开发");
        }
  }
  
  public static class AndroidDeveloper implements Developer {
        @Override
        public void work() {
            System.out.println("Android程序，使用Java语言开发");
        }
  }
  
  public static class CDeveloper implements Developer {
        @Override
        public void work() {
            System.out.println("C程序，使用C语言开发");
        }
  }
```
先了解一下上限：`List<? extends Developer>`,因无法确定具体的对象类型,add方法受限,语法报错如下：
![image](https://github.com/13767004362/JavaTraining/blob/master/GenericDemo/document/%E4%B8%8A%E9%99%90.jpg)

先了解一下下限：`List<? super Developer>`因不缺定具体父类类型,get方法受限,语法报错如何：
![image](https://github.com/13767004362/JavaTraining/blob/master/GenericDemo/document/%E4%B8%8B%E9%99%90.jpg)


**1. `? extends E`通配符**


先定义上限通配符：
```
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
```

测试：
```
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
```
输出结果：
```
通配符上限测试：Java程序，使用Java语言开发
通配符上限测试：Android程序，使用Java语言开发
通配符上限测试：C程序，使用C语言开发
```


**2. `? super   E`通配符**


定义下限通配符:
```
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
```
测试
```
    /**
     * 测试下限
     */
    public static void test4(){
        List<Developer> developerList1 = new ArrayList<>();
        developerList1.add(Developer.createJavaDeveloper());
        developerList1.add(Developer.createAndroidDeveloper());
        print2(developerList1);
    }
```

输出结果：
```
通配符下限测试：Java程序，使用Java语言开发
通配符下限测试：Android程序，使用Java语言开发
```

总结如下：

![image](https://github.com/13767004362/JavaTraining/blob/master/GenericDemo/document/%E6%B3%9B%E5%9E%8B%E7%9A%84%E5%88%86%E6%9E%90.png)


