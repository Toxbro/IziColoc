package com.uqac.frenchies.izicoloc.activities.classes;

import android.graphics.drawable.Drawable;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Date;

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

    private String isLoggedWith;

    private GoogleApiClient mGoogleApiClient;


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

    public String getIsLoggedWith() { return isLoggedWith; }

    public void setIsLoggedWith(String isLoggedWith) { this.isLoggedWith = isLoggedWith; }

    public GoogleApiClient getmGoogleApiClient() { return mGoogleApiClient; }

    public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) { this.mGoogleApiClient = mGoogleApiClient; }
}
