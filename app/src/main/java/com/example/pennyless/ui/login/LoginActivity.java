package com.example.pennyless.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pennyless.HomeActivity;
import com.example.pennyless.R;
import com.example.pennyless.entities.Database;
import com.example.pennyless.entities.User;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button signInButton;
    private TextView usernameText;
    private TextView passwordText;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private static final String appPreferences = "PennylessAppPreferences";
    private SharedPreferences sharedpreferences;
    private String username;
    private String password;
    private Database databaseHelper;
    private boolean userExists;
    private boolean isActive = false;
    private boolean loggedOut = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedpreferences = getSharedPreferences(appPreferences, Context.MODE_PRIVATE);
        if(sharedpreferences != null){
            username = sharedpreferences.getString("username", null);
            password = sharedpreferences.getString("password", null);
            loggedOut = sharedpreferences.getBoolean("loggedOut", false);
        }

        signInButton = (Button) findViewById(R.id.sign_in_btn);
        usernameText = (TextView) findViewById(R.id.username_text);
        passwordText = (TextView) findViewById(R.id.password_text);

        databaseHelper = new Database(getApplicationContext());

        if(!loggedOut && username != null && password != null){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_anim, R.anim.fade_anim);
        }else {

            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login();
                }
            });
        }

        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                switch(key){
                    case "username":
                        username = sharedPreferences.getString("username", null);
                        break;
                    case "password":
                        password = sharedpreferences.getString("password", null);
                        break;
                    case "loggedOut":
                        loggedOut = sharedpreferences.getBoolean("loggedOut", false);
                        break;

                    default:
                        break;
                }
            }
        };

        sharedpreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();

        if(username!= null && password!=null) {
            User newUser = new User(username,password);
            databaseHelper.saveUser(newUser);
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.welcome) + " "+username + "!", Toast.LENGTH_LONG).show();
        }
        if(!userExists){
            User newUser = new User(username,password);
            databaseHelper.saveUser(newUser);
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.welcome) + " "+username + "!", Toast.LENGTH_LONG).show();
        }
//        if(databaseHelper.checkCredentials(username, password)){
//            Toast.makeText(getApplicationContext(), getResources().getString(R.string.welcome) + " "+username + "!", Toast.LENGTH_LONG).show();
//            onLoginSuccess();
//        }
//        else{
//            onLoginFailed();
//        }
        onLoginSuccess();
    }

    public boolean validate() {
        boolean valid = true;
        password = passwordText.getText().toString().trim();
        username = usernameText.getText().toString().trim();
        if (username.isEmpty()) {
            usernameText.setError(getResources().getString(R.string.empty_username), getResources().getDrawable(R.drawable.outline_info_24));
            valid = false;
        } else {
            usernameText.setError(null);
        }

        if (password.isEmpty()) {
            passwordText.setError(getResources().getString(R.string.empty_password), getResources().getDrawable(R.drawable.outline_info_24));
            valid = false;
        } else {
            passwordText.setError(null);
        }
        return valid;
    }

    public void onLoginSuccess() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("loggedOut", false);
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_anim, R.anim.fade_anim);
    }

    public void onLoginFailed() {
        if(isActive) {
            Toast.makeText(getBaseContext(), getResources().getString(R.string.login_failed), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        isActive = true;
        super.onStart();
    }

    @Override
    protected void onStop() {
        isActive = false;
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        isActive = false;
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        isActive = true;
        super.onResume();
    }
}