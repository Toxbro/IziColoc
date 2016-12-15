package com.uqac.frenchies.izicoloc.activities.menus_semaine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityMenusSemaine extends AppCompatActivity
{
    ListView listeMenus;
    List<Menu> menus;
    MenuAdapter menuAdapter;
    String codeColoc = "XER4356";

    List<String> donnees;

    ArrayList<String> jours;
    int jourActuel;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus_semaine);

        sharedPreferences = getPreferences(MODE_PRIVATE); //Données locales

        jours = new ArrayList<>();

        listeMenus = (ListView) findViewById(R.id.listeMenus); //Récupération de la listView

        donnees = new ArrayList<String>();

        menus = new ArrayList<Menu>();
        initialiserJour();
        initialiserListe();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        sauvegardeLocale();
    }

    private void initialiserListe()
    {
        menuAdapter = new MenuAdapter(ActivityMenusSemaine.this, menus);
        listeMenus.setAdapter(menuAdapter);
        listeMenus.setTextFilterEnabled(true);

        loadData();

        listeMenus.setOnItemClickListener(new ClicList());
        listeMenus.setOnItemLongClickListener(new LongItemList());
    }

    private void initialiserJour()
    {
        jours = new ArrayList<String>();

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) //Brutal mais optimisé
        {
            case Calendar.MONDAY :
                jours.add("Lundi");
                jours.add("Mardi");
                jours.add("Mercredi");
                jours.add("Jeudi");
                jours.add("Vendredi");
                jours.add("Samedi");
                jours.add("Dimanche");
                break;
            case Calendar.TUESDAY :
                jours.add("Mardi");
                jours.add("Mercredi");
                jours.add("Jeudi");
                jours.add("Vendredi");
                jours.add("Samedi");
                jours.add("Dimanche");
                jours.add("Lundi");
                break;
            case Calendar.WEDNESDAY :
                jours.add("Mercredi");
                jours.add("Jeudi");
                jours.add("Vendredi");
                jours.add("Samedi");
                jours.add("Dimanche");
                jours.add("Lundi");
                jours.add("Mardi");
                break;
            case Calendar.THURSDAY :
                jours.add("Jeudi");
                jours.add("Vendredi");
                jours.add("Samedi");
                jours.add("Dimanche");
                jours.add("Lundi");
                jours.add("Mardi");
                jours.add("Mercredi");
                break;
            case Calendar.FRIDAY :
                jours.add("Vendredi");
                jours.add("Samedi");
                jours.add("Dimanche");
                jours.add("Lundi");
                jours.add("Mardi");
                jours.add("Mercredi");
                jours.add("Jeudi");
                break;
            case Calendar.SATURDAY :
                jours.add("Samedi");
                jours.add("Dimanche");
                jours.add("Lundi");
                jours.add("Mardi");
                jours.add("Mercredi");
                jours.add("Jeudi");
                jours.add("Vendredi");
                break;
            case Calendar.SUNDAY :
                jours.add("Dimanche");
                jours.add("Lundi");
                jours.add("Mardi");
                jours.add("Mercredi");
                jours.add("Jeudi");
                jours.add("Vendredi");
                jours.add("Samedi");
                break;
        }
    }

    //Sauvegarde locale de tous les menus
    private void saveData()
    {
        sauvegardeLocale();
        if (internetConnection())
            sauvegardeDistante();
    }

    private void loadData()
    {
        chargementLocal();
        if(internetConnection())
            chargementDistant();
        menus.get(0).setJour("Aujourd'hui");
        menus.get(1).setJour("Demain");
        menuAdapter.notifyDataSetChanged();
    }

    //Édite le jour cliqué et met à jour la BDD
    class ClicList implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            //Récupération de l'élément cliqué
            ViewGroup vg = (ViewGroup) view;
            TextView tv = (TextView)vg.findViewById(R.id.jour);
            //Recherche dans la liste
            for(int i=0 ; i<menus.size() ; i++)
                if(menus.get(i).getJour().equals(tv.getText().toString()))
                {
                    jourActuel = i;

                    final EditText input2 = new EditText(ActivityMenusSemaine.this);
                    //Définition du menu du soir
                    new AlertDialog.Builder(ActivityMenusSemaine.this)
                            .setTitle("Modifier le menu du soir")
                            .setView(input2)
                            .setPositiveButton("Valider", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int whichButton)
                                {
                                    String men = input2.getText().toString();
                                    menus.get(jourActuel).setSoir(men);
                                    saveData();
                                }
                            })
                            .setNegativeButton("Sauter", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int whichButton){}
                            })
                            .show();

                    final EditText input = new EditText(ActivityMenusSemaine.this);

                    //Définition du menu du midi
                    new AlertDialog.Builder(ActivityMenusSemaine.this)
                            .setTitle("Modifier le menu du matin")
                            .setView(input)
                            .setPositiveButton("Valider", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int whichButton)
                                {
                                    String men = input.getText().toString();
                                    menus.get(jourActuel).setMidi(men);
                                    saveData();
                                }
                            })
                            .setNegativeButton("Sauter", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int whichButton){}
                            })
                            .show();
                    menuAdapter.notifyDataSetChanged();
                }
        }
    }

    //Ajoute l'élément cliqué à la liste de courses
    class LongItemList implements AdapterView.OnItemLongClickListener
    {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
        {
            //Récupération de l'élément cliqué
            ViewGroup vg = (ViewGroup) view;
            TextView tv = (TextView)vg.findViewById(R.id.jour);
            //Recherche dans la liste
            for(int i=0 ; i<menus.size() ; i++)
                if(menus.get(i).getJour().equals(tv.getText().toString()))
                {
                    dialogueAjout1(i);
                    dialogueAjout2(i);
                }
            return true; //Vérification clic long
        }
    }

    private void chargementLocal()
    {
        for(int i=0 ; i<jours.size() ; i++)
        {
            if(sharedPreferences.contains(jours.get(i) + "m") && sharedPreferences.contains(jours.get(i) + "s"))
                menus.add(new Menu(jours.get(i), sharedPreferences.getString(jours.get(i)+"m", new String()),
                        sharedPreferences.getString(jours.get(i)+"s", new String())));
            else
                menus.add(new Menu(jours.get(i), "-", "-"));
        }
    }

    private void chargementDistant()
    {
        String getUrl = "http://maelios.zapto.org/izicoloc/getAllMenu.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, getUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        String[] tmp = response.split("\"");
                        if(tmp.length > 9) //Au moins un élément dans la liste
                        {
                            for (int i = 9 ; i<tmp.length ; i += 20)
                                for (int j = 0 ; j<menus.size() ; j++)
                                    if(tmp[i].equals(jours.get(j))) //Correspondance de jour
                                    {
                                        menus.get(j).setMidi(tmp[i + 4]);
                                        menus.get(j).setSoir(tmp[i + 8]);
                                    }
                        }
                        else
                        {
                            initBDD();
                        }
                        menuAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(ActivityMenusSemaine.this,error.toString(),Toast.LENGTH_LONG).show();
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

    private void sauvegardeLocale()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        for(int i=0 ; i<menus.size(); i++)
        {
            editor.putString(jours.get(i) + "m", menus.get(i).getMidi()); //Forme "jour" + m (matin)
            editor.putString(jours.get(i) + "s", menus.get(i).getSoir()); //Forme "jour" + s (soir)
        }
        editor.commit();
    }

    private void sauvegardeDistante()
    {
        if(internetConnection())
        {
            String setUrl = "http://maelios.zapto.org/izicoloc/updateMenu.php";
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.POST, setUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ActivityMenusSemaine.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("lunch_menu", menus.get(jourActuel).getMidi());
                    params.put("dinner_menu", menus.get(jourActuel).getSoir());
                    params.put("day_menu", jours.get(jourActuel));
                    params.put("code_coloc", codeColoc);
                    return params;
                }
            };
            requestQueue.add(request);
        }
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

    private void dialogueAjout1(final int index)
    {
        final EditText input = new EditText(ActivityMenusSemaine.this);
        new AlertDialog.Builder(ActivityMenusSemaine.this)
                .setTitle("Ajouter une decription de " + menus.get(index).getMidi() + " pour l'ajouter à la liste de courses")
                .setView(input)
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        addElemBDDCourses(menus.get(index).getMidi(), input.getText().toString());
                    }
                })
                .setNegativeButton("Ne pas ajouter", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton){}
                })
                .show();
    }

    private void dialogueAjout2(final int index)
    {
        final EditText input = new EditText(ActivityMenusSemaine.this);
        new AlertDialog.Builder(ActivityMenusSemaine.this)
                .setTitle("Ajouter une decription de " + menus.get(index).getSoir() + " pour l'ajouter à la liste de courses")
                .setView(input)
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        addElemBDDCourses(menus.get(index).getSoir(), input.getText().toString());
                    }
                })
                .setNegativeButton("Ne pas ajouter", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton){}
                })
                .show();
    }

    private void initBDD()
    {
        for(int i=0 ; i<jours.size() ; i++)
            initJourBDD(i);
    }

    private void initJourBDD(final int index)
    {
        String setUrl = "http://maelios.zapto.org/izicoloc/insertMenu.php";
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, setUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActivityMenusSemaine.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("lunch_menu", "-");
                params.put("dinner_menu", "-");
                params.put("day_menu", jours.get(index));
                params.put("code_coloc", codeColoc);
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void addElemBDDCourses(final String prod, final String descr)
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
                    params.put("name_item", prod);
                    params.put("desc_item", descr);

                    return params;
                }
            };
            requestQueue.add(request);
        }
    }
}
