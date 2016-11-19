package com.uqac.frenchies.izicoloc.activities.authentication;

import android.graphics.drawable.Drawable;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Date;

/**
 * Created by Thomas on 2016-11-15.
 */

public class Profile {

    private static String firstname;

    private static String lastname;

    private static String email;

    private static String username;

    private static String phone;

    private static Date birthday;

    private static Drawable picture;

    private static String isLoggedWith;

    private static GoogleApiClient mGoogleApiClient;


    public static String getFirstname() {
        return firstname;
    }

    public static void setFirstname(String firstname) {
        Profile.firstname = firstname;
    }

    public static String getLastname() {
        return lastname;
    }

    public static void setLastname(String lastname) {
        Profile.lastname = lastname;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Profile.email = email;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Profile.username = username;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        Profile.phone = phone;
    }

    public static Date getBirthday() {
        return birthday;
    }

    public static void setBirthday(Date birthday) {
        Profile.birthday = birthday;
    }

    public static Drawable getPicture() {
        return picture;
    }

    public static void setPicture(Drawable picture) {
        Profile.picture = picture;
    }

    public static String getIsLoggedWith() {
        return isLoggedWith;
    }

    public static void setIsLoggedWith(String isLoggedWith) {
        Profile.isLoggedWith = isLoggedWith;
    }

    public static GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    public static void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
        Profile.mGoogleApiClient = mGoogleApiClient;
    }
}
