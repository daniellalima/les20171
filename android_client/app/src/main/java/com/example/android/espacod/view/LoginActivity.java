package com.example.android.espacod.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.android.espacod.R;
import com.example.android.espacod.util.UrlJsonAsyncTask;
import com.example.android.espacod.util.Util;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private final static String LOGIN_API_ENDPOINT_URL = "https://infinite-bayou-64424.herokuapp.com/auth/login";

    private SharedPreferences mPreferences;
    private String mUserEmail;
    private String mUserPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
    }

    public void login(View button) {
        EditText userEmailField = (EditText) findViewById(R.id.userEmail);
        mUserEmail = userEmailField.getText().toString();
        EditText userPasswordField = (EditText) findViewById(R.id.userPassword);
        mUserPassword = userPasswordField.getText().toString();

        if (mUserEmail.length() == 0 || mUserPassword.length() == 0) {
            Toast.makeText(this, "Please complete all the fields",
                    Toast.LENGTH_LONG).show();
            return;
        } else {
            LoginTask loginTask = new LoginTask(LoginActivity.this);
            loginTask.setMessageLoading("Logando...");
            loginTask.execute(new String[] {mUserEmail, mUserPassword});
        }
    }

    private class LoginTask extends UrlJsonAsyncTask {

        private static final String EMAIL_PARAM = "email";
        private static final String PASSWORD_PARAM = "password";
        public final String LOG_TAG = LoginTask.class.getSimpleName();

        public LoginTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String email = params[0];
            String password = params[1];

            Uri builtUri = Uri.parse(LOGIN_API_ENDPOINT_URL).buildUpon()
                    .appendQueryParameter(EMAIL_PARAM, email)
                    .appendQueryParameter(PASSWORD_PARAM, password).build();

            URL url = Util.createUrl(builtUri.toString());

            Log.i(LOG_TAG, url.toString());

            String jsonResponse = null;
            try {
                jsonResponse = Util.makeHttpRequest(url, "POST", null);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error closing input stream", e);
            }

            if (TextUtils.isEmpty(jsonResponse)) {
                return null;
            }

            JSONObject baseJsonResponse = null;
            try {
                baseJsonResponse = new JSONObject(jsonResponse);

            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the book list JSON results", e);
            }

            return baseJsonResponse;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json.has("auth_token")) {
                    // everything is ok
                    SharedPreferences.Editor editor = mPreferences.edit();
                    // save the returned auth_token into
                    // the SharedPreferences
                    editor.putString("AuthToken", json.getString("auth_token"));
                    editor.commit();

                    // launch the MainActivity and close this one
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                Toast.makeText(context, "Login realizado com sucesso!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                // something went wrong: show a Toast
                // with the exception message
                Toast.makeText(context, "Login inválido!", Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }
}
