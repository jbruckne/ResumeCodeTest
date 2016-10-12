package io.jbruckne.github.joebrucknercodetest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {
    private List<Person> people = new ArrayList<>();
    private List<Listener> listeners = new ArrayList<>();
    private int idCounter = 0;

    public PersonRepositoryImpl() {
        people.add(new Person(0, "Joe", "Bruckner", "5742021943", new Date(), 47906));
        people.add(new Person(1, "Dan", "Bruckner", "5742023006", new Date(), 46526));
        people.add(new Person(2, "Paul", "Bruckner", "5742023391", new Date(), 46526));
        people.add(new Person(3, "Gail", "Bruckner", "5742021297", new Date(), 46526));
    }

    @Override
    public void getAll() {
        for (Listener l : listeners)
            l.onPeopleAvailable(people);
    }

    @Override
    public void add(String firstName, String lastName, String phoneNumber, Date dateOfBirth,
                    int zipCode) {
        Person person = new Person(
                idCounter++,
                firstName,
                lastName,
                phoneNumber,
                dateOfBirth,
                zipCode
        );
        people.add(person);
        for (Listener l : listeners)
            l.onPersonAdded(person);
    }

    @Override
    public void update(Person person) {
        for (Person p : people) {
            if (p.id == person.id) {
                int index = people.indexOf(p);
                people.remove(p);
                people.add(index, person);
                for (Listener l : listeners)
                    l.onPersonUpdated(person);
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        for (Person p : people) {
            if (p.id == id) {
                people.remove(p);
                for (Listener l : listeners)
                    l.onPersonDeleted(p);
                break;
            }
        }
    }

    @Override
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }
}
