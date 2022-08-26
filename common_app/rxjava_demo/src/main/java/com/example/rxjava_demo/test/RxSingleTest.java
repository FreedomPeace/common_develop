package com.example.rxjava_demo.test;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.core.SingleTransformer;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;

public class RxSingleTest {
    public static void main(String[] args) {

        Single.create(new SingleOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull SingleEmitter<String> emitter) throws Throwable {
                        System.out.printf("start time task 第 %s 个", 1);
                        System.out.println();
                        Thread.sleep(1000);
                        emitter.onSuccess("2");
                    }
                })
                .compose(new SingleTransformer<String, String>() {
                    @Override
                    public @NonNull SingleSource<String> apply(@NonNull Single<String> upstream) {
                        Single<String> map = upstream.map("6"::concat);
                        System.out.println("compose" + "->" + map);
                        return map;
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Throwable {
                        boolean b = Integer.parseInt(s) % 2 == 0;
                        System.out.println("filter" + "-> " + s + (b ? "偶数" : "奇数"));
                        if (!b) {
                            //奇数
                            throw new Exception("奇数问题");
                        }
                        return b;//保留偶数
                    }
                })
                .flatMap(new Function<String, MaybeSource<Integer>>() {
                    @Override
                    public MaybeSource<Integer> apply(String s) throws Throwable {
                        Maybe<Integer> just = Maybe.just(Integer.parseInt(s));
                        System.out.println("flatMap" + "->" + just);
                        System.out.println("flatMap" + "->" + s);
                        return just;
                    }
                })
                .zipWith(new Maybe<String>() {
                    @Override
                    protected void subscribeActual(@NonNull MaybeObserver<? super String> observer) {
                        observer.onSuccess("other source");
                    }
                }, new BiFunction<Integer, String, Object>() {
                    @Override
                    public Object apply(Integer integer, String s) throws Throwable {
                        return s.concat(integer.toString());
                    }
                })
                .doOnSuccess(System.out::println)
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Throwable {
                        System.out.println("doOnComplete");
                    }
                })
                .doOnError(throwable -> {
                    System.out.println(throwable.getMessage());
                })
                .subscribe(o -> {
                    System.out.println(o.toString());
                }, throwable -> {
                    System.out.println(throwable.getMessage());
                });
    }
}
