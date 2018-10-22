/*MathFactsGameActivity.java
*
*
*Created by: Spencer Miller
*Created on: 2/24/18
*Last Modified by: Spencer Miller
*Last Modified on: 3/2/18
*Assignment/Project: A290 Final Project
*Part of: Math Facts
**/

package edu.indiana.spemille.mathfacts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MathFactsGameActivity extends AppCompatActivity {


    private MathFactsGameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_facts_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gameView = new MathFactsGameView(this);
        setContentView(gameView);
        gameView.requestFocus();
    }

}
