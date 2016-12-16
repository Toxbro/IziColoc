package com.uqac.frenchies.izicoloc.tools.classes;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


/**
 * Created by Maxime on 15/12/2016.
 */

public class AccesBDD extends Thread{
    private Boolean isOk;
    private static String resultat;
    private String getAllColocByCode = "http://maelios.zapto.org/izicoloc/getAllColocByCode.php";
    private String getAllColoc = "http://maelios.zapto.org/izicoloc/getAllColoc.php";
    private String getAllCourse = "http://maelios.zapto.org/izicoloc/getAllCourse.php";
    private String getAllMenu = "http://maelios.zapto.org/izicoloc/getAllMenu.php";
    private String getAllUser = "http://maelios.zapto.org/izicoloc/getAllUser.php";
    private String getCodeColoc = "http://maelios.zapto.org/izicoloc/getCodeColoc.php";
    private String getColoc = "http://maelios.zapto.org/izicoloc/getColoc.php";
    private String getDepenseByColoc = "http://maelios.zapto.org/izicoloc/getDepenseByColoc.php";
    private String getDepenseByUser = "http://maelios.zapto.org/izicoloc/getDepenseByUser.php";
    private String getUser = "http://maelios.zapto.org/izicoloc/getUser.php";
    private String insertColoc = "http://maelios.zapto.org/izicoloc/insertColoc.php";
    private String insertCourse = "http://maelios.zapto.org/izicoloc/insertCourse.php";
    private String insertDepense = "http://maelios.zapto.org/izicoloc/insertDepense.php";
    private String insertMenu = "http://maelios.zapto.org/izicoloc/insertMenu.php";
    private String insertUser = "http://maelios.zapto.org/izicoloc/insertUser.php";
    private String updateMenu = "http://maelios.zapto.org/izicoloc/updateMenu.php";
    private String updateUser = "http://maelios.zapto.org/izicoloc/updateUser.php";
    private String deleteAllCourse = "http://maelios.zapto.org/izicoloc/deleteAllCourse.php";
    private String deleteByNameCourse = "http://maelios.zapto.org/izicoloc/deleteByNameCourse.php";
    private String deleteColoc = "http://maelios.zapto.org/izicoloc/deleteColoc.php";
    private String deleteDepense = "http://maelios.zapto.org/izicoloc/deleteDepense.php";
    private String deleteUser = "http://maelios.zapto.org/izicoloc/deleteUser.php";

    private Context appContext;
    private String requeteName;
    private HashMap<String,String> paramsTab;
    private RequestQueue requestQueue;

    public void setParams(final Context appliContext, String nomReq, final HashMap<String,String> tabParams){
        this.resultat="";
        this.setOk(false);
        this.setAppContext(appliContext);
        this.setRequeteName(nomReq);
        this.setParamsTab(tabParams);
        this.requestQueue = Volley.newRequestQueue(this.getAppContext());
        this.requestQueue.start();
    }
    public void run(){
        String getUrl = "";
        switch(this.getRequeteName()){
            case "getAllColocByCode":
                getUrl = getAllColocByCode;
                break;
            case "getAllColoc":
                getUrl = getAllColoc;
                break;
            case "getAllCourse":
                getUrl = getAllCourse;
                break;
            case "getAllMenu":
                getUrl = getAllMenu;
                break;
            case "getAllUser":
                getUrl = getAllUser;
                break;
            case "getCodeColoc":
                getUrl = getCodeColoc;
                break;
            case "getColoc":
                getUrl = getColoc;
                break;
            case "getDepenseByColoc":
                getUrl = getDepenseByColoc;
                break;
            case "getDepenseByUser":
                getUrl = getDepenseByUser;
                break;
            case "getUser":
                getUrl = getUser;
                break;
            case "insertColoc":
                getUrl = insertColoc;
                break;
            case "insertCourse":
                getUrl = insertCourse;
                break;
            case "insertDepense":
                getUrl = insertDepense;
                break;
            case "insertMenu":
                getUrl = insertMenu;
                break;
            case "insertUser":
                getUrl = insertUser;
                break;
            case "updateMenu":
                getUrl = updateMenu;
                break;
            case "updateUser":
                getUrl = updateUser;
                break;
            case "deleteAllCourse":
                getUrl = deleteAllCourse;
                break;
            case "deleteByNameCourse":
                getUrl = deleteByNameCourse;
                break;
            case "deleteColoc":
                getUrl = deleteColoc;
                break;
            case "deleteDepense":
                getUrl = deleteDepense;
                break;
            case "deleteUser":
                getUrl = deleteUser;
                break;
        }
        StringRequest request = new StringRequest(Request.Method.POST, getUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("HELLOOOOOOOOOOOOO"+response);
                setResultat("1");
                System.out.println("CCCCCCCCCCCCCCC"+resultat+"DDDDDDDDDDD"+getResultat());
                setOk(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = (Map<String, String>) getParamsTab().clone();
                return params;
            }
        };
        requestQueue.add(request);
        //requestQueue.
        //requestQueue.stop();
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String res) {
        resultat = res;
    }

    public Boolean getOk() {
        return isOk;
    }

    public void setOk(Boolean ok) {
        isOk = ok;
    }

    public Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    public String getRequeteName() {
        return requeteName;
    }

    public void setRequeteName(String requeteName) {
        this.requeteName = requeteName;
    }

    public HashMap<String, String> getParamsTab() {
        return paramsTab;
    }

    public void setParamsTab(HashMap<String, String> paramsTab) {
        this.paramsTab = (HashMap<String,String>)paramsTab.clone();
    }
}
