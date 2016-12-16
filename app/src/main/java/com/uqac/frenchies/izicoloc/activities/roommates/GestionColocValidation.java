package com.uqac.frenchies.izicoloc.activities.roommates;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.uqac.frenchies.izicoloc.activities.main.MainMenu;
import com.uqac.frenchies.izicoloc.tools.classes.AccesBDD;

import java.util.HashMap;
import java.util.Map;

public class GestionColocValidation extends AppCompatActivity {
    private String idUser;
    private TextView code;
    private String codeColoc;
    private Button confirmColoc, cancelColoc;
    private String setUrl = "http://maelios.zapto.org/izicoloc/insertColoc.php";
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_coloc_validation);
        codeColoc = getIntent().getStringExtra("codeColoc");
        idUser = getIntent().getStringExtra("idUser");
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        code = (TextView) findViewById(R.id.codeColoc);
        code.setText(codeColoc);
        confirmColoc = (Button) findViewById(R.id.confirmColoc);
        cancelColoc = (Button) findViewById(R.id.cancelColoc);
        cancelColoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionColocValidation.super.finish();
                //AccesBDD requete = new AccesBDD();
                //HashMap<String,String> params = new HashMap<String, String>();
                //params.put("mail_user","camarche@bell.com");
                //params.put("nom_user","marche");
                //params.put("prenom_user","ca");
                //params.put("reseau_user","facebook");
                //requete.setParams(getApplicationContext(),"insertUser", params);
                //Thread t1 = new Thread(requete);
                //t1.start();
                //new Thread(requete).start();
                /*try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                /*while(!requete.getOk()){
                    System.out.println("EEEEEEEEE"+requete.getOk());
                }
                /*t1.notifyAll();
                if(t1.isInterrupted()){
                    System.out.println("WWWWWWWWWWWWWWWWWWWWWWWW");
                }*/
                //while(t1.isAlive()){}
                /*while(t1.isAlive()){

                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBB"+requete.getResultat()+requete.getOk());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }*/

                //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBB" + requete.getResultat());
                //Toast.makeText(getApplicationContext(), requete.getResultat(), Toast.LENGTH_LONG).show();
            }
        });

        confirmColoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccesBDD req = new AccesBDD();
                HashMap<String,String> params = new HashMap<String, String>();
                params.put("id_user", idUser);
                params.put("code_coloc", codeColoc);
                req.setParams(getApplicationContext(),"insertColoc",params);
                new Thread(req).start();
                Intent intent = new Intent(getApplicationContext(), GestionColocMain.class);
                intent.putExtra("codeColoc",codeColoc);
                intent.putExtra("idUser",idUser);
                startActivity(intent);
                /*StringRequest request = new StringRequest(Request.Method.POST, setUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            Intent intent = new Intent(getApplicationContext(), GestionColocMain.class);
                            //intent.putExtra("codeColoc",codeColoc);
                            //intent.putExtra("idUser",idUser);
                            startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id_user", idUser);
                        params.put("code_coloc", codeColoc);

                        return params;
                    }
                };
                requestQueue.add(request);*/
            }
        });

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

    public void backToAccueil(){
        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
        intent.putExtra("codeColoc", codeColoc);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }
}
