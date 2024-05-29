package com.example.effortlist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Editor extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        replaceFragment(new EditorNotesFragment(), true);
    }
    private void replaceFragment(Fragment fragment, boolean isAppInitialized){
        System.out.println("replace is been called!");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialized) {
            fragmentTransaction.add(R.id.constraintLayout, fragment);
        } else {
            fragmentTransaction.replace(R.id.constraintLayout, fragment);
        }
        fragmentTransaction.commit();
    }
}
