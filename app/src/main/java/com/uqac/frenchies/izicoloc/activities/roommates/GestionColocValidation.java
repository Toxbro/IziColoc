package com.uqac.frenchies.izicoloc.activities.roommates;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uqac.frenchies.izicoloc.R;

import java.util.HashMap;
import java.util.Map;

public class GestionColocValidation extends AppCompatActivity {
    private int idUser;
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
        idUser = getIntent().getIntExtra("idUser",0);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        code = (TextView) findViewById(R.id.codeColoc);
        code.setText(codeColoc);
        confirmColoc = (Button) findViewById(R.id.confirmColoc);
        cancelColoc = (Button) findViewById(R.id.cancelColoc);
        cancelColoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionColocValidation.super.finish();
            }
        });

        confirmColoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, setUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            Intent intent = new Intent(getApplicationContext(), GestionColocMain.class);
                            /*intent.putExtra("codeColoc",codeColoc);
                            intent.putExtra("idUser",idUser);*/
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
                        params.put("id_user", Integer.toString(idUser));
                        params.put("code_coloc", codeColoc);

                        return params;
                    }
                };
                requestQueue.add(request);
            }
        });

    }
}
