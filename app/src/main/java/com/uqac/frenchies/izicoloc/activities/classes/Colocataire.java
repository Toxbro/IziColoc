package com.uqac.frenchies.izicoloc.activities.classes;

import android.graphics.drawable.Drawable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Thomas on 2016-11-15.
 */

public class Colocataire {

    private String firstname;

    private String lastname;

    private String email;

    private String username;

    private String phone;

    private Date birthday;

    private int id;

    private Drawable picture;


    public Colocataire(String s){
        String[] splitted = s.split(";");
        for(String temp : splitted){
            String[] value = temp.split(":");
            switch(value[0]){
                case "Id": { this.id = Integer.parseInt(value[1]); }
                case "Firstname": { this.firstname = value[1]; }
                case "Lastname": { this.lastname = value[1]; }
                case "Email": { this.email = value[1]; }
                case "Phone": { this.phone = value[1]; }
                case "Birthday": {
                    DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
                    this.birthday = dtf.parse(value[1]); }
            }
        }
    }

    public String getFirstname() { return firstname; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public Date getBirthday() { return birthday; }

    public void setBirthday(Date birthday) { this.birthday = birthday; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Drawable getPicture() { return picture; }

    public void setPicture(Drawable picture) { this.picture = picture; }

    public String toString() {
        String result = "";
        result += "Id:"+String.valueOf(getId())+";";
        result += "Firstname:"+getFirstname()+";";
        result += "Lastname:"+getLastname()+";";
        result += "Email:"+getEmail()+";";
        result += "Phone:"+getPhone()+";";
        DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        result += "Birthday:"+dtf.format(getBirthday())+";";
        return result;
    }
}
