package com.example.go4lunch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.go4lunch.Geo.GeoFragment;
import com.example.go4lunch.ListRest.ListRestFragment;
import com.example.go4lunch.ListStaff.ListStaffFragment;
import com.example.go4lunch.ListStaff.StaffViewModel;
import com.example.go4lunch.ListStaff.UserRepository;
import com.example.go4lunch.ListStaff.UserViewModelFactory;
import com.example.go4lunch.objetGoogle.Place;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar mToolbar;
    BottomNavigationView mBottomNavigationView;
    DrawerLayout mDrawerLayout;
    FloatingActionButton mFloatingActionButton;
    NavigationView mNavigationView;
    UserRepository mUserRepository;
    MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.activity_toolbar);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mFloatingActionButton = findViewById(R.id.center_buttonMap);
        SearchView simpleSearchView = (SearchView) findViewById ( R . id . simpleSearchView );
        mMainViewModel = new ViewModelProvider(this, MainViewModelFactory.getInstance()).get(MainViewModel.class);
        setSupportActionBar(mToolbar);
        mUserRepository = UserRepository.getInstance();
        this.configureDrawerLayout();
        this.configureNavigationView();
        setup();

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //Cette fonction doit retourner une list ou un ??l??ment ? Cette fonction ne fait que la comparaison et retourner le resultat?
        }
        FirebaseUser mfbuser = intent.getParcelableExtra("mfbUser");
        mUserRepository.addUser(mfbuser);


        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null){mMainViewModel.updateSearchText(newText);
                    return true;}

                return false;
            }
        });


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
        Log.d("nameActivity",item.getItemId()+"");
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



    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.activity_main_drawer_profile :
                break;
            case R.id.activity_main_drawer_settings:
                break;
            case R.id.activity_main_drawer_logout:
                break;
            default:
                break;
        }

        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.mDrawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.mNavigationView = findViewById(R.id.activity_main_nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }
}