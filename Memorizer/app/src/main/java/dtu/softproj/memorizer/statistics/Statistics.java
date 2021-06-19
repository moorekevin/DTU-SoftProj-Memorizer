package dtu.softproj.memorizer.statistics;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import dtu.softproj.memorizer.MainActivity;
import dtu.softproj.memorizer.R;
import dtu.softproj.memorizer.numberMemoryGame.NumberGame;
import dtu.softproj.memorizer.sequenceMemoryGame.SequenceGame;
import dtu.softproj.memorizer.visualMemoryGame.VisualMemoryGame;

public class Statistics extends AppCompatActivity {
    private TextView mGameName;
    private String game;
    private ImageButton mOnlineOfflineToggle;
    private boolean showingOfflineScores;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_layout);
        mGameName = (TextView) findViewById(R.id.gameName);


        // Getting the game info that called statistics
        game = getIntent().getStringExtra("Game");
        if (!game.equals(MainActivity.ACTIVITY_NAME)) {
            mGameName.setText(game);
        }

        LinearLayout rLayout = (LinearLayout) findViewById(R.id.stasticsRelativeLayout);
        switch (game) {
            case NumberGame.GAME_NAME:
                break;

            case SequenceGame.GAME_NAME:
                break;

            case VisualMemoryGame.GAME_NAME:
                break;

            case MainActivity.ACTIVITY_NAME:
                break;
        }
        createLocalScoreFragment();

        // Setting the toggle button between online and offline scores
        showingOfflineScores = true;
        mOnlineOfflineToggle = (ImageButton) findViewById(R.id.onlineOfflineToggle);
        mOnlineOfflineToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showingOfflineScores) {
                    createOnlineScoreFragment();
                    showingOfflineScores = false;
                } else {
                    showingOfflineScores = true;
                    createLocalScoreFragment();
                }
            }
        });
    }

    private void createLocalScoreFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("Game", game);

        Fragment offFrag = new OfflineScoreFragment();
        offFrag.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction ft = fragmentManager
                .beginTransaction();
        ft.replace(R.id.scoreTable, offFrag);
        ft.commit();
    }

    private void createOnlineScoreFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("Game", game);

        Fragment onlFrag = new OnlineScoreFragment();
        onlFrag.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction ft = fragmentManager
                .beginTransaction();
        ft.replace(R.id.scoreTable, onlFrag);
        ft.commit();
    }


}
