package dtu.softproj.memorizer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import java.util.Random;


public class NumberGame extends AppCompatActivity {
    private static int level;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        level = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_layout);

        Fragment mFragment = new NumberFragment1();

        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        fragmentTransaction.replace(R.id.flFragment, mFragment).commit();

    }

    public static String generateNumberString() {
        level++;

        String numberString = "";
        for (int i = 0; i < level; i++) {
            Random rand = new Random();
            int n = rand.nextInt(10);
            numberString += n;
        }
        return numberString;
    }

    public static int getLevel() {
        return level;
    }

}
