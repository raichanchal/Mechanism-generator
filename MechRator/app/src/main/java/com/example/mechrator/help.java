package com.example.mechrator;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class help extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout)findViewById(R.id.draw_layouti);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MessageFragment()).addToBackStack(null).commit();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.guide:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MessageFragment()).addToBackStack(null).commit();
//                Intent Intent1 = new Intent(this,help.class);
//                startActivity(Intent1);
                break;
            case R.id.nav_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                String text = "https://wikipedia.com";
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,text);
                startActivity(intent);
                break;
            case R.id.feedback:
                Intent Int = new Intent(this,contactAndFeedback.class);
                startActivity(Int);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
