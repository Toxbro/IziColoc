package com.uqac.frenchies.izicoloc.activities.roommates;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.uqac.frenchies.izicoloc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GestionColocDetection extends AppCompatActivity {
    private int idUser;
    private Button scanColoc;
    private Button checkColoc;
    private EditText numColoc;
    private String codeColoc;
    private RequestQueue requestQueue;
    private String getUrl = "http://maelios.zapto.org/izicoloc/getColoc.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_coloc_detection);
        idUser = getIntent().getIntExtra("idUser",0);
        scanColoc = (Button) findViewById(R.id.scanColoc);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final Activity activity = this;
        scanColoc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
                            }
        });
        checkColoc = (Button) findViewById(R.id.checkColoc);
        checkColoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View v = view;
                numColoc = (EditText) findViewById(R.id.numColoc);
                codeColoc = numColoc.getText().toString();
                if(codeColoc != null){
                    if (!codeColoc.isEmpty()){
                       Toast.makeText(v.getContext(),"Code saisie : "+codeColoc,Toast.LENGTH_SHORT).show();
                        //vérification que le code existe
                        StringRequest postRequest = new StringRequest(Request.Method.POST, getUrl,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jo = new JSONObject(response);
                                            JSONArray colocs = jo.getJSONArray("colocs");
                                            if(colocs.length()!=0){
                                                Intent intent = new Intent(getApplicationContext(), GestionColocValidation.class);
                                                intent.putExtra("codeColoc",codeColoc);
                                                intent.putExtra("idUser",idUser);
                                                startActivity(intent);
                                            }
                                            else{
                                                Toast.makeText(v.getContext(), "Erreur le code " + codeColoc + " ne correspond à aucune colocation", Toast.LENGTH_LONG).show();
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
                                params.put("code_coloc", codeColoc);

                                return params;
                            }
                        };
                        requestQueue.add(postRequest);
                    }else{
                        Toast.makeText(v.getContext(),"Vous n'avez pas saisie de code",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(v.getContext(),"system failure",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "Vous avez annulé le scan", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, result.getContents(),Toast.LENGTH_SHORT).show();
                final String code = result.getContents();
                //vérification que le code existe
                StringRequest postRequest = new StringRequest(Request.Method.POST, getUrl,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jo = new JSONObject(response);
                                    JSONArray colocs = jo.getJSONArray("colocs");
                                    if(colocs.length()!=0){
                                        Intent intent = new Intent(getApplicationContext(), GestionColocValidation.class);
                                        intent.putExtra("codeColoc",code);
                                        intent.putExtra("idUser",idUser);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Erreur le QRCode ne correspond à aucune colocation", Toast.LENGTH_LONG).show();
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
                        params.put("code_coloc", code);

                        return params;
                    }
                };
                requestQueue.add(postRequest);
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
