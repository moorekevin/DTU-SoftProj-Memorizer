package dtu.softproj.memorizer.verbalMemoryGame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import dtu.softproj.memorizer.BlinkingAnimation;
import dtu.softproj.memorizer.R;

public class VerbalGame extends AppCompatActivity {
    public static final String GAME_NAME = "Verbal Memory";
    private final double PROBABILITY_CONSTANT = 0.262;
    private final double PROBABILITY_COEFFICIENT = 0.638;
    private final double PROBABILITY_EXPONENT = -0.613;
    private int lives = 3;
    private int level = 0;
    private double probabilityOfNewWord;
    private ArrayList<String> dictionary = new ArrayList<>();
    private ArrayList<String> wordsSeen = new ArrayList<>();
    private TextView tvWord;
    private TextView tvLives;
    private TextView tvScore;
    private LinearLayout rLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verbal_layout);
        tvWord = findViewById(R.id.tvWord);
        tvLives = findViewById(R.id.tvVerbalLives);
        tvScore = findViewById(R.id.tvVerbalScore);
        rLayout = (LinearLayout) findViewById(R.id.verbal_layout);

        defDictionary();
        newRound();
    }

    public double probabilityOfNewWord() {
        return PROBABILITY_CONSTANT + PROBABILITY_COEFFICIENT * Math.pow(wordsSeen.size(), PROBABILITY_EXPONENT);
    }

    public void newRound() {
        tvLives.setText("Lives: " + lives);
        tvScore.setText("Score: " + level);

        double number = Math.random();
        if (level < 2 || number < probabilityOfNewWord()) {
            if (dictionary.isEmpty()) {
                System.out.println("No more words in our dictionary. You Win!");
                finish();
                Intent intent = new Intent(VerbalGame.this, VerbalStartMenu.class);
                startActivity(intent);
            } else {
                int index = (int) (Math.random() * dictionary.size());
                tvWord.setText(dictionary.get(index));
                dictionary.remove(index);
            }
        } else {
            while (true) {
                int index = (int) (Math.random() * wordsSeen.size());
                if (!tvWord.getText().toString().equals(wordsSeen.get(index))) {
                    tvWord.setText(wordsSeen.get(index));
                    break;
                }
            }
        }
    }

    public void roundOver(boolean roundWon) {
        if (roundWon) {
            level++;
            newRound();
        } else {
            BlinkingAnimation.manageBlinkEffect(rLayout, "#e088ff", "#ff6d6d", 300);
            lives--;
            if (lives > 0) {
                newRound();
            } else {
                finish();
                Intent intent = new Intent(VerbalGame.this, VerbalGameOver.class);
                intent.putExtra("score", level);
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
        //text file found at: https://github.com/hugsy/stuff/blob/master/random-word/english-nouns.txt
        //
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
