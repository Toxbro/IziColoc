package com.uqac.frenchies.izicoloc.activities.accounting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uqac.frenchies.izicoloc.R;
import com.uqac.frenchies.izicoloc.activities.classes.Profile;

public class AccountingFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public AccountingFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AccountingFragment newInstance(int sectionNumber) {
        AccountingFragment fragment = new AccountingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_personalaccounting, container, false);
//        if(getArguments().getInt(ARG_SECTION_NUMBER) == 0) {
//            rootView = inflater.inflate(R.layout.fragment_personalaccounting, container, false);
//            TextView FirstName = (TextView) rootView.findViewById(R.id.FirstName);
//            FirstName.setText(Profile.getFirstname());
//        }
//        else if(getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
//            rootView = inflater.inflate(R.layout.fragment_commonaccounting, container, false);
//            TextView LastName = (TextView) rootView.findViewById(R.id.LastName);
//            LastName.setText(Profile.getLastname());
//        }
        View rootView = inflater.inflate(R.layout.fragment_personalaccounting, container, false);
        TextView FirstName = (TextView) rootView.findViewById(R.id.FirstName);
        FirstName.setText("Hello !");
        Log.d("AccountingFragment", "Toto");
        return rootView;
    }
}
