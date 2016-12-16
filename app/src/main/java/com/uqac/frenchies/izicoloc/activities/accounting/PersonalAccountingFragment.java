package com.uqac.frenchies.izicoloc.activities.accounting;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.uqac.frenchies.izicoloc.R;
import com.uqac.frenchies.izicoloc.tools.classes.Colocataire;
import com.uqac.frenchies.izicoloc.tools.classes.Colocation;
import com.uqac.frenchies.izicoloc.tools.classes.Profile;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.round;

public class PersonalAccountingFragment extends Fragment {

    private String expensesText;
    private ArrayList<String> result;
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
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_personalaccounting, container, false);

        updateData();

        TextView expenses = (TextView) rootView.findViewById(R.id.expenses);
        expenses.setText(expensesText);

        ArrayAdapter<String> adapter = new sharedExpense(this.getContext(), 0, result);
        ListView expensesList = (ListView) rootView.findViewById(R.id.sharedExpensesList);

        expensesList.setAdapter(adapter);
        return rootView;
    }

    private void updateData(){
        Colocataire owner = Colocation.getColocataireById(Profile.getEmail());

        expensesText = String.valueOf("Balance : "+Colocation.getBalance(owner)+" $");

        HashMap<String, Float> shares = new HashMap<>();
        for(Colocataire c : Colocation.getColocataires()){
            shares.put(c.getFirstname(), Colocation.getShare(owner, c) - Colocation.getShare(c, owner));
        }
        shares.remove(owner.getFirstname());

        result = new ArrayList<>();
        for(Map.Entry<String, Float> entry : shares.entrySet()){
            result.add(entry.getKey()+":"+String.valueOf(round(entry.getValue())));
        }
    }
}
