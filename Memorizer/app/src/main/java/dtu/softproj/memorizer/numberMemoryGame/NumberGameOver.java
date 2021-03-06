package dtu.softproj.memorizer.numberMemoryGame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import dtu.softproj.memorizer.MainActivity;
import dtu.softproj.memorizer.R;
import dtu.softproj.memorizer.statistics.Statistics;
import dtu.softproj.memorizer.User;

public class NumberGameOver extends AppCompatActivity {
    private Button mPlayAgain;
    private Button mStatistics;
    private Button mSubmitScore;
    private EditText mNameInput;
    private ImageButton homeButton;
    private TextView mYourScoreValue;
    private String currentGame = NumberGame.GAME_NAME;
    private int score;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover_layout);
        mPlayAgain = (Button) findViewById(R.id.playAgainButton);
        mStatistics = (Button) findViewById(R.id.statisticsButton);
        homeButton = (ImageButton) findViewById(R.id.homeButton);
        mStatistics = (Button) findViewById(R.id.statisticsButton);

        saveAndShowScore();

        // For online score
        mSubmitScore = (Button) findViewById(R.id.name_submit);
        mNameInput = (EditText) findViewById(R.id.typeName);


        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.gameOverRelativeLayout);
        rLayout.setBackgroundColor(Color.parseColor(NumberGame.GAME_COLOR));

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameHomeIntent = new Intent(NumberGameOver.this, MainActivity.class);
                startActivity(gameHomeIntent);

            }
        });

        mPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(NumberGameOver.this, NumberGame.class);
                startActivity(intent);
            }
        });

        mSubmitScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mNameInput.getText().toString();
                if (username.equals("")) {
                    Context context = getApplicationContext();
                    Toast fillTextBox = Toast.makeText(context, "Please insert a name", Toast.LENGTH_SHORT);
                    fillTextBox.show();
                } else {
                    User user = new User(username, Integer.parseInt(mYourScoreValue.getText().toString()));

                    DatabaseReference mUserDatabase = FirebaseDatabase.getInstance("https://dtu-memorizer-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("users/" + NumberGame.GAME_NAME);
                    mUserDatabase.child(user.getName()).child("score").setValue(user.getScore());

                    mNameInput.setText("SCORE SAVED!");
                    mNameInput.setEnabled(false);
                    mSubmitScore.setVisibility(View.GONE);

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
            }
        });

        mStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Statistics.class);
                intent.putExtra("Game", NumberGame.GAME_NAME);
                startActivity(intent);
            }
        });

    }

    private void saveAndShowScore() {
        score = NumberGame.getLevel();

        mYourScoreValue = (TextView) findViewById(R.id.yourScoreValue);
        mYourScoreValue.setText("" + score);

        SharedPreferences prefs = this.getSharedPreferences(currentGame, Context.MODE_PRIVATE);
        if (score > 0) {
            SharedPreferences.Editor editor = prefs.edit();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            editor.putInt(formatter.format(date), Integer.parseInt(mYourScoreValue.getText().toString()));
            editor.commit();
        }

        // Showing alltime personal highscore

        int highscore = -1;
        for (String dateKey : prefs.getAll().keySet()) {
            int scoreFound = (int) prefs.getAll().get(dateKey);
            if (scoreFound > highscore) {
                highscore = scoreFound;
            }
        }

        if (highscore != -1) {
            TextView mHighScoreValue = findViewById(R.id.highScoreValue);
            mHighScoreValue.setText("" + highscore);
        }
    }


}
