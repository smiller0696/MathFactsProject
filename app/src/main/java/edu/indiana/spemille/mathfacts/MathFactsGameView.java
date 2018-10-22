/*MathFactsGameView.java
*
*Contains the Java code for the
*Game View in this project
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
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.graphics.Point;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.media.MediaPlayer;

import java.util.Locale;
import java.util.Random;

import android.speech.SpeechRecognizer;

import java.util.ArrayList;


/**
 * Created by smill on 2/27/2018.
 */

public class MathFactsGameView extends View {
    private MediaPlayer mp;
    private SpeechRecognizer sr;
    public boolean[] checks = MathFactsSettingsActivity.values;
    public String mText = "nothing";
    public String response = "nothing";
    private static final String TAG = "MyGameView";

    long startTime;
    int framesPerSecond = 60;
    Matrix matrix = new Matrix();
    int scoreint = 0;
    int[] addNumsEasy = new int[]{0, 1, 2, 3, 4, 5};
    int[] addNumsMed = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int[] addNumsHard = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
    int[] multNumsEasy = new int[]{0, 1, 2, 3, 4, 5};
    int[] multNumsMed = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int[] multNumsHard = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    int[] divNumsEasy = new int[]{ 1, 2, 3, 4, 5};
    int[] divNumsMed = new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int[] divNumsHard = new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    int correctAnswer;
    int speed = 80;


