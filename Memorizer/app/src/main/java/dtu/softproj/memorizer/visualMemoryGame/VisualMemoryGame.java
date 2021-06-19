package dtu.softproj.memorizer.visualMemoryGame;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import dtu.softproj.memorizer.R;

public class VisualMemoryGame extends AppCompatActivity {
    public static final String GAME_NAME = "Visual Memory";
    private final int DISPLAY_TIME = 1500;
    private final int END_DELAY = 1000;
    private final int START_DELAY = 750;
    private final int MAX_GRID_SIZE = 8;
    private int level = 1;
    private int lives = 3;
    private int tempLives = 3;
    private Button[][] buttons = new Button[MAX_GRID_SIZE][MAX_GRID_SIZE];
    private boolean[][] grid;
    private int numberOfTrueTilesPressed;
    private CountDownTimer mCountDownTimer;
    private LinearLayout rLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visual_memory_layout);
        rLayout = (LinearLayout) findViewById(R.id.visual_layout);
        buttonViewsToArray();

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setEnabled(false);
            }
        }

        startRound();
    }

    public void buttonViewsToArray() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String buttonId = "b" + (i + 1) + "_" + (j + 1);
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = (Button) findViewById(resId);

            }
        }
    }

    public void startRound() {
        makeGUI();
        generateTrueTiles();
        showTrueTiles();
        defButtonOnClick();
    }

    public void makeGUI() {
        TextView tvLevel = findViewById(R.id.tvLevel);
        TextView tvLives = findViewById(R.id.tvLives);
        tvLevel.setText("Level: " + level);
        tvLives.setText("Lives: " + lives);

        int dimension = getDimensionFromLevel();
        for (int i = 0; i < dimension; i++) {
            for (int j = dimension; j < MAX_GRID_SIZE; j++) {

                //Set visibility for linearLayout rows
                String rowResId = "linearLayout" + (j + 1);
                int rowId = getResources().getIdentifier(rowResId, "id", getPackageName());
                LinearLayout row = findViewById(rowId);

                row.setVisibility(View.GONE);
                //Set visibility for buttons
                buttons[i][j].setVisibility(View.GONE);
            }
        }

    }


    public void generateTrueTiles() {
        int numberOfTrueTiles = level + 2;
        int dimension = getDimensionFromLevel();
        grid = new boolean[dimension][dimension];

        for (int i = 0; i < numberOfTrueTiles; i++) {
            int x = (int) (Math.random() * dimension);
            int y = (int) (Math.random() * dimension);
            if (grid[x][y]) {
                i--;
            } else {
                grid[x][y] = true;
            }
        }
    }

    public void showTrueTiles() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        if (grid[i][j]) {
                            buttons[i][j].getBackground().setLevel(1);
                        }
                    }
                }
            }
        }, START_DELAY);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        buttons[i][j].getBackground().setLevel(0);
                        buttons[i][j].setEnabled(true);
                    }
                }
            }
        }, DISPLAY_TIME + END_DELAY);
    }

    public void defButtonOnClick() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                defOnClick(buttons[i][j], grid[i][j]);
            }
        }
    }

    public void defOnClick(Button btn, boolean isTrueTile) {
        btn.setOnClickListener((View v) -> {
            btn.setEnabled(false);
            if (isTrueTile) {
                numberOfTrueTilesPressed++;
                btn.getBackground().setLevel(2);
                if (numberOfTrueTilesPressed == level + 2) {
                    roundOver(true);
                } else {

                    //btn.setBackgroundColor(Color.parseColor("#9CD2CE"));
                }
            } else {
                tempLives--;
                btn.getBackground().setLevel(3);
                if (tempLives < 1) {
                    roundOver(false);
                } else {

                    //btn.setBackgroundColor(Color.parseColor("#294191"));
                }
            }
        });
    }

    public void roundOver(boolean roundWon) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                buttons[i][j].setEnabled(false);
            }
        }

        if (roundWon) {
            // Blink green
            manageBlinkEffect(rLayout, "#88e3ff", "#94ff9B");
        } else {
            // Blink red and display missed tiles
            manageBlinkEffect(rLayout, "#88e3ff", "#ff6d6d");
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j]) {
                        buttons[i][j].getBackground().setLevel(1);
                    }
                }
            }
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (roundWon) {
                    level++;
                    newRound();
                } else {
                    lives--;
                    if (lives > 0) {
                        newRound();
                    } else {
                        finish();
                        Intent intent = new Intent(VisualMemoryGame.this, VisualGameOver.class);
                        intent.putExtra("score", level - 1);
                        startActivity(intent);
                    }
                }
            }
        }, END_DELAY);

    }

    public void newRound() {
        resetAllButtons();
        numberOfTrueTilesPressed = 0;
        tempLives = 3;
        startRound();
    }

    public void resetAllButtons() {
        for (int i = 0; i < MAX_GRID_SIZE; i++) {
            String rowResId = "linearLayout" + (i + 1);
            int rowId = getResources().getIdentifier(rowResId, "id", getPackageName());
            LinearLayout row = findViewById(rowId);
            row.setVisibility(View.VISIBLE);

            for (int j = 0; j < MAX_GRID_SIZE; j++) {
                //buttons[i][j].setEnabled(true);
                buttons[i][j].getBackground().setLevel(0);
                buttons[i][j].setVisibility(View.VISIBLE);
            }
        }
    }

    @SuppressLint("WrongConstant")
    public void manageBlinkEffect(LinearLayout cLayout, String startColor, String endColor) {
        ObjectAnimator anim = ObjectAnimator.ofInt(cLayout, "backgroundColor",
                Color.parseColor(startColor), Color.parseColor(endColor));
        anim.setDuration(300);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(1);
        anim.start();
    }

    public int getDimensionFromLevel() {
        if (level <= 2)
            return 3;
        if (level <= 5)
            return 4;
        if (level <= 9)
            return 5;
        if (level <= 14)
            return 6;
        if (level <= 20)
            return 7;
        return 8;
    }
}