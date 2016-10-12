package io.jbruckne.github.joebrucknercodetest;

import java.util.Date;

public class Person {
    final int id;
    final String firstName;
    final String lastName;
    final String phoneNumber;
    final Date dateOfBirth;
    final int zipCode;

    public Person(int id, String firstName, String lastName, String phoneNumber, Date dateOfBirth, int zipCode) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.zipCode = zipCode;
    }
}