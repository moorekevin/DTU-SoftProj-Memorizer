package dtu.softproj.memorizer.verbalMemoryGame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import dtu.softproj.memorizer.R;
import dtu.softproj.memorizer.User;

public class VerbalStartMenu extends AppCompatActivity {
    private Button mPlayButton;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_layout);
        mPlayButton = findViewById(R.id.playbutton);

        // Setting the background color
        LinearLayout layout = (LinearLayout) findViewById(R.id.playLayout);
        layout.setBackgroundColor(Color.parseColor("#e088ff"));
        // Setting the logo
        ImageView img = (ImageView) findViewById(R.id.imageView);
        img.setImageResource(R.drawable.verbalmemory_logo);
        // Setting the game name
        TextView gameName = (TextView) findViewById(R.id.gameName);
        gameName.setText("Verbal Memory");
        // Setting the game description
        TextView gameDescription = (TextView) findViewById(R.id.gameDescription);
        gameDescription.setText("Identify whether you have seen the word before.");

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(VerbalStartMenu.this, VerbalGame.class);
                startActivity(playIntent);
            }
        });

        getAllTimeHighScore();
    }

    private void getAllTimeHighScore() {
        TextView allTimeScore = (TextView) findViewById(R.id.gameHighScoreValue);
        DatabaseReference mUserDatabase = FirebaseDatabase.getInstance("https://dtu-memorizer-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users/" + VerbalGame.GAME_NAME);
        // Finding the player with the highest score in game
        Query mDatabaseHighestPlayer = mUserDatabase.orderByChild("score").limitToLast(1);
        mDatabaseHighestPlayer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    if (childSnapshot.exists()) {
                        User user = childSnapshot.getValue(User.class);
                        user.setName(childSnapshot.getKey());
                        allTimeScore.setText("" + user.getName() + " with the score of " + user.getScore() + "!");
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

}
