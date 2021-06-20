package dtu.softproj.memorizer.statistics;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import dtu.softproj.memorizer.verbalMemoryGame.VerbalMemoryGame;
import dtu.softproj.memorizer.visualMemoryGame.VisualMemoryGame;

public class Statistics extends AppCompatActivity {
    private TextView mGameName;
    private String game;
    private ImageButton mOnlineOfflineToggle;
    private TextView mOnlineOfflineText;
    private boolean showingOfflineScores;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_layout);
        mGameName = (TextView) findViewById(R.id.gameName);


        // Getting the game info that called statistics and setting the view accordingly
        showingOfflineScores = true;
        setStatistics(getIntent().getStringExtra("Game"));

        LinearLayout rLayout = (LinearLayout) findViewById(R.id.stasticsRelativeLayout);

        // Setting the toggle button between online and offline scores
        mOnlineOfflineText = (TextView) findViewById(R.id.onlineOfflineText);

        mOnlineOfflineToggle = (ImageButton) findViewById(R.id.onlineOfflineToggle);
        mOnlineOfflineToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showingOfflineScores) {
                    createOnlineScoreFragment();
                    showingOfflineScores = false;
                    mOnlineOfflineToggle.setBackgroundResource(R.drawable.online_scoretable_icon);
                    mOnlineOfflineToggle.setImageResource(R.drawable.online_scoretable_icon);

                    mOnlineOfflineText.setText("SHOWING ONLINE PLAYER SCORES");
                } else {
                    showingOfflineScores = true;
                    createLocalScoreFragment();
                    mOnlineOfflineToggle.setBackgroundResource(R.drawable.offline_scoretable_icon);
                    mOnlineOfflineToggle.setImageResource(R.drawable.offline_scoretable_icon);

                    mOnlineOfflineText.setText("SHOWING YOUR PERSONAL SCORES");
                }
            }
        });

        connectGameButtons();
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

    private void connectGameButtons() {
        Button mVerbalGameButton = (Button) findViewById(R.id.verbalgamebutton);
        mVerbalGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!game.equals(VerbalMemoryGame.GAME_NAME)) {
                    setStatistics(VerbalMemoryGame.GAME_NAME);
                }
            }
        });


        Button mNumberGameButton = (Button) findViewById(R.id.numbergamebutton);
        mNumberGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!game.equals(NumberGame.GAME_NAME)) {
                    setStatistics(NumberGame.GAME_NAME);
                }
            }
        });

        Button mSequenceGameButton = (Button) findViewById(R.id.sequencegamebutton);
        mSequenceGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!game.equals(SequenceGame.GAME_NAME)) {
                    setStatistics(SequenceGame.GAME_NAME);
                }
            }
        });

        Button mVisualGameButton = (Button) findViewById(R.id.visualgamebutton);
        mVisualGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!game.equals(VisualMemoryGame.GAME_NAME)) {
                    setStatistics(VisualMemoryGame.GAME_NAME);
                }
            }
        });
    }

    private void setStatistics(String gameName) {
        game = gameName;
        mGameName.setText(game);
        if (showingOfflineScores) {
            createLocalScoreFragment();
        } else {
            createOnlineScoreFragment();
        }
    }


}
