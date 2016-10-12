package io.jbruckne.github.joebrucknercodetest;

import java.util.Date;
import java.util.List;

class MainPresenterImpl implements MainPresenter, PersonRepository.Listener {
    private PersonRepository personRepository;
    private MainView view = null;

    MainPresenterImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void attachView(MainView view) {
        this.view = view;
        personRepository.addListener(this);
    }

    @Override
    public void detachView() {
        view = null;
        personRepository.removeListener(this);
    }

    @Override
    public void onAddPerson(String firstName, String lastName, String phoneNumber, Date dateOfBirth,
                            int zipCode) {
        personRepository.add(firstName, lastName, phoneNumber, dateOfBirth, zipCode);
    }

    @Override
    public void onRefreshList() {
        if (view == null) return;
        view.showLoading();
        personRepository.getAll();
    }

    @Override
    public void onDelete(int id) {
        personRepository.delete(id);
    }

    @Override
    public void onUpdatePerson(Person person) {
        personRepository.update(person);
    }

    @Override
    public void onSearchQuery(String query) {
        // Todo
    }

    @Override
    public void onPeopleAvailable(List<Person> people) {
        if (view != null) view.showPeople(people);
    }

    @Override
    public void onPersonAdded(Person person) {
        if (view != null) {
            view.showSuccess(person.firstName + " " + person.lastName + " added");
            onRefreshList();
        }
    }

    @Override
    public void onPersonUpdated(Person person) {
        if (view != null) {
            view.showSuccess(person.firstName + " " + person.lastName + " updated");
            onRefreshList();
        }
    }

    @Override
    public void onPersonDeleted(Person person) {
        if (view != null) {
            view.showSuccess(person.firstName + " " + person.lastName + " deleted");
            onRefreshList();
        }
    }

    @Override
    public void onError(Exception e) {
        if (view != null) {
            view.showFailure("Failed to complete action");
        }
    }
}
