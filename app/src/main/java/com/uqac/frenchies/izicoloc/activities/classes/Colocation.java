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

    public static ArrayList<Colocataire> getColocataires(){
        return colocataires;
    }
}
