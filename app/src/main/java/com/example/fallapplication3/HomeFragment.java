package com.example.fallapplication3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {

    private TextView statusTextView;
    private Button deactivateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize views
        statusTextView = view.findViewById(R.id.text_center);
        deactivateButton = view.findViewById(R.id.button_center2);

        // Set initial status
        statusTextView.setText("Online");
        statusTextView.setBackgroundResource(R.drawable.circular_button);
        deactivateButton.setText("Deactivate");
        deactivateButton.setBackgroundResource(R.drawable.rounded_rectangle_button);

        // Set button click listener
        deactivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusTextView.getText().equals("Online")) {
                    // Change the status text and color
                    statusTextView.setText("Offline");
                    statusTextView.setBackgroundResource(R.drawable.red_circular_button);

                    // Change the button text and color
                    deactivateButton.setText("Activate");
                    deactivateButton.setBackgroundResource(R.drawable.green_rounded_rectangle_button);
                } else {
                    // Change the status text and color
                    statusTextView.setText("Online");
                    statusTextView.setBackgroundResource(R.drawable.circular_button);

                    // Change the button text and color
                    deactivateButton.setText("Deactivate");
                    deactivateButton.setBackgroundResource(R.drawable.rounded_rectangle_button);
                }
            }
        });

        return view;
    }
}
