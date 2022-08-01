package com.example.jawabasic.interf;

import java.util.function.Consumer;

public interface ConsumerString extends Consumer<String>{
}
 class Test{
     public static void main(String[] args) {
         ConsumerString onClickL = System.out::println;
         onClickL.accept("66");
         Consumer<String> stringConsumer = s -> System.out.println(s);
         stringConsumer.accept("88");
     }
}
