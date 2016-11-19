package com.uqac.frenchies.izicoloc;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Thomas on 2016-11-08.
 */

public class Profile implements Serializable{

    private static String last_name;

    private static String first_name;

    private static String username;

    private static String email;

    private static Drawable picture;

    public Profile(){

    }

    public static String getLast_name() {
        return last_name;
    }

    public static void setLast_name(String last_name) {
        Profile.last_name = last_name;
    }

    public static String getFirst_name() {
        return first_name;
    }

    public static void setFirst_name(String first_name) {
        Profile.first_name = first_name;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Profile.username = username;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Profile.email = email;
    }

    public static Drawable getPicture() {
        return picture;
    }

    public static void setPicture(Drawable picture) {
        Profile.picture = picture;
    }
}