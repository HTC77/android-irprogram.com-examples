package com.example.irprogramtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    NavigationFragment mNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbar);
        mToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        mNav = (NavigationFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_navigation_drawer);
        mNav.setup(R.id.fragment_navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout),mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ( mNav.mToggle.onOptionsItemSelected(item))
            return true;
        if (id == R.id.action_setting){
            Toast.makeText(this, "Setting clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.navigate)
            startActivity(new Intent(this, SubActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
