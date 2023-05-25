package edu.sjsu.android.crossword;

import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import java.util.Timer;

public class CrosswordHelper {

    private Context mContext;
    public CrosswordHelper(Context context) {
        this.mContext = context;
    }
    void updateTextView(TextView textView, String text) {
        textView.setText(text);
        textView.setTextSize(15); // set text size to 15sp
        textView.setGravity(Gravity.CENTER); // center text horizontally and vertical
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setBackgroundResource(R.drawable.correct_border);
    }

    void setNoHint(TextView textView){
        textView.setBackgroundResource(R.drawable.letter_border);
        textView.setText("Out of hint");
    }
}
