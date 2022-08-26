package com.example.rxjava_demo.test;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;

public class RxObservableTest {
    public static void main(String[] args) {

        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                        for (int i = 0; i < 5; i++) {
                            System.out.printf("start time task 第 %s 个", i);
                            System.out.println();
                            Thread.sleep(1000);
                            emitter.onNext(i + "");
                        }
                    }
                })
                .compose(new ObservableTransformer<String, String>() {
                    @Override
                    public @NonNull ObservableSource<String> apply(@NonNull Observable<String> upstream) {
                        Observable<String> map = upstream.map("6"::concat);
                        System.out.println("compose" + "->" + map);
                        return map;
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Throwable {
                        boolean b = Integer.parseInt(s) % 2 == 0;
                        System.out.println("filter" + "-> " + s + (b ?"偶数":"奇数"));
                        return b;//保留偶数
                    }
                })
                .flatMap(new Function<String, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(String s) throws Throwable {
                        Observable<Integer> just = Observable.just(Integer.parseInt(s));
                        System.out.println("flatMap" + "->" + just);
                        System.out.println("flatMap" + "->" + s);
                        return just;
                    }
                })
                .zipWith(new Observable<String>() {
                    @Override
                    protected void subscribeActual(@NonNull Observer<? super String> observer) {
                        //两者结果数 取最小的
                        for (int i = 0; i < 2; i++) {
                            observer.onNext("other source " +i +"==");
                        }
                    }
                }, new BiFunction<Integer, String, Object>() {
                    @Override
                    public Object apply(Integer integer, String s) throws Throwable {
                        return s.concat(integer.toString());
                    }
                })
                .doOnNext(System.out::println).subscribe();
    }
}
