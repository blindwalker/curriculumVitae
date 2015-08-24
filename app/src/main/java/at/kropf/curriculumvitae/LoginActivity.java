package at.kropf.curriculumvitae;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
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

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import at.kropf.curriculumvitae.application.CurriculumVitaeApplication;
import at.kropf.curriculumvitae.net.ResponseListener;
import at.kropf.curriculumvitae.net.WSUser;
import at.kropf.curriculumvitae.net.model.Session;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTaskFirst mAuthTaskFirst = null;
    private UserLoginTaskSecond mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

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

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTaskFirst != null) {
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
            mAuthTaskFirst = new UserLoginTaskFirst(email);
            mAuthTaskFirst.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return email.length() > 2;

    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
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
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setName(session.getUser().getName());
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setUserName(session.getUser().getUsername());
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setUserImage(session.getUser().getImage());

                        findViewById(R.id.profile_image).setVisibility(View.VISIBLE);
                        Picasso.with(LoginActivity.this).load(session.getUser().getImage()).noFade().into((ImageView) findViewById(R.id.profile_image));
                        mPasswordView.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onError(Throwable error) {
                    Log.e("RESPONSE", error.toString());

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
        private final Session mSessionFirst;

        UserLoginTaskSecond(Session sessionFirst, String email, String password) {
            mSessionFirst = sessionFirst;
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            WSUser wsUser = new WSUser(LoginActivity.this, new ResponseListener() {
                @Override
                public void onComplete(JSONObject json) {
                    Log.d("RESPONSE", json.toString());
                    Session session = Session.readSessionSecond(mSessionFirst, json);
                    if (session != null) {
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setSessionToken(session.getToken());
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setSessionExpires(session.getExpires());
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setName(session.getUser().getName());
                        CurriculumVitaeApplication.getInstance().getPreferenceHandler().setUserName(session.getUser().getUsername());

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }

                @Override
                public void onError(Throwable error) {
                    Log.e("RESPONSE", error.toString());

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
}

