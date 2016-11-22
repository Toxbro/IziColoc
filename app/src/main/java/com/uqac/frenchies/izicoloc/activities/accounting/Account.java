package com.uqac.frenchies.izicoloc.activities.accounting;

import com.uqac.frenchies.izicoloc.activities.classes.Colocataire;

import java.util.HashMap;

/**
 * Created by quentin on 16-11-17.
 */

public class Account {

    private int balance;
    private HashMap<Colocataire, Integer> shares;

    public boolean addExpense(Expense ex){
        boolean result = true;
        for(Colocataire p : ex.getShares()){
            if(shares.containsKey(p) && result)
                shares.put(p,shares.get(p)+ex.getAmount());
            else
                result = false;
        }
        if(result)
            balance += ex.getAmount();
        return result;
    }
}
