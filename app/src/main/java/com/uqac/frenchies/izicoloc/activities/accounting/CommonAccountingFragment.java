package com.uqac.frenchies.izicoloc.activities.accounting;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by quentin on 16-11-20.
 */

public class CommonAccountingFragment extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public CommonAccountingFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CommonAccountingFragment newInstance() {
        CommonAccountingFragment fragment = new CommonAccountingFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        ////////////////////////////////////////////////////////////////////
//        Colocataire thomas = new Colocataire();
//        thomas.setId(1849);
//        thomas.setFirstname("Thomas");
//        thomas.setLastname("Navarro");
//        thomas.setEmail("thomas.navarro@live.fr");
//        thomas.setPhone("0606060606");
//        DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
//        try {
//            thomas.setBirthday(dtf.parse("26/03/1994"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Colocataire quentin = new Colocataire();
//        quentin.setId(2016);
//        quentin.setFirstname("Quentin");
//        quentin.setLastname("Rollin");
//        quentin.setEmail("rollin.quentin@live.fr");
//        quentin.setPhone("0606060606");
//        try {
//            quentin.setBirthday(dtf.parse("05/04/1994"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Colocataire maxime = new Colocataire();
//        quentin.setId(1341);
//        quentin.setFirstname("Maxime");
//        quentin.setLastname("Roux");
//        quentin.setEmail("roux.maxime@live.fr");
//        quentin.setPhone("0606060606");
//        try {
//            quentin.setBirthday(dtf.parse("06/07/1994"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Colocation.addColocataire(thomas);
//        Colocation.addColocataire(quentin);
//        Colocation.addColocataire(maxime);
//
//        Colocation.addExpense(quentin, new Expense(quentin, new Colocataire[]{quentin, maxime, thomas}, 100, "27/10/2016", "Courses"));
//        Colocation.addExpense(quentin, new Expense(quentin, new Colocataire[]{thomas}, 200, "05/11/2016", "Montréal"));
//        Colocation.addExpense(quentin, new Expense(quentin, new Colocataire[]{quentin, maxime}, 300, "12/11/2016", "Restaurant Montréal"));
//        Colocation.addExpense(quentin, new Expense(quentin, new Colocataire[]{maxime, thomas}, 400, "21/11/2016", "NYC"));
//        ////////////////////////////////////////////////////////////////////

        View rootView = inflater.inflate(R.layout.fragment_commonaccounting, container, false);

        ArrayAdapter<Expense> adapter = new listExpense(this.getContext(), 0, Colocation.getExpenses());
        ListView expensesList = (ListView) rootView.findViewById(R.id.commonExpensesList);

        expensesList.setAdapter(adapter);

//            TextView LastName = (TextView) rootView.findViewById(R.id.LastName);
//            LastName.setText(Profile.getLastname());
        return rootView;
    }
}
