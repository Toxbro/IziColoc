package com.uqac.frenchies.izicoloc;

/**
 * Created by quentin on 16-11-17.
 */

public class Expense {

    private Profile owner;
    private int amount;
    private Profile[] shares;

    public Expense(Profile owner, Profile[] shares, int amount){
        this.owner = owner;
        this.shares = shares;
        this.amount = amount;
    }

    public Profile getOwner() {
        return owner;
    }

    public int getAmount() {
        return amount;
    }

    public Profile[] getShares() {
        return shares;
    }

    public int getSharedAmount(){
        return amount/shares.length;
    }
}
