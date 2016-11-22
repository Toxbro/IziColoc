package com.uqac.frenchies.izicoloc.activities.classes;

import com.uqac.frenchies.izicoloc.activities.classes.Colocataire;
import com.uqac.frenchies.izicoloc.activities.classes.Expense;

import java.util.HashMap;

/**
 * Created by quentin on 16-11-17.
 */

public class Account {

    private Colocataire owner;
    private int balance;
    private HashMap<Colocataire, Integer> shares;

    public boolean addExpense(Expense ex){
        boolean result = true;
        for(Colocataire c : ex.getShares()){
            if(shares.containsKey(c) && result)
                shares.put(c,shares.get(c)+ex.getAmount());
            else
                result = false;
        }
        if(result)
            balance += ex.getAmount();
        return result;
    }

    public int getBalance() {
        return balance;
    }
}
