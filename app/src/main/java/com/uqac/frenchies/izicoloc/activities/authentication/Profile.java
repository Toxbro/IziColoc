package com.uqac.frenchies.izicoloc.activities.authentication;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Thomas on 2016-11-08.
 */

public class Profile implements Serializable{

    private String last_name;

    private String first_name;

    private String username;

    private String email;

    private Drawable picture;

    public Profile(){

    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Drawable getPicture() {
        return picture;
    }

    public void setPicture(Drawable picture) {
        this.picture = picture;
    }
}
