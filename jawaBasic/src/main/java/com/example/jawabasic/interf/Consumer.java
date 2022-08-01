package com.example.jawabasic.interf;


public interface Consumer<T> {
    void accept(T t);
}

interface Provider<T> {
    T provider();
}

class Animal {
    public String name;

    public Animal(String name) {
        this.name = name;
    }

    protected void eat(String name) {
        System.out.println(name.concat("eat"));
    }
}

class Dog extends Animal {

    public Dog() {
        super("dog");
    }

    public Dog(String name) {
        super(name);
    }

    @Override
    protected void eat(String name) {
        super.eat(name);
        System.out.println("bone");
    }
}

class Test {
    public static void main(String[] args) {
        Consumer<? super Animal> consumer = animal -> {animal.eat(animal.name);};

        Provider<? extends Animal> provider = Dog::new;

        Provider<? extends Animal> provider2 = () -> new Animal("an");

        consumer.accept(provider.provider());
        consumer.accept(provider2.provider());
    }
}
