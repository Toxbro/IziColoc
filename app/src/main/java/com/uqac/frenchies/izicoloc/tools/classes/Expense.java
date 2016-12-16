package com.uqac.frenchies.izicoloc.tools.classes;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by quentin on 16-11-17.
 */

public class Expense {

    private Colocataire owner;
    private float amount;
    private ArrayList<Colocataire> shares;
    private String label;
    private String date;

    public Expense(Colocataire owner, Colocataire[] shares, float amount, String date, String label){
        this.owner = owner;
        this.shares = new ArrayList<>(Arrays.asList(shares));
        this.amount = amount;
        this.date = date;
        this.label = label;
    }

    public Colocataire getOwner() {
        return owner;
    }

    public float getAmount() {
        return amount;
    }

    public Colocataire[] getShares() {
            return shares.toArray(new Colocataire[shares.size()]);
    }

    public float getSharedAmount(){
        return amount/shares.size();
    }

    public String getLabel() {
        return label;
    }

    public String getDate() {
        return date;
    }

}
