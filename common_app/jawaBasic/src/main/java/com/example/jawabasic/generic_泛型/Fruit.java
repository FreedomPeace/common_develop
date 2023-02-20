package com.example.jawabasic.generic_泛型

����;

import java.util.ArrayList;
import java.util.List;

class Fruit extends ShengXian{

}
class ShengXian{

}

class Orange extends Fruit {

}
class Apple extends Fruit {

}

/**
 * ���� PECS  producer extends  consumer super
 */
class Test2{
    private List<? extends Fruit> xieBianfruits;//Э��,�����˷��͵����� --Э���������ߣ�����������
    private List<? super Fruit> nibianfruits2;//��� �����˷��͵����� ������Fruit�ĸ���
    private List<?> fruit3;
    public void addFruit(){
        //���벻����<? extends Fruit> ���Ͷ��������ޣ�������orange Apple ��Fruit������
//        fruits.add(new Orange());
        xieBianfruits = new ArrayList<Apple>();
        xieBianfruits = new ArrayList<Orange>();
        Fruit fruit = xieBianfruits.get(0);//������

        nibianfruits2 = new ArrayList<Object>();
        nibianfruits2 = new ArrayList<ShengXian>();

        nibianfruits2.add(new Orange());//������
        nibianfruits2.add(new Apple());
        nibianfruits2.add(new Fruit());
//        fruits2.add(new Object());//���벻��
//        fruits2.add(new ShengXian());//���벻��
        Object object = nibianfruits2.get(0);
//        fruit3.add(new Object());//���벻��
//        fruit3.add(new Orange());//���벻��
    }

    public static void main(String[] args) {

    }
}
