package com.policia.codigopolicia;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.policia.negocio.logica.Negocio_AVATAR;
import com.policia.negocio.logica.Negocio_DOCUMENTO;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.LoginPoliciaNalResult;

/**
 * A LoginPoliciaNal screen that offers LoginPoliciaNal via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Keep track of the LoginPoliciaNal task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private final Activity activity = this;

    private String usuarioID;

    private Seguridad sesion;

    @Override
    public void finish() {
        try {
            super.finish();
            if (!sesion.getUsuario().equals(usuarioID)) {
                String funcionario = sesion.getFuncionario();
                String[] partes = funcionario.split(" ");
                if (partes.length == 5)
                    funcionario = partes[0] + " " + partes[3] + " " + partes[1];

                Toast.makeText(activity, getString(R.string.login_welcome) + "\r\n" + funcionario, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        // Set up the LoginPoliciaNal form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
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

        try {
            sesion = Seguridad.Sesion(getBaseContext());
            usuarioID = sesion.getUsuario();

            new Negocio_AVATAR(this).drawAVATAR(Negocio_AVATAR.AVATAR.SCREEN_LOGIN,
                    (ImageView) findViewById(R.id.imageView2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the LoginPoliciaNal form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual LoginPoliciaNal attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the LoginPoliciaNal attempt.
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
            // There was an error; don't attempt LoginPoliciaNal and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user LoginPoliciaNal attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return !(email.length() == 0);
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return !(password.length() == 0);
    }

    /**
     * Shows the progress UI and hides the LoginPoliciaNal form.
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
     * Represents an asynchronous LoginPoliciaNal/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsuario;
        private final String mContrasena;

        UserLoginTask(String usuario, String contrasena) {
            mUsuario = usuario;
            mContrasena = contrasena;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                LoginPoliciaNalResult result = RemoteClient.connect(activity).LoginPoliciaNal(mUsuario, mContrasena);

                int count = new Negocio_DOCUMENTO(activity).countDocumentos();
                if (count == 0) {

                    new Negocio_DOCUMENTO(activity).sincronizar();
                }

                return Seguridad.Sesion(activity).abrirSesionPolicia(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
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
            mAuthTask = null;
            showProgress(false);
        }
    }
}

