package com.uqac.frenchies.izicoloc.activities.classes;

import java.util.ArrayList;

/**
 * Created by quentin on 16-11-19.
 */

public class Colocation {

    private static ArrayList<Colocataire> colocataires;


    public Colocation(){}

    public static void addColocataire(Colocataire c){
        colocataires.add(c);
    }

    public static void addColocataires(String s){
        String[] splitted = s.split("/");
        for(String temp : splitted){
            addColocataire(new Colocataire(temp));
        }
    }

    public static ArrayList<Colocataire> getColocataires(){
        return colocataires;
    }

    public static Colocataire getColocataire(int id) {
        for(Colocataire c : colocataires){
            if(c.getId() == id)
                return c;
        }
        return null;
    }

    public static String convertToString(){
        String result = "";
        for(Colocataire c : colocataires){
            result+=c.toString()+"/";
        }
        result = result.substring(0, result.length()-1);
        return result;
    }
}
