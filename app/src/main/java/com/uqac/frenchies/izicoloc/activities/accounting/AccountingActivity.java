package com.uqac.frenchies.izicoloc.activities.accounting;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.uqac.frenchies.izicoloc.R;
import com.uqac.frenchies.izicoloc.tools.classes.Colocataire;
import com.uqac.frenchies.izicoloc.tools.classes.Colocation;
import com.uqac.frenchies.izicoloc.tools.classes.Expense;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AccountingActivity extends AppCompatActivity{

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Colocation.resetExpenses();

//        String path =  getFilesDir().getPath()+"/data.xml";

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

        Colocation.addExpense(quentin, new Expense(quentin, new Colocataire[]{quentin, maxime, thomas}, 100, "27/10/2016", "Courses"));
        Colocation.addExpense(quentin, new Expense(quentin, new Colocataire[]{thomas}, 200, "05/11/2016", "Montréal"));
        Colocation.addExpense(quentin, new Expense(quentin, new Colocataire[]{quentin, maxime}, 300, "12/11/2016", "Restaurant Montréal"));
        Colocation.addExpense(quentin, new Expense(quentin, new Colocataire[]{maxime, thomas}, 400, "21/11/2016", "NYC"));
        ////////////////////////////////////////////////////////////////////
//
//        coloc.parse(path);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accounting, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            Bundle bundle = new Bundle();
//            bundle.putString("path", getFilesDir().getPath()+"/data.xml");
            switch(position){
                case 0: {
                    Fragment fragment = PersonalAccountingFragment.newInstance();
//                    fragment.setArguments(bundle);
                    return fragment;
                }
                case 1: {
                    Fragment fragment = CommonAccountingFragment.newInstance();
//                    fragment.setArguments(bundle);
                    return fragment;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Personal";
                case 1:
                    return "Common";
            }
            return null;
        }
    }
}
