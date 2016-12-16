package com.uqac.frenchies.izicoloc.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uqac.frenchies.izicoloc.R;
import com.uqac.frenchies.izicoloc.activities.accounting.AccountingActivity;
import com.uqac.frenchies.izicoloc.activities.authentication.Login;
import com.uqac.frenchies.izicoloc.tools.classes.Colocation;
import com.uqac.frenchies.izicoloc.tools.classes.Profile;
import com.uqac.frenchies.izicoloc.activities.roommates.GestionColocMain;
import com.uqac.frenchies.izicoloc.activities.listecourses.MainCourses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String idUser;
    private static String codeColoc;
    private ArrayList<String> listMail = new ArrayList<String>();
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.start();
        setCodeColoc("");
        idUser = "";
        try {
            String res = getIntent().getStringExtra("idUser");
            if(res!=null){
                idUser = res;
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        try {
            String res = getIntent().getStringExtra("codeColoc");
            if(res!=null){
                setCodeColoc(res);
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        if(Colocation.getColocataires().size()==0) {
            getCodeColoc(idUser);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.textView);
        nav_user.setText(Profile.getFirstname()+" "+ Profile.getLastname());
        ImageView imageView = (ImageView)hView.findViewById(R.id.imageView);
        imageView.setImageDrawable(Profile.getPicture());

        ImageButton roommatesButton = (ImageButton) findViewById(R.id.buttonRoommates);
        roommatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, GestionColocMain.class);
                intent.putExtra("idUser",idUser);
                startActivity(intent);
                //Activité de Maxime si colocation inexistante, vue globale de la colocation sinon

            }
        });

        ImageButton accountingButton = (ImageButton) findViewById(R.id.buttonAccounting);
        accountingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codeColoc.isEmpty()){
                    Intent intent = new Intent(MainMenu.this, GestionColocMain.class);
                    intent.putExtra("idUser",idUser);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainMenu.this, AccountingActivity.class);
                    intent.putExtra("idUser",idUser);
                    intent.putExtra("codeColoc",getCodeColoc());
                    MainMenu.this.startActivity(intent);
                }

            }
        });

        ImageButton shopButton = (ImageButton) findViewById(R.id.buttonShop);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Activité de Dylan
                if(codeColoc.isEmpty()){
                    Intent intent = new Intent(MainMenu.this, GestionColocMain.class);
                    intent.putExtra("idUser",idUser);
                    startActivity(intent);
                }else {
                    Intent myIntent = new Intent(MainMenu.this, MainCourses.class);
                    myIntent.putExtra("idUser",idUser);
                    myIntent.putExtra("codeColoc",getCodeColoc());
                    MainMenu.this.startActivity(myIntent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //Logging out
        if (id == R.id.logout) {
            logout();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        Login.getmInstance().logout();
        Intent intent = new Intent(MainMenu.this, Login.class);
        startActivity(intent);
    }

    public void getCodeColoc(final String idUser){
        String getUrl = "http://maelios.zapto.org/izicoloc/getCodeColoc.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, getUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            JSONArray user = jo.getJSONArray("getCodeColoc");
                            if(user!=null){
                                setCodeColoc(user.getJSONObject(0).getString("code_coloc"));
                                chargeColoc(user.getJSONObject(0).getString("code_coloc"),idUser);
                                Colocation.setId(user.getJSONObject(0).getString("code_coloc"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("id_user", idUser);

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    public void chargeColoc(final String codeColoc, final String idUser){
        //System.out.println("CodeColoc "+codeColoc+" idUser "+idUser);
        if(!codeColoc.isEmpty()) {
            String getUrlAllColoc = "http://maelios.zapto.org/izicoloc/getAllColocByCode.php";
            StringRequest postRequest = new StringRequest(Request.Method.POST, getUrlAllColoc,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONArray users = new JSONArray();
                            try {
                                JSONObject jo = new JSONObject(response);
                                users = jo.getJSONArray("getAllColocByCode");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (users.length() != 0) {
                                for (int i = 0; i < users.length(); i++) {
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
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("code_coloc", codeColoc);

                    return params;
                }
            };
            requestQueue.add(postRequest);
        }

    }

    public void addUser(final ArrayList<String> mail){
        String getUrlUser = "http://maelios.zapto.org/izicoloc/getAllUser.php";
        StringRequest postReq = new StringRequest(Request.Method.POST, getUrlUser,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        JSONArray listUser = new JSONArray();
                        try {
                            JSONObject jo = new JSONObject(response);
                            listUser = jo.getJSONArray("getAllUser");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for(int i=0; i<listUser.length(); i++) {
                            try {
                                if (mail.contains(listUser.getJSONObject(i).getString("mail_user"))) {
                                    com.uqac.frenchies.izicoloc.tools.classes.Colocataire coloc = new com.uqac.frenchies.izicoloc.tools.classes.Colocataire();
                                    coloc.setEmail(listUser.getJSONObject(i).getString("mail_user"));
                                    coloc.setFirstname(listUser.getJSONObject(i).getString("prenom_user"));
                                    coloc.setLastname(listUser.getJSONObject(i).getString("nom_user"));
                                    com.uqac.frenchies.izicoloc.tools.classes.Colocation.addColocataire(coloc);
                                    System.out.println("////////////////////////////////////////");
                                    System.out.println(coloc.getEmail());
                                    System.out.println(coloc.getFirstname());
                                    System.out.println(coloc.getLastname());
                                    System.out.println(Colocation.getColocataires().size());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                return params;
            }
        };
        requestQueue.add(postReq);
    }

    public void setColocation(){

    }

    public String getCodeColoc() {
        return codeColoc;
    }

    public void setCodeColoc(String codeColoc) {
        this.codeColoc = codeColoc;
    }
}
