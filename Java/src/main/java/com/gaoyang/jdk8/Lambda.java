package com.gaoyang.jdk8;

/**
 * JDK8新特性 Lambda表达式
 * <p>
 * 1.语法：(Parameters) -> { Body }
 * <p>
 * -> 分隔参数和lambda表达式主体。参数括在括号中，与方法相同，而lambda表达式主体是用大括号括起来的代码块。
 * <p>
 * 2.特点：
 * lambda表达式主体可以有局部变量语句。我们可以在lambda表达式主体中使用break，continue和return。我们甚至可以从lambda表达式主体中抛出异常。
 * <p>
 * lambda表达式没有名称，因为它表示匿名内部类。
 * <p>
 * lambda表达式的返回类型由编译器推断。
 * <p>
 * lambda表达式不能像方法一样有throws子句。
 * <p>
 * lambda表达式不能是泛型，而泛型在函数接口中定义。
 *
 */
public class Lambda {
    public static void main(String[] args) {

    }
}
