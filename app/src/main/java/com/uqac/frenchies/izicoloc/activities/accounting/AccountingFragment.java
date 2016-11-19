package com.uqac.frenchies.izicoloc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View rootView = inflater.inflate(R.layout.fragment_personalaccounting, container, false);;
        if(getArguments().getInt(ARG_SECTION_NUMBER) == 0) {
            rootView = inflater.inflate(R.layout.fragment_personalaccounting, container, false);
            TextView FirstName = (TextView) rootView.findViewById(R.id.FirstName);
            FirstName.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            TextView LastName = (TextView) rootView.findViewById(R.id.LastName);
            LastName.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
            rootView = inflater.inflate(R.layout.fragment_commonaccounting, container, false);
        }
        return rootView;
    }
}
