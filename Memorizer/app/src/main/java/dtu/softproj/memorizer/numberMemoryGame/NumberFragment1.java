package dtu.softproj.memorizer.numberMemoryGame;

import android.os.Bundle;

import android.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import dtu.softproj.memorizer.R;

public class NumberFragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private ProgressBar mProgressBar;
    private CountDownTimer mCountDownTimer;
    private int progress = 0;
    private int timerSeconds = 2 + NumberGame.getLevel();
    private String numberString;


    public NumberFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_number1, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        TextView textview = (TextView) view.findViewById(R.id.numberView);

        numberString = NumberGame.generateNumberString();
        textview.setText(numberString);


        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.setProgress(progress);

        mCountDownTimer = new CountDownTimer(timerSeconds * 1000, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                progress++;
                mProgressBar.setProgress((int) progress * 5 / (timerSeconds));
            }
            // TODO: Timer crashes the program if you go back or press the home button

            @Override
            public void onFinish() {
                mProgressBar.setProgress(100);

                NumberFragment2 fragment = new NumberFragment2();
                Bundle args = new Bundle();
                args.putString("numberString",numberString);
                fragment.setArguments(args);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, fragment)
                        .commit();
            }
        };
        mCountDownTimer.start();

    }

}