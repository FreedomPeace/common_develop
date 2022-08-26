package com.example.rxjava_demo.test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;

public class RxDistinctTest {
    public static void main(String[] args) {
        List<Integer> s1 =new ArrayList<>();
        List<Integer> s2 =new ArrayList<>();
        s1.add(1);
        s1.add(2);
        s1.add(3);

        s2.add(2);
        s2.add(3);
        s2.add(4);

//        distinct1();
        distinct1(s1,s2);
    }

    private static void distinct1() {
        Observable.just(1,2,3,4,6,3,4)
//                .distinctUntilChanged()//新的item 和上一次的不同 才分发
                .distinct(new Function<Integer, String>() {//去重复
                    @Override
                    public String apply(Integer integer) throws Throwable {
//                        System.out.println( "distinct == "+integer);
                        return integer+"";
                    }
                })
                .doOnNext(System.out::println)
                .subscribe();
    }
    private static void distinct1(List<Integer> s1 ,List<Integer> s2) {
        System.out.print("s1:::");
        System.out.println(s1);
        System.out.print("s2:::");
        System.out.println(s2);
        //从s1 中找到和 s2 相同的对象。
        Observable.fromIterable(s1).filter(s2::contains)
                .toList()
                .doOnSuccess(integers -> {
                    System.out.print("相同的数据：：：");
                    System.out.println(integers);
                })
                .subscribe();
        //从s1 中找到和 s2 不相同的对象。
        Observable.fromIterable(s1).filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Throwable {
                        return !s2.contains(integer);
                    }
                })
                .toList()
                .doOnSuccess(integers -> {
                    System.out.print("s1不相同的数据：：：");
                    System.out.println(integers);
                })
                .subscribe();

        //从s2 中找到和 s1 不相同的对象。
        Observable.fromIterable(s2).filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Throwable {
                        return !s1.contains(integer);
                    }
                })
                .toList()
                .doOnSuccess(integers -> {
                    System.out.print("s2 不相同的数据：：：");
                    System.out.println(integers);
                })
                .subscribe();
    }
}
