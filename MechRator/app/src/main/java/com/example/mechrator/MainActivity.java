package com.example.mechrator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout)findViewById(R.id.draw_layouti);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle;
        //toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        //drawer.addDrawerListener(toggle);
        //toggle.syncState();


    }

    public void CheckInput(View view) {
        Spinner nol = (Spinner) findViewById(R.id.numOfLinks);
        String NOL = nol.getSelectedItem().toString();

        Spinner dof = (Spinner) findViewById(R.id.DegreeOfFreedom);
        String DOF = dof.getSelectedItem().toString();

        if (!NOL.equals("Select One...") && !DOF.equals("Select One...")) {
            int numberOfLink = Integer.parseInt(NOL);
            int degreeOfFreedom = Integer.parseInt(DOF);

            float checkNumberOfJoints = (3 * (numberOfLink - 1) - degreeOfFreedom) / (float) 2;

            if (checkNumberOfJoints == (int) checkNumberOfJoints && degreeOfFreedom<numberOfLink) {
                //Toast.makeText(getApplicationContext(), "Submitted successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, calcArray.class);


                intent.putExtra("degreeOfFreedom",degreeOfFreedom);
                intent.putExtra("numberOfLink",numberOfLink);

                startActivity(intent);

            }
            else {
                Toast.makeText(getApplicationContext(), "Please enter valid combination of number of link and degree of freedom !", Toast.LENGTH_SHORT).show();
            }
        } else
            Toast.makeText(getApplicationContext(), "Please select the required field!", Toast.LENGTH_SHORT).show();
    }


}
