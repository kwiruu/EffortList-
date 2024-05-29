package com.example.effortlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.effortlist.Utils.DBHelper;

public class Register extends AppCompatActivity {

    private EditText editTextFname, editTextLname, editTextUsername, editTextEmailaddress, editTextPassword, editTextConfirmpassword;
    private Button btnSignup, btnBack;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextFname = findViewById(R.id.editTextFname);
        editTextLname = findViewById(R.id.editTextLname);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmailaddress = findViewById(R.id.editTextEmailaddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmpassword = findViewById(R.id.editTextConfirmpassword);
        btnSignup = findViewById(R.id.btnSignup);
        btnBack = findViewById(R.id.btnBack);

        dbHelper = new DBHelper(this);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = editTextFname.getText().toString();
                String lname = editTextLname.getText().toString();
                String username = editTextUsername.getText().toString();
                String email = editTextEmailaddress.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmpassword.getText().toString();

                if (password.equals(confirmPassword)) {
                    boolean isInserted = dbHelper.insertData(username, email, password, fname, lname, 0);
                    if (isInserted) {
                        Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, LogIn.class));
                    } else {
                        Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, LogIn.class));
            }
        });
    }
}
