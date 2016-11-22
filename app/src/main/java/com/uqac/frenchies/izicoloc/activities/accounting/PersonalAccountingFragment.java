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

        ////////////////////////////////////////////////////////////////////
        Colocataire thomas = new Colocataire();
        thomas.setId(1849);
        thomas.setFirstname("Thomas");
        thomas.setLastname("Navarro");
        thomas.setEmail("thomas.navarro@live.fr");
        thomas.setPhone("0606060606");
        DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        try {
            thomas.setBirthday(dtf.parse("26/03/1994"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Colocataire quentin = new Colocataire();
        quentin.setId(2016);
        quentin.setFirstname("Quentin");
        quentin.setLastname("Rollin");
        quentin.setEmail("rollin.quentin@live.fr");
        quentin.setPhone("0606060606");
        try {
            quentin.setBirthday(dtf.parse("05/04/1994"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Colocataire maxime = new Colocataire();
        maxime.setId(1341);
        maxime.setFirstname("Maxime");
        maxime.setLastname("Roux");
        maxime.setEmail("roux.maxime@live.fr");
        maxime.setPhone("0606060606");
        try {
            maxime.setBirthday(dtf.parse("06/07/1994"));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Colocation.addColocataire(thomas);
        Colocation.addColocataire(quentin);
        Colocation.addColocataire(maxime);

        Colocation.addExpense(new Expense(quentin, new Colocataire[]{quentin, maxime, thomas}, 100));
        Colocation.addExpense(new Expense(quentin, new Colocataire[]{thomas}, 200));
        Colocation.addExpense(new Expense(quentin, new Colocataire[]{quentin, maxime}, 300));
        Colocation.addExpense(new Expense(quentin, new Colocataire[]{maxime, thomas}, 400));
        ////////////////////////////////////////////////////////////////////

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

        ArrayList<Expense> expensesQuentin = Colocation.getExpensesOf(quentin);

        int amount = 0;
        for(Expense e : expensesQuentin)
            amount += e.getAmount();
        TextView expenses = (TextView) rootView.findViewById(R.id.expenses);
        expenses.setText(String.valueOf(amount));

        HashMap<String, Integer> shares = new HashMap<>();
        for(Colocataire c : Colocation.getColocataires()){
                shares.put(c.getFirstname(), 0);
        }
        shares.remove(quentin.getFirstname());

        for(Expense e : expensesQuentin){
            Log.d("tato", String.valueOf(e.getSharedAmount()));
            for(Colocataire c : e.getShares()){
                shares.put(c.getFirstname(), shares.get(c.getFirstname())+e.getSharedAmount());
            }
        }

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
