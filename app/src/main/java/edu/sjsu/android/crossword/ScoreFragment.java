package edu.sjsu.android.crossword;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogFragmentStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        // Retrieve the score from the arguments
        int score = getArguments().getInt("score");
        final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.click);
        // Display the score in the layout
        TextView scoreTextView = view.findViewById(R.id.score_board);
        TextView choice = view.findViewById(R.id.choice);
        ImageView exit = view.findViewById(R.id.exit);
        scoreTextView.setText(String.valueOf(score));
        // Set the NavController for the TextView
        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                NavController navController = NavHostFragment.findNavController(ScoreFragment.this);
                dismiss();
                int destinationId = navController.getCurrentDestination().getId();
                if (destinationId == R.id.easyLevelScreen){
                    navController.navigate(R.id.action_easyLevelScreen_to_levelSelection);
                } else if (destinationId == R.id.mediumLevelScreen) {
                    navController.navigate(R.id.action_mediumLevelScreen_to_levelSelection);
                } else {
                    navController.navigate(R.id.action_hardLevelScreen_to_levelSelection);
                }
            }
        });
        getDialog().setCancelable(false);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = getActivity();
                if (activity != null) {
                    mp.start();
                    activity.finish();
                }
            }
        });
        return view;
    }
}
