package dtu.softproj.memorizer.sequenceMemoryGame;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Random;

import dtu.softproj.memorizer.R;

public class SequenceGame extends AppCompatActivity implements View.OnClickListener {
    public static final String GAME_NAME = "Sequence Memory";
    private final int DISPLAY_TIME = 1000;
    private final int DELAY_TIME = 1000;
    private ArrayList<Integer> sequence;
    private Button[] buttons;
    private int currentNum;
    private boolean isSequenceBeingDisplayed;
    private static int level;
    private LinearLayout rLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sequence_layout);
        rLayout = (LinearLayout) findViewById(R.id.sequence_layout);
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

            // Display as white while pressed down
            buttons[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        view.getBackground().setLevel(1);
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        view.getBackground().setLevel(0);
                    }
                    return false;
                }
            });
        }

        addNumberToSequence();
        displaySequence();
    }

    public void addNumberToSequence() {
        Random rand = new Random();
        int newNum = rand.nextInt(9); // random number between 0-8
        while (sequence.size() > 0 && newNum == sequence.get(sequence.size() - 1)) {
            newNum = rand.nextInt(9);
        }
        sequence.add(newNum);
    }

    public static int getLevel() {
        return level;
    }

    public void displaySequence() {
        isSequenceBeingDisplayed = true;
        for (int i = 0; i < buttons.length; i++) buttons[i].setSoundEffectsEnabled(false);

        for (int i = 0; i < sequence.size(); i++) {

            final Handler handler = new Handler();
            int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    buttons[sequence.get(finalI)].getBackground().setLevel(1);
                }
            }, DISPLAY_TIME * i + DELAY_TIME);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    buttons[sequence.get(finalI)].getBackground().setLevel(0);
                    if (finalI == (sequence.size() - 1)) {
                        isSequenceBeingDisplayed = false;
                        for (int i = 0; i < buttons.length; i++)
                            buttons[i].setSoundEffectsEnabled(true);
                    }
                }
            }, DISPLAY_TIME * (i + 1) + DELAY_TIME);
        }
    }
    
    @SuppressLint("WrongConstant")
    public void manageBlinkEffect(LinearLayout rLayout , String startColor, String endColor) {
        ObjectAnimator anim = ObjectAnimator.ofInt(rLayout , "backgroundColor",
                Color.parseColor(startColor) , Color.parseColor(endColor));
        anim.setDuration(300);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(1);
        anim.start();
    }

    @Override
    public void onClick(View view) {
        if (!isSequenceBeingDisplayed) {
            if (view.getId() == buttons[sequence.get(currentNum)].getId()) {
                currentNum++;
                if (currentNum == sequence.size()) {
                    TextView levelTextView = findViewById(R.id.sequence_level);
                    addNumberToSequence();
                    level = sequence.size();
                    levelTextView.setText("LEVEL: " + level);
                    currentNum = 0;
                    manageBlinkEffect(rLayout , "#ff9494", "#94ff9B");
//                    cLayout.setBackgroundColor(Color.parseColor("#ff9494"));
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
