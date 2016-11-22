package com.uqac.frenchies.izicoloc.activities.classes;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by quentin on 16-11-19.
 */

public class Colocation {

    private static ArrayList<Colocataire> colocataires = new ArrayList<>();

    private static HashMap<Colocataire, Account> accounts = new HashMap<>();

    private static ArrayList<Expense> expenses = new ArrayList<>();

    public Colocation(){}

    public static void addColocataire(Colocataire c){
        colocataires.add(c);
        accounts.put(c, new Account(c));
    }

//    public static void addColocataires(String s){
//        String[] splitted = s.split("/");
//        for(String temp : splitted){
//            addColocataire(new Colocataire(temp));
//        }
//    }

    public static ArrayList<Colocataire> getColocataires(){
        return colocataires;
    }

    public static Colocataire getColocataire(int id) {
        for(Colocataire c : colocataires){
            if(c.getId() == id)
                return c;
        }
        return null;
    }

    public static Colocataire getColocataire(String nom) {
        for(Colocataire c : colocataires){
            if(c.getFirstname().equals(nom))
                return c;
        }
        return null;
    }

//    public static void parse(String pathToFile){
//        Parser.addNode(pathToFile, "root", "Colocation", "");
//        for(Colocataire c : colocataires){
//            c.parse(pathToFile);
//        }
//    }

    public static int getBalance(Colocataire c){
        return accounts.get(c).getBalance();
    }

    public static int getShare(Colocataire c, Colocataire d){
        return accounts.get(c).getShare(d);
    }

    public static void addExpense(Colocataire c, Expense e){
        expenses.add(e);
        accounts.get(c).addExpense(e);
    }

    public static ArrayList<Expense> getExpenses() {
        Log.d("expenses", Arrays.toString(expenses.toArray()));
        return expenses; }
}
