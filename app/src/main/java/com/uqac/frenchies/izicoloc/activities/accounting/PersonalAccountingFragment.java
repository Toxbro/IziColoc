package com.uqac.frenchies.izicoloc.activities.accounting;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uqac.frenchies.izicoloc.R;
import com.uqac.frenchies.izicoloc.tools.Parser;

import org.xmlpull.v1.XmlPullParser;

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

        String path = this.getArguments().getString("path");

        String data = Parser.getInformation(path, "expense2");

        TextView expenses = (TextView) rootView.findViewById(R.id.expenses);
        expenses.setText(data);
        return rootView;
    }
}
