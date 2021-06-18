package dtu.softproj.memorizer.verbalMemoryGame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import dtu.softproj.memorizer.R;

public class VerbalMemoryGame extends AppCompatActivity {
    public static final String GAME_NAME = "Verbal Memory";
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
    public void newRound() {
        tvLives.setText("Lives: " + lives);
        tvScore.setText("Score: " + score);

        double probabilityOfNewWord = 0.3;
        double number = Math.random();
        if (wordsSeen.size() < 5) {
            if (wordsSeen.isEmpty()) {
                number = 0;
            } else {
                number /= 2.5;
            }
        }
        if (number < probabilityOfNewWord) {
            if (dictionary.isEmpty()) {
                //TODO remove this when having a proper dictionary that can't be empty
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
        if (roundWon){
            score++;
            newRound();
        } else {
            lives--;
            if (lives > 0){
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
//        add("table");
//        add("window");
//        add("chair");
//        add("floor");
//        add("wall");
        dictionary.addAll(Arrays.asList("Actor", "Gold", "Painting", "Advertisement", "Grass", "Parrot", "Afternoon", "Greece", "Pencil", "Airport", "Guitar", "Piano", "Ambulance", "Hair", "Pillow", "Animal", "Hamburger", "Pizza", "Answer", "Helicopter", "Planet", "Apple", "Helmet", "Plastic", "Army", "Holiday", "Portugal", "Australia", "Honey", "Potato", "Balloon", "Horse", "Queen", "Banana", "Hospital", "Quill", "Battery", "House", "Rain", "Beach", "Hydrogen", "Rainbow", "Beard", "Ice", "Raincoat", "Bed", "Insect", "Refrigerator", "Belgium", "Insurance", "Restaurant", "Boy", "Iron", "River", "Branch", "Island", "Rocket", "Breakfast", "Jackal", "Room", "Brother", "Jelly", "Rose", "Camera", "Jewellery", "Russia", "Candle", "Jordan", "Sandwich", "Car", "Juice", "School", "Caravan", "Kangaroo", "Scooter", "Carpet", "King", "Shampoo", "Cartoon", "Kitchen", "Shoe", "China", "Kite", "Soccer", "Church", "Knife", "Spoon", "Crayon", "Lamp", "Stone", "Crowd", "Lawyer", "Sugar", "Daughter", "Leather", "Sweden", "Death", "Library", "Teacher", "Denmark", "Lighter", "Telephone", "Diamond", "Lion", "Television", "Dinner", "Lizard", "Tent", "Disease", "Lock", "Thailand", "Doctor", "London", "Tomato", "Dog", "Lunch", "Toothbrush", "Dream", "Machine", "Traffic", "Dress", "Magazine", "Train", "Easter", "Magician", "Truck", "Egg", "Manchester", "Uganda", "Eggplant", "Market", "Umbrella", "Egypt", "Match", "Van", "Elephant", "Microphone", "Vase", "Energy", "Monkey", "Vegetable", "Engine", "Morning", "Vulture", "England", "Motorcycle", "Wall", "Evening", "Nail", "Whale", "Eye", "Napkin", "Window", "Family", "Needle", "Wire", "Finland", "Nest", "Xylophone", "Fish", "Nigeria", "Yacht", "Flag", "Night", "Yak", "Flower", "Notebook", "Zebra", "Football", "Ocean", "Zoo", "Forest", "Oil", "Garden", "Fountain", "Orange", "Gas", "France", "Oxygen", "Girl", "Furniture", "Oyster", "Glass", "Garage", "Ghost"));
    }
//    public void add(String word) {
//        dictionary.add(word);
//    }
}
