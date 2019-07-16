#  **集合**

**特点**：

1. 长度是不固定的，容器内能存放任意类型的对象。
2. 数据是存放到对象中，对象存放到集合内。
3. 能进行增删改查的操作。

与数组比较：

>  数组固定长度，数组可以存储基本类型数据，也可以存储对象。集合不对固定长度，只能存储不同类型的对象。数组和集合存储的是对象的地址（即对象的引用），而不是对象的实体。数据存储在对象中，集合存储的是对象。


### **Coollection接口**

Collection接口：是框架集合中最基础的接口。

常用API:

- `add()`：添加元素
- `clear()`：清空所有的元素
- `isEmpty()`: 判断是否存在元素
- `remove()`：移除指定的元素
- `size()`:  计算元素个数
- `Iterator   iterator()`:   将集合的元素信息转存成Iterator对象。
- `Object[]  toArray()`:将集合中的所有元素转存到数组后传回该数组。
- `retainAll()`：取交际部分，保留共有的部分，去除不共有的部分。
- `sort()`：元素排序

注意点：  set、list继承了collection，故继承了其的方法。map没有即继承collection，故不能使用其的方法

### **List接口**

**特点**：
1. 元素可以重复
2. 元素是有序的，元素的顺序就是对象插入的顺序
3. 类似java数组，可以根据索引（元素在集合的位置）来访问集合中的元素。

**特有的API**：
- 增：add(index,element):根据索引添加元素
- 删：remove(index,element):根据索引删除元素
- 查： get(index):根据索引得到元素。
        sublist(from,to):得到指定的from(包含)到to(不包含)的部分。
        listIterator():转成迭代器,输出元素。
- 改： set(int  index, Object  obj):将索引位置上的元素替换成指定对象obj


实现类：

**1.ArrayList实现类**

ArrayList底层使用的是数组结构。

特点：

 1. 能动态的增加或减少数组的大小，允许保存所有元素，包括null 
 2. 查询速度快，增删速度慢

实际案例
```
   public static void test1() {
        List<String> dataList = new ArrayList<>();
        dataList.add("a");
        dataList.add("b");
        dataList.add("c");
        for (String s : dataList) {
            System.out.println(TAG + " for循环输出：" + s);
        }
    }
```
输出结果：
```
ListDemo for循环输出：a
ListDemo for循环输出：b
ListDemo for循环输出：c
```

**2.LinkedList实现类**

 LinkedList底层使用的是链表数据结构，用来保存数据。

特点：增删速度快，查询慢

特有API：
- `addFirst()`和`addLast()`:  在头 、尾添加元素
- getFirst()和getLast()           获取第一个 最后一个元素
- removeFirst()  removeLast()        获取且删除  第一个   最后一个元素   

在集合中没有元素时，以上方法会抛出NosuchElementException.
                                                           
- `offerFirst()`:在头部添加元素
- `peekFirst()`：获取第一个元素
- `pollFirst()`:  获取且移除第一个元素

在集合中没有元素时，以上方法会返回一个null.

实际案例：

```
    public static void test2() {
        List<String> dataList = new LinkedList<>();
        dataList.add("a");
        dataList.add("b");
        dataList.add("c");
       Iterator<String> iterator=  dataList.iterator();
       while (iterator.hasNext()){
           System.out.println(TAG + " 迭代器Iterator循环输出：" + iterator.next());
       }
    }
```

输出结果：
```
ListDemo 迭代器Iterator循环输出：a
ListDemo 迭代器Iterator循环输出：b
ListDemo 迭代器Iterator循环输出：c
```


### **Set接口**


**特点**:
1. 无序(填加顺序与存储顺序不同)
2. 对象不重复。


实现类

**HashSet类**：

 底层数据结构是哈希表（哈希表的数据是按自身的哈希值来存储)


**TreeSet类**：

底层数据结构是二叉树，可以对set集合中元素进行排序。

保证元素的唯一性：compareTo()方法, returen 0


实际案例：
```
public static void test(){
        Set<String> stringSet=new HashSet<>();
        final  String value1="110",value2="119",value3="120";
        stringSet.add(value1);
        stringSet.add(value2);
        stringSet.add(value3);
        // 重复添加,验证元素是否唯一。
        stringSet.add(value1);
        for (String value:stringSet){
            System.out.println(TAG+"  for循环输出 value:"+value);
        }
    }
```
输出结果：
```
SetDemo  for循环输出 value:110
SetDemo  for循环输出 value:119
SetDemo  for循环输出 value:120

```

