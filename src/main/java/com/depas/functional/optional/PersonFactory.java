package com.depas.functional.optional;

import java.util.ArrayList;
import java.util.List;

public class PersonFactory {


    public static List<Person> getPersonList() {

        // create some insurances
        Insurance ins1 = new Insurance("insurance one");
        Insurance ins2 = new Insurance("insurance two");
        Insurance ins3 = new Insurance("insurance three");
        Insurance ins4;

        try {
            ins4 = new Insurance("insurance three");
        }
        catch (Exception e) {
            System.out.println(e);
        }

        // create some cars
        Car car1 = new Car(ins1);
        Car car2 = new Car(ins2);
        Car car3 = new Car(ins3);
        Car car4 = new Car();

        List<Person> personList = new ArrayList<>();
        Person p = new Person(car1, 33);
        personList.add(p);

        p = new Person(car2, 17);
        personList.add(p);

        p = new Person(car3, 54);
        personList.add(p);

        p = new Person(car4, 25);
        personList.add(p);

        p = new Person(16);
        personList.add(p);

        return personList;
    }
}
