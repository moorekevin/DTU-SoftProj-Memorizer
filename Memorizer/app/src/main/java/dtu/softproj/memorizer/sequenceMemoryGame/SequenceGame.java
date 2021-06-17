package dtu.softproj.memorizer.sequenceMemoryGame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import dtu.softproj.memorizer.R;

public class SequenceGame extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Integer> sequence;
    private Button[] buttons;
    private int currentNum;
    private boolean isSequenceBeingDisplayed;
    private final int DISPLAY_TIME = 1000;
    private static int level;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sequence_layout);
        level = 1;
        sequence = new ArrayList<Integer>();

        buttons = new Button[9];

        // Add buttons from xml to array
        for (int i = 0; i < 9; i++) {
            String butName = "sequence_tile" + (i + 1);
            int resID = getResources().getIdentifier(butName, "id", getPackageName());
            buttons[i] = (Button) findViewById(resID);
            buttons[i].getBackground().setLevel(0);
            buttons[i].setOnClickListener(this);
        }

        System.out.println("Tal:");
        for (int i = 0; i < 1; i++) {
            addNumberToSequence();
        }
        System.out.println();

        displaySequence();
    }

    public void addNumberToSequence() {
        Random rand = new Random();
        int newNum = rand.nextInt(9); // random number between 0-8
        System.out.print(newNum + 1);
//        int newId = buttons[newNum].getId();
        sequence.add(newNum);
    }

    public static int getLevel() {
        return level;
    }

    // boolean sequenceIsBeingDisplayed = true
    // for every button in sequence
    //     CountDownTimer = new CountDownTimer(1 sec);
    //     start timer and display
    //     when timer stops then stop displaying
    // end for
    // sequenceIsBeingDisplayed = false

    public void displaySequence() {
        isSequenceBeingDisplayed = true;
        for (int i = 0; i <buttons.length; i++) buttons[i].setEnabled(false);

        for (int i = 0; i < sequence.size(); i++) {

            final Handler handler = new Handler();
            int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    buttons[sequence.get(finalI)].getBackground().setLevel(1);
                }
            }, DISPLAY_TIME * i);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    buttons[sequence.get(finalI)].getBackground().setLevel(0);
                    if (finalI == (sequence.size() - 1)) {
                        isSequenceBeingDisplayed = false;
                        for (int i = 0; i <buttons.length; i++) buttons[i].setEnabled(true);
                    }
                }
            }, DISPLAY_TIME * (i + 1));
        }
    }


    @Override
    public void onClick(View view) {
        if (!isSequenceBeingDisplayed) {
            if (view.getId() == buttons[sequence.get(currentNum)].getId()) {
                System.out.println("Du har trykket på den rigtige knap");
                currentNum++;
                if (currentNum == sequence.size()) {
                    TextView levelTextView = findViewById(R.id.sequence_level);
                    addNumberToSequence();
                    System.out.println();
                    level = sequence.size();
                    levelTextView.setText("LEVEL: " + level);
                    currentNum = 0;
                    displaySequence();
                }
            } else {
                finish();
                Intent intent = new Intent(SequenceGame.this, SequenceGameOver.class);
                startActivity(intent);
            }
        }
    }
}
