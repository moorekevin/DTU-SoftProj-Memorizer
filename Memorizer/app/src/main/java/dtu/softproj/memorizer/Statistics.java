package dtu.softproj.memorizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import dtu.softproj.memorizer.numberMemoryGame.NumberGame;
import dtu.softproj.memorizer.sequenceMemoryGame.SequenceGame;
import dtu.softproj.memorizer.visualMemoryGame.VisualMemoryGame;

public class Statistics extends AppCompatActivity {
    private TextView mGameName;
    private String game;
    private TableLayout tl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_layout);
        mGameName = (TextView) findViewById(R.id.gameName);
        tl = (TableLayout) findViewById(R.id.tableLayout);


        // Getting the game info that called statistics
        game = getIntent().getStringExtra("Game");
        mGameName.setText(game);

        LinearLayout rLayout = (LinearLayout) findViewById(R.id.stasticsRelativeLayout);
        switch(game) {
            case NumberGame.gameName:
//                rLayout.setBackgroundColor(Color.parseColor(NumberGame.gameColor));
                break;

            case SequenceGame.gameName:
                break;

            case VisualMemoryGame.gameName:
                break;
        }

        sortLocalTable();


    }


    public void createRows(String dateKey, int scoreKey, int index) {
        TableRow tr = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(lp);

        TextView date = new TextView(this);
        date.setPadding(3, 3, 3, 3);
        date.setText("" + dateKey);

        TextView score = new TextView(this);
        score.setGravity(Gravity.RIGHT);
        score.setText("" + scoreKey);

        tr.addView(date);
        tr.addView(score);
        tl.addView(tr, index);

    }

    public void sortLocalTable () {
        SharedPreferences prefs = this.getSharedPreferences(game, Context.MODE_PRIVATE);
        HashMap scoreMap = (HashMap) prefs.getAll();

        Object[] a = scoreMap.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });
        HashMap<String, Integer> sortedMap = new HashMap<String, Integer>();

        int index = 2;
        for (Object e : a) {
            createRows(((Map.Entry<String, Integer>) e).getKey(), ((Map.Entry<String, Integer>) e).getValue(), index);
            index++;
        }
    }
}
