package dtu.softproj.memorizer;

import android.os.Bundle;

import android.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Context;

public class NumberFragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressBar mProgressBar;
    private CountDownTimer mCountDownTimer;
    private int progress = 0;
    private int timerSeconds = 2;
    private int number = 1;
    private NumberGame numberGame;

    public NumberFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NumberFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static NumberFragment1 newInstance(String param1, String param2) {
        NumberFragment1 fragment = new NumberFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_number1, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        numberGame = new NumberGame();
        TextView textview = (TextView) view.findViewById(R.id.numberView);
        textview.setText(numberGame.generateNumber());


        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.setProgress(progress);

        mCountDownTimer = new CountDownTimer(timerSeconds * 1000, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                progress++;
                mProgressBar.setProgress((int) progress * 5 / (timerSeconds));
            }

            @Override
            public void onFinish() {
                mProgressBar.setProgress(100);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, new NumberFragment2())
                        .commit();
            }
        };
        mCountDownTimer.start();

    }




}