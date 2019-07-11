package com.xingen.enumdemo;

/**
 * author: xinGen
 * date : 2019/7/11
 * blog: https://blog.csdn.net/hexingen
 */
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
