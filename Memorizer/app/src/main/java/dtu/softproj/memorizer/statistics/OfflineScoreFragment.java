package dtu.softproj.memorizer.statistics;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import android.app.Fragment;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import dtu.softproj.memorizer.R;

public class OfflineScoreFragment extends Fragment {
    private TableLayout tl;
    private String game;

    public OfflineScoreFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        game = getArguments().getString("Game");

        return inflater.inflate(R.layout.statistics_offline_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tl = (TableLayout) view.findViewById(R.id.tableLayout);
        createTable();

    }

    private void createRows(String dateKey, int scoreKey, int index) {
        TableRow tr = new TableRow(getContext());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(lp);

        TextView date = new TextView(getContext());
        date.setPadding(3, 3, 3, 3);
        date.setText("" + dateKey);
        date.setTextColor(Color.BLACK);
        date.setTextSize(20);

        TextView score = new TextView(getContext());
        score.setGravity(Gravity.RIGHT);
        score.setText("" + scoreKey);
        score.setTextColor(Color.BLACK);
        score.setTextSize(20);


        tr.addView(date);
        tr.addView(score);
        tl.addView(tr, index);

    }

    private void createTable() {
        SharedPreferences prefs = getContext().getSharedPreferences(game, Context.MODE_PRIVATE);
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
