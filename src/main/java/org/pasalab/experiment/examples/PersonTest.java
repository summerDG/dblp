package org.pasalab.experiment.examples;

import org.pasalab.experiment.persons.Person;

public class PersonTest {
    public static void main(String[] args) {
        Person p =new Person("Michael Ley", "l/Ley:Michael");
        String[] ca = p.getCoauthors();
        for (String c : ca) {
            System.out.println(c);
        }
    }
}
