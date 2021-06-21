package dtu.softproj.memorizer.numberMemoryGame;

import android.graphics.Color;
import android.os.Bundle;

import android.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import dtu.softproj.memorizer.R;

public class DisplayNumberFragment extends Fragment {

    private ProgressBar mProgressBar;
    private CountDownTimer mCountDownTimer;
    private int progress = 0;
    private int timerSeconds = 2 + NumberGame.getLevel();
    private String numberString;


    public DisplayNumberFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.display_number_fragment, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        TextView textview = (TextView) view.findViewById(R.id.numberView);

        numberString = NumberGame.generateNumberString();
        textview.setText(numberString);


        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.getProgressDrawable().setColorFilter(
                Color.rgb(0, 180, 0), android.graphics.PorterDuff.Mode.SRC_IN);
        mProgressBar.setProgress(progress);

        mCountDownTimer = new CountDownTimer(timerSeconds * 1000, 30) {
            @Override
            public void onTick(long millisUntilFinished) {
                progress++;
                mProgressBar.setProgress((int) progress * 3 / (timerSeconds));
            }

            @Override
            public void onFinish() {
                startNumberFragment2();
            }
        };
        mCountDownTimer.start();

    }

    public void startNumberFragment2() {
        mProgressBar.setProgress(100);

        SubmitNumberFragment fragment = new SubmitNumberFragment();
        Bundle args = new Bundle();
        args.putString("numberString", numberString);
        fragment.setArguments(args);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, fragment)
                .commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCountDownTimer.cancel();
        startNumberFragment2();
    }

}