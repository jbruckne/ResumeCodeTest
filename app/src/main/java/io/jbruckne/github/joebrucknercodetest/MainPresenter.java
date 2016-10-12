package io.jbruckne.github.joebrucknercodetest;

import java.util.Date;

interface MainPresenter {
    void attachView(MainView view);
    void detachView();
    void onAddPerson(String firstName, String lastName, String phoneNumber, Date dateOfBirth, int zipCode);
    void onRefreshList();
    void onDelete(int id);
    void onUpdatePerson(Person person);
    void onSearchQuery(String query);
}
