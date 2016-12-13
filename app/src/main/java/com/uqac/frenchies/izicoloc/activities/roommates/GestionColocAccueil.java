package com.uqac.frenchies.izicoloc.activities.roommates;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.uqac.frenchies.izicoloc.R;
import com.uqac.frenchies.izicoloc.activities.main.MainMenu;

public class GestionColocAccueil extends AppCompatActivity {
    private String idUser;
    private Button createColoc, joinColoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_coloc_accueil);
        idUser = getIntent().getStringExtra("idUser");
        createColoc = (Button) findViewById(R.id.createColoc);
        joinColoc = (Button) findViewById(R.id.joinColoc);
        createColoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GestionColocAffiche.class);
                intent.putExtra("idUser", idUser);
                startActivity(intent);
            }
        });

        joinColoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GestionColocDetection.class);
                intent.putExtra("idUser",idUser);
                startActivity(intent);
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
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }
}