    public void startListening() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, Locale.getDefault());

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        sr.startListening(intent);
    }


    public MathFactsGameView(Context context) {


        super(context);
        mp = MediaPlayer.create(context, R.raw.game_over);

        setFocusable(true);
        setFocusableInTouchMode(true);
        this.startTime = System.currentTimeMillis();
        sr = SpeechRecognizer.createSpeechRecognizer(context);

        sr.setRecognitionListener(new listener());
    }


    //Checks if the given answer is correct
    public boolean correct() {
        Log.d("FROM NOT CORRECT", response);
        return (response.equals(String.valueOf(correctAnswer)));
    }

    //Retrieves the data entered in the settings page
    public int getDiff() {
        int diff = 3;
        if (checks[0]) {
            diff = 1;

        } else if (checks[1]) {
            diff = 2;
        }
        return diff;
    }

    //creates a division problem
    public String getDiv(int diff) {
        Random rand = new Random();
        int[] numbers;
        if (diff == 1) {
            numbers = divNumsEasy;
        } else if (diff == 2) {
            numbers = divNumsMed;

        } else {
            numbers = divNumsHard;
        }
        int i = numbers[rand.nextInt(numbers.length)];
        int j = numbers[rand.nextInt(numbers.length)];
        int high = i * j;
        correctAnswer = j;
        return (String.valueOf(high) + " / " + String.valueOf(i));
    }

    //creates an addition problem
    public String getAdd(int diff) {
        Random rand = new Random();
        int[] numbers;
        if (diff == 1) {
            numbers = addNumsEasy;
        } else if (diff == 2) {
            numbers = addNumsMed;

        } else {
            numbers = addNumsHard;
        }
        int i = numbers[rand.nextInt(numbers.length)];
        int j = numbers[rand.nextInt(numbers.length)];
        correctAnswer = i + j;
        return (String.valueOf(i) + " + " + String.valueOf(j));
    }

    //creates a multiplication problem
    public String getMult(int diff) {
        Random rand = new Random();
        int[] numbers;
        if (diff == 1) {
            numbers = multNumsEasy;
        } else if (diff == 2) {
            numbers = multNumsMed;

        } else {
            numbers = multNumsHard;
        }
        int i = numbers[rand.nextInt(numbers.length)];
        int j = numbers[rand.nextInt(numbers.length)];
        correctAnswer = i * j;
        return (String.valueOf(i) + " x " + String.valueOf(j));

    }


    //creates a subtraction problem
    public String getSub(int diff) {
        Random rand = new Random();
        int[] numbers;
        if (diff == 1) {
            numbers = addNumsEasy;
        } else if (diff == 2) {
            numbers = addNumsMed;

        } else {
            numbers = addNumsHard;
        }
        int i = numbers[rand.nextInt(numbers.length)];
        int j = numbers[rand.nextInt(numbers.length)];
        int high = i + j;
        correctAnswer = j;
        return (String.valueOf(high) + " - " + String.valueOf(i));

    }

    //returns problem by the given settings for the operator to use
    public String getProblem(int diff) {

        Random rand = new Random();
        String problem = "problem0";
        boolean add = checks[3];
        boolean sub = checks[4];
        boolean mult = checks[5];
        boolean div = checks[6];

        //Case for every combination of operators
        if (add && sub && mult && div) {
            int op = rand.nextInt(4);
            if (op == 3) {
                problem = getDiv(diff);
            } else if (op == 2) {
                problem = getMult(diff);
            } else if (op == 1) {
                problem = getSub(diff);
            } else {
                problem = getAdd(diff);
            }
        } else if (add && sub && mult && !div) {
            int op = rand.nextInt(3);


            if (op == 2) {
                problem = getMult(diff);
            } else if (op == 1) {
                problem = getSub(diff);
            } else {
                problem = getAdd(diff);
            }
        } else if (add && sub && div && !mult) {
            int op = rand.nextInt(3);


            if (op == 2) {
                problem = getDiv(diff);
            } else if (op == 1) {
                problem = getSub(diff);
            } else {
                problem = getAdd(diff);
            }
        } else if (add && sub && !mult && !div) {
            int op = rand.nextInt(2);


            if (op == 1) {
                problem = getSub(diff);
            } else {
                problem = getAdd(diff);
            }
        } else if (add && !sub && mult && div) {
            int op = rand.nextInt(3);


            if (op == 2) {
                problem = getDiv(diff);
            } else if (op == 1) {
                problem = getMult(diff);
            } else {
                problem = getAdd(diff);
            }
        } else if (add && !sub && mult && !div) {


            int op = rand.nextInt(2);


            if (op == 1) {
                problem = getMult(diff);

            } else {
                problem = getAdd(diff);
            }

        } else if (add && !sub && !mult && div) {

            int op = rand.nextInt(2);


            if (op == 1) {
                problem = getDiv(diff);
            } else {
                problem = getAdd(diff);
            }
        } else if (add && !sub && !mult && !div) {

            problem = getAdd(diff);

        } else if (!add && sub && mult && div) {
            int op = rand.nextInt(3);


            if (op == 2) {
                problem = getDiv(diff);
            } else if (op == 1) {
                problem = getMult(diff);
            } else {
                problem = getSub(diff);
            }

        } else if (!add && sub && mult && !div) {

            int op = rand.nextInt(2);


            if (op == 1) {
                problem = getMult(diff);
            } else {
                problem = getSub(diff);
            }
        } else if (!add && sub && !mult && div) {int op = rand.nextInt(2);


            if (op == 1) {
                problem = getDiv(diff);
            } else {
                problem = getSub(diff);
            }
        } else if (!add && sub && !mult && !div) {


            problem = getSub(diff);
        } else if (!add && !sub && mult && div) {
            int op = rand.nextInt(2);


            if (op == 1) {
                problem = getDiv(diff);
            } else {
                problem = getMult(diff);
            }
        } else if (!add && !sub && mult && !div) {

            problem = getMult(diff);

        } else if (!add && !sub && !mult && div) {
            problem = getDiv(diff);

        }


        return problem;


    }

    String problem = getProblem(getDiff());

    @Override
    //draws the gameboard
    protected void onDraw(Canvas canvas) {
        //starts recording audio
        startListening();

        //background paint
        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.gameBackground));
        canvas.drawRect(0, 0, getWidth(), getHeight(), background);

        //top bar paint
        Paint top = new Paint();
        top.setColor(getResources().getColor(R.color.gameTop));
        canvas.drawRect(0, 0, getWidth(), getHeight() / 10, top);

        //triangles on top of screen
        Paint triangles = new Paint();
        triangles.setColor(android.graphics.Color.BLACK);

        triangles.setStrokeWidth(4);
        triangles.setColor(getResources().getColor(R.color.trianglesColor));
        triangles.setStyle(Paint.Style.FILL_AND_STROKE);
        triangles.setAntiAlias(true);

        Point a = new Point(0, getHeight() / 10);
        Point b = new Point(getWidth() / 15, getHeight() / 10);
        Point c = new Point(getWidth() / 15 / 2, getHeight() / 20 + getHeight() / 10);



        Path path = new Path();
        path.setFillType(FillType.EVEN_ODD);
        path.moveTo(0, getHeight() / 10);
        path.lineTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.lineTo(a.x, a.y);
        path.close();
        canvas.drawPath(path, triangles);

        for (int i = 1; i < 15; i++) {
            int add = (getWidth() / 15) * i;

            path.setFillType(FillType.EVEN_ODD);
            path.moveTo(0 + add, getHeight() / 10);
            path.lineTo(b.x + add, b.y);
            path.lineTo(c.x + add, c.y);
            path.lineTo(a.x + add, a.y);
            path.close();

            canvas.drawPath(path, triangles);
        }
        //display score on top
        Paint score = new Paint();
        score.setTextSize(getHeight() * 0.05f);
        score.setTextAlign(Paint.Align.CENTER);
        score.setColor(getResources().getColor(R.color.text));

        String scorest = "SCORE: " + Integer.toString(scoreint);
        canvas.drawText(scorest, getWidth() / 2, getHeight() / 15, score);

        //draw circles with the problems in them
        Paint circles = new Paint();
        circles.setTextSize(getHeight() * 0.05f);
        circles.setColor(getResources().getColor(R.color.circles));
        int posX = getWidth() / 2;
        int posY = getHeight() + 200;
        int temp = posY;

        long elapsedTime = System.currentTimeMillis() - startTime;

        float[] values = new float[9];
        matrix.getValues(values);
        //ball has not touched the top yet
        if ((-1 * values[5] < getHeight() - ((getHeight() / 20 + getHeight() / 10)))) {


            //correct answer was heard
            if (correct()) {


                scoreint++;
                matrix = new Matrix();

                startTime = System.currentTimeMillis();
                this.postInvalidateDelayed(speed-=5 / framesPerSecond);
                response = "nothing";
                problem = getProblem(getDiff());
            } else {//correct answer not heard yet
                matrix.postTranslate(0, -1 * elapsedTime / 1000);
                // other transformations...

                canvas.concat(matrix);

                canvas.drawCircle(posX, posY, 200, circles);
                circles.setColor(getResources().getColor(R.color.text));
                circles.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(problem, getWidth()/2 , posY+40, circles);
                this.postInvalidateDelayed(speed / framesPerSecond);
            }
        } else {
            //ball has reached the top with no corret answer

            //play game over sound
            mp.start();

            //game over screen
            Paint gameover = new Paint();
            gameover.setColor(getResources().getColor(R.color.gameover));
            canvas.drawRect(0, 0, getWidth(), getHeight(), gameover);
            gameover.setColor(getResources().getColor(R.color.text));
            gameover.setTextAlign(Paint.Align.CENTER);
            gameover.setTextSize(getHeight()/20);
            canvas.drawText("GAME OVER", getWidth()/2,getHeight()/4,gameover );
            gameover.setTextSize(getHeight()/25);
            canvas.drawText("Score: "+String.valueOf(scoreint), getWidth()/2, getHeight()/2, gameover);
            Log.d(TAG, mText);




        }
        if (mText != "nothing") {
            startListening();
        }

    }



    //Listener to hear voice
    class listener implements RecognitionListener {

        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "onReadyForSpeech");
        }

        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech");
        }

        public void onRmsChanged(float rmsdB) {
            Log.d(TAG, "onRmsChanged");
        }

        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived");
        }

        public void onEndOfSpeech() {
            Log.d(TAG, "onEndofSpeech");
        }

        public void onError(int error) {
            if (error == 8) {

            }
            Log.d(TAG, "error " + error);
            mText.equals("error " + error);
        }

        //Get results
        public void onResults(Bundle results) {
            String str = new String();
            Log.d(TAG, "onResults " + results);
            ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++) {
                Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }
            //mText.setText("results: "+String.valueOf(data.size()));
            if (data.size() > 0)
                response = String.valueOf(data.get(0));
            mText.equals(String.valueOf(data.get(0)));

        }

        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
        }

        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
}






