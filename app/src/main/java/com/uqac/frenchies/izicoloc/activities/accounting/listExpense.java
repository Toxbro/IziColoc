package com.uqac.frenchies.izicoloc.activities.accounting;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uqac.frenchies.izicoloc.R;
import com.uqac.frenchies.izicoloc.activities.classes.Expense;

import java.util.List;

/**
 * Created by quentin on 16-11-21.
 */

public class listExpense extends ArrayAdapter<Expense> {

    public listExpense(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public listExpense(Context context, int resource, List<Expense> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.expenseitem, null);
        }

        Expense e = getItem(position);

        if (e != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.label);
            TextView tt2 = (TextView) v.findViewById(R.id.date);
            TextView tt3 = (TextView) v.findViewById(R.id.owner);
            TextView tt4 = (TextView) v.findViewById(R.id.amount);

            tt1.setText(e.getLabel());
            tt2.setText(e.getDate());
            tt3.setText(e.getOwner().getFirstname());
            tt4.setText(String.valueOf(e.getAmount()));
        }

        return v;
    }

}
