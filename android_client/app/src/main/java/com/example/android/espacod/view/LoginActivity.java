package com.example.android.espacod.view;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.espacod.R;

public class LoginActivity extends AppCompatActivity {

    private final static String LOGIN_API_ENDPOINT_URL = "http://10.0.2.2:3000/api/v1/sessions.json";
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
//            LoginTask loginTask = new LoginTask(LoginActivity.this);
//            loginTask.setMessageLoading("Logging in...");
//            loginTask.execute(LOGIN_API_ENDPOINT_URL);
        }
    }
}
