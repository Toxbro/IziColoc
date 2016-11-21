package com.uqac.frenchies.izicoloc.activities.listecourses;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.uqac.frenchies.izicoloc.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listeCourses;
    List<Course> courses;
    CoursesAdapter listAdapter;

    GridView listeSuggestions;
    List<Course> suggestions;
    CoursesAdapter gridAdapter;

    AlertDialog.Builder boiteDialogueSuppression;

    List<String> donnees;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                boiteDialogueSuppression.setMessage("Supprimer tous les éléments ?").setPositiveButton("Yes", BoiteAjout)
                        .setNegativeButton("No", BoiteAjout).show();
            }
        });
        //Bouton d'upload
        FloatingActionButton boutonRefresh = (FloatingActionButton) findViewById(R.id.refresh);
        boutonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
            }
        });
        boiteDialogueSuppression = new AlertDialog.Builder(MainActivity.this);
        boiteDialogueSuppression.setTitle("Supprimer la liste de courses");

        listeCourses = (ListView) findViewById(R.id.listView); //Récupération de la listView
        listeSuggestions = (GridView) findViewById(R.id.gridView); //Récupération de la gridView

        donnees = new ArrayList<String>();

        courses = new ArrayList<Course>();
        initialiserListe();

        suggestions = initialiserSuggestions();
        initialiserGrid();
    }

    private List<Course> initialiserSuggestions()
    {
        List<Course> initSugg = new ArrayList<Course>();
        initSugg.add(new Course(Color.RED, "Raclette", "Une meule"));
        initSugg.add(new Course(Color.RED, "Bière", "Un pack"));
        initSugg.add(new Course(Color.RED, "Pâtes", "3 paquets"));
        initSugg.add(new Course(Color.RED, "Nuggets", "1 paquet"));
        initSugg.add(new Course(Color.RED, "Poissons panés", "1 paquet"));
        initSugg.add(new Course(Color.RED, "Yaourts", "2 paquets"));
        return initSugg;
    }

    private void initialiserListe()
    {
        listAdapter = new CoursesAdapter(MainActivity.this, courses);
        listeCourses.setAdapter(listAdapter);
        listeCourses.setTextFilterEnabled(true);

        listeCourses.setOnItemClickListener(new ItemList());
        listeCourses.setOnItemLongClickListener(new LongItemList());
    }

    private void initialiserGrid()
    {
        gridAdapter = new CoursesAdapter(MainActivity.this, suggestions);
        listeSuggestions.setAdapter(gridAdapter);
        listeSuggestions.setTextFilterEnabled(true);

        listeSuggestions.setOnItemClickListener(new ItemGrid());
    }

    //Demande du produit
    private void afficherDialogues()
    {
        final EditText input = new EditText(MainActivity.this);
        new AlertDialog.Builder(MainActivity.this)
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
        final EditText input = new EditText(MainActivity.this);
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Ajouter la quantité")
                .setView(input)
                .setPositiveButton("Continuer", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        String quant = input.getText().toString();
                        courses.get(courses.size()-1).setQuantite(quant);

                        donnees.add(courses.get(courses.size()-1).getProduit());
                        donnees.add(courses.get(courses.size()-1).getQuantite());

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

    private void upload()
    {
        //Envoie des donnees
    }

    private void download()
    {
        //Récupération des suggestions et de la liste
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
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Supprimer le produit : " + courses.get(pos).getProduit())
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int whichButton)
                                {
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
                    suggestions.remove(i);
                    break;
                }
            gridAdapter.notifyDataSetChanged();
        }
    }
}
