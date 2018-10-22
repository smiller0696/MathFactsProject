/*MathFactsMainActivity.java
*
*Contains the Java code for the
*Primary Activity in this project
*
*Created by: Spencer Miller
*Created on: 2/24/18
*Last Modified by: Spencer Miller
*Last Modified on: 3/2/18
*Assignment/Project: A290 Final Project
*Part of: Math Facts, refers to content_math_facts_main.xml
**/

package edu.indiana.spemille.mathfacts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MathFactsMainActivity extends AppCompatActivity implements View.OnClickListener {
    public int REQUEST_MICROPHONE = 293;

    //ask for permission to record audio
    protected void checkPermission(){
        if(!(checkSelfPermission(Manifest.permission.RECORD_AUDIO )== PackageManager.PERMISSION_GRANTED)){
            if(shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)){
                Toast.makeText(this, "Microphone permissions needed to hear answers.", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_MICROPHONE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkPermission();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_facts_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        setSupportActionBar(toolbar);

        //set  listeners
        View ContinueButton = findViewById(R.id.btn_play);
        ContinueButton.setOnClickListener(this);
        View SettingsButton = findViewById(R.id.btn_settings);
        SettingsButton.setOnClickListener(this);


    }

    public void onClick(View v) {

        //set functions for the buttons
        switch (v.getId()) {
            case R.id.btn_settings:
                Intent i = new Intent(this, MathFactsSettingsActivity.class);
                startActivity(i);
                break;
            case R.id.btn_play:
                openNewGameDialog();
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_math_facts_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void startGame(){

        Intent intent = new Intent(this, MathFactsGameActivity.class);
        startActivity(intent);
    }
    private void openNewGameDialog() {
        startGame();
    }

}
