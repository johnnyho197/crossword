package edu.sjsu.android.crossword;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimeOutFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Time's up!")
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Close the dialog
                        dismiss();
                        // Navigate to the EasyLevelScreen
                        NavController navController = NavHostFragment.findNavController(TimeOutFragment.this);
                        int destinationId = navController.getCurrentDestination().getId();
                        if (destinationId == R.id.easyLevelScreen){
                            navController.navigate(R.id.action_easyLevelScreen_self);
                        } else if (destinationId == R.id.mediumLevelScreen) {
                            navController.navigate(R.id.action_mediumLevelScreen_self);
                        } else {
                            navController.navigate(R.id.action_hardLevelScreen_self);
                        }
                    }
                });
        setCancelable(false);
        return builder.create();
    }
}