###  **Map接口**

已key-value形式存储的集合,其中key是唯一，value是多值。

常用API：

- `put(k key,v value )`:将key与value添加映射关系存储
- `containkey()`:如果映射到key有映射关系,即key有对应的value，返回true
- `comtainvalue()`:如果映射到值有一个或者多个key,返回true
- `get(Object key)`:得到key指定的value
- `keyset()`:  返回该集合中key对象所形成的set集合
- `values()`:返回该集合中value对象所形成的Collection集合


输出：

Map不能直接同时迭代key 和values
通过调用`Map.Entry()`将Map转换成collection的子类，在通过迭代器进行迭代key和values。


实现类：

**1. HashMap类** 

底层是哈希表数据结构，允许允许使用null键和null值，该集合不同步。添加和删除映射关系的效率更高。

**2. TreeMap类**

 底层是二叉树数据结构，该集合不同步，可以给map集合中的键进行排序。通过使用树实现Map接口，提供了按排序顺序存储关键字/值的有效手段，树映射保证元素按照关键字升序排列。不能让键对象为零
 
 
 实际案例：
 ```
     public static void test1() {
        Map<String, String> map = new HashMap<>();
        map.put("工作", "java developer");
        map.put("年龄", "26");
        // 输出
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            System.out.println(" 循环输出map中的key->value: " + entry.getKey() + " , " + entry.getValue());
        }
    }
 ```
 输出结果：
 ```
 循环输出map中的key->value: 工作 , java developer
 循环输出map中的key->value: 年龄 , 26
 ```

### **Iterable接口**
     
Iterable是在不同容器中的取出方式的共性
Iterator可以完成通过循环输出类集内容，从而获得或删除元素。
        
作用：集合取出元素的方式，与遍历类似。

迭代的方法：

- hasNext()：有元素则迭代，返回true.  
- next()：返回下一个元素。
- remove():去除迭代器返回的最后一个元素 


### **比较器**

java的比较器有两类，分别是Comparable接口和Comparator接口。

`Comparable#compareTo()`和`Comparator#compare()`的比较规则：

1. 返回 正数，则o1大于o2.
2. 返回  0，则o1等于o2.
3. 返回 负数,则o1<o2.

**1. Comparable接口**

定义实体对象,实现Comparable接口:
```
    /**
     *  实现Comparable接口
     */
    public static class Worker implements Comparable<Worker> {
        private int age;
        private String name;

        public Worker setName(String name) {
            this.name = name;
            return this;
        }

        public Worker setAge(int age) {
            this.age = age;
            return this;
        }

        public static Worker create() {
            return new Worker();
        }

        @Override
        public int compareTo(Worker o) {
            if (this.age > o.age) {
                return 1;
            } else if (this.age < o.age) {
                return -1;
            } else {
                return 0;
            }
        }
    }
```
进行排序：
```
    /**
     * 测试: 比较器Comparable使用
     */
    public static void test1() {
        final int size = 3;
        Worker[] workers = new Worker[size];
        workers[0] = Worker.create().setAge(120).setName("医生");
        workers[1] = Worker.create().setAge(110).setName("公安");
        workers[2] = Worker.create().setAge(119).setName("火警");
        Arrays.sort(workers);
        for (Worker worker:workers) {
            System.out.println(TAG + " 排序后的输出：" + worker.age+" , "+worker.name);
        }
    
```

输出结果：
```
CompareDemo 排序后的输出：110 , 公安
CompareDemo 排序后的输出：119 , 火警
CompareDemo 排序后的输出：120 , 医生
```

**2. Comparator接口**


创建Comparator对象,进行排序
```
    /**
     * 测试：比较器Comparator使用
     */
    public static void test2() {
        List<String> dataList = new LinkedList<>();
        dataList.add("d");
        dataList.add("b");
        dataList.add("c");
        dataList.add("a");
        // 按照自然顺序(ASCII码表)排序
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 返回 正数，则o1大于o2.
                // 返回  0，则o1等于o2.
                // 返回 负数,则o1<o2.
                return o1.compareTo(o2);
            }
        };
        Collections.sort(dataList, comparator);
        for (String s : dataList) {
            System.out.println(TAG + " 排序后的输出：" + s);
        }
    }
```
输出结果：
```
CompareDemo 排序后的输出：a
CompareDemo 排序后的输出：b
CompareDemo 排序后的输出：c
CompareDemo 排序后的输出：d
```

