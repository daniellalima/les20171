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

public class RegisterActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private String mUserEmail;
    private String mUserName;
    private String mUserPassword;
    private String mUserPasswordConfirmation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
    }

    public void registerNewAccount(View button) {

        EditText userEmailField = (EditText) findViewById(R.id.userEmail);
        mUserEmail = userEmailField.getText().toString();
        EditText userNameField = (EditText) findViewById(R.id.userName);
        mUserName = userNameField.getText().toString();
        EditText userPasswordField = (EditText) findViewById(R.id.userPassword);
        mUserPassword = userPasswordField.getText().toString();
        EditText userPasswordConfirmationField = (EditText) findViewById(R.id.userPasswordConfirmation);
        mUserPasswordConfirmation = userPasswordConfirmationField.getText().toString();

        if (mUserEmail.length() == 0 || mUserName.length() == 0 || mUserPassword.length() == 0 || mUserPasswordConfirmation.length() == 0) {
            // input fields are empty
            Toast.makeText(this, "Please complete all the fields",
                    Toast.LENGTH_LONG).show();
            return;
        } else {
            if (!mUserPassword.equals(mUserPasswordConfirmation)) {
                // password doesn't match confirmation
                Toast.makeText(this, "Your password doesn't match confirmation, check again",
                        Toast.LENGTH_LONG).show();
                return;
            } else {
                // everything is ok!
                RegisterTask registerTask = new RegisterTask(RegisterActivity.this);
                registerTask.setMessageLoading("Registering new account...");

                registerTask.execute(new String[] {mUserName, mUserEmail, mUserPassword, mUserPasswordConfirmation});
            }
        }
    }

    private class RegisterTask extends UrlJsonAsyncTask {

        private final static String REGISTER_API_ENDPOINT_URL = "https://infinite-bayou-64424.herokuapp.com/signup";
        private static final String EMAIL_PARAM = "email";
        private static final String NAME_PARAM = "name";
        private static final String PASSWORD_PARAM = "password";
        private static final String PASSWORD_CONFIRMATION_PARAM = "password_confirmation";

        private final String LOG_TAG = RegisterTask.class.getSimpleName();

        public RegisterTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String name = params[0];
            String email = params[1];
            String password = params[2];
            String password_confirmation = params[3];

            Uri builtUri = Uri.parse(REGISTER_API_ENDPOINT_URL).buildUpon()
                    .appendQueryParameter(EMAIL_PARAM, email)
                    .appendQueryParameter(NAME_PARAM, name)
                    .appendQueryParameter(PASSWORD_PARAM, password)
                    .appendQueryParameter(PASSWORD_CONFIRMATION_PARAM, password_confirmation).build();

            URL url = Util.createUrl(builtUri.toString());

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
                Toast.makeText(context, json.getString("message"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                // something went wrong: show a Toast
                // with the exception message
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }
}