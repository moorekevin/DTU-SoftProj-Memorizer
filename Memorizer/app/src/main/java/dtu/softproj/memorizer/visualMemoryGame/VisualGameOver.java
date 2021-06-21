package dtu.softproj.memorizer.visualMemoryGame;

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
import dtu.softproj.memorizer.User;
import dtu.softproj.memorizer.statistics.Statistics;

public class VisualGameOver extends AppCompatActivity {
    private Button mPlayAgain;
    private Button mStatistics;
    private ImageButton homeButton;
    private Button mSubmitScore;
    private EditText mNameInput;
    private String currentGame = VisualGame.GAME_NAME;
    private int score;
    private TextView mYourScoreValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover_layout);
        mPlayAgain = (Button) findViewById(R.id.playAgainButton);
        mPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent playAgainIntent = new Intent(VisualGameOver.this, VisualGame.class);
                startActivity(playAgainIntent);
            }
        });

        homeButton = (ImageButton) findViewById(R.id.homeButton);

        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.gameOverRelativeLayout);
        rLayout.setBackgroundColor(Color.parseColor("#88e3ff"));

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameHomeIntent = new Intent(VisualGameOver.this, MainActivity.class);
                startActivity(gameHomeIntent);
            }
        });

        saveAndShowScore();

        // Online score logic
        mSubmitScore = (Button) findViewById(R.id.name_submit);
        mNameInput = (EditText) findViewById(R.id.typeName);
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
                            .getReference("users/" + currentGame);
                    mUserDatabase.child(user.getName()).child("score").setValue(user.getScore());

                    mNameInput.setText("SCORE SAVED!");
                    mNameInput.setEnabled(false);
                    mSubmitScore.setVisibility(View.GONE);

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
            }
        });

        mStatistics = (Button) findViewById(R.id.statisticsButton);
        mStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Statistics.class);
                intent.putExtra("Game", currentGame);
                startActivity(intent);
            }
        });
    }

    private void saveAndShowScore() {
        Intent mIntent = getIntent();
        score = mIntent.getIntExtra("score", 0);

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

        // Showing all-time personal highscore

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