package dtu.softproj.memorizer.sequenceMemoryGame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

import dtu.softproj.memorizer.R;

public class SequenceGame extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Integer> sequence;
    private Button[] buttons;
    private int currentNum;
//    private View.OnClickListener btnClick = new View.OnClickListene() {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sequence_layout);

        sequence = new ArrayList<Integer>();

        buttons = new Button[9];

        // Add buttons from xml to array
        for (int i = 0; i < 9; i++) {
            String butName = "sequence_tile" + (i + 1);
            int resID = getResources().getIdentifier(butName, "id", getPackageName());
            buttons[i] = ((Button) findViewById(resID));
            buttons[i].setOnClickListener(this);
        }

        System.out.println("Tal:");
        for (int i = 0; i < 1; i++) {
            addNumberToSequence();
        }
        System.out.println();
    }

    public void addNumberToSequence() {
        Random rand = new Random();
        int newNum = rand.nextInt(9); // random number between 0-8
        System.out.print(newNum + 1);
        int newId = buttons[newNum].getId();
        sequence.add(newId);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == sequence.get(currentNum)) {
            System.out.println("Du har trykket på den rigtige knap");
            currentNum++;
            if (currentNum == sequence.size()) {
                TextView levelTextView = findViewById(R.id.sequence_level);
                addNumberToSequence();
                System.out.println();
                levelTextView.setText("LEVEL: " + sequence.size());
                currentNum = 0;
            }
        } else {
            finish();
            Intent intent = new Intent(SequenceGame.this, SequenceGameOver.class);
            startActivity(intent);
        }
    }
}
