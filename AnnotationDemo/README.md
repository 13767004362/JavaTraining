# **Annotation注解**

**前言**：
>通过使用注释，可以再不改变原有逻辑的情况下，在源文件中嵌入一些补充的信息。

Annotation可以用来修饰类，属性，方法，且不影响程序运行，无论是否使用Annotation代码都可以正常执行。

`java.lang.annotation.Annotation`是Annotation的接口，只要时Annotation(系统自带的Annotation还是自定义的Annotation)都必须实现此接口。

 **JDK中自带的三种Annotation**

| 常见的注释 | 描述 |
| :---|:---|
| `@Override`注释 | 用于声明覆盖重写父类或者接口的方法|
| `@Deprecated`注释 | 用于声明不建议使用，但仍然可以使用。通常修饰在属性，类，方法中。 |
| `@SuppressWarnings`注释  | 用于抑制安全警告的注解 |



接下来,通过一些实际案例，来加深理解。

### **@Override注解**

**用途**：用于声明覆盖重写父类或者接口的方法,防止在复写方法时将方法定义出错。

**使用场景**： 只能使用在方法上，不能修饰属性或者类。

**使用案例**：

先定义一个接口
```Java
public interface Developer {
    void work();
}
```
实现类：
```Java
public class JavaDeveloper implements  Developer{
    private final  String TAG=JavaDeveloper.class.getSimpleName();
    @Override
    public void work() {
        System.out.println("我的工作是"+TAG);
    }

    public static  Developer create(){
        return new JavaDeveloper();
    }
}
```
测试如下：
```Java
    /**
     * 测试@Override注解
     */
    public static void testOverrideAnnotation() {
        Developer developer = JavaDeveloper.create();
        developer.work();
    }
```
控制台输出结果：
```
我的工作是JavaDeveloper
```

### **@Deprecated注解**

**用途**：用于声明不建议使用，但仍然可以使用，编译时将出现警告信息。

**使用场景**：通常修饰在属性，类，方法中。

**使用案例**：

```Java
@Deprecated
public class DeprecatedClass {
    @Deprecated
    public  final  String DeprecatedField="DeprecatedField";

    @Deprecated
    public  void deprecated(){
        System.out.println("调用的是一个废弃方法");
    }
    public static  DeprecatedClass create(){
        return new DeprecatedClass();
    }
}
```
测试如下：
```Java
    /**
     * 测试@Deprecated注解
     */
     public static void testDeprecatedAnnotation() {
        DeprecatedClass instance = DeprecatedClass.create();
        instance.deprecated();
        System.out.println("输出一个废弃属性 " + instance.DeprecatedField);
    }
```
控制台输出结果：
```
输出一个废弃属性 DeprecatedField
```


### **@SuppressWarnings注解**

**用途**：用来抑制其他警告注释(例如`"unchecked", "deprecation"`)

**使用场景**： 修饰在方法中。

**使用案例**：

先用`@Deprecated`注解修饰一个类。
```Java
@Deprecated
public class SuppressWarningsClass<T> {
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
```
接着，再用`@SuppressWarnings`抑制检查和不建议使用的警告(`unchecked`注解和`deprecation`注解)。
```Java
    @SuppressWarnings(value = {"unchecked", "deprecation"})
    public static void testSuppressWarnings() {
        SuppressWarningsClass instance = new SuppressWarningsClass();
        instance.setT("测试@SuppressWarnings注解 ,这里压制 unchecked警告和 deprecation警告 ");
        System.out.println(instance.getT());
    }
```
测试如下：
```Java
    /**
     * 测试@SuppressWarnings注解
     */
    public static void testSuppressWarningsAnnotation() {
        SuppressWarningsClass.testSuppressWarnings();
    }
```
控制台输出结果：
```
测试@SuppressWarnings注解 ,这里压制 unchecked警告和 deprecation警告 
```


**@SuppressWarnings中的关键字**

