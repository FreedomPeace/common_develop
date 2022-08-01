package com.example.jawabasic.interf;


public interface Consumer<T>{
    void accept(T t) ;
}
interface Provider<T>{
    T provider() ;
}
 class Test{
     public static void main(String[] args) {
         Consumer<? super String> consumer = System.out::println;

         Provider<? extends String> provider = () -> "99";

         consumer.accept(provider.provider());
     }
}
