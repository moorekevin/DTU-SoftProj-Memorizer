package dtu.softproj.memorizer.sequenceMemoryGame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dtu.softproj.memorizer.MainActivity;
import dtu.softproj.memorizer.R;

public class SequenceGameOver extends AppCompatActivity {

    private Button mPlayAgain;
    private Button mStatistics;
    private ImageButton homeButton;
    private TextView yourScore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover_layout);
        mPlayAgain = (Button) findViewById(R.id.playAgainButton);
//        mStatistics = (Button) findViewById(R.id.statisticsButton);
        homeButton = (ImageButton) findViewById(R.id.homeButton);
        yourScore = (TextView) findViewById(R.id.yourScore);

        yourScore.setText("YOUR SCORE: " + SequenceGame.getLevel());

        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.gameOverRelativeLayout);
        rLayout.setBackgroundColor(Color.parseColor("#ff9494"));

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameHomeIntent = new Intent(SequenceGameOver.this, MainActivity.class);
                startActivity(gameHomeIntent);
            }
        });

        mPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent restartIntent = new Intent(SequenceGameOver.this, SequenceGame.class);
                startActivity(restartIntent);
            }
        });
    }
}
