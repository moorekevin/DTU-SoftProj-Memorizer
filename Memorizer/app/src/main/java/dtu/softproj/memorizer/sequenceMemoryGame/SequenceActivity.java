package dtu.softproj.memorizer.sequenceMemoryGame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dtu.softproj.memorizer.R;

public class SequenceActivity extends AppCompatActivity {
    private Button mPlayButton;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_layout);
        mPlayButton = findViewById(R.id.playbutton);

        // Setting the background color
        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.playRelativeLayout);
        rLayout.setBackgroundColor(Color.parseColor("#ff9494"));
        // Setting the logo
        ImageView img = (ImageView) findViewById(R.id.imageView);
        img.setImageResource(R.drawable.sequence_logo);
        // Setting the game name
        TextView gameName = (TextView) findViewById(R.id.gameName);
        gameName.setText("Sequence Memory");
        // Setting the game description
        TextView gameDescription = (TextView) findViewById(R.id.gameDescription);
        gameDescription.setText("Remember an increasingly long pattern \n" +
                "of button presses");

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(SequenceActivity.this, SequenceGameOver.class);
                startActivity(playIntent);
            }
        });
    }
}
