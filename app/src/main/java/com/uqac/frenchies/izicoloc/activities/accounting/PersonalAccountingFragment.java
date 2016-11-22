package com.uqac.frenchies.izicoloc.activities.accounting;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.uqac.frenchies.izicoloc.R;
import com.uqac.frenchies.izicoloc.activities.classes.Colocataire;
import com.uqac.frenchies.izicoloc.activities.classes.Colocation;
import com.uqac.frenchies.izicoloc.activities.classes.Expense;
import com.uqac.frenchies.izicoloc.activities.classes.Profile;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PersonalAccountingFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PersonalAccountingFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PersonalAccountingFragment newInstance() {
        PersonalAccountingFragment fragment = new PersonalAccountingFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_personalaccounting, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

//                getString(R.string.cost)
            }
        });

//        String path = this.getArguments().getString("path");
//
//        String data = Parser.getAllInformation(path, "root").toString();

        Colocataire quentin = Colocation.getColocataire("Quentin");

        TextView expenses = (TextView) rootView.findViewById(R.id.expenses);
        expenses.setText(String.valueOf("Balance : "+Colocation.getBalance(quentin)));

        HashMap<String, Integer> shares = new HashMap<>();
        for(Colocataire c : Colocation.getColocataires()){
            shares.put(c.getFirstname(), Colocation.getShare(quentin, c));
        }
        shares.remove(quentin);

        ArrayList<String> result = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : shares.entrySet()){
            result.add(entry.getKey()+":"+String.valueOf(entry.getValue()));
        }

        ArrayAdapter<String> adapter = new sharedExpense(this.getContext(), 0, result);
        ListView expensesList = (ListView) rootView.findViewById(R.id.sharedExpensesList);

        expensesList.setAdapter(adapter);
        return rootView;
    }
}
