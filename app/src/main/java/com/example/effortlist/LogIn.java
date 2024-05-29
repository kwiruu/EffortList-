package com.example.effortlist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.effortlist.Utils.DBHelper;

public class LogIn extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private CheckBox checkBoxRememberMe;
    private Button btnSignIn;
    private TextView textViewSignUp;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        editTextUsername = findViewById(R.id.editTextEmailaddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        checkBoxRememberMe = findViewById(R.id.checkBox2);
        btnSignIn = findViewById(R.id.button);
        textViewSignUp = findViewById(R.id.textView7);

        dbHelper = new DBHelper(this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser();
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void authenticateUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = dbHelper.getUser(username, password);
        if (cursor != null && cursor.moveToFirst()) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LogIn.this, SplashActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
    }
}
