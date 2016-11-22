package com.uqac.frenchies.izicoloc.activities.roommates;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uqac.frenchies.izicoloc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GestionColocMain extends AppCompatActivity {
    private int idUser;
    private String codeColoc;
    private String getUrl = "http://maelios.zapto.org/izicoloc/getUser.php";
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_coloc_main);
        checkPermission();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        codeColoc="";
        idUser = 0;
        try {
            idUser = getIntent().getIntExtra("idUser", 0);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        finally {
            idUser = 0;
        }
        try {
            codeColoc = getIntent().getStringExtra("codeColoc");
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        finally {
            codeColoc = "";
        }
        //check si le user est en coloc
        StringRequest postRequest = new StringRequest(Request.Method.POST, getUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            JSONArray user = jo.getJSONArray("user");
                            if(user.length()!=0){
                                codeColoc = user.getString(0);
                                String[] tmp = codeColoc.split("\"");
                                codeColoc = tmp[3];
                            }
                            if(codeColoc.isEmpty()){
                                Intent intent = new Intent(GestionColocMain.this, GestionColocAccueil.class);
                                intent.putExtra("idUser",idUser);
                                startActivity(intent);
                            }
                            else{
                                Intent intent = new Intent(GestionColocMain.this, GestionColocAffiche.class);
                                intent.putExtra("idUser",idUser);
                                intent.putExtra("codeColoc",codeColoc);
                                startActivity(intent);
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
                params.put("id_user", Integer.toString(idUser));

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    private Boolean checkPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 0:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.d("Main", "OK");
                }
                Log.d("Main", "ELSE");
                break;

            default:
                break;
        }
    }
}
