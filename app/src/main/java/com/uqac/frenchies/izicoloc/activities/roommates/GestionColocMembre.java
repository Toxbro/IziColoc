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
    private String getUrlAllColoc = "http://maelios.zapto.org/izicoloc/getAllColocByCode.php";
    private RequestQueue requestQueue;
    private ArrayList<String> listMembre = new ArrayList<String>();
    private ArrayList<String> listMail = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_coloc_membres);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        codeColoc="";
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
        }
        listMember = (ListView) findViewById(R.id.listmember);
        StringRequest postRequest = new StringRequest(Request.Method.POST, getUrlAllColoc,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        JSONArray users = new JSONArray();
                        try {
                            JSONObject jo = new JSONObject(response);
                            users = jo.getJSONArray("colocs");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(users.length()!=0){
                            for(int i=0; i<users.length(); i++){
                                try {
                                    listMail.add(users.getJSONObject(i).getString("id_user"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            addUser(listMail);
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("code_coloc", codeColoc);

                return params;
            }
        };
        requestQueue.add(postRequest);
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

    public void addUser(final ArrayList<String> mail){
        String getUrlUser = "http://maelios.zapto.org/izicoloc/getAllUser.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postReq = new StringRequest(Request.Method.POST, getUrlUser,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        JSONArray listUser = new JSONArray();
                        try {
                            JSONObject jo = new JSONObject(response);
                            listUser = jo.getJSONArray("users");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for(int i=0; i<listUser.length(); i++){
                            try {
                                if(mail.contains(listUser.getJSONObject(i).getString("mail_user"))){
                                    listMembre.add(listUser.getJSONObject(i).getString("prenom_user") + " " + listUser.getJSONObject(i).getString("nom_user"));
                                    com.uqac.frenchies.izicoloc.tools.classes.Colocataire coloc = new com.uqac.frenchies.izicoloc.tools.classes.Colocataire();
                                    coloc.setEmail(listUser.getJSONObject(i).getString("mail_user"));
                                    coloc.setFirstname(listUser.getJSONObject(i).getString("prenom_user"));
                                    coloc.setLastname(listUser.getJSONObject(i).getString("nom_user"));
                                    com.uqac.frenchies.izicoloc.tools.classes.Colocation.addColocataire(coloc);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        listMember.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,listMembre));
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                return params;
            }
        };
        requestQueue.add(postReq);
    }
}
