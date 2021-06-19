package dtu.softproj.memorizer.numberMemoryGame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import dtu.softproj.memorizer.R;
import dtu.softproj.memorizer.User;


public class NumberMemoryActivity extends AppCompatActivity {
 private Button mPlayButton;
 private DatabaseReference mUserDatabase;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_layout);
        mPlayButton = findViewById(R.id.playbutton);

        // Setting the background color
        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.playRelativeLayout);
        rLayout.setBackgroundColor(Color.parseColor("#88ff98"));
        // Setting the logo
        ImageView img = (ImageView) findViewById(R.id.imageView);
        img.setImageResource(R.drawable.numbermemory_logo);
        // Setting the game name
        TextView gameName = (TextView) findViewById(R.id.gameName);
        gameName.setText(NumberGame.GAME_NAME);
        // Setting the game description
        TextView gameDescription = (TextView) findViewById(R.id.gameDescription);
        gameDescription.setText("Memorize the longest sequence of numbers. \n " +
                "The average person can remember \n 7 numbers at once");

        // Setting the highscore using the database
        TextView allTimeScore = (TextView) findViewById(R.id.gameHighScoreValue);

        mUserDatabase = FirebaseDatabase.getInstance("https://dtu-memorizer-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users/" + NumberGame.GAME_NAME);
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


        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(NumberMemoryActivity.this, NumberGame.class);
                startActivity(playIntent);
            }
        });
    }

    
}
