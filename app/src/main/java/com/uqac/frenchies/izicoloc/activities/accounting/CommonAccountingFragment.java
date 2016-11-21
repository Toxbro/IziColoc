package com.uqac.frenchies.izicoloc.activities.accounting;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uqac.frenchies.izicoloc.R;
import com.uqac.frenchies.izicoloc.activities.classes.Profile;

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

        View rootView = inflater.inflate(R.layout.fragment_commonaccounting, container, false);
//            TextView LastName = (TextView) rootView.findViewById(R.id.LastName);
//            LastName.setText(Profile.getLastname());
        return rootView;
    }
}
