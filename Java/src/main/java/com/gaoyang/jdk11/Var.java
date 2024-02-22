package com.gaoyang.jdk11;

import java.util.ArrayList;

/**
 * JDK10推出 var关键字 局部变量的类型推断
 * <p>
 * var 变量类型推断的使用也有局限性，
 * <p>
 * 仅局限于具有初始化值的局部变量、增强型 for 循环中的索引变量、传统 for 循环中声明的局部变量，
 * <p>
 * 而不能用于推断方法的参数类型，不能用于构造函数参数类型推断，不能用于推断方法返回类型，也不能用于字段类型推断，同时还不能用于捕获表达式（或任何其他类型的变量声明）。
 */
public class Var {
    public static void main(String[] args) {
        //局部变量自动类型推断
        var name = "gaoyang";
        var age = 18;
        var list = new ArrayList<String>();
        System.out.println(name);
        System.out.println(age);
        System.out.println(list);
        list.add("a");
        list.add("b");
        list.add("c");
        //增强for循环中的索引变量
        for(var l:list){

            System.out.println(l);

        }




    }
}
