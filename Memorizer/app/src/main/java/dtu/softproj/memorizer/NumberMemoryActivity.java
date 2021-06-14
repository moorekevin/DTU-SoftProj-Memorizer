package dtu.softproj.memorizer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class NumberMemoryActivity extends AppCompatActivity {
 private Button mPlayButton;

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
        gameDescription.setText("This game is about memorizing sequences of numbers");

        mPlayButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                   Intent playIntent = new Intent(NumberMemoryActivity.this, NumberGameOver.class);
                   startActivity(playIntent);
            }
        });
    }

    
}
