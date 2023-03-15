package com.example.lib_kotlin_learn.generic_in_out;

import java.util.ArrayList;
import java.util.List;

//Use bounded wildcards[通配符] to increase API flexibility
//The wildcard type argument ? extends E indicates
// not just E itself
public class GenericJava {
    // Java
    interface Collection<E> {
        void addAll(Collection<E> items);
        // that this method accepts a collection of objects of E or a subtype of E,
        void addAll2(Collection<? extends E> items);

    }
    // Java
    void copyAll(Collection<Object> to, Collection<String> from) {
//        to.addAll(from);
        to.addAll2(from);
        // !!! Would not compile with the naive declaration of addAll:
        // Collection<String> is not a subtype of Collection<Object>
    }

    public static void main(String[] args) {
        // Java
        // First, generic types in Java are invariant【】,
        // meaning that List<String> is not a subtype【】 of List<Object>
//        Java prohibits such things in order to guarantee run-time safety
        //泛型在java中是不变的，
        List<String> strs = new ArrayList<String>();
//        List<Object> objs = strs; // !!! A compile-time error here saves us from a runtime exception later.
//        objs.add(1); // Put an Integer into a list of Strings
        String s = strs.get(0); // !!! ClassCastException: Cannot cast Integer to String
    }
}




