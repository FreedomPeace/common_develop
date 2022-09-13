package com.example.rxjava_demo.test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;

public class RxTransformerTest {
    public static void main(String[] args) {
//        cast();
//        concatMap();
//        groupBy();
        flatMap();
    }

    private static void cast() {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
        Integer[] num2 = {1, 3, 5};
        Observable.just(letters, num2, 2).filter(String[].class::isInstance).cast(String[].class)
//                .compose(new ObservableTransformer<String[], List<String>>() {
//                    @Override
//                    public @NonNull ObservableSource<List<String>> apply(@NonNull Observable<String[]> upstream) {
//
//                        return upstream.map(new Function<String[], List<String>>() {
//                            @Override
//                            public List<String> apply(String[] strings) throws Throwable {
//                                return Arrays.asList(strings);
//                            }
//                        });
//                    }
//                })
                .map(Arrays::asList)
                .subscribe(RxCombiningTest::print);
    }

    private static void concatMap() {
        Observable.range(0, 5)
                .concatMap(i -> {
                    long delay = Math.round(Math.random() * 2);

                    return Observable.timer(delay, TimeUnit.SECONDS).map(n -> {
//                        System.out.println(n);
                        return i;
                    });
                })
                .blockingSubscribe(System.out::print);

// prints 01234
//        Observable.interval(100,TimeUnit.MILLISECONDS).subscribe(System.out::println);
    }

    private static void groupBy() {
        Observable<String> animals = Observable.just(
                "Tiger", "Elephant", "Cat", "Chameleon", "Frog", "Fish", "Turtle", "Flamingo");

        animals.groupBy(animal -> animal.charAt(0), String::toUpperCase)
                .concatMapSingle(Observable::toList)
                .subscribe(System.out::println);

// prints:
// [TIGER, TURTLE]
// [ELEPHANT]
// [CAT, CHAMELEON]
// [FROG, FISH, FLAMINGO]
    }

    private static void flatMap() {
        Observable.just("A", "B", "C")
                .map(a -> {
                    return Observable.intervalRange(1, 3, 0, 1, TimeUnit.SECONDS)
                            .map(b -> '(' + a + ", " + b + ')');
                })
                .blockingSubscribe(System.out::println);
        Observable.just("A", "B", "C")
                .flatMap(a -> {
                    return Observable.intervalRange(1, 3, 0, 1, TimeUnit.SECONDS)
                            .map(b -> '(' + a + ", " + b + ')');
                })
                .blockingSubscribe(System.out::println);

// prints (not necessarily in this order):
// (A, 1)
// (C, 1)
// (B, 1)
// (A, 2)
// (C, 2)
// (B, 2)
// (A, 3)
// (C, 3)
// (B, 3)
    }private static void temperate() {

    }
}
