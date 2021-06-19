package dtu.softproj.memorizer.verbalMemoryGame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import dtu.softproj.memorizer.R;

public class VerbalMemoryGame extends AppCompatActivity {
    public static final String GAME_NAME = "Verbal Memory";
    private final double PROBABILITY_CONSTANT = 0.262;
    private final double PROBABILITY_COEFFICIENT = 0.638;
    private final double PROBABILITY_EXPONENT = -0.613;
    private int lives = 3;
    private int score = 0;
    private ArrayList<String> dictionary = new ArrayList<>();
    private ArrayList<String> wordsSeen = new ArrayList<>();
    private TextView tvWord;
    private TextView tvLives;
    private TextView tvScore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verbal_layout);
        tvWord = findViewById(R.id.tvWord);
        tvLives = findViewById(R.id.tvVerbalLives);
        tvScore = findViewById(R.id.tvVerbalScore);

        defDictionary();
        newRound();
    }

    public double probabilityOfNewWord() {
        return PROBABILITY_CONSTANT + PROBABILITY_COEFFICIENT * Math.pow(wordsSeen.size(), PROBABILITY_EXPONENT);
    }

    public void newRound() {
        tvLives.setText("Lives: " + lives);
        tvScore.setText("Score: " + score);

        double probabilityOfNewWord = probabilityOfNewWord();
        double number = Math.random();
        if (number < probabilityOfNewWord) {
            if (dictionary.isEmpty()) {
                finish();
                Intent intent = new Intent(VerbalMemoryGame.this, VerbalMemoryActivity.class);
                startActivity(intent);
                System.out.println("No more words in our dictionary. You Win!");
            } else {
                int index = (int) (Math.random() * dictionary.size());
                tvWord.setText(dictionary.get(index));
                dictionary.remove(index);
            }
        } else {
            int index = (int) (Math.random() * wordsSeen.size());
            tvWord.setText(wordsSeen.get(index));
        }
    }

    public void roundOver(boolean roundWon) {
        if (roundWon) {
            score++;
            newRound();
        } else {
            lives--;
            if (lives > 0) {
                newRound();
            } else {
                finish();
                Intent intent = new Intent(VerbalMemoryGame.this, VerbalGameOver.class);
                startActivity(intent);
            }
        }
    }

    public void seenOnClick(View view) {
        boolean isNew = !wordsSeen.contains(tvWord.getText().toString());
        if (!isNew) {
            roundOver(true);
        } else {
            wordsSeen.add(tvWord.getText().toString());
            roundOver(false);
        }

    }

    public void newOnClick(View view) {
        boolean isNew = !wordsSeen.contains(tvWord.getText().toString());
        if (isNew) {
            wordsSeen.add(tvWord.getText().toString());
            roundOver(true);
        } else {
            roundOver(false);
        }
    }

    public void defDictionary() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.dictionary);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            while (line != null && !line.equals("")) {
                dictionary.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
