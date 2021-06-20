package dtu.softproj.memorizer.statistics;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import dtu.softproj.memorizer.R;
import dtu.softproj.memorizer.User;

public class OnlineScoreFragment extends Fragment {
    private TableLayout tl;
    private String game;
    private DatabaseReference mUserDatabase;
    private boolean hasNotGottenData;

    public OnlineScoreFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        game = getArguments().getString("Game");

        return inflater.inflate(R.layout.statistics_offline_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tl = (TableLayout) view.findViewById(R.id.tableLayout);

        // Getting the database values
        mUserDatabase = FirebaseDatabase.getInstance("https://dtu-memorizer-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users/" + game);
        Query mDatabaseOrdered = mUserDatabase.orderByChild("score").limitToLast(15);

        hasNotGottenData = true; // Variable needed so it doesnt update the database while the user is watching the leaderboard
        mDatabaseOrdered.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if (hasNotGottenData) {
                    hasNotGottenData = false;
                    int index = 2;
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        if (childSnapshot.exists()) {
                            User user = childSnapshot.getValue(User.class);
                            user.setName(childSnapshot.getKey());
                            createRows(user.getName(), user.getScore(), index);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    private void createRows(String playerKey, int scoreKey, int index) {
        TableRow tr = new TableRow(getActivity());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(lp);

        TextView date = new TextView(getActivity());
        date.setPadding(3, 3, 3, 3);
        date.setText("" + playerKey);
        date.setTextColor(Color.BLACK);
        date.setTextSize(20);

        TextView score = new TextView(getActivity());
        score.setGravity(Gravity.RIGHT);
        score.setText("" + scoreKey);
        score.setTextColor(Color.BLACK);
        score.setTextSize(20);


        tr.addView(date);
        tr.addView(score);
        tl.addView(tr, index);
    }
}
