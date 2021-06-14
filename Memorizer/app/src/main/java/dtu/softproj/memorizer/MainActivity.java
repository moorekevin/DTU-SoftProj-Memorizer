
package dtu.softproj.memorizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageButton mButton;
    final private String toastMessage = "More game modes will be added soon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openGameButton(R.id.sequence_button, SequenceActivity.class);
        openGameButton(R.id.numbermemory_button, NumberMemoryActivity.class);
        openGameButton(R.id.visualmemory_button, VisualMemoryActivity.class);
        sendToastMessage(R.id.comingsoon_button);
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

    public void sendToastMessage(int id) {
        mButton = findViewById(id);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast comingsoonToast = Toast.makeText(context, toastMessage, duration);
                comingsoonToast.show();
            }
        });
    }
}