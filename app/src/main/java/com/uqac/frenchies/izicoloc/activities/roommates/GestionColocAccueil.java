package com.uqac.frenchies.izicoloc.activities.roommates;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.uqac.frenchies.izicoloc.R;

public class GestionColocAccueil extends AppCompatActivity {
    private int idUser;
    private Button createColoc, joinColoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_coloc_accueil);
        idUser = getIntent().getIntExtra("idUser",0);
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
}
