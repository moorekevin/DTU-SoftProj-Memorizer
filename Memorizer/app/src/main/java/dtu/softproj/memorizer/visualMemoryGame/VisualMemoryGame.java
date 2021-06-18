package dtu.softproj.memorizer.visualMemoryGame;

import android.os.CountDownTimer;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import dtu.softproj.memorizer.R;

public class VisualMemoryGame extends AppCompatActivity {
    public static final String gameName = "Visual Memory";
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
        prepareGameLayout();
        playGame();


    }

    public void playGame(){
        generateTrueTiles();
        //TODO make it not take level as argument (it can find it itself)
        setGame(getDimensionFromLevel(level));
        showCorrect();
        //TODO make it so you can't press buttons while showing the correct ones
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid.length; j++){
                clickFunction(buttons[i][j], grid[i][j]);
            }
        }
    }
    public void prepareGameLayout(){
//        TextView tvLevel = findViewById(R.id.tvLevel);
//        TextView tvLives = findViewById(R.id.tvLives);
//        tvLevel.setText("Level: " + level);
//        tvLives.setText("Lives: " + lives);
        for (int i = 0; i <  8; i++){
            for (int j = 0; j < 8; j++){
                String buttonId = "b" + (i + 1) + "_" + (j + 1);
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = (Button) findViewById(resId);
                buttons[i][j].setLayoutParams (new LinearLayout.LayoutParams(0, 0));

            }
        }
    }

    public void setGame(int dimension){
        TextView tvLevel = findViewById(R.id.tvLevel);
        TextView tvLives = findViewById(R.id.tvLives);
        tvLevel.setText("Level: " + level);
        tvLives.setText("Lives: " + lives);
        int tileSize;
        //TODO Do based on screen size instead? (to make work on small phones and tablets)
        switch (dimension) {
            case 3:
                tileSize = 300;
                break;
            case 4:
                tileSize = 225;
                break;
            case 5:
                tileSize = 180;
                break;
            case 6:
                tileSize = 150;
                break;
            case 7:
                tileSize = 125;
                break;
            case 8:
                tileSize = 110;
                break;
            default:
                tileSize = 0;
                break;
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++){
                buttons[i][j].setLayoutParams (new LinearLayout.LayoutParams(tileSize, tileSize));
//                if(grid[i][j]){
//                    buttons[i][j].setText("True");
//                } else{
//                    buttons[i][j].setText("False");
//                }

            }
        }

    }

    public void showCorrect(){
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid.length; j++){
                if (grid[i][j]){
                    buttons[i][j].setText("T");
                }
            }
        }
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
        mCountDownTimer = new CountDownTimer(3000, 50) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                for (int i = 0; i < grid.length; i++){
                    for (int j = 0; j < grid.length; j++){
                        buttons[i][j].setText("");
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
                btn.setText("T");
                //TODO enable buttons in grid[][] == true again
                if (numberOfTrueTilesPressed == level + 2) {
                    //TODO change to show win screen
                    roundDone = true;
                }
            } else {
                tempLives--;
                if (tempLives < 1) {
                    //TODO if lives > 0 should start again at same level instead
                    lives--;
                    roundDone = true;

                } else {
                    //btn.setBackgroundColor(Color.BLACK);
                    btn.setText("F");
                    btn.setEnabled(false);
                }
            }
            if (roundDone){
                nextScreen();
            }
        });
    }
    public void resetUsedButtons(){
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid.length; j++){
                buttons[i][j].setEnabled(true);
                buttons[i][j].setText("");
            }
        }
    }
    public void nextScreen(){
        if (lives > 0){
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
    public int getDimensionFromLevel(int level){
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

    public void generateTrueTiles(){
        int numberOfTrueTiles = level + 2;
        int dimension = getDimensionFromLevel(level);
        grid = new boolean[dimension][dimension];

        for (int i = 0; i < numberOfTrueTiles; i++){
            int x = (int) (Math.random() * dimension);
            int y = (int) (Math.random() * dimension);
            if (grid[x][y]){
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