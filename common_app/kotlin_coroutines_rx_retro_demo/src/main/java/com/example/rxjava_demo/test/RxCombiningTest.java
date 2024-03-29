package com.example.rxjava_demo.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxCombiningTest {
    public static void main(String[] args) {
        List<Integer> s1 = new ArrayList<>();
        List<Integer> s2 = new ArrayList<>();
        s1.add(1);
        s1.add(2);
        s1.add(3);

        s2.add(2);
        s2.add(3);
        s2.add(4);

        Integer[] numbers = {1, 2, 3, 4, 5, 6};
        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
        combineLatest(numbers, letters);
        switch2();
        startWith();
        merge(s1, s2);
        combine3();
        zip();
//        System.out.println(1_500);
    }

    private static void zipAndFilter(List<Integer> s1, List<Integer> s2) {
        printSource(s1, s2);
        System.out.println("=============zip zip==============");
        Observable.zip(Observable.fromIterable(s1).filter(new Predicate<Integer>() {
                            @Override
                            public boolean test(Integer integer) throws Throwable {
                                return !s2.contains(integer);
                            }
                        })
                        .toList().doOnSuccess(integers -> {
                            System.out.print("s1 不相同的数据：：：");
                            System.out.println(integers);
                        }).toObservable(),
                Observable.fromIterable(s2).filter(new Predicate<Integer>() {
                            @Override
                            public boolean test(Integer integer) throws Throwable {
                                return !s1.contains(integer);
                            }
                        })
                        .toList().doOnSuccess(integers -> {
                            System.out.print("s2 不相同的数据：：：");
                            System.out.println(integers);
                        })
                        .toObservable(),
                new BiFunction<List<Integer>, List<Integer>, androidx.core.util.Pair<List<Integer>, List<Integer>>>() {
                    @Override
                    public androidx.core.util.Pair<List<Integer>, List<Integer>> apply(List<Integer> integers, List<Integer> integers2) throws Throwable {
                        return androidx.core.util.Pair.create(integers, integers2);
                    }
                }).subscribe(listListPair -> {
            System.out.print("zip:::不同数据");
            System.out.print(listListPair.first);
            System.out.print(":::");
            System.out.println(listListPair.second);
        }, throwable -> {
            System.out.println(throwable.getCause());
        });
    }

    private static void merge(List<Integer> s1, List<?> s2) {
        System.out.println("=============merge==============");
        printSource(s1, s2);
        Observable<Integer> source1 = Observable.fromIterable(s1);
        Observable<Object> source2 = Observable.fromIterable(s2);
        Observable.merge(source1, source2)
//                .toMap(new Function<Integer, String>() {
//                    @Override
//                    public String apply(Integer integer) throws Throwable {
//                        return integer+"key";
//                    }
//                })
                .toList()
                .subscribe(System.out::println);
    }

    private static void combineLatest(Integer[] numbers, String[] letters) {
        System.out.println("=============combineLatest==============");
        printSource(Arrays.asList(numbers), Arrays.asList(letters));
        Observable<String> observable1 = Observable.fromArray(letters);
        Observable<Integer> observable2 = Observable.fromArray(numbers);
        Observable.combineLatest(observable1, observable2, (a, b) -> a + b)
                .toList()
                .subscribe(RxCombiningTest::print);
    }

    private static void switch2() {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
        String[] numbers = {"1"};// "1"
        String[] empty = {};//
        Observable<String> observable1 = Observable.fromArray(letters);
        Observable<String> observable2 = Observable.fromArray(numbers);
        Observable<String> emptyOb = Observable.fromArray(empty);

        System.out.println("=============switch2====has value==========");
        printSource(Arrays.asList(numbers), Arrays.asList(letters));
        observable2.switchIfEmpty(observable1).toList().subscribe(RxCombiningTest::print);

        System.out.println("=============switch2===empty===========");
        printSource(Arrays.asList(empty), Arrays.asList(letters));
        emptyOb.switchIfEmpty(observable1).toList().subscribe(RxCombiningTest::print);
    }

    private static void startWith() {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
        String[] numbers = {"1", "2"};// "1"
        Observable<String> asyLetterObs = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                for (String letter : letters) {
                    Thread.sleep(200);
                    emitter.onNext(letter);
                }
                emitter.onComplete();
            }
        });
        Observable<String> numObs = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                for (String number : numbers) {
                    Thread.sleep(200);
                    emitter.onNext(number);
                }
                emitter.onComplete();
            }
        });

        System.out.println("=============startWith==============");
        printSource(Arrays.asList(numbers), Arrays.asList(letters));

//        asyLetterObs.startWithArray(numbers).subscribe(RxCombiningTest::print);

        asyLetterObs.startWith(numObs).blockingSubscribe(RxCombiningTest::print);
    }

    private static void combine3() {
        Observable<Long> newsRefreshes = Observable.interval(100, TimeUnit.MILLISECONDS);
        Observable<Long> weatherRefreshes = Observable.interval(50, TimeUnit.MILLISECONDS);
        Observable.combineLatest(newsRefreshes, weatherRefreshes,
                        (newsRefreshTimes, weatherRefreshTimes) ->
                                "Refreshed news " + newsRefreshTimes + " times and weather " + weatherRefreshTimes)
                .blockingSubscribe(item -> System.out.println(item));

// prints:
// Refreshed news 0 times and weather 0
// Refreshed news 0 times and weather 1
// Refreshed news 0 times and weather 2
// Refreshed news 1 times and weather 2
// Refreshed news 1 times and weather 3
// Refreshed news 1 times and weather 4
// Refreshed news 2 times and weather 4
// Refreshed news 2 times and weather 5
// ...
    }

    private static void zip()  {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
        String[] numbers = {"1", "2"};// "1"
        Observable<String> asyLetterObs = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                for (String letter : letters) {
                    Thread.sleep(100);
                    emitter.onNext(letter);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
        Observable<String> numObs = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                for (String number : numbers) {
                    Thread.sleep(200);
                    emitter.onNext(number);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        System.out.println("=============zip==============");
        printSource(Arrays.asList(numbers), Arrays.asList(letters));


        asyLetterObs.zipWith(numObs, new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) throws Throwable {
                return s.concat(s2);
            }
        }).blockingSubscribe(RxCombiningTest::print);

    }

    private static void temp() {

    }

    private static void printSource(Object s1, Object s2) {
        System.out.print("s1:::");
        System.out.println(s1);
        System.out.print("s2:::");
        System.out.println(s2);
    }

    public static void print(Object s1) {
        System.out.print("rt:::");
        System.out.println(s1);
    }

}
