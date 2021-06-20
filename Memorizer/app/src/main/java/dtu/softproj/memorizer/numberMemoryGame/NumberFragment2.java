package dtu.softproj.memorizer.numberMemoryGame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.app.Fragment;
import android.widget.Toast;

import dtu.softproj.memorizer.R;

public class NumberFragment2 extends Fragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button submitButton = view.findViewById(R.id.number_submit);
        EditText mEditText = (EditText) view.findViewById(R.id.number_input);
        mEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.SHOW_IMPLICIT);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_number2, container, false);
    }
}