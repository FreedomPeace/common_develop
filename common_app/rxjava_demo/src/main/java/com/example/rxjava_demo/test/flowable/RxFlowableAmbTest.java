package com.example.rxjava_demo.test.flowable;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

public class RxFlowableAmbTest {
    public static void main(String[] args) {
        amb();
        Disposable d = Flowable.just(" Hello"," world"," world")
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .subscribeWith(new DisposableSubscriber<String>() {
                    @Override public void onStart() {
                        System.out.println("Start!");
                        request(1);
                    }
                    @Override public void onNext(String t) {
                        System.out.println("onNext"+t+"=="+System.currentTimeMillis());
                        request(1);
                    }
                    @Override public void onError(Throwable t) {
                        t.printStackTrace();
                    }
                    @Override public void onComplete() {
                        System.out.println("Done!");
                    }
                });
        try {
            Thread.sleep(10000);
            System.out.println("end up sorry!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static void amb() {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
        String[] numbers = {"1","2","3"};// "1"

        Flowable<String> f1 = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> emitter) throws Throwable {
                for (String letter : letters) {
                    emitter.onNext(letter);
                    Thread.sleep(100);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
        Flowable<String> f2 = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> emitter) throws Throwable {
                for (String n : numbers) {
                    emitter.onNext(n);
                    Thread.sleep(200);
                }
                emitter.onComplete();

            }
        },BackpressureStrategy.BUFFER);
        Disposable disposable =
                Flowable.ambArray(f1, f2).subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                        request(1);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
//        disposable.dispose();

    }
    private static void printSource(Object s1, Object s2) {
        System.out.print("s1:::");
        System.out.println(s1);
        System.out.print("s2:::");
        System.out.println(s2);
    }
}
