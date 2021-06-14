
package dtu.softproj.memorizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton mSequenceButton;
    private ImageButton mNumberMemoryButton;
    private ImageButton mVisualMemoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Sequence button
        mSequenceButton = findViewById(R.id.sequence_button);
        mSequenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sequenceIntent = new Intent(MainActivity.this, SequenceActivity.class);
                startActivity(sequenceIntent);
            }
        });

        // Number memory button
        mNumberMemoryButton = findViewById(R.id.numbermemory_button);
        mNumberMemoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numberIntent = new Intent(MainActivity.this, NumberMemoryActivity.class);
                startActivity(numberIntent);
            }
        });

        // Visual memory button
        mVisualMemoryButton = findViewById(R.id.visualmemory_button);
        mVisualMemoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent visualIntent = new Intent(MainActivity.this, VisualMemoryActivity.class);
                startActivity(visualIntent);
            }
        });
    }

}