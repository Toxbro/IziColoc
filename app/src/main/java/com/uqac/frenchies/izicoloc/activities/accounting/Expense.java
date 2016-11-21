package com.uqac.frenchies.izicoloc.activities.accounting;

import com.uqac.frenchies.izicoloc.activities.classes.Colocataire;
import com.uqac.frenchies.izicoloc.activities.classes.Colocation;
import com.uqac.frenchies.izicoloc.activities.classes.Profile;

import java.util.ArrayList;

/**
 * Created by quentin on 16-11-17.
 */

public class Expense {

    private Colocataire owner;
    private int amount;
    private Colocataire[] shares;

    public Expense(Colocataire[] shares, int amount){
        for(Colocataire c : shares){
            if(c.getId() == Profile.getId())
                this.owner = c;
        }
        this.shares = shares;
        this.amount = amount;
    }

    public Expense(String s){
        String[] splitted = s.split(";");
        for(String temp : splitted){
            String[] value = temp.split(":");
            switch(value[0]){
                case "owner": { this.owner = Colocation.getColocataire(Integer.parseInt(value[1])); }
                case "amount": { this.amount = Integer.parseInt(value[1]); }
                case "shares": {
                    String[] splittedagain = s.split("/");
                    ArrayList<Colocataire> colocataires = new ArrayList<>();
                    for(String tempagain : splittedagain){
                        colocataires.add(new Colocataire(tempagain));
                    }
                    this.shares = colocataires.toArray(new Colocataire[colocataires.size()]); }
            }
        }
    }

    public Colocataire getOwner() {
        return owner;
    }

    public int getAmount() {
        return amount;
    }

    public Colocataire[] getShares() { return shares; }

    public int getSharedAmount(){
        return amount/shares.length;
    }

    public String toString(){
        String result = "";
        result += "owner:"+this.owner+";";
        result += "amount:"+this.amount+";";
        result += "shares:";

        String temp = "";
        for(Colocataire c : shares){
            temp+=c.toString()+"/";
        }
        temp = result.substring(0, result.length()-1);
        result += temp;
        return result;
    }
}
