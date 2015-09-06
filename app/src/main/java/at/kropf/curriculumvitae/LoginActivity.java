package at.kropf.curriculumvitae;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import at.kropf.curriculumvitae.application.CurriculumVitaeApplication;
import at.kropf.curriculumvitae.net.WSUser;
import at.kropf.curriculumvitae.net.model.Session;
import at.kropf.mkropfvolley.ResponseListener;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import me.philio.pinentry.PinEntryView;

/**
 * A login screen that offers login via username/pin.
 */
public class LoginActivity extends Activity {

    private UserLoginTaskFirst mAuthTaskFirst = null;
    private UserLoginTaskSecond mAuthTaskSecond = null;

    // UI references.
    private EditText mUsernameView;
    private PinEntryView mPinView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView txtHello;
    private TextView noAccount;
    private Button differentAccount;
    private ImageView mProfileView;
    private Button mCheckUserButton;
    private Button mEmailSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //if the user is already logged in, skip this screen and go to main-screen
        if(CurriculumVitaeApplication.getInstance().isLoggedIn()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.email);

        // Set keyboard editor action
        mUsernameView.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getAction() != KeyEvent.ACTION_DOWN) {
                    return false;
                } else if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || event == null
                        || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    attemptUserCheck();
                    return true;
                }
                return true;
            }
        });

        txtHello  =(TextView) findViewById(R.id.txtHello);

        noAccount  =(TextView) findViewById(R.id.noAccount);
        differentAccount  =(Button) findViewById(R.id.differentAccount);

        mProfileView = (ImageView)findViewById(R.id.profile_image);

        differentAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetViewsToInit();
            }
        });

        //start email intent onclick
        noAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "martinkropf08@gmail.com", null));
                startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.sendMail)));
            }
        });

        mPinView = (PinEntryView) findViewById(R.id.password);

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mCheckUserButton = (Button) findViewById(R.id.check_user_button);

        mCheckUserButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUserCheck();
            }
        });

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    //check for username
    private void attemptUserCheck() {
        if (mAuthTaskFirst != null) {
            return;
        }


        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a empty username.
        if (TextUtils.isEmpty(username)) {
            Crouton.makeText(LoginActivity.this, getString(R.string.error_field_required), Style.ALERT).show();
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the username field
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the check-user attempt.
            showProgress(true);
            mCheckUserButton.setVisibility(View.GONE);
            mEmailSignInButton.setVisibility(View.VISIBLE);
            mAuthTaskFirst = new UserLoginTaskFirst(username);
            mAuthTaskFirst.execute((Void) null);
        }
    }

    //check for pin
    private void attemptLogin() {
        if (mAuthTaskSecond != null) {
            return;
        }

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String pin = mPinView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a empty pin
        if (TextUtils.isEmpty(pin)) {
            Crouton.makeText(LoginActivity.this, getString(R.string.noPinError), Style.ALERT).show();
            focusView = mPinView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the pin field
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTaskSecond = new UserLoginTaskSecond(username, pin);
            mAuthTaskSecond.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous check-user task
     */
    public class UserLoginTaskFirst extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;

        UserLoginTaskFirst(String email) {
            mEmail = email;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            WSUser wsUser = new WSUser(LoginActivity.this, new ResponseListener() {
                @Override
                public void onComplete(JSONObject json) {

                    //if the request completed, parse the values from the json response
                    Session session = Session.readSessionFirst(json);
                    showProgress(false);

                    //if parsing worked, build the views and fill the parameters for this user
                    if (session != null) {
                        mProfileView.setVisibility(View.VISIBLE);
                        Picasso.with(LoginActivity.this).load(session.getUser().getImage()).noFade().into(mProfileView);
                        mPinView.setVisibility(View.VISIBLE);
                        mUsernameView.setVisibility(View.GONE);
                        txtHello.setVisibility(View.VISIBLE);
                        txtHello.setText(getString(R.string.hello) + " " + session.getUser().getName());
                        differentAccount.setVisibility(View.VISIBLE);
                        noAccount.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onError(Throwable error) {
                    Log.e("RESPONSE", error.toString());
                    //if request failed, show error to the user
                    if(error instanceof AuthFailureError){
                        Crouton.makeText(LoginActivity.this, getString(R.string.invalidUserError), Style.ALERT).show();
                    }else if(error instanceof NoConnectionError && error.getMessage().contains("No authentication")){
                        Crouton.makeText(LoginActivity.this, getString(R.string.invalidUserError), Style.ALERT).show();

                    }else if(error instanceof NoConnectionError){
                        Crouton.makeText(LoginActivity.this, getString(R.string.noConnection), Style.ALERT).show();

                    }
                    showError();
                }
            });

            try {
                wsUser.doLoginFirst(mEmail);
            } catch (JSONException e) {
                e.printStackTrace();
                showError();

            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTaskFirst = null;
        }

        @Override
        protected void onCancelled() {
            mAuthTaskFirst = null;
            showProgress(false);
        }

        private void showError(){
            runOnUiThread(new Thread(new Runnable() {
                @Override
                public void run() {
                    resetViewsToInit();
                    showProgress(false);
                }
            }));
        }
    }

    /**
     * Represents an asynchronous login task used to authenticate the user.
     */
    public class UserLoginTaskSecond extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTaskSecond(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            WSUser wsUser = new WSUser(LoginActivity.this, new ResponseListener() {
                @Override
                public void onComplete(JSONObject json) {
                    //if the request succeeded, read the parameters from json
                    Session session = Session.readSessionSecond(json);

                    //if everything worked, store the values in the preference handler and continue to main-screen
                    if (session != null) {
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setSessionToken(session.getToken());
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setSessionExpires(session.getExpires());
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setName(session.getUser().getName());
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setUserName(session.getUser().getUsername());
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setUserImage(session.getUser().getImage());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();

                    }
                }

                @Override
                public void onError(Throwable error) {
                    Log.e("RESPONSE", error.toString());
                    if(error instanceof AuthFailureError){
                        Crouton.makeText(LoginActivity.this, getString(R.string.invalidPinError), Style.ALERT).show();
                    }else if(error instanceof NoConnectionError && error.getMessage().contains("No authentication")){
                        Crouton.makeText(LoginActivity.this, getString(R.string.invalidPinError), Style.ALERT).show();

                    } else if(error instanceof NoConnectionError){
                        Crouton.makeText(LoginActivity.this, getString(R.string.noConnection), Style.ALERT).show();

                    }

                    showError();

                }
            });

            try {
                wsUser.doLoginSecond(mEmail, mPassword);
            } catch (JSONException e) {
                e.printStackTrace();
                showError();
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTaskSecond = null;

            if (!success) {
                mPinView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTaskSecond = null;
            showProgress(false);
        }

        private void showError(){
            runOnUiThread(new Thread(new Runnable() {
                @Override
                public void run() {
                    showProgress(false);
                    mPinView.setText("");
                }
            }));
        }
    }

    //resets all the views to their initial state
    private void resetViewsToInit(){
        mProfileView.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        mPinView.setVisibility(View.GONE);
        mUsernameView.setVisibility(View.VISIBLE);
        txtHello.setVisibility(View.GONE);
        txtHello.setText("");
        differentAccount.setVisibility(View.GONE);
        noAccount.setVisibility(View.VISIBLE);
        mCheckUserButton.setVisibility(View.VISIBLE);
        mEmailSignInButton.setVisibility(View.GONE);
    }
}

