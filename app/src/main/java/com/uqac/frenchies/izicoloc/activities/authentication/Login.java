package com.uqac.frenchies.izicoloc.activities.authentication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.uqac.frenchies.izicoloc.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {

    private final String TAG = "Login";

    private CallbackManager callbackManager;

    private LoginButton loginButton;

    private TextView textView;

    private Profile profile;

    private ProfileTracker profileTracker;

    private String facebookEmail;

    private String facebookBirthday;

    private GoogleApiClient mGoogleApiClient;

    private GoogleSignInAccount googleSignInAccount;

    private int RC_SIGN_IN = 5;

    private boolean isConnectedWithFacebook;

    private boolean isConnectedWithGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this, getString(R.string.facebook_app_id));
        callbackManager = CallbackManager.Factory.create();



        //*****************************************************************************//
        //***************************************FACEBOOK******************************//
        //*****************************************************************************//

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                if (currentProfile != null) {
                    SpannableString ss = new SpannableString("    "+currentProfile.getFirstName()+" "+currentProfile.getLastName());
                    Drawable d = new BitmapDrawable(getResources(), getFacebookProfilePicture(currentProfile.getId()));
                    d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                    ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
                    ss.setSpan(span, 0, 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    textView.setText(ss);

                }
            }
        };

        setContentView(R.layout.activity_login);

        textView = ((TextView)findViewById(R.id.textViewInfo));

        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                getFacebookAdditionalInformation(loginResult.getAccessToken());
                loginSuccess();
            }

            @Override
            public void onCancel() {
                ((TextView)findViewById(R.id.textViewInfo)).setText("Login attempt canceled by user");
            }

            @Override
            public void onError(FacebookException error) {
                ((TextView)findViewById(R.id.textViewInfo)).setText("Login attempt failed");
            }
        });

        //*****************************************************************************//
        //***************************************GOOGLE********************************//
        //*****************************************************************************//

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(), getString(R.string.error_sign_in_failed), Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        OptionalPendingResult<GoogleSignInResult> pendingResult =
                Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (pendingResult.isDone()) {
            // There's immediate result available.
            //Toast.makeText(getApplicationContext(), "T'ES DEJA CONNECTE AVEC GOOGLE!", Toast.LENGTH_LONG).show();
            isConnectedWithGoogle = true;
            googleSignInAccount = pendingResult.get().getSignInAccount();
            loginSuccess();
            //updateButtonsAndStatusFromSignInResult(pendingResult.get());
        } else {
            //Toast.makeText(getApplicationContext(), "T'ES PAS ENCORE CONNECTE !", Toast.LENGTH_LONG).show();
            isConnectedWithGoogle = false;
            // There's no immediate result ready, displays some progress indicator and waits for the
            // async callback.
            //showProgressIndicator();
            /*pendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    updateButtonsAndStatusFromSignInResult(result);
                    hideProgressIndicator();
                }
            });*/
        }

        isConnectedWithFacebook = Profile.getCurrentProfile() != null;
        if (isConnected())
            loginSuccess();
    }

    private void getFacebookAdditionalInformation(AccessToken accessToken){
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());
                        try {
                            // Application code
                            facebookEmail = object.getString("email");
                            facebookBirthday = object.getString("birthday"); // 01/31/1980 format
                            com.uqac.frenchies.izicoloc.activities.classes.Profile.setEmail(facebookEmail);
                            try {
                                com.uqac.frenchies.izicoloc.activities.classes.Profile.setBirthday(DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).parse(facebookBirthday));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private boolean isConnected(){
        if (isConnectedWithGoogle) {
            String fullName = googleSignInAccount.getDisplayName();
            com.uqac.frenchies.izicoloc.activities.classes.Profile.setFirstname(fullName.split(" ")[0]);
            com.uqac.frenchies.izicoloc.activities.classes.Profile.setLastname(fullName.split(" ")[1]);
            Log.d(TAG, "Email :" + googleSignInAccount.getEmail());

            com.uqac.frenchies.izicoloc.activities.classes.Profile.setEmail(googleSignInAccount.getEmail());
            if (googleSignInAccount.getPhotoUrl() != null)
                com.uqac.frenchies.izicoloc.activities.classes.Profile.setPicture(new BitmapDrawable(getResources(), getGoogleProfilePicture(googleSignInAccount.getPhotoUrl().toString())));
            else
                com.uqac.frenchies.izicoloc.activities.classes.Profile.setPicture(getResources().getDrawable(R.mipmap.ic_defaultgoogle));
            com.uqac.frenchies.izicoloc.activities.classes.Profile.setIsLoggedWith("google");

            com.uqac.frenchies.izicoloc.activities.classes.Profile.setmGoogleApiClient(mGoogleApiClient);
            return true;
        }
        else if (isConnectedWithFacebook){
            getFacebookAdditionalInformation(AccessToken.getCurrentAccessToken());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            com.uqac.frenchies.izicoloc.activities.classes.Profile.setFirstname(Profile.getCurrentProfile().getFirstName());
            com.uqac.frenchies.izicoloc.activities.classes.Profile.setLastname(Profile.getCurrentProfile().getLastName());
            com.uqac.frenchies.izicoloc.activities.classes.Profile.setEmail(facebookEmail);
            com.uqac.frenchies.izicoloc.activities.classes.Profile.setPicture(new BitmapDrawable(getResources(), getFacebookProfilePicture(Profile.getCurrentProfile().getId())));
            try {
                if (facebookBirthday != null)
                    com.uqac.frenchies.izicoloc.activities.classes.Profile.setBirthday(DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).parse(facebookBirthday));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            com.uqac.frenchies.izicoloc.activities.classes.Profile.setIsLoggedWith("facebook");
            return true;
        }
        else {
            return false;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            isConnectedWithGoogle = true;
            googleSignInAccount = result.getSignInAccount();
            isConnected();
            Log.d(TAG, "API :" + mGoogleApiClient.isConnected());
            loginSuccess();
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.error_sign_in_failed), Toast.LENGTH_LONG).show();
        }
    }

    private void loginSuccess(){
        //Toast.makeText(getApplicationContext(), "C'EST BON T'ES CONNECTÉ !", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, com.uqac.frenchies.izicoloc.activities.main.MainMenu.class);
        startActivity(intent);
    }

    public static Bitmap getFacebookProfilePicture(String userID){
        Bitmap bitmap = null;
        try {
            bitmap = new NetworkOperation().execute("facebook", userID).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap getGoogleProfilePicture(String url){
        Bitmap bitmap = null;
        try {
            bitmap = new NetworkOperation().execute(url).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }
}
