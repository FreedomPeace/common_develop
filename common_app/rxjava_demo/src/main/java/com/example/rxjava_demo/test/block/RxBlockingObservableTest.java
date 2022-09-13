package com.example.rxjava_demo.test.block;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxBlockingObservableTest {
    public static void main(String[] args) {
        observableBlocking();
        ParallelFlowable<Integer> source = Flowable.range(1, 1000).parallel();
    }
    /*
    blockingSubscribe
        1
        2
        3
        on completed
        main-----start --blocking==========
        main-----end====blocking======
-----------------------
    subscribe
        main-----start --blocking==========
        1
        2
        3
        on completed
        main-----end====blocking======
     */
    private static void observableBlocking() {

        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
        String[] numbers = {"1","2","3"};// "1"
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                for (String number : numbers) {
                    emitter.onNext(number);
                    Thread.sleep(100);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).subscribe(
                new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        System.out.println(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        System.out.println(throwable.getMessage());
                    }
                }, () -> {
                    System.out.println("on completed");
                }
        );
        System.out.println("main-----start --blocking==========");
        try {
            Thread.sleep(2000);
            System.out.println("main-----end====blocking======");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private static void printSource(Object s1, Object s2) {
        System.out.print("s1:::");
        System.out.println(s1);
        System.out.print("s2:::");
        System.out.println(s2);
    }
}
