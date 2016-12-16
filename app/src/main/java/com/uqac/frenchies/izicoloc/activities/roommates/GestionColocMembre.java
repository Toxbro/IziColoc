package com.uqac.frenchies.izicoloc.activities.roommates;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uqac.frenchies.izicoloc.R;
import com.uqac.frenchies.izicoloc.activities.main.MainMenu;
import com.uqac.frenchies.izicoloc.tools.classes.Colocataire;
import com.uqac.frenchies.izicoloc.tools.classes.Colocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GestionColocMembre extends AppCompatActivity {
    private ListView listMember;
    private String codeColoc;
    private String idUser;
    private ArrayList<String> listMembre = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_coloc_membres);
        /*codeColoc="";
        idUser = "";
        try {
            String res = getIntent().getStringExtra("idUser");
            if(!res.isEmpty()){
                idUser = res;
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        try {
            String res = getIntent().getStringExtra("codeColoc");
            if(!res.isEmpty()){
                codeColoc = res;
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }*/
        listMember = (ListView) findViewById(R.id.listmember);
        ArrayList<Colocataire> tmp = Colocation.getColocataires();

        //System.out.println(Colocation.getColocataire(0).getFirstname()+" "+Colocation.getColocataire(0).getLastname());
        //System.out.println("ALLLLLLLLLLLLLLLLLLOOOOOA"+Colocation.getColocataires().size());
        //System.out.println(Colocation.getColocataire(1).getFirstname()+" "+Colocation.getColocataire(1).getLastname());
        //System.out.println(Colocation.getColocataire(2).getFirstname()+" "+Colocation.getColocataire(2).getLastname());
        for(int i=0; i< tmp.size(); i++){
            System.out.println(tmp.get(i).getFirstname()+" "+tmp.get(i).getLastname());
            listMembre.add(tmp.get(i).getFirstname()+" "+tmp.get(i).getLastname());
        }
        listMember.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,listMembre));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.goMenu:
                backToAccueil();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void backToAccueil() {
        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
        intent.putExtra("codeColoc", codeColoc);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }
}
