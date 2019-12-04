package com.bigO.via;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class HelpAndFeedbackFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_help_and_feedback, container, false);

        TextInputEditText reasonView = view.findViewById(R.id.feedback_reason);
        TextInputEditText explainView = view.findViewById(R.id.feedback_explain);
        Button submitButton = view.findViewById(R.id.feedback_submit);

        submitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String notify = "Form Submitted (a stub)!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getContext(), notify, duration);
                toast.show();

                reasonView.setText("");
                explainView.setText("");

            }
        });


        return view;
    }
}
