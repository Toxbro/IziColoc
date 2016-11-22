package com.uqac.frenchies.izicoloc.activities.classes;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by quentin on 16-11-17.
 */

public class Expense {

    private Colocataire owner;
    private int amount;
    private ArrayList<Colocataire> shares;
    private String label;
    private String date;

    public Expense(Colocataire owner, Colocataire[] shares, int amount, String date, String label){
        this.owner = owner;
        this.shares = new ArrayList<>(Arrays.asList(shares));
        this.amount = amount;
        this.date = date;
        this.label = label;
    }

//    public Expense(String s){
//        String[] splitted = s.split(";");
//        for(String temp : splitted){
//            String[] value = temp.split(":");
//            switch(value[0]){
//                case "owner": { this.owner = Colocation.getColocataire(Integer.parseInt(value[1])); }
//                case "amount": { this.amount = Integer.parseInt(value[1]); }
//                case "shares": {
//                    String[] splittedagain = s.split("/");
//                    ArrayList<Colocataire> colocataires = new ArrayList<>();
//                    for(String tempagain : splittedagain){
//                        colocataires.add(new Colocataire(tempagain));
//                    }
//                    this.shares = colocataires.toArray(new Colocataire[colocataires.size()]); }
//            }
//        }
//    }

    public Colocataire getOwner() {
        return owner;
    }

    public int getAmount() {
        return amount;
    }

    public Colocataire[] getShares() {
        if(shares.contains(owner)) {
            ArrayList<Colocataire> temp = new ArrayList<>(shares);
            temp.remove(owner);
            return temp.toArray(new Colocataire[temp.size()]);
        }
            else {
            return shares.toArray(new Colocataire[shares.size()]);
        }

    }

    public int getSharedAmount(){
        return amount/(shares.size());
    }

    public String getLabel() {
        return label;
    }

    public String getDate() {
        return date;
    }

//    public String toString(){
//        String result = "";
//        result += "owner:"+this.owner+";";
//        result += "amount:"+this.amount+";";
//        result += "shares:";
//
//        String temp = "";
//        for(Colocataire c : shares){
//            temp+=c.toString()+"/";
//        }
//        temp = result.substring(0, result.length()-1);
//        result += temp;
//        return result;
//    }
}
