package com.uqac.frenchies.izicoloc.activities.accounting;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uqac.frenchies.izicoloc.R;
import com.uqac.frenchies.izicoloc.tools.classes.Colocataire;
import com.uqac.frenchies.izicoloc.tools.classes.Expense;

import java.util.ArrayList;
import java.util.Arrays;
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
            TextView tt5 = (TextView) v.findViewById(R.id.shared);

            tt1.setText(e.getLabel());
            tt2.setText(e.getDate());
            tt3.setText(e.getOwner().getFirstname());
            tt4.setText(String.valueOf(e.getAmount())+ " $");

            String shares = "";
            System.out.println(Arrays.deepToString(e.getShares()));
            for(Colocataire c : e.getShares())
                shares += c.getFirstname()+", ";
            shares = shares.substring(0, shares.length()-2);
            tt5.setText(shares);
        }

        return v;
    }

}
