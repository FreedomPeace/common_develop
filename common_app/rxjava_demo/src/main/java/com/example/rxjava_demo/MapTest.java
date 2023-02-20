package com.example.rxjava_demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapTest {
    public static void main(String[] args) {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("d" + i);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        data.stream().flatMap(s -> Stream.of(s.concat("6")))
                .parallel()
                .forEach(System.out::println);
        List<String> collect =
                data.stream().flatMap(s -> Stream.of(s)).collect(Collectors.toList());
        System.out.println(collect);
//        }
        System.out.println("end");
    }
}
