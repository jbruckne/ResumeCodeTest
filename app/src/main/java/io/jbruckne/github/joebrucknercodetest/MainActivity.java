package io.jbruckne.github.joebrucknercodetest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView, PersonAdapter.Listener {

    // Objects
    MainPresenter presenter;
    PersonAdapter adapter = new PersonAdapter();

    // Views
    RecyclerView personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showForm(null);
            }
        });

        personList = (RecyclerView) findViewById(R.id.person_list);
        adapter.setListener(this);
        personList.setAdapter(adapter);

        MyApp app = (MyApp) getApplication();
        presenter = new MainPresenterImpl(app.getPersonRepository());
        presenter.attachView(this);
        presenter.onRefreshList();
    }

    private void showForm(Person person) {
        final PersonFormBottomSheet sheet = new PersonFormBottomSheet();
        if (person != null) sheet.setPerson(person);
        sheet.show(getSupportFragmentManager(), sheet.getTag());
        sheet.setListener(new PersonFormBottomSheet.Listener() {
            @Override
            public void onSavePressed(String first, String last, String phone, Date dob, int zip) {
                presenter.onAddPerson(first, last, phone, dob, zip);
            }
            @Override
            public void onSavePressed(Person person) {
                presenter.onUpdatePerson(person);
            }
        });
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showPersonForm() {

    }

    @Override
    public void showPersonForm(Person person) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showPeople(List<Person> people) {
        adapter.updatePeople(people);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showSuccess(String action) {

    }

    @Override
    public void showFailure(String action) {

    }

    @Override
    public void onUpdate(Person person, int position) {
        showForm(person);
    }

    @Override
    public void onDelete(Person person, int position) {
        presenter.onDelete(person.id);
    }

    @Override
    public void onCall(Person person, int position) {

    }
}
