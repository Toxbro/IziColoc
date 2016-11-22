package com.uqac.frenchies.izicoloc.activities.classes;

import java.util.ArrayList;

/**
 * Created by quentin on 16-11-19.
 */

public class Colocation {

    private static ArrayList<Colocataire> colocataires = new ArrayList<>();

    private static ArrayList<Expense> expenses = new ArrayList<>();


    public Colocation(){}

    public static void addColocataire(Colocataire c){
        colocataires.add(c);
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

//    public static void parse(String pathToFile){
//        Parser.addNode(pathToFile, "root", "Colocation", "");
//        for(Colocataire c : colocataires){
//            c.parse(pathToFile);
//        }
//    }

    public static void addExpense(Expense e) {
        expenses.add(e);
    }

    public static ArrayList<Expense> getExpenses(){
        return expenses;
    }

    public static ArrayList<Expense> getExpensesOf(Colocataire c){
        ArrayList<Expense> result = new ArrayList<>();
        for(Expense e : expenses)
            if(e.getOwner().getId() == c.getId())
                result.add(e);
        return result;
    }
}
