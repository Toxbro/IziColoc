package com.uqac.frenchies.izicoloc.activities.accounting;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uqac.frenchies.izicoloc.R;

import java.util.List;

/**
 * Created by quentin on 16-11-21.
 */

public class sharedExpense extends ArrayAdapter<String> {

    public sharedExpense(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public sharedExpense(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.shareitem, null);
        }

        String[] p = getItem(position).split(":");
        String shared = p[0];
        String amount = p[1];

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.shared);
            TextView tt2 = (TextView) v.findViewById(R.id.amount);

                tt1.setText(shared);

                tt2.setText(amount);
        }

        return v;
    }

}
