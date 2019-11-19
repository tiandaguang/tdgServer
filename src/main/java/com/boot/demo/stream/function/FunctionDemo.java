package com.boot.demo.stream.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 1.Predicate接口（断言，判断）
 * <p>
 * 看官方API，主要有and()：类比逻辑与，isEqual()：判断是否相等，
 * negate()：类比逻辑非，or()：类比逻辑或，test()：根据给定的参数计算Predicate，这几个方法
 * <p>
 * <p>
 * 2。Function （转化，传递）
 * Function<T, R>可以理解为：传递一个T类型的参数进去，返回一个R类型的结果，
 * 而apply(T t)即给Function传递参数T t，然后返回R类型的结果，apply()是抽象方法，所以我们要实现它
 * <p>
 * compose()，表示在某个方法之前执行，返回一个组合函数，该函数首先将before函数应用于其输入，然后将此函数应用于结果。
 * <p>
 * andThen() ，和compose相反，在某个方法之后执行。
 * <p>
 * 3.Supplier接口（创造，生产，无中生有）
 * <p>
 * 只有一个抽象方法T get()，没有参数，只返回一个T类型的结果。和Function接口用法相似。
 * <p>
 * 4.Consumer接口（消费者，消费，吞噬）
 * <p>
 * void accept(T t)，接收一个T类型的参数t，然后在里面执行各种操作（比如输出），没有返回值。
 */
public class FunctionDemo {

    public static void main(String[] args) {
        Predicate<String> p1 = str -> str.length() == 9;

        System.out.println(p1.test("tianda"));

        p1 = str -> str.startsWith("aa");

        System.out.println(p1.test("aaaabbvb"));


        p1 = Predicate.isEqual("test");
        System.out.println(p1.test("test"));
        System.out.println(p1.test("test1"));


        Function<String, Teacher> p2 = Teacher::new;
        Teacher t1 = p2.apply("Tianda");
        System.out.println(t1); // Teacher [name=CSDN]

        //执行 前面function  后执行自己
        Function<String, String> f2 = str -> "aaaaaaaaaaaa--->".concat(str);

        t1 = p2.compose(f2).apply("new_Name");
        System.out.println(t1);


        //andThen  先执行自己
        Function<String, Teacher> t2 = Teacher::new;
        Function<Teacher, String> f3 = teacher -> teacher.getName().concat("----------->");

        String s2 = t2.andThen(f3).apply("aa");

        System.out.println(s2);


        Supplier<Teacher> f4 = Teacher::new;
        Teacher t3 = f4.get();

        t3.setName("t3_name");
        System.out.println(t3.getName());


        //相当于进行处理修饰
        System.out.println("--------Consumer----------------");
        Consumer<Teacher> f5 = new Consumer<Teacher>() {
            @Override
            public void accept(Teacher teacher) {
                teacher.setName("Consumer_aaa");
                System.out.println(teacher.getName());
            }
        };
        Teacher t5 = new Teacher();

        f5.accept(t5);

        System.out.println("--------Consumer.andThen----------------");
        f5 = f5.andThen(new Consumer<Teacher>() {
            @Override
            public void accept(Teacher teacher) {
                teacher.setName("andThen_set_name");
                System.out.println(teacher.getName());
            }
        });
        f5.accept(t5);
    }

}
