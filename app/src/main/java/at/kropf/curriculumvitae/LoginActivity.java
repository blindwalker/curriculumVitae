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
import android.widget.AutoCompleteTextView;
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
import at.kropf.curriculumvitae.net.ResponseListener;
import at.kropf.curriculumvitae.net.WSUser;
import at.kropf.curriculumvitae.net.model.Session;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTaskFirst mAuthTaskFirst = null;
    private UserLoginTaskSecond mAuthTaskSecond = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView txtHello;
    private TextView noAccount;
    private TextView differentAccount;
    private ImageView mProfileView;
    private Button mCheckUserButton;
    private Button mEmailSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(CurriculumVitaeApplication.getInstance().isLoggedIn()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        txtHello  =(TextView) findViewById(R.id.txtHello);

        noAccount  =(TextView) findViewById(R.id.noAccount);
        differentAccount  =(TextView) findViewById(R.id.differentAccount);

        mProfileView = (ImageView)findViewById(R.id.profile_image);

        differentAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetViewsToInit();
            }
        });

        noAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "martinkropf08@gmail.com", null));
                startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.sendMail)));
            }
        });

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mCheckUserButton = (Button) findViewById(R.id.check_user_button);

        mCheckUserButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUserCheck();
                view.setVisibility(View.GONE);
                mEmailSignInButton.setVisibility(View.VISIBLE);
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

    public void attemptUserCheck() {
        if (mAuthTaskFirst != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTaskFirst = new UserLoginTaskFirst(email);
            mAuthTaskFirst.execute((Void) null);
        }
    }

    public void attemptLogin() {
        if (mAuthTaskSecond != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTaskSecond = new UserLoginTaskSecond(email, password);
            mAuthTaskSecond.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return email.length() > 2;

    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
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
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
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
                    Log.d("RESPONSE", json.toString());
                    Session session = Session.readSessionFirst(json);
                    if (session != null) {
                        mProfileView.setVisibility(View.VISIBLE);
                        Picasso.with(LoginActivity.this).load(session.getUser().getImage()).noFade().into(mProfileView);
                        mPasswordView.setVisibility(View.VISIBLE);
                        mEmailView.setVisibility(View.GONE);
                        txtHello.setVisibility(View.VISIBLE);
                        txtHello.setText(getString(R.string.hello) + " " + session.getUser().getUsername());
                        differentAccount.setVisibility(View.VISIBLE);
                        noAccount.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(Throwable error) {
                    Log.e("RESPONSE", error.toString());
                    if(error instanceof AuthFailureError){
                        Crouton.makeText(LoginActivity.this, getString(R.string.invalidUserError), Style.ALERT).show();
                    }else if(error instanceof NoConnectionError){
                        Crouton.makeText(LoginActivity.this, getString(R.string.noConnection), Style.ALERT).show();

                    }
                    resetViewsToInit();

                }
            });

            try {
                wsUser.doLoginFirst(mEmail);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTaskFirst = null;
            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mAuthTaskFirst = null;
            showProgress(false);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
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
                    Log.d("RESPONSE", json.toString());
                    Session session = Session.readSessionSecond(json);
                    if (session != null) {
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setSessionToken(session.getToken());
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setSessionExpires(session.getExpires());
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setName(session.getUser().getName());
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setUserName(session.getUser().getUsername());
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setUserImage(session.getUser().getImage());

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }

                @Override
                public void onError(Throwable error) {
                    Log.e("RESPONSE", error.toString());
                    if(error instanceof AuthFailureError){
                        Crouton.makeText(LoginActivity.this, getString(R.string.invalidUserError), Style.ALERT).show();
                    }else if(error instanceof NoConnectionError){
                        Crouton.makeText(LoginActivity.this, getString(R.string.noConnection), Style.ALERT).show();

                    }
                }
            });

            try {
                wsUser.doLoginSecond(mEmail, mPassword);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTaskFirst = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTaskFirst = null;
            showProgress(false);
        }
    }

    private void resetViewsToInit(){
        mProfileView.setVisibility(View.INVISIBLE);
        mPasswordView.setVisibility(View.GONE);
        mEmailView.setVisibility(View.VISIBLE);
        txtHello.setVisibility(View.GONE);
        txtHello.setText("");
        differentAccount.setVisibility(View.GONE);
        noAccount.setVisibility(View.VISIBLE);

        mCheckUserButton.setVisibility(View.VISIBLE);
        mEmailSignInButton.setVisibility(View.GONE);
    }
}

