package io.jbruckne.github.joebrucknercodetest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder>{
    private List<Person> people;
    private Listener listener = null;
    private int selected = -1;

    public void updatePeople(List<Person> people) {
        this.people = people;
        notifyDataSetChanged();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 1) {
            View view = inflater.inflate(R.layout.item_person, parent, false);
            return new PersonViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_person_selected, parent, false);
            return new SelectedPersonViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder holder, int position) {
        final Person person = people.get(position);
        holder.name.setText(person.firstName + " " + person.lastName);
        holder.dob.setText(new SimpleDateFormat("MM/dd/yyyy", Locale.US)
                .format(person.dateOfBirth)
        );
        holder.zip.setText(String.valueOf(person.zipCode));
        holder.phone.setText(person.phoneNumber);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int old = selected;
                selected = holder.getAdapterPosition();
                notifyItemChanged(old);
                if (selected != old)
                    notifyItemChanged(selected);
                else
                    selected = -1;
            }
        });

        if (holder instanceof SelectedPersonViewHolder) {
            SelectedPersonViewHolder selectedHolder = (SelectedPersonViewHolder) holder;
            selectedHolder.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onUpdate(person, holder.getAdapterPosition());
                }
            });
            selectedHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onDelete(person, holder.getAdapterPosition());
                }
            });
            selectedHolder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onCall(person, holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == selected) return 0;
        else return 1;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView dob;
        final TextView zip;
        final TextView phone;
        final View view;

        PersonViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.person_name);
            dob = (TextView) itemView.findViewById(R.id.person_dob);
            zip = (TextView) itemView.findViewById(R.id.person_zip);
            phone = (TextView) itemView.findViewById(R.id.person_phone);
            view = itemView;
        }
    }

    class SelectedPersonViewHolder extends PersonViewHolder {
        final Button delete;
        final Button update;
        final Button call;

        SelectedPersonViewHolder(View itemView) {
            super(itemView);
            delete = (Button) itemView.findViewById(R.id.person_delete);
            update = (Button) itemView.findViewById(R.id.person_update);
            call = (Button) itemView.findViewById(R.id.person_call);
        }
    }

    interface Listener {
        void onUpdate(Person person, int position);
        void onDelete(Person person, int position);
        void onCall(Person person, int position);
    }
}
