package com.uqac.frenchies.izicoloc;

import java.util.HashMap;

/**
 * Created by quentin on 16-11-17.
 */

public class Account {

    private Profile owner;
    private int balance;
    private HashMap<Profile, Integer> shares;

    public boolean addExpense(Expense ex){
        boolean result = true;
        for(Profile p : ex.getShares()){
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
