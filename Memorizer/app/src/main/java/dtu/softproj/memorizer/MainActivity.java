
package dtu.softproj.memorizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openGameButton(R.id.sequence_button, SequenceActivity.class);
        openGameButton(R.id.numbermemory_button, NumberMemoryActivity.class);
        openGameButton(R.id.visualmemory_button, VisualMemoryActivity.class);
    }

    public void openGameButton(int id, Class gameMenu) {
    
    mButton = findViewById(id);
    mButton.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
         Intent intent = new Intent(MainActivity.this, gameMenu);
         startActivity(intent);
         }
    });
    }
}