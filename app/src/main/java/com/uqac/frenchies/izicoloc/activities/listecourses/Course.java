package com.uqac.frenchies.izicoloc.activities.listecourses;

/**
 * Created by Dylan on 14/11/2016.
 */

public class Course
{
    private int color;
    private String produit;
    private String quantite;

    public Course(int color, String prod, String quant)
    {
        this.color = color;
        this.produit = prod;
        this.quantite = quant;
    }

    public int getColor() {return this.color;}
    public void setColor(int newColor) {this.color = newColor;}

    public String getProduit() {return this.produit;}
    public void setProduit(String newProduit) {this.produit = newProduit;}

    public String getQuantite() {return this.quantite;}
    public void setQuantite(String newQuantite) {this.quantite = newQuantite;}
}
