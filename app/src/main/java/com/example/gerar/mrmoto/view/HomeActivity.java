package com.example.gerar.mrmoto.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.gerar.mrmoto.R;
import com.example.gerar.mrmoto.fragments.HistoryFragment;
import com.example.gerar.mrmoto.fragments.MapFragment;

public class HomeActivity extends AppCompatActivity {

    Fragment currentFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    currentFragment = new MapFragment();
                    changeFragment(currentFragment);
                    return true;
                case R.id.navigation_history:
                    currentFragment = new HistoryFragment();
                    changeFragment(currentFragment);
                    return true;
            }
            return false;
        }
    };

    private void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        showToolbar("Mr Moto", false);

        if(savedInstanceState == null){
            currentFragment = new MapFragment();
            changeFragment(currentFragment);
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void showToolbar(String title, boolean upButton){
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }



}
