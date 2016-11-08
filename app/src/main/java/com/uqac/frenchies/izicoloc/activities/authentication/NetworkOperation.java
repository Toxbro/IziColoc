package com.uqac.frenchies.izicoloc.activities.authentication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Thomas on 2016-10-24.
 */

public class NetworkOperation extends AsyncTask<String,Integer,Bitmap> {

    Bitmap bitmap = null;

    @Override
    protected Bitmap doInBackground(String... params) {

        String userID = params[0];
        URL imageURL = null;
        try {
            imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");

            bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
