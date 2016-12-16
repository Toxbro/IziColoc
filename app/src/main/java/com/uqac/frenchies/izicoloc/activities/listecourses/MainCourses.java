package com.uqac.frenchies.izicoloc.activities.listecourses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainCourses extends AppCompatActivity
{
    ListView listeCourses;
    List<Course> courses;
    CoursesAdapter listAdapter;

    GridView listeSuggestions;
    List<Course> suggestions;
    CoursesAdapter gridAdapter;

    AlertDialog.Builder boiteDialogueSuppression;

    SharedPreferences sharedPreferences;
    int nbSuggestion = 0;
    String codeColoc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        try{
            String res = getIntent().getStringExtra("codeColoc");
            if(res != null)
                codeColoc = res;
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
        
        //Bouton d'ajout 1
        FloatingActionButton boutonAjout = (FloatingActionButton) findViewById(R.id.ajout);
        boutonAjout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Boîte de dialogue d'ajout
                afficherDialogues();
            }
        });
        //Bouton de suppression
        FloatingActionButton boutonRetrait = (FloatingActionButton) findViewById(R.id.supprmier);
        boutonRetrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boiteDialogueSuppression.setMessage("Supprimer tous les éléments ?").setPositiveButton("Oui", BoiteAjout)
                        .setNegativeButton("Non", BoiteAjout).show();
            }
        });

        sharedPreferences = getPreferences(MODE_PRIVATE); //Données locales

        boiteDialogueSuppression = new AlertDialog.Builder(MainCourses.this);
        boiteDialogueSuppression.setTitle("Supprimer la liste de courses");

        listeCourses = (ListView) findViewById(R.id.listView); //Récupération de la listView
        listeSuggestions = (GridView) findViewById(R.id.gridView); //Récupération de la gridView

        courses = new ArrayList<Course>();
        initialiserListe();

        suggestions = initialiserSuggestions();
        initialiserGrid();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(int i=0 ; i<courses.size() ; i++)
        {
            editor.putString("cp" + Integer.toString(i), courses.get(i).getProduit());
            editor.putString("cq" + Integer.toString(i), courses.get(i).getQuantite());
        }
        editor.commit();
    }

    private List<Course> initialiserSuggestions()
    {
        List<Course> initSugg = new ArrayList<Course>();

        for(int i=0 ; i<9 ; i++)
        {
            if(sharedPreferences.contains(Integer.toString(i)+"p"))
            {
                initSugg.add(new Course(Color.RED, sharedPreferences.getString(Integer.toString(i) + "p", new String()),
                        sharedPreferences.getString(Integer.toString(i) + "q", new String())));
            }
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

        return initSugg;
    }

    private void initialiserListe()
    {
        listAdapter = new CoursesAdapter(MainCourses.this, courses);
        listeCourses.setAdapter(listAdapter);
        listeCourses.setTextFilterEnabled(true);

        if(internetConnection())
            chargementDistant();
        else
            chargementLocal();

        listeCourses.setOnItemClickListener(new ItemList());
        listeCourses.setOnItemLongClickListener(new LongItemList());
    }

    private void initialiserGrid()
    {
        gridAdapter = new CoursesAdapter(MainCourses.this, suggestions);
        listeSuggestions.setAdapter(gridAdapter);
        listeSuggestions.setTextFilterEnabled(true);

        listeSuggestions.setOnItemClickListener(new ItemGrid());
    }

    //Demande du produit
    private void afficherDialogues()
    {
        final EditText input = new EditText(MainCourses.this);
        new AlertDialog.Builder(MainCourses.this)
                .setTitle("Ajouter un produit")
                .setView(input)
                .setPositiveButton("Continuer", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        String prod = input.getText().toString();
                        courses.add(new Course(Color.RED, prod, ""));
                        listAdapter.notifyDataSetChanged();
                        afficherDialogues2();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton){}
                })
                .show();
    }

    //Demande de la quantité
    private void afficherDialogues2()
    {
        final EditText input = new EditText(MainCourses.this);
        new AlertDialog.Builder(MainCourses.this)
                .setTitle("Ajouter la quantité")
                .setView(input)
                .setPositiveButton("Continuer", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        String quant = input.getText().toString();
                        courses.get(courses.size()-1).setQuantite(quant);

                        addLastEntry();

                        listAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        courses.remove(courses.size()-1);
                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

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

    private void addLastEntry()
    {
        addElemBDD();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Integer.toString(nbSuggestion)+"p", courses.get(courses.size()-1).getProduit());
        editor.putString(Integer.toString(nbSuggestion)+"q", courses.get(courses.size()-1).getQuantite());
        nbSuggestion++;
        editor.commit();
    }

    //Boîte de dialogue
    DialogInterface.OnClickListener BoiteAjout = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    courses.clear();
                    viderBDD();
                    courses = new ArrayList<Course>();
                    initialiserListe();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    //Clic sur un item de la liste
    class ItemList implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            //Récupération de l'élément cliqué
            ViewGroup vg = (ViewGroup) view;
            TextView tv = (TextView)vg.findViewById(R.id.produit);
            //Recherche dans la liste
            for(int i=0 ; i<courses.size() ; i++)
                if(courses.get(i).getProduit().equals(tv.getText().toString()))
                {
                    //Changement de couleur
                    if(courses.get(i).getColor() == Color.RED)
                        courses.get(i).setColor(Color.GREEN);
                    else
                        courses.get(i).setColor(Color.RED);
                    break;
                }
            listAdapter.notifyDataSetChanged();
        }
    }

    //Clic sur un item de la liste
    class LongItemList implements AdapterView.OnItemLongClickListener
    {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
        {
            //Récupération de l'élément cliqué
            ViewGroup vg = (ViewGroup) view;
            TextView tv = (TextView)vg.findViewById(R.id.produit);
            //Recherche dans la liste
            for(int i=0 ; i<courses.size() ; i++)
                if(courses.get(i).getProduit().equals(tv.getText().toString()))
                {
                    final int pos = i;
                    new AlertDialog.Builder(MainCourses.this)
                            .setTitle("Supprimer le produit : " + courses.get(pos).getProduit())
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int whichButton)
                                {
                                    delElemBDD(courses.get(pos).getProduit());
                                    courses.remove(pos);
                                    listAdapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Non", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int whichButton){}
                            })
                            .show();
                    break;
                }
            return true; //Vérification clic long
        }
    }

    //Clic sur un item de la liste
    class ItemGrid implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            //Récupération de l'élément cliqué
            ViewGroup vg = (ViewGroup) view;
            TextView tv = (TextView)vg.findViewById(R.id.produit);
            //Recherche dans la liste
            for(int i=0 ; i<suggestions.size() ; i++)
                if(suggestions.get(i).getProduit().equals(tv.getText().toString()))
                {
                    courses.add(suggestions.get(i));
                    addLastEntry();
                    suggestions.remove(i);
                    break;
                }
            gridAdapter.notifyDataSetChanged();
        }
    }

    private void chargementLocal()
    {
        boolean elem = true;
        int i = 0;
        while(elem)
        {
            if(sharedPreferences.contains("cq" + Integer.toString(i)))
                courses.add(new Course(Color.RED, sharedPreferences.getString("cp" + Integer.toString(i), new String()),
                                                  sharedPreferences.getString("cq" + Integer.toString(i), new String())));
            else
                elem = false;
            i++;
        }
        listAdapter.notifyDataSetChanged();
    }

    private void chargementDistant()
    {
        String getUrl = "http://maelios.zapto.org/izicoloc/getAllCourse.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, getUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        String[] tmp = response.split("\"");
                        if(tmp.length > 16) //Au moins un élément dans la liste
                            for(int i=9 ; i<tmp.length ; i+=16)
                                courses.add(new Course(Color.RED, tmp[i], tmp[i+4]));
                        listAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(MainCourses.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("code_coloc", codeColoc);

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    private void addElemBDD()
    {
        if(internetConnection())
        {
            String setUrl = "http://maelios.zapto.org/izicoloc/insertCourse.php";
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
                    params.put("code_coloc", codeColoc);
                    params.put("name_item", courses.get(courses.size() - 1).getProduit());
                    params.put("desc_item", courses.get(courses.size() - 1).getQuantite());

                    return params;
                }
            };
            requestQueue.add(request);
        }
    }

    private void delElemBDD(final String item)
    {
        if(internetConnection())
        {
            String setUrl = "http://maelios.zapto.org/izicoloc/deleteByNameCourse.php";
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
                    params.put("code_coloc", codeColoc);
                    params.put("name_item", item);

                    return params;
                }
            };
            requestQueue.add(request);
        }
    }

    private void viderBDD()
    {
        String setUrl = "http://maelios.zapto.org/izicoloc/deleteAllCourse.php";
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
                params.put("code_coloc", codeColoc);

                return params;
            }
        };
        requestQueue.add(request);
    }

    //Si l'appareil est connecté à internet
    private boolean internetConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else
            return false;
    }
}
