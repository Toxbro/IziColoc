package com.uqac.frenchies.izicoloc.tools.classes;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by quentin on 16-11-19.
 */

public class Colocation {

    private static String id;

    private static ArrayList<Colocataire> colocataires = new ArrayList<>();

    private static HashMap<Colocataire, Account> accounts = new HashMap<>();

    private static ArrayList<Expense> expenses = new ArrayList<>();

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        Colocation.id = id;
    }

    public static void addColocataire(Colocataire c){
        colocataires.add(c);
        accounts.put(c, new Account(c));
    }

    public static ArrayList<Colocataire> getColocataires(){
        return colocataires;
    }

    public static Colocataire getColocataireById(String id) {
        for(Colocataire c : colocataires){
            if(c.getEmail().equals(id))
                return c;
        }
        return null;
    }

    public static Colocataire getColocataireByName(String nom) {
        for(Colocataire c : colocataires){
            String temp = c.getFirstname()+" "+c.getLastname();
            if(temp.equals(nom))
                return c;
        }
        return null;
    }

    public static float getBalance(Colocataire c){
        return accounts.get(c).getBalance();
    }

    public static float getShare(Colocataire c, Colocataire d){
        return accounts.get(c).getShare(d);
    }

    public static void addExpense(Expense e){
        expenses.add(e);
        accounts.get(e.getOwner()).addExpense(e);
    }

    public static ArrayList<Expense> getExpenses() {
        Log.d("expenses", Arrays.toString(expenses.toArray()));
        return expenses;
    }

    public static void removeExpense(Expense e){
        expenses.remove(e);
        accounts.get(e.getOwner()).removeExpense(e);
    }

    public static void resetAccounts(){
        expenses = new ArrayList<>();
        accounts = new HashMap<>();
        for(Colocataire c : colocataires){
            accounts.put(c, new Account(c));
        }
    }
}
