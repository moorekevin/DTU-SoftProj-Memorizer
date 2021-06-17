package dtu.softproj.memorizer.visualMemoryGame;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import dtu.softproj.memorizer.R;

public class VisualMemoryGame extends AppCompatActivity {
    private int level = 1;
    private int lives = 3;
    private int tempLives = 3;
    private Button[][] buttons = new Button[8][8];
    private boolean[][] grid;
    private int numberOfTrueTilesPressed;
    private CountDownTimer mCountDownTimer;
    private int screenSizeLowest;
    private TextView tvLevel;
    private TextView tvLives;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visual_memory_layout);
        buttonViewsToArray();
        initFields();

        startRound();


    }


    public void buttonViewsToArray(){
        for (int i = 0; i <  8; i++){
            for (int j = 0; j < 8; j++){
                String buttonId = "b" + (i + 1) + "_" + (j + 1);
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = (Button) findViewById(resId);

            }
        }
    }

    public void initFields(){
        tvLevel = findViewById(R.id.tvLevel);
        tvLives = findViewById(R.id.tvLives);
        //TODO Change to use weight in linearLayout? If not, check if this works on other screen sizes
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        if (screenWidth > screenHeight - 600) {
            screenSizeLowest = screenHeight - 600;
        } else {
            screenSizeLowest = screenWidth;
        }
    }

    public void startRound() {
        makeGUI();
        generateTrueTiles();
        showTrueTiles();
        defButtonOnClick();
    }

    public void makeGUI() {
        //reset();
        tvLevel.setText("Level: " + level);
        tvLives.setText("Lives: " + lives);

        int dimension = getDimensionFromLevel();
        for (int i = 0; i < dimension; i++){
            for (int j = dimension; j < 8; j++){

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

    public void reset(){
        for (int i = 0; i < 8; i ++){
            String rowResId = "linearLayout" + (i + 1);
            int rowId = getResources().getIdentifier(rowResId, "id", getPackageName());
            LinearLayout row = findViewById(rowId);
            row.setVisibility(View.VISIBLE);
            for (int j = 0; j < 8; j++){
                buttons[i][j].setVisibility(View.VISIBLE);

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
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j]) {
                    buttons[i][j].setBackgroundColor(Color.parseColor("#9CD2CE"));
                }
            }
        }
        //TODO make it so you can't press buttons while showing the correct ones
        //TODO Consider give longer time based on amount of squares/grid size?
        //TODO problem if finishing level before 'un-showing' the correct tiles
        mCountDownTimer = new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid.length; j++) {
                        buttons[i][j].setBackgroundColor(Color.parseColor("#4E9AE6"));
                    }
                }
            }
        };
        mCountDownTimer.start();
    }
    public void defButtonOnClick(){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                defOnClick(buttons[i][j], grid[i][j]);
            }
        }
    }
    public void defOnClick(Button btn, boolean isTrueTile) {
        btn.setOnClickListener((View v) -> {
            btn.setEnabled(false);
            if (isTrueTile) {
                numberOfTrueTilesPressed++;
                if (numberOfTrueTilesPressed == level + 2) {
                    roundOver(true);
                } else{
                    btn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            } else {
                tempLives--;
                if (tempLives < 1) {
                    roundOver(false);
                } else {
                    btn.setBackgroundColor(Color.parseColor("#294191"));
                }
            }
        });
    }

    public void roundOver(boolean roundWon) {
        if (roundWon){
            level++;
            newRound();
        } else {
            lives--;
            if (lives > 0) {
                newRound();
            } else {
                finish();
                Intent intent = new Intent(VisualMemoryGame.this, VisualGameOver.class);
                startActivity(intent);
            }
        }
    }

    public void newRound() {
        revertToStart();
        startRound();
    }

    public void revertToStart(){
        resetAllButtons();
        numberOfTrueTilesPressed = 0;
        tempLives = 3;
    }

    public void resetAllButtons() {
       /*
        String rowResId = "linearLayout" + getDimensionFromLevel();

        int rowId = getResources().getIdentifier(rowResId, "id", getPackageName());
        LinearLayout layout = findViewById(rowId);
        layout.setVisibility(View.VISIBLE);
        */
        for (int i = 0; i < 8; i++){

            String rowResId = "linearLayout" + (i + 1);
            int rowId = getResources().getIdentifier(rowResId, "id", getPackageName());
            LinearLayout row = findViewById(rowId);
            row.setVisibility(View.VISIBLE);

            for (int j = 0; j < 8; j++) {
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackgroundColor(Color.parseColor("#6495ED"));
                buttons[i][j].setVisibility(View.VISIBLE);

            }
        }
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


/*Entire old file - 17-Jun-2021, 13:47 - works with a few flaws*/
/*
package dtu.softproj.memorizer.visualMemoryGame;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import dtu.softproj.memorizer.R;

public class VisualMemoryGame extends AppCompatActivity {
    private int level = 1;
    private int lives = 3;
    private int tempLives = 3;
    private Button[][] buttons = new Button[8][8];
    private boolean[][] grid;
    private int numberOfTrueTilesPressed;
    private CountDownTimer mCountDownTimer;
    private boolean roundDone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visual_memory_layout);
        //createGame(3);
        removeButtonsAndInitButtonArray();
        playGame();


    }

    public void playGame() {
        generateTrueTiles();
        setGame(getDimensionFromLevel());
        showCorrect();
        //TODO make it so you can't press buttons while showing the correct ones
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                clickFunction(buttons[i][j], grid[i][j]);
            }
        }
    }

    public void removeButtonsAndInitButtonArray() {
//        TextView tvLevel = findViewById(R.id.tvLevel);
//        TextView tvLives = findViewById(R.id.tvLives);
//        tvLevel.setText("Level: " + level);
//        tvLives.setText("Lives: " + lives);
//        RelativeLayout rel = findViewById(R.id.ok);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String buttonId = "b" + (i + 1) + "_" + (j + 1);
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = (Button) findViewById(resId);
                // rel.removeView(buttons[i][j]);
                //buttons[i][j].setLayoutParams (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                buttons[i][j].setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            }
        }
    }

    public void createGame(int dimension) {
        TextView tvLevel = findViewById(R.id.tvLevel);
        TextView tvLives = findViewById(R.id.tvLives);
        tvLevel.setText("Level: " + level);

        tvLives.setText("Lives: " + lives);

        float weight = (float) dimension;


        for (int i = 0; i < dimension; i++) {
            String layoutId = "linearLayout" + (i + 1);
            int resLayoutId = getResources().getIdentifier(layoutId, "id", getPackageName());
            LinearLayout linearLayout = findViewById(resLayoutId);
            linearLayout.setWeightSum(weight);

            for (int j = 0; j < dimension; j++) {
                String buttonId = "b" + (i + 1) + "_" + (j + 1);
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = (Button) findViewById(resId);
                buttons[i][j].setBackgroundColor(Color.WHITE);

            }

        }
    }

    public void setGame(int dimension) {
        TextView tvLevel = findViewById(R.id.tvLevel);
        TextView tvLives = findViewById(R.id.tvLives);
        tvLevel.setText("Level: " + level);
        tvLives.setText("Lives: " + lives);
//        int tileSize;
        //TODO Change to use weight in linearLayout? If not, check if this works on other screen sizes
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;
        int screenSizeLowest;
        if (screenWidth > screenHeight - 600) {
            screenSizeLowest = screenHeight - 600;
        } else {
            screenSizeLowest = screenWidth;
        }
        int tileSize = (screenSizeLowest - 60) / dimension;
//        switch (dimension) {
//            case 3:
//                tileSize = 300;
//                break;
//            case 4:
//                tileSize = 225;
//                break;
//            case 5:
//                tileSize = 180;
//                break;
//            case 6:
//                tileSize = 150;
//                break;
//            case 7:
//                tileSize = 125;
//                break;
//            case 8:
//                tileSize = 110;
//                break;
//            default:
//                tileSize = 0;
//                break;
//        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                buttons[i][j].setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
//                if(grid[i][j]){
//                    buttons[i][j].setText("True");
//                } else{
//                    buttons[i][j].setText("False");
//                }

            }
        }

    }

    public static void setLayoutWeight(View view, float weight) {
        ((LinearLayout.LayoutParams) view.getLayoutParams()).weight = weight;
        view.requestLayout();
    }


    public void showCorrect() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j]) {
                    //LinearLayout linearLayout = findViewById(R.id.linearLayout1);
                    //setLayoutWeight(buttons[i][j], 2);
                    //buttons[i][j].setSupportButtonTintList(ContextCompat.getColorStateList(Activity.this, R.color.colorPrimary));
                    //buttons[i][j].setText("T");
                    buttons[i][j].setBackgroundColor(Color.WHITE);
                }
            }
        }
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try{
//                    synchronized(this) {
//                        wait(3000);
//                    }
//                } catch(InterruptedException e){
//
//                }
//            }
//        };
//        thread.start();
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid.length; j++) {
//                buttons[i][j].setBackgroundColor(Color.parseColor("#6495ED"));
//            }
//        }
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    SystemClock.sleep(3000);
//                }
//            });
//            thread.start();
//            for (int i = 0; i < grid.length; i++){
//                for (int j = 0; j < grid.length; j++){
//                    if (grid[i][j]){
//                        buttons[i][j].setText("");
//                    }
//                }
//            }
        //TODO Consider give longer time based on amount of squares/grid size?
        //TODO problem if finishing level before 'un-showing' the correct tiles
        mCountDownTimer = new CountDownTimer(3000, 50) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid.length; j++) {
                        buttons[i][j].setBackgroundColor(Color.parseColor("#6495ED"));
                    }
                }


            }
        };
        mCountDownTimer.start();
    }

    public void clickFunction(Button btn, boolean bool) {
        btn.setOnClickListener((View v) -> {
            if (bool) {
                numberOfTrueTilesPressed++;
                //(Color.parseColor("#ffffff"));
                btn.setEnabled(false);
                btn.setBackgroundColor(Color.WHITE);
                if (numberOfTrueTilesPressed == level + 2) {
                    roundDone = true;
                }
            } else {
                tempLives--;
                if (tempLives < 1) {
                    lives--;
                    level--;
                    roundDone = true;

                } else {
                    //btn.setBackgroundColor(Color.BLACK);
                    btn.setBackgroundColor(Color.GRAY);
                    btn.setEnabled(false);
                }
            }
            if (roundDone) {
                nextScreen();
            }
        });
    }

    public void resetUsedButtons() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackgroundColor(Color.parseColor("#6495ED"));
            }
        }
    }

    public void nextScreen() {
        if (lives > 0) {
            level++;
            resetUsedButtons();
            numberOfTrueTilesPressed = 0;
            tempLives = 3;
            roundDone = false;
            playGame();
        } else {
            Intent intent = new Intent(VisualMemoryGame.this, VisualGameOver.class);
            startActivity(intent);
        }
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
        //3x3: 1-2      2
        //4x4: 3-5      3
        //5x5: 6-9      4
        //6x6: 10-14    5
        //7x7: 15-20    6
        //8x8  21+

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
//        for (int i = 0; i < grid.length; i++){
//            for (int j = 0; j < grid[i].length; j++){
//                if(grid[i][j]){
//                    System.out.print(" * ");
//                } else {
//                    System.out.print(" - ");
//                }
//            }
//            System.out.println("");
//        }

    }
    // i = j1 j2 j3 j4
    // i2 = j5 j6
    //
    //  a[1][1]     a[1][2]     a[1][3]
    //  a[2][1]     a[2][2]     a[2][3]
}


//    public void drawGame(int tiles){
//        TextView tvLevel = findViewById(R.id.tvLevel);
//        TextView tvLives = findViewById(R.id.tvLives);
//        tvLevel.setText("Level: " + level);
//        tvLives.setText("Lives: " + lives);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        params.setMargins(10, 10, 10, 10);
//        for (int i = 0; i < tiles; i++){
//            RelativeLayout relativeLayout = new RelativeLayout(this);
//            for (int j = 0; j < tiles; j++){
//                Button button = new Button(this);
//                button.setLayoutParams(params);
//                button.setBackgroundColor(Color.parseColor(buttonColor));
//            }
//        }
//
//    }


//was in onCreate
//        Fragment mFragment = new NumberFragment1();
//
//        FragmentManager fragmentManager = getFragmentManager();
//
//        FragmentTransaction fragmentTransaction = fragmentManager
//                .beginTransaction();
//
//        fragmentTransaction.replace(R.id.flFragment, mFragment).commit();

 */





