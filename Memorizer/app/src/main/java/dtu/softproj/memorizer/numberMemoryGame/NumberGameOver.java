package dtu.softproj.memorizer.numberMemoryGame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import dtu.softproj.memorizer.MainActivity;
import dtu.softproj.memorizer.R;
import dtu.softproj.memorizer.User;

public class NumberGameOver extends AppCompatActivity {
    private Button mPlayAgain;
    private Button mStatistics;
    private Button mSubmitScore;
    private ImageButton homeButton;
    private EditText mNameInput;
    private TextView mYourScoreValue, mHighScoreValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover_layout);
        mPlayAgain = (Button) findViewById(R.id.playAgainButton);
        mStatistics = (Button) findViewById(R.id.statisticsButton);
        homeButton = (ImageButton) findViewById(R.id.homeButton);

        // For highscore
        mYourScoreValue = (TextView) findViewById(R.id.yourScoreValue);
        mYourScoreValue.setText("" + (NumberGame.getLevel()-1));
        mHighScoreValue = (TextView) findViewById(R.id.highScoreValue);
        mSubmitScore = (Button) findViewById(R.id.name_submit);
        mNameInput = (EditText) findViewById(R.id.typeName);

        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.gameOverRelativeLayout);
        rLayout.setBackgroundColor(Color.parseColor("#88ff98"));

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
                if (username.equals("") || username == null) {
                    Context context = getApplicationContext();
                    Toast fillTextBox = Toast.makeText(context, "Please insert a name", Toast.LENGTH_SHORT);
                    fillTextBox.show();
                    return;
                } else {
                    User user = new User(username, Integer.parseInt(mYourScoreValue.getText().toString()));

                    DatabaseReference mUserDatabase = FirebaseDatabase.getInstance("https://dtu-memorizer-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("users/numberGame");
                    mUserDatabase.child(user.getName()).setValue(user.getScore());

                    mNameInput.setText("SCORE SAVED!");
                    mNameInput.setEnabled(false);
                    mSubmitScore.setVisibility(View.GONE);

                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                }
            }
        });

    }


}
