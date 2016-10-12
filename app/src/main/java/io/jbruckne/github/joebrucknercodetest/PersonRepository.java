package io.jbruckne.github.joebrucknercodetest;

import java.util.Date;
import java.util.List;

public interface PersonRepository {
    void getAll();
    void add(String firstName, String lastName, String phoneNumber, Date dateOfBirth, int zipCode);
    void update(Person person);
    void delete(int id);
    void addListener(Listener listener);
    void removeListener(Listener listener);

    interface Listener {
        void onPeopleAvailable(List<Person> people);
        void onPersonAdded(Person person);
        void onPersonUpdated(Person person);
        void onPersonDeleted(Person person);
        void onError(Exception e);
    }
}
