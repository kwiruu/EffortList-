package com.example.effortlist;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomePage extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        bottomNavigation = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frameLayout);

        replaceFragment(new HomeFragment(), true);


        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int itemId = menuItem.getItemId();
                if(itemId == R.id.home){
                    replaceFragment(new HomeFragment(), false);
                }
                else if(itemId == R.id.todo){
                    replaceFragment(new TodoFragment(), false);
                }
                else if(itemId == R.id.notes){
                    replaceFragment(new NotesFragment(), false);
                }
                else if(itemId == R.id.list){
                    replaceFragment(new ListFragment(), false);
                }

                return true;
            }
        });
        replaceFragment(new HomeFragment(), true);
    }

    private void replaceFragment(Fragment fragment, boolean isAppInitialized){
        System.out.println("replace is been called!");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(isAppInitialized){
            fragmentTransaction.add(R.id.frameLayout, fragment);
        } else{
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }
        fragmentTransaction.commit();
    }

}