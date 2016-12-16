package com.uqac.frenchies.izicoloc.tools.classes;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by quentin on 16-11-17.
 */

public class Account {

    private Colocataire owner;
    private float balance;
    private HashMap<Colocataire, Float> shares = new HashMap<>();
    private ArrayList<Expense> expensesList = new ArrayList<>();

    public Account(Colocataire c){
        this.owner = c;
        balance = 0;
    }

    public void addExpense(Expense ex){
        Colocataire[] coloc = ex.getShares();
        expensesList.add(ex);

        for(Colocataire c : coloc){
            if(!shares.containsKey(c))
                shares.put(c, (float) 0);
            shares.put(c,shares.get(c)+ex.getSharedAmount());
        }
        balance += ex.getAmount();
    }

    public void removeExpense(Expense e){
        if(expensesList.contains(e)) {
            balance -= e.getAmount();
            for (Colocataire c : e.getShares()) {
                shares.put(c, shares.get(c) - e.getSharedAmount());
            }
        }
    }

    public float getBalance() {
        return balance;
    }

    public float getShare(Colocataire c){
        if(shares.containsKey(c))
            return shares.get(c);
        else
            return 0;
    }
}
