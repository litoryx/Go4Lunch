package com.example.go4lunch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.go4lunch.ListRest.ListRestFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;
    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.activity_toolbar);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        setSupportActionBar(mToolbar);

        setup();

        if (savedInstanceState == null) {
            showFragment(GeoFragment.newInstance());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.choice1:
                return true;
            case R.id.choice2:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void setup() {
        mBottomNavigationView.setOnNavigationItemReselectedListener(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.geo) {
                showFragment(GeoFragment.newInstance());
            } else if (menuItem.getItemId() == R.id.List) {
                showFragment(ListRestFragment.newInstance());
            } else if (menuItem.getItemId() == R.id.ListCollegue) {
                showFragment(ListStaffFragment.newInstance());
            }
            return true;
        });
    }

    void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.activity_main_fragment_container, fragment).commit();

    }
}