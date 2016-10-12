package io.jbruckne.github.joebrucknercodetest;


import java.util.List;

public interface MainView {
    void showPersonForm();
    void showPersonForm(Person person);
    void showLoading();
    void showPeople(List<Person> people);
    void showError(String error);
    void showSuccess(String action);
    void showFailure(String action);
}
