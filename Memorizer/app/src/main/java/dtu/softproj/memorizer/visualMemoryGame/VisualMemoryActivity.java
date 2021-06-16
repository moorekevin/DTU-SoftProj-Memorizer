package dtu.softproj.memorizer.visualMemoryGame;

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

public class VisualMemoryActivity extends AppCompatActivity {
    private Button mPlayButton;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_layout);
        mPlayButton = findViewById(R.id.playbutton);

        // Setting the background color
        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.playRelativeLayout);
        rLayout.setBackgroundColor(Color.parseColor("#fff781"));
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
                Intent playIntent = new Intent(VisualMemoryActivity.this, VisualGameOver.class);
                startActivity(playIntent);
            }
        });
    }
}
