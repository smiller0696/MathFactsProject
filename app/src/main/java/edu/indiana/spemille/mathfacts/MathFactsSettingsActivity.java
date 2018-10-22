/*MathFactsSettingsActivity.java
*
*Contains the Java code for the
*Settings Activity in this project
*
*Created by: Spencer Miller
*Created on: 2/24/18
*Last Modified by: Spencer Miller
*Last Modified on: 3/2/18
*Assignment/Project: A290 Final Project
*Part of: Math Facts, refers to content_math_facts_settings.xml layout
**/
package edu.indiana.spemille.mathfacts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class MathFactsSettingsActivity extends AppCompatActivity implements View.OnClickListener{

    //array used to pass check box values to the view
    public static boolean[] values = new boolean[] {false, true, false, true, true, true, true};

    CheckBox AddCheck;
    CheckBox SubCheck;
    CheckBox MultCheck;
    CheckBox DivCheck;
    SharedPreferences settings;
    RadioButton HardCheck;
    RadioButton MedCheck;
    RadioButton EasyCheck;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_facts_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set listener for every button

        EasyCheck = findViewById(R.id.radio_easy);
        EasyCheck.setOnClickListener(this);

        MedCheck = findViewById(R.id.radio_med);
        MedCheck.setOnClickListener(this);

        HardCheck = findViewById(R.id.radio_hard);
        HardCheck.setOnClickListener(this);


        AddCheck = findViewById(R.id.add_check);
        AddCheck.setOnClickListener(this);

        SubCheck = findViewById(R.id.sub_check);
        SubCheck.setOnClickListener(this);

        MultCheck = findViewById(R.id.mult_check);
        MultCheck.setOnClickListener(this);

        DivCheck = findViewById(R.id.div_check);
        DivCheck.setOnClickListener(this);

        View Home = findViewById(R.id.btn_home);
        Home.setOnClickListener(this);

        //used to save states of check boxes
        settings = getSharedPreferences("mysettings", 0);
        SharedPreferences.Editor editor = settings.edit();

        //save state of each box
        boolean easyCheckBoxValue = EasyCheck.isChecked();
        editor.putBoolean("Easycheck", easyCheckBoxValue);
        editor.commit();

        boolean medCheckBoxValue = MedCheck.isChecked();
        editor.putBoolean("MedCheck", medCheckBoxValue);
        editor.commit();

        boolean hardCheckBoxValue = HardCheck.isChecked();
        editor.putBoolean("HardCheck", hardCheckBoxValue);
        editor.commit();

        boolean addCheckBoxValue = AddCheck.isChecked();
        editor.putBoolean("Addcheck", addCheckBoxValue);
        editor.commit();

        boolean subCheckBoxValue = SubCheck.isChecked();
        editor.putBoolean("SubCheck", subCheckBoxValue);
        editor.commit();


        boolean multCheckBoxValue = MultCheck.isChecked();
        editor.putBoolean("Multcheck", multCheckBoxValue);
        editor.commit();

        boolean divCheckBoxValue = DivCheck.isChecked();
        editor.putBoolean("Divcheck", divCheckBoxValue);
        editor.commit();

        //initialize each box
        AddCheck.setChecked(settings.getBoolean("AddCheck", values[3]));
        SubCheck.setChecked(settings.getBoolean("SubbCheck", values[4]));
        MultCheck.setChecked(settings.getBoolean("MultCheck", values[5]));
        DivCheck.setChecked(settings.getBoolean("DivCheck", values[6]));

        EasyCheck.setChecked(settings.getBoolean("EasyCheck", values[0]));
        MedCheck.setChecked(settings.getBoolean("MedCheck", values[1]));
        HardCheck.setChecked(settings.getBoolean("HardCheck", values[2]));



    }
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_home:
                Intent i = new Intent(this, MathFactsMainActivity.class);
                startActivity(i);
                break;
            case R.id.radio_easy:
                EasyCheck.setChecked(settings.getBoolean("AddCheck", true));
                MedCheck.setChecked(settings.getBoolean("AddCheck", false));
                HardCheck.setChecked(settings.getBoolean("AddCheck", false));

                values[0] = true;
                values[1] = false;
                values[2] = false;
                break;
            case R.id.radio_med:
                EasyCheck.setChecked(settings.getBoolean("AddCheck", false));
                MedCheck.setChecked(settings.getBoolean("AddCheck", true));
                HardCheck.setChecked(settings.getBoolean("AddCheck", false));
                values[0] = false;
                values[1] = true;
                values[2] = false;
                break;
            case R.id.radio_hard:
                EasyCheck.setChecked(settings.getBoolean("AddCheck", false));
                MedCheck.setChecked(settings.getBoolean("AddCheck", false));
                HardCheck.setChecked(settings.getBoolean("AddCheck", true));

                values[0] = false;
                values[1] = false;
                values[2] = true;
                break;
            case R.id.add_check:
                AddCheck.setChecked(settings.getBoolean("AddCheck", !values[3]));


                values[3] = !values[3];
                break;
            case R.id.sub_check:
                SubCheck.setChecked(settings.getBoolean("SubbCheck", !values[4]));
                values[4] = !values[4];

                break;

            case R.id.mult_check:
                MultCheck.setChecked(settings.getBoolean("MultCheck", !values[5]));

                values[5] = !values[5];
                break;

            case R.id.div_check:
                DivCheck.setChecked(settings.getBoolean("DivCheck", !values[6]));

                values[6] = !values[6];
                break;


        }


    }




}