| 关键字 | 描述 |
| :---  | :--- |
| deprecation | 使用了不赞成使用的类或者方法时的警告 |
| unchecked | 执行了未检查的转换时警告 |
| fallthrough | 当使用switch操作时 case 后未添加breadk操作，导致程序继续执行其他的case语句时出现的警告 |
| path | 当设置了一个错误的类路径、源文件路径时出现的警告 |
| serial | 当在可序列化的类上缺少serialVersionUID定义时的警告 |
| finally | 任何finally子句不能正常完成时的警告|
| all | 关于以上所有的情况的警告 |







###  **自定义注解**


自定义Annotation的结构体,如下：

```Java
public @interface Annotation名 {
 
      数据类型  变量名();
}
```


理解：
- 一个自定义的Annotation , 使用@interface注解修饰，相当于继承java.lang.Annotation接口
- 一个自定义Annotation,可以使用各种变量,但是变量之后必须使用`()`,默认定义格式`  数据类型  变量名() default 默认值`。

接下来，如何使用该自定义的Annatation？

使用方式 ： `@Annotation名称`的形式。


接下来,实战编码前,先了解几个重要点：

- **关键点1**： `@Retention`注释,定义一个Annotation的保存范围。
- **关键点2**： `@Target`注释,用于指定Annotation使用位置，不使用该注释，则可以使用在任何位置。
- **关键点3**：  `@Inherited `注释，用于注解所在类的子类，可以继承该注解
- **关键点4**：  `@Documented`注释,用于生成javadoc将一些文档的信息写入。

**@Retention对应的RetentionPolicy的3个范围**

| 范围   |  描述  |
| :---   | :---|
| SOURCE | 用于被修饰的Annotation类型的信息只会保留在源文件`.java`中，编辑后不会保存在类文件`.class`中。 |
| CLASS  | 用于被修饰的Annotation类型的信息只会保留在源文件`.java`和类文件`.class`中,执行时候不会被加载到虚拟机JVM中。一个Annotation声明时没有指定范围,则默认是此范围。|
| RUNTIME | 用于被修饰的Annotation类型的信息只会保留在源文件`.java`和类文件`.class`中,执行时候会被加载到虚拟机JVM中 |

**@Target对应的ElementType的范围**

| 范围 |  描述  |
| :--- | :--- |
| TYPE | 只能用在类、接口、枚举类型上，不可以修饰方法和属性 |
| PACKAGE | 只能用在包的声明上 |
| CONSTRUCTOR | 只能用在构造方法的声明上|
| FIELD | 只能用在属性字段(包括枚举常量)的声明上|
| LOCAL_VARIABLE | 只能用在局部变量的声明上|
| METHOD | 只能用在方法的声明上|
| PARAMETER | 只能用在参数的声明上|
| ANNOTATION_TYPE | 只能用在注释的声明上|


**接下来，通过一个实际案例来加深理解**。

先自定义一个枚举，指定范围Annotation的取值范围。
```Java
public enum CustomEnum {
    red,yellow;  
}
```
接下来,自定义一个Annotation , 指定保存范围,指定使用的位置,定义多个属性字段。
```Java
@Retention(value = RetentionPolicy.RUNTIME) //指定该Annotation在执行时候起作用，会保留在源文件(.java )和类文件(.class)中，也会加载进入JVM中。
@Inherited                                   //用于注解所在类的子类，可以继承该注解
@Target(value ={ ElementType.TYPE })          //指定Annotation使用范围，这里指定修饰类，接口，枚举中。
public @interface CustomAnnotation {
    /**
     * 定义一个字符串数组类型的属性
     *
     * @return
     */
    String[] value();

    /**
     * 定义了一个变量的默认值，在使用该注解的时候，可以不指定该属性的值。
     *
     * @return
     */
    String key() default "设置key的默认值";

    /**
     * 使用enum枚举来限制Annotation注解中属性类型，设置范围值
     *
     * @return
     */
    CustomEnum enumField() default CustomEnum.yellow;
}
```
**1. 结合反射,获取一个Annotation中修饰它的Annotation信息**：

`Class#isAnnotationPresent()`用于判断是否被某个指定Annotation修饰。

