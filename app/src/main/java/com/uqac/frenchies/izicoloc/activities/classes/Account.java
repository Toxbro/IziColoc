package com.uqac.frenchies.izicoloc.activities.classes;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by quentin on 16-11-17.
 */

public class Account {

    private Colocataire owner;
    private int balance;
    private HashMap<Colocataire, Integer> shares = new HashMap<>();

    public Account(Colocataire c){
        this.owner = c;
        balance = 0;
    }

    public boolean addExpense(Expense ex){
        boolean result = true;
        Colocataire[] coloc = ex.getShares();

        for(Colocataire c : coloc){
            if(!shares.containsKey(c))
                shares.put(c, 0);
            shares.put(c,shares.get(c)+ex.getSharedAmount());
        }
        balance += ex.getAmount();
        return result;
    }

    public int getBalance() {
        return balance;
    }

    public int getShare(Colocataire c){
        if(shares.containsKey(c))
            return shares.get(c);
        else
            return 0;
    }
}
