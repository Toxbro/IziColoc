package com.uqac.frenchies.izicoloc.activities.accounting;

import com.uqac.frenchies.izicoloc.activities.classes.Colocataire;
import com.uqac.frenchies.izicoloc.activities.classes.Colocation;
import com.uqac.frenchies.izicoloc.activities.classes.Profile;

/**
 * Created by quentin on 16-11-17.
 */

public class Expense {

    private Colocataire owner;
    private int amount;
    private Colocataire[] shares;

    public Expense(Colocataire[] shares, int amount){
        this.owner = Colocation.Profile.;
        this.shares = shares;
        this.amount = amount;
    }

    public Profile getOwner() {
        return owner;
    }

    public int getAmount() {
        return amount;
    }

    public Colocataire[] getShares() {
        return shares;
    }

    public int getSharedAmount(){
        return amount/shares.length;
    }
}
