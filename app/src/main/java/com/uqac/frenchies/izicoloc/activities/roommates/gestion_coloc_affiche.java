package com.uqac.frenchies.izicoloc.activities.roommates;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.uqac.frenchies.izicoloc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class gestion_coloc_affiche extends AppCompatActivity {
    private String codeColoc;
    private int idUser;
    private TextView code;
    private ImageView qrCode;
    private Button quitColoc;

    private String getUrl = "http://maelios.zapto.org/izicoloc/getUser.php";
    private String getUrlColoc = "http://maelios.zapto.org/izicoloc/getColoc.php";
    private String getUrlAllColoc = "http://maelios.zapto.org/izicoloc/getAllColoc.php";
    private String setUrl = "http://maelios.zapto.org/izicoloc/insertColoc.php";
    private String delUrl = "http://maelios.zapto.org/izicoloc/deleteColoc.php";
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_coloc_affiche);
        qrCode = (ImageView) findViewById(R.id.qrCode);
        quitColoc = (Button) findViewById(R.id.quitColoc);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        idUser = getIntent().getIntExtra("idUser",0);
        try{
            codeColoc = getIntent().getStringExtra("codeColoc");
        }
        catch (NullPointerException e){

        }finally {
            codeColoc="";
        }
        quitColoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, delUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(getApplicationContext(), gestion_coloc_main.class);
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
        if(codeColoc.isEmpty()){
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
                                    code = (TextView) findViewById(R.id.codeColoc);
                                    code.setText(codeColoc);
                                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                                    try{
                                        BitMatrix bitMatrix = multiFormatWriter.encode(codeColoc, BarcodeFormat.QR_CODE,200,200);
                                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                        qrCode.setImageBitmap(bitmap);
                                    }
                                    catch (WriterException e){
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    StringRequest postRequest = new StringRequest(Request.Method.POST, getUrlAllColoc,
                                            new Response.Listener<String>()
                                            {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject jo = new JSONObject(response);
                                                        JSONArray colocs = jo.getJSONArray("colocs");
                                                        Boolean exist;
                                                        do{
                                                            exist = false;
                                                            codeColoc=generate(10);
                                                            for(int i=0; i<colocs.length();i++) {
                                                                String[] tmp = colocs.getString(i).split("\"");
                                                                if (tmp[11] == codeColoc) {
                                                                    exist = true;
                                                                }
                                                            }
                                                        }while(exist);
                                                        StringRequest request = new StringRequest(Request.Method.POST, setUrl, new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                code = (TextView) findViewById(R.id.codeColoc);
                                                                code.setText(codeColoc);
                                                                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                                                                try{
                                                                    BitMatrix bitMatrix = multiFormatWriter.encode(codeColoc, BarcodeFormat.QR_CODE,200,200);
                                                                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                                                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                                                    qrCode.setImageBitmap(bitmap);
                                                                }
                                                                catch (WriterException e){
                                                                    e.printStackTrace();
                                                                }
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
        else{
            code = (TextView) findViewById(R.id.codeColoc);
            code.setText(codeColoc);
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try{
                BitMatrix bitMatrix = multiFormatWriter.encode(codeColoc, BarcodeFormat.QR_CODE,200,200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                qrCode.setImageBitmap(bitmap);
            }
            catch (WriterException e){
                e.printStackTrace();
            }
        }
    }

    public static String generate(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuffer pass = new StringBuffer();
        for(int x=0;x<length;x++)   {
            int i = (int)Math.floor(Math.random() * (chars.length() -1));
            pass.append(chars.charAt(i));
        }
        return pass.toString();
    }
}