#  **Enum枚举**

理解：
> 枚举类型成员都可以看做枚举类型的一个实例， 这些枚举类型成员被默认final、public 、static修饰。故可以是用枚举类型名称调用枚举成员。
枚举类型成员

特点：

- 枚举类型将常量封装在类中或者接口中
- 提供安全检查
- 实质是以类的形式存在

与常量定义方式比较:

常量一般放在接口中，便于直接使用，且不能被改动（final与static修饰）。枚举替代常量定义方式。

接下来,通过一些案例,进一步了解枚举类。


**1. 通过enum关键字定义枚举和构造器与枚举项**：
```Java
public enum Direction1 {
    // 定义枚举项
    FRONT, BEHIND, LEFT, RIGHT;
    Direction1() { //枚举的构造方法
        System.out.println("创建枚举项会,调用enum枚举的构造方法，有几个枚举项，就会调用几次构造方法");
    }
}
```
使用枚举项：
```Java
    /**
     * 测试枚举项与枚举的构造方法
     */
    public static void testEnumConstruction(){
        Direction1 direction1= Direction1.FRONT;
    }
```
控制台输出结果：
```
创建枚举项会,调用enum枚举的构造方法，有几个枚举项，就会调用几次构造方法
创建枚举项会,调用enum枚举的构造方法，有几个枚举项，就会调用几次构造方法
创建枚举项会,调用enum枚举的构造方法，有几个枚举项，就会调用几次构造方法
创建枚举项会,调用enum枚举的构造方法，有几个枚举项，就会调用几次构造方法
```
理解：

1. 在定义枚举项时，多个枚举项之间使用逗号分隔，最后一个枚举项后需要给出分号。
2. 不能使用new来创建枚举类的对象，因为**枚举类中的实例就是类中的枚举项**，所以在类外只能使用类名.枚举项。
3. 枚举类也可以有构造器，构造器默认都是private修饰，而且只能是private。因为枚举类的实例不能让外界来创建。
4. 其实创建枚举项就等同于调用本类的无参构造器，所以FRONT、BEHIND、LEFT、RIGHT四个枚举项等同于调用了四次无参构造器，所以你会看到四个输出提示。

**2. 枚举与switch语句**:
```Java
public enum Direction2 {
    // 定义枚举项
    FRONT, BEHIND, LEFT, RIGHT;
    /**
     * 测试枚举与swicth语句
     */
    public static void testSwitch(){
        Direction2 direction=Direction2.BEHIND;
        switch(direction) {
            case FRONT:
                System.out.println("前面");
                break;
            case BEHIND:
                System.out.println("后面");
                break;
            case LEFT:
                System.out.println("左面");
                break;
            case RIGHT:
                System.out.println("右面");
                break;
            default:
                System.out.println("错误的方向");
        }
    }
}
```
输出结果：
```
后面
```
在switch中，不能使用枚举类名称，例如：`case Direction.FRONT：`这是错误的，因为编译器会根据switch中d的类型来判定每个枚举类型，在case中必须直接给出与direction相同类型的枚举选项，而不能再有类型。


**3. 枚举的属性与方法**

```
public enum Direction3 {
    // 定义枚举项,构造传参
    FRONT("front->前面"),
    BEHIND("behind->后面"),
    LEFT("left->左边"),
    RIGHT("right->右边");

    //定义属性
    private String name;
    Direction3(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
```
调用：
```
    /**
     *  测试枚举中属性和方法
     */
    public static void test(){
        Direction3 direction=Direction3.LEFT;
        System.out.println("调用枚举中属性："+direction.getName());
    }
```
控制台输出结果：
```
调用枚举中属性：left->左边
```
因为Direction3类只有唯一的构造器，并且是有参的构造器，所以在创建枚举项时，必须为构造器赋值：`FRONT("front->前面")`，其中`"front->前面"`就是传递给构造器的参数。必须得接受这种语法的书写形式。



**5. 枚举与抽象方法与实现接口**

枚举在定义抽象方法或者实现接口方式,创建枚举项时候会以匿名内部类形式，覆盖重写该抽象方法。换句话说,枚举中有抽象方法时,每个枚举项就是一个匿名内部类。

先定义一个接口
```
public interface BaseDirection {
    String direction();
}
```
定义带有抽象方法的枚举：
```
public enum Direction4 implements BaseDirection {
    // 定义匿名内部类形式的枚举项
    FRONT() {
        @Override
        public void print() {
            System.out.println("枚举项以匿名内部类形式,复写抽象方法：" + direction());
        }

        @Override
        public String direction() {
            return "front->前面";
        }
    },
    BEHIND() {
        @Override
        public void print() {
            System.out.println("枚举项以匿名内部类形式,复写抽象方法：" + direction());
        }

        @Override
        public String direction() {
            return "behind->后面";
        }
    };
    // 定义枚举中的抽象方法
    public abstract void print();
    
}
```
测试：
```
    /**
     * 测试枚举类抽象方法，枚举项匿名内部类实现。
     */
    public static void test4(){
        Direction4 direction=Direction4.FRONT;
        direction.print();
    }
```
输出结果：
```
    /**
     * 测试枚举类抽象方法，枚举项匿名内部类实现。
     */
    public static void test4(){
        Direction4 direction=Direction4.FRONT;
        direction.print();
    }
```