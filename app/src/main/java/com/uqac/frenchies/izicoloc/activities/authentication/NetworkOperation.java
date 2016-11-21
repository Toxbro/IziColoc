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
        String imageURL_String = params[0];
        URL imageURL = null;
        try {
            if (imageURL_String.equals("facebook")){
                String userID = params[1];
                imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
            }
            else {
                imageURL = new URL(imageURL_String);
            }

            bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
