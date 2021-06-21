package dtu.softproj.memorizer.numberMemoryGame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.app.Fragment;
import android.widget.TextView;

import dtu.softproj.memorizer.R;

public class WinNumberFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.win_number_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView levelTextView = view.findViewById(R.id.tvLevel);
        levelTextView.setText("LEVEL: " + NumberGame.getLevel());

        Button nextButton = view.findViewById(R.id.bNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, new DisplayNumberFragment())
                        .commit();
            }
        });
    }
}