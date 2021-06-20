package dtu.softproj.memorizer.visualMemoryGame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import dtu.softproj.memorizer.sequenceMemoryGame.SequenceGame;

public class VisualMemoryActivity extends AppCompatActivity {
    private Button mPlayButton;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_layout);
        mPlayButton = findViewById(R.id.playbutton);

        // Setting the background color
        LinearLayout layout = (LinearLayout) findViewById(R.id.playLayout);
        layout.setBackgroundColor(Color.parseColor("#88e3ff"));
        // Setting the logo
        ImageView img = (ImageView) findViewById(R.id.imageView);
        img.setImageResource(R.drawable.visualmemory_logo);
        // Setting the game name
        TextView gameName = (TextView) findViewById(R.id.gameName);
        gameName.setText("Visual Memory");
        // Setting the game description
        TextView gameDescription = (TextView) findViewById(R.id.gameDescription);
        gameDescription.setText("Remember the increasingly number of \n" +
                "marked squares on the grid");

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(VisualMemoryActivity.this, VisualMemoryGame.class);
                startActivity(playIntent);
            }
        });

        getAllTimeHighScore();
    }

    private void getAllTimeHighScore() {
        TextView allTimeScore = (TextView) findViewById(R.id.gameHighScoreValue);
        DatabaseReference mUserDatabase = FirebaseDatabase.getInstance("https://dtu-memorizer-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users/" + SequenceGame.GAME_NAME);
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
