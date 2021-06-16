package dtu.softproj.memorizer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.app.Fragment;
import android.widget.Toast;

import androidx.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NumberFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumberFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NumberFragment2() {
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
    public static NumberFragment2 newInstance(String param1, String param2) {
        NumberFragment2 fragment = new NumberFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button submitButton = view.findViewById(R.id.number_submit);



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mEditText = (EditText) view.findViewById(R.id.number_input);
                String input = mEditText.getText().toString();
                if (input.equals("") || input == null) {
                    Context context = getActivity();
                    Toast fillTextBox = Toast.makeText(context, "Please Insert a Number", Toast.LENGTH_SHORT);
                    fillTextBox.show();
                    return;
                }

                String numberString = getArguments().getString("numberString");
                System.out.println("Passed numberString: " + numberString);
                if (input.equals(numberString)) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.flFragment, new NumberFragment3())
                            .commit();

                } else {
                    getActivity().finish();
                    Intent gameOverIntent = new Intent(getContext(), NumberGameOver.class);
                    startActivity(gameOverIntent);
                }
            }
        });

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
        return inflater.inflate(R.layout.fragment_number2, container, false);
    }
}