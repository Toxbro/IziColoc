package com.uqac.frenchies.izicoloc.activities.accounting;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by quentin on 16-11-20.
 */

public class CommonAccountingFragment extends Fragment{

    private ArrayAdapter<Expense> adapter = null;
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

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExpenseDialog();
            }
        });

        adapter = new listExpense(this.getContext(), 0, Colocation.getExpenses());
        ListView expensesList = (ListView) rootView.findViewById(R.id.commonExpensesList);

        expensesList.setAdapter(adapter);

        expensesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeExpenseDialog(adapter, position);
                return false;
            }
        });
        return rootView;
    }

    private void removeExpenseDialog(final ArrayAdapter<Expense> adapter, final int pos){
        new AlertDialog.Builder(getActivity())
                .setTitle("Voulez-vous supprimer cette dépense ?")
                .setPositiveButton("Supprimer", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                    Expense e = adapter.getItem(pos);
                    adapter.remove(e);
                    Colocation.removeExpense(e);

                        removeExpenseFromDB(e);
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton){
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void addExpenseDialog()
    {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_addexpense, null);

        final EditText label = (EditText) view.findViewById(R.id.label);
        final EditText shares = (EditText) view.findViewById(R.id.shares);
        final EditText date = (EditText) view.findViewById(R.id.date);
        final EditText amount = (EditText) view.findViewById(R.id.amount);

        shares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                setShareMatesDialog(shares);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                String myFormat = "dd/MM/yyyy";
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRENCH);
                                date.setText(sdf.format(c.getTime()));

                            }
                        }, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE));
                dpd.show();
            }
        });

        new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {

                        String[] shareList = shares.getText().toString().split(", ");
                        Colocataire[] sharedWithColocs = new Colocataire[shareList.length];

                        for (int i = 0; i < shareList.length; i++) {
                            sharedWithColocs[i] = Colocation.getColocataireByName(shareList[i]);
                        }

                        if(sharedWithColocs.length != 0
                                && !amount.getText().toString().equals("")
                                && !date.getText().toString().equals("")
                                && !label.getText().toString().equals("")) {
                            Expense e = new Expense(
                                    Colocation.getColocataireById(Profile.getEmail()),
                                    sharedWithColocs,
                                    Integer.parseInt(amount.getText().toString()),
                                    date.getText().toString(),
                                    label.getText().toString());
                            Colocation.addExpense(e);
                            addExpenseDB(e);
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(getContext(), "Un champ est vide !", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton){
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void setShareMatesDialog(final EditText shares){
        final ArrayList SelectedItems = new ArrayList();  // Where we track the selected items
        ArrayList<Colocataire> colocs = Colocation.getColocataires();
        final String[] names = new String[colocs.size()];

        int i = 0;

        for(Colocataire c : colocs){
            names[i] = c.getFirstname() + " " + c.getLastname();
            i++;
        }

        new AlertDialog.Builder(getActivity())
            .setMultiChoiceItems(names, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            SelectedItems.add(which);
                        } else if (SelectedItems.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            SelectedItems.remove(Integer.valueOf(which));
                        }
                    }
                })
            // Set the action buttons
            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    String result = "";
                    for (int j = 0; j < SelectedItems.size(); j++) {
                        result += names[(int) SelectedItems.get(j)] +", ";
                    }
                    shares.setText(result);
                }
            })
            .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            })
        .show();
    }

    private void removeExpenseFromDB(final Expense e){
        String setUrl = "http://maelios.zapto.org/izicoloc/deleteDepense.php";
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, setUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("user_depense", e.getOwner().getEmail());
                params.put("libelle_depense", e.getLabel());

                return params;
            }
        };
        requestQueue.add(request);
    }

    private void addExpenseDB(final Expense e){
        String setUrl = "http://maelios.zapto.org/izicoloc/insertDepense.php";
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, setUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("user_depense", e.getOwner().getEmail());
                params.put("date_depense", e.getDate());
                params.put("montant_depense", String.valueOf(e.getAmount()));
                String shares = "";
                for(Colocataire c : e.getShares())
                    shares += c.getEmail()+",";
                shares = shares.substring(0, shares.length()-1);
                params.put("share_user_depense", shares);
                params.put("libelle_depense", e.getLabel());

                return params;
            }
        };
        requestQueue.add(request);
    }
}
