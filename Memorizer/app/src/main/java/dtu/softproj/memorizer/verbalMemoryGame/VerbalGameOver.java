package dtu.softproj.memorizer.verbalMemoryGame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import dtu.softproj.memorizer.MainActivity;
import dtu.softproj.memorizer.R;
import dtu.softproj.memorizer.visualMemoryGame.VisualGameOver;
import dtu.softproj.memorizer.visualMemoryGame.VisualMemoryGame;

public class VerbalGameOver extends AppCompatActivity {
    private Button mPlayAgain;
    private Button mStatistics;
    private ImageButton homeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover_layout);
        mPlayAgain = (Button) findViewById(R.id.playAgainButton);
        mPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent playAgainIntent = new Intent(VerbalGameOver.this, VerbalMemoryGame.class);
                startActivity(playAgainIntent);
            }
        });

//        mStatistics = (Button) findViewById(R.id.statisticsButton);
        homeButton = (ImageButton) findViewById(R.id.homeButton);

        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.gameOverRelativeLayout);
        rLayout.setBackgroundColor(Color.parseColor("#e088ff"));

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameHomeIntent = new Intent(VerbalGameOver.this, MainActivity.class);
                startActivity(gameHomeIntent);
            }
        });
    }
}