这里，获取CustomAnnotation中Target注释的指定使用范围值。
```Java
   public static void testTargetAnnotation() {
        try {
            Class<CustomAnnotation> mClass = CustomAnnotation.class;
            //获取指定的Annotation
            if (mClass.isAnnotationPresent(Target.class)) {
                Target target = mClass.getAnnotation(Target.class);
                ElementType[] elementTypeArray = target.value();
                System.out.println("反射获取到@CustomAnnotation中修饰的target " + elementTypeArray[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

```
控制台输出结果：
```
反射获取到@CustomAnnotation中修饰的target TYPE
```
**2. 使用自定义Annotation,结合反射获取相应的信息**：

使用自定义的CustomAnnotation注释修饰某个类,且给注释设置参数。
```Java
@CustomAnnotation(value = {"测试自定义Annotation,且传递一个数组值给它"}, enumField = CustomEnum.red)    //使用自定义的Annotation
public class TestCustomAnnotation {
    
}
```
反射获取Annnotation信息：
```Java
    /**
     * 反射获取Annotation中完整信息
     */
    public static void testClassGetAnnotationMessage(String tag, Class<?> mClass) {
        try {
            //获取指定的Annotation
            if (mClass.isAnnotationPresent(CustomAnnotation.class)) {
                CustomAnnotation customAnnotation = mClass.getAnnotation(CustomAnnotation.class);
                String value = customAnnotation.value()[0];
                String key = customAnnotation.key();
                CustomEnum enumField = customAnnotation.enumField();
                System.out.println(tag + " 反射获取到指定的Annotation ,输出 CustomAnnotation 注解 中定义的属性：" + " value = " + value + " ，key = " + key + " ,enumField = " + enumField);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
调用：
```Java
    /**
     * 测试自定义Annotation注解
     */
    public static void testCustomAnnotation() {
        TestCustomAnnotation.testClassGetAnnotationMessage(TestCustomAnnotation.TAG, TestCustomAnnotation.class);
    }
```
控制台输出结果：
```
TestCustomAnnotation 反射获取到指定的Annotation ,输出 CustomAnnotation 注解 中定义的属性： value = 测试自定义Annotation,且传递一个数组值给它 ，key = 设置key的默认值 ,enumField = red
```

**3. 结合`@Inherited `注释，获取从父类继承的Annotation**：


先定义一个继承带有注释父类的子类：
```
  /**
     * 定义一个子类，用于测试@Inherited注解
     */
    public static class ChildTestCustomAnnotation extends TestCustomAnnotation {
        public static final String TAG = ChildTestCustomAnnotation.class.getSimpleName();

        public static void testInheritedAnnotation(String tag, Class<?> mClasss) {
            TestCustomAnnotation.testClassGetAnnotationMessage(tag, mClasss);
        }
    }
```
反射调用,获取Annotation中信息
```
     /**
     * 反射获取Annotation中完整信息
     */
    public static void testClassGetAnnotationMessage(String tag, Class<?> mClass) {
        try {
            //获取指定的Annotation
            if (mClass.isAnnotationPresent(CustomAnnotation.class)) {
                CustomAnnotation customAnnotation = mClass.getAnnotation(CustomAnnotation.class);
                String value = customAnnotation.value()[0];
                String key = customAnnotation.key();
                CustomEnum enumField = customAnnotation.enumField();
                System.out.println(tag + " 反射获取到指定的Annotation ,输出 CustomAnnotation 注解 中定义的属性：" + " value = " + value + " ，key = " + key + " ,enumField = " + enumField);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
```
调用：
```Java
  /**
     * 测试@Inherited
     */
    public static void testInheritedAnnotation() {
        TestCustomAnnotation.ChildTestCustomAnnotation.testInheritedAnnotation(TestCustomAnnotation.ChildTestCustomAnnotation.TAG, TestCustomAnnotation.ChildTestCustomAnnotation.class);
    }
```
控制台输出结果：
```
ChildTestCustomAnnotation 反射获取到指定的Annotation ,输出 CustomAnnotation 注解 中定义的属性： value = 测试自定义Annotation,且传递一个数组值给它 ，key = 设置key的默认值 ,enumField = red
```