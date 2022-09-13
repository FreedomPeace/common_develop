package com.example.rxjava_demo.test.flowable;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableRange;

public class RxFlowableAmbTest {
    public static void main(String[] args) {
        amb();
    }
    private static void amb() {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
        String[] numbers = {"1","2","3"};// "1"
        String[] empty = {};//
        List<String> lettersList = Arrays.asList(letters);
        List<String> numList = Arrays.asList(numbers);
        List<String> empList = Arrays.asList(empty);

        Flowable<String> f1 = Flowable.fromIterable(lettersList);
        Flowable<String> f2 = Flowable.fromIterable(numList);
        Flowable<String> f3 = FlowableRange.fromIterable(empList);
        Disposable disposable = Flowable.ambArray(f2, f1, f3).subscribe(
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
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable.dispose();

    }
    private static void printSource(Object s1, Object s2) {
        System.out.print("s1:::");
        System.out.println(s1);
        System.out.print("s2:::");
        System.out.println(s2);
    }
}
