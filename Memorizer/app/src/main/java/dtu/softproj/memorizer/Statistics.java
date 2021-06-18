package dtu.softproj.memorizer;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dtu.softproj.memorizer.numberMemoryGame.NumberGame;
import dtu.softproj.memorizer.sequenceMemoryGame.SequenceGame;
import dtu.softproj.memorizer.visualMemoryGame.VisualMemoryGame;

public class Statistics extends AppCompatActivity {
    private TextView mGameName;
    private String game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_layout);
        mGameName = (TextView) findViewById(R.id.gameName);

        // Getting the game info that called statistics
        game = getIntent().getStringExtra("Game");
        mGameName.setText(game);

        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.stasticsRelativeLayout);
        switch(game) {
            case NumberGame.gameName:
                rLayout.setBackgroundColor(Color.parseColor(NumberGame.gameColor));
                break;

            case SequenceGame.gameName:
                break;

            case VisualMemoryGame.gameName:
                break;
        }


    }
}
