package com.uqac.frenchies.izicoloc.activities.menus_semaine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.uqac.frenchies.izicoloc.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityMenusSemaine extends AppCompatActivity
{
    ListView listeMenus;
    List<Menu> menus;
    MenuAdapter menuAdapter;

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

    private void initialiserListe()
    {
        menuAdapter = new MenuAdapter(ActivityMenusSemaine.this, menus);
        listeMenus.setAdapter(menuAdapter);
        listeMenus.setTextFilterEnabled(true);

        loadData();

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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        for(int i=0 ; i<menus.size(); i++)
        {
            editor.putString(jours.get(i) + "m", menus.get(i).getMidi()); //Forme "jour" + m (matin)
            editor.putString(jours.get(i) + "s", menus.get(i).getSoir()); //Forme "jour" + s (soir)
        }
        editor.commit();
    }

    private void loadData()
    {
        for(int i=0 ; i<jours.size() ; i++)
        {
            if(sharedPreferences.contains(jours.get(i) + "m") && sharedPreferences.contains(jours.get(i) + "s"))
                menus.add(new Menu(jours.get(i), sharedPreferences.getString(jours.get(i)+"m", new String()),
                                                 sharedPreferences.getString(jours.get(i)+"s", new String())));
            else
                menus.add(new Menu(jours.get(i), "-", "-"));
        }
        menus.get(0).setJour("Aujourd'hui");
        menus.get(1).setJour("Demain");
        menuAdapter.notifyDataSetChanged();
    }

    //Long clic sur un item de la liste
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
            return true; //Vérification clic long
        }
    }
}
