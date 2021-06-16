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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dtu.softproj.memorizer.R;


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
        gameName.setText("Number Memory");
        // Setting the game description
        TextView gameDescription = (TextView) findViewById(R.id.gameDescription);
        gameDescription.setText("Memorize the longest sequence of numbers. \n " +
                "The average person can remember \n 7 numbers at once");

        // Setting the highscore using the database
        mUserDatabase = FirebaseDatabase.getInstance("https://dtu-memorizer-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users");


        FirebaseDatabase.getInstance().getReference().setValue("test");
        mUserDatabase.setValue("apptest");

        mPlayButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                   Intent playIntent = new Intent(NumberMemoryActivity.this, NumberGame.class);
                   startActivity(playIntent);
            }
        });
    }

    
}
