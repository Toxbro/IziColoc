package com.uqac.frenchies.izicoloc.activities.main;

import android.content.Intent;
import android.os.Bundle;
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

import com.uqac.frenchies.izicoloc.R;
import com.uqac.frenchies.izicoloc.activities.accounting.AccountingActivity;
import com.uqac.frenchies.izicoloc.activities.authentication.Login;
import com.uqac.frenchies.izicoloc.tools.classes.Profile;
import com.uqac.frenchies.izicoloc.activities.roommates.GestionColocMain;
import com.uqac.frenchies.izicoloc.activities.listecourses.MainCourses;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String idUser;
    private String codeColoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        codeColoc="";
        idUser = "mr@test.com";
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

        //Toast.makeText(getApplicationContext(), idUser, Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(MainMenu.this, AccountingActivity.class);
                MainMenu.this.startActivity(intent);
            }
        });

        ImageButton shopButton = (ImageButton) findViewById(R.id.buttonShop);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Activité de Dylan
                Intent myIntent = new Intent(MainMenu.this, MainCourses.class);
                MainMenu.this.startActivity(myIntent);
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
}
