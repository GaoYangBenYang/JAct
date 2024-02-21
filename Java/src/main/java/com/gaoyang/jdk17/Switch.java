package com.gaoyang.jdk17;

/**
 * JDK14推出Switch表达式（正式版）
 * 1.简洁的语法：Switch表达式的语法更加简洁，可以在单个表达式中完成多个分支的逻辑。
 * <p>
 * 2.箭头符号：Switch表达式使用箭头符号"->"来分隔每个分支的条件和执行语句。
 * <p>
 * 3.值返回：Switch表达式可以返回一个值，这使得它们可以被赋值给一个变量或者作为方法的返回值。
 * <p>
 * 4.新关键字：
 *          yield：在Switch表达式中，用于返回一个值。它替代了传统Switch语句中的break语句，并可以将结果直接返回给调用方。
 *          ->：箭头符号，用于分隔每个分支的条件和执行语句。它取代了传统Switch语句中的case和:符号的组合。
 *          ,：逗号，用于在一个分支中列举多个常量值，表示它们共享相同的执行语句。
 *          default：用于指定Switch表达式中的默认分支，当没有任何分支匹配时执行该分支的代码块。
 * <p>
 * 5.简化的语法：Switch表达式可以省略每个分支的"break"语句，从而减少了代码冗余。
 * <p>
 * 6.支持多个常量：Switch表达式支持多个常量值在同一个分支中共享相同的执行语句。
 */
public class Switch {

    public static void main(String[] args) {
        int day = 11;
        //Switch表达式
        String dayName = switch (day) {
            case 1,10,6 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            default -> {
                var a = "asd";
                var b = "sdd";

                yield a+b;

            }
        };
        
        System.out.println("Day: " + dayName);


        //Switch语法
        switch (day) {
            case 1,10,6 -> {
                System.out.println("111");
            }
            case 2 -> {
                System.out.println("222");
            }
            case 3 -> {
                System.out.println("333");
            }
            case 4 -> {
                System.out.println("444");
            }
            case 5 -> {
                System.out.println("555");
            }
            default -> {
                var a = "asd";
                var b = "sdd";
                System.out.println(a+b);

            }
        };



    }
}
