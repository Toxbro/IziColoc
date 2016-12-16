package com.uqac.frenchies.izicoloc.activities.accounting;

import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uqac.frenchies.izicoloc.R;
import com.uqac.frenchies.izicoloc.tools.classes.Colocataire;
import com.uqac.frenchies.izicoloc.tools.classes.Colocation;
import com.uqac.frenchies.izicoloc.tools.classes.Expense;
import com.uqac.frenchies.izicoloc.tools.classes.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

        Colocation.resetAccounts();

        readDataFromDB();

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

    private void readDataFromDB(){
        String setUrl = "http://maelios.zapto.org/izicoloc/getDepensesByColoc.php";
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, setUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray data = (new JSONObject(response)).getJSONArray("getDepensesByColoc");
                    if(data.length()!=0){
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject objet = data.getJSONObject(i);

                            String shares = objet.getString("share_user_depense");
                            String[] sharesColoc = shares.split(",");
                            Colocataire[] sharesColocList = new Colocataire[sharesColoc.length];
                            for (int j = 0; j < sharesColoc.length; j++)
                                sharesColocList[j] = Colocation.getColocataireById(sharesColoc[j]);

                            Colocation.addExpense(new Expense(Colocation.getColocataireById(objet.getString("user_depense")),
                                    sharesColocList, Float.parseFloat(objet.getString("montant_depense")),
                                    objet.getString("date_depense"), objet.getString("libelle_depense")));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("code_coloc", Colocation.getId());

                return params;
            }
        };
        requestQueue.add(request);
    }
}
