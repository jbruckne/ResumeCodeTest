package io.jbruckne.github.joebrucknercodetest;

import android.app.Application;

public class MyApp extends Application {
    private PersonRepository personRepository = new PersonRepositoryImpl();

    public PersonRepository getPersonRepository() {
        return personRepository;
    }
}
