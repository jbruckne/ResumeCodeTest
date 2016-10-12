package io.jbruckne.github.joebrucknercodetest;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PersonFormBottomSheet extends BottomSheetDialogFragment {

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    private Listener listener = null;
    private Person person = null;

    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        final View contentView = View.inflate(getContext(), R.layout.form_person, null);
        dialog.setContentView(contentView);

        if (person != null) setInfo(person, contentView);
        Button cancel = (Button) contentView.findViewById(R.id.form_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button save = (Button) contentView.findViewById(R.id.form_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Sheet", "I'm here");
                if (listener == null) return;
                EditText first = (EditText) contentView.findViewById(R.id.form_first);
                EditText last = (EditText) contentView.findViewById(R.id.form_last);
                EditText phone = (EditText) contentView.findViewById(R.id.form_phone);
                EditText dob = (EditText) contentView.findViewById(R.id.form_dob);
                EditText zip = (EditText) contentView.findViewById(R.id.form_zip);

                try {
                    Date date = new SimpleDateFormat("MM/dd/yyyy", Locale.US)
                            .parse(dob.getText().toString());

                    if (person != null) {
                        person = new Person(
                                person.id,
                                first.getText().toString(),
                                last.getText().toString(),
                                phone.getText().toString(),
                                date,
                                Integer.valueOf(zip.getText().toString())
                        );
                        listener.onSavePressed(person);
                    } else {
                        listener.onSavePressed(
                                first.getText().toString(),
                                last.getText().toString(),
                                phone.getText().toString(),
                                date,
                                Integer.valueOf(zip.getText().toString())
                        );
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
            }
        });

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if( behavior != null && behavior instanceof BottomSheetBehavior ) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    private void setInfo(Person person, View view) {
        if (view == null) return;

        EditText first = (EditText) view.findViewById(R.id.form_first);
        EditText last = (EditText) view.findViewById(R.id.form_last);
        EditText phone = (EditText) view.findViewById(R.id.form_phone);
        EditText dob = (EditText) view.findViewById(R.id.form_dob);
        EditText zip = (EditText) view.findViewById(R.id.form_zip);

        first.setText(person.firstName);
        last.setText(person.lastName);
        phone.setText(person.phoneNumber);
        dob.setText(new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(person.dateOfBirth));
        zip.setText(String.valueOf(person.zipCode));
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onSavePressed(String first, String last, String phone, Date dob, int zip);
        void onSavePressed(Person person);
    }
}
