package edu.sjsu.android.crossword;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class EasyLevelScreen extends Fragment {

    HashMap<String, String> letterMap = new HashMap<>();

    private int numHintsUsed = 0;
    CrosswordHelper helper;
    private StringBuilder selectedLetters = new StringBuilder();
    Set<String> submittedAnswers = new HashSet<>();
    int correctAnswerSubmitted = 0;
    int score = 0;

    private CountDownTimer timer;

    private CrosswordDataManager dbManager;

    String[] correctWords = {"BLUR", "BURL", "SLUR", "RUB", "BUS"};

    public EasyLevelScreen() {
        // Required empty public constructor
    }

    public static EasyLevelScreen newInstance(String param1, String param2) {
        EasyLevelScreen fragment = new EasyLevelScreen();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }   

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_easy_level_screen, container, false);
        pupulateHashMap();
        final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.bingo);
        final MediaPlayer wrong = MediaPlayer.create(getContext(), R.raw.wrong_answer);
        final MediaPlayer contain = MediaPlayer.create(getContext(), R.raw.error_sound);
        final MediaPlayer complete = MediaPlayer.create(getContext(), R.raw.complete);
        TextView letterR = view.findViewById(R.id.e_R);
        TextView letterU = view.findViewById(R.id.e_U);
        TextView letterL = view.findViewById(R.id.e_L);
        TextView letterB = view.findViewById(R.id.e_B);
        TextView letterS = view.findViewById(R.id.e_S);
        TextView guessInput = view.findViewById(R.id.guessInput_e);
        Button submitButton = view.findViewById(R.id.submitBtn_e);
        TextView e_score = view.findViewById(R.id.e_score);
        TextView timerTextView = view.findViewById(R.id.e_timer);
        TextView delete = view.findViewById(R.id.delete);
        TextView hintButton = view.findViewById(R.id.hintBtn);

        timer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                // Update the timer display with the remaining time
                timerTextView.setText("Time:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                TimeOutFragment dialogFragment = new TimeOutFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                dialogFragment.show(fragmentManager, "TimeOutFragment");
            }
        };
        timer.start();

        helper = new CrosswordHelper(getContext());
        dbManager = new CrosswordDataManager(getContext());
        View.OnClickListener letterClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String letter = ((TextView) v).getText().toString();
                populateGuessInput(guessInput, letter);
            }
        };

        letterR.setOnClickListener(letterClickListener);
        letterU.setOnClickListener(letterClickListener);
        letterL.setOnClickListener(letterClickListener);
        letterB.setOnClickListener(letterClickListener);
        letterS.setOnClickListener(letterClickListener);

        TextView e2_1 = view.findViewById(R.id.e_2_1);

        TextView e2_2 = view.findViewById(R.id.e_2_2);
        TextView e1_2 = view.findViewById(R.id.e_1_2);
        TextView e2_4 = view.findViewById(R.id.e_2_4);

        TextView e1_1 = view.findViewById(R.id.e_1_1);
        TextView e1_3 = view.findViewById(R.id.e_1_3);
        TextView e3_1 = view.findViewById(R.id.e_3_1);
        TextView e3_2 = view.findViewById(R.id.e_3_2);
        TextView e3_3 = view.findViewById(R.id.e_3_3);
        TextView e4_1 = view.findViewById(R.id.e_4_1);
        TextView e4_2 = view.findViewById(R.id.e_4_2);
        TextView e5_1 = view.findViewById(R.id.e_5_1);
        TextView e5_2 = view.findViewById(R.id.e_5_2);
        TextView e5_3 = view.findViewById(R.id.e_5_3);

        TextView[] textViews = { e2_1, e2_2, e1_2, e2_4, e1_1, e1_3, e3_1, e3_2, e3_3,
                e4_1, e4_2, e5_1, e5_2, e5_3 };

        TextView noHint = view.findViewById(R.id.noHint);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (selectedLetters.length() >0){
                    selectedLetters.deleteCharAt(selectedLetters.length()-1);
                    guessInput.setText(selectedLetters.toString());
                }
            }
        });

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (numHintsUsed <2) {
                    numHintsUsed++;
                    Random random = new Random();
                    int randomIndex = random.nextInt(textViews.length);
                    TextView randomTextView = textViews[randomIndex];
                    while (!randomTextView.getText().toString().isEmpty()) {
                        randomIndex = random.nextInt(textViews.length);
                        randomTextView = textViews[randomIndex];
                    }
                    String resourceId = getResources().getResourceEntryName(randomTextView.getId());
                    String letter = letterMap.get(resourceId);
                    helper.updateTextView(randomTextView, letter);
                    score -=100;
                    e_score.setText("Score: " + score);
                    if (isCrosswordLevelComplete()) {
                        timer.cancel();
                        complete.start();
                        FragmentActivity activity = getActivity();
                        if (activity != null) {
                            ScoreFragment scoreFragment = new ScoreFragment();
                            // Set any data that you want to pass to the fragment using arguments
                            Bundle args = new Bundle();
                            args.putInt("score", score);
                            scoreFragment.setArguments(args);
                            // Show the fragment using the FragmentManager
                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
                            scoreFragment.show(fragmentManager, "score");
                            dbManager.updateScoreIfHigher(1, "easy", score);
                        }
                    }

                    if (numHintsUsed ==2){
                        hintButton.setEnabled(false);
                        helper.setNoHint(noHint);
                    }
                }
            }

            private boolean isCrosswordLevelComplete() {
                for (TextView textView : textViews) {
                    if (textView.getText().toString().isEmpty()) {
                        return false;
                    }
                }
                return true;
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guess = guessInput.getText().toString();
                if (!guess.isEmpty() && !submittedAnswers.contains(guess)){
                    int correctIndex = -1;
                    for (int i = 0; i < correctWords.length; i++){
                        if (guess.equals(correctWords[i])){
                            correctIndex = i;
                            correctAnswerSubmitted +=1;
                            submittedAnswers.add(guess);
                            break;
                        }
                    }

                    if (correctIndex != -1){
                        if (correctAnswerSubmitted != correctWords.length){
                            mp.start();
                        }
                        int remainingTime = Integer.parseInt(timerTextView.getText().toString().substring(5));
                        score += (remainingTime * 20 + guess.length() * 10);
                        e_score.setText("Score: " + score);
                        switch (correctWords[correctIndex]){
                            case "BLUR":
                                helper.updateTextView(e2_1, "B");
                                helper.updateTextView(e2_2, "L");
                                helper.updateTextView(e1_2, "U");
                                helper.updateTextView(e2_4, "R");
                                break;
                            case "BURL":
                                helper.updateTextView(e1_1, "B");
                                helper.updateTextView(e1_2, "U");
                                helper.updateTextView(e1_3, "R");
                                helper.updateTextView(e3_2, "L");
                                break;
                            case "SLUR":
                                helper.updateTextView(e3_1, "S");
                                helper.updateTextView(e3_2, "L");
                                helper.updateTextView(e3_3, "U");
                                helper.updateTextView(e4_1, "R");
                                break;
                            case "RUB":
                                helper.updateTextView(e4_1, "R");
                                helper.updateTextView(e4_2, "U");
                                helper.updateTextView(e5_1, "B");
                                break;
                            case "BUS":
                                helper.updateTextView(e5_1, "B");
                                helper.updateTextView(e5_2, "U");
                                helper.updateTextView(e5_3, "S");
                                break;

                        }
                        if (correctAnswerSubmitted == correctWords.length){
                            timer.cancel();
                            complete.start();
                            FragmentActivity activity = getActivity();
                            if (activity != null) {
                                ScoreFragment scoreFragment = new ScoreFragment();
                                // Set any data that you want to pass to the fragment using arguments
                                Bundle args = new Bundle();
                                args.putInt("score", score);
                                scoreFragment.setArguments(args);
                                // Show the fragment using the FragmentManager
                                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                scoreFragment.show(fragmentManager, "score");
                                dbManager.updateScoreIfHigher(1, "easy", score);
                            }
                        }
                    } else {
                        wrong.start();
                    }
                } else {
                    contain.start();
                }
                guessInput.setText("");
                selectedLetters = new StringBuilder("");
            }
        });

        return view;
    }

    private void pupulateHashMap() {
        letterMap.put("e_1_1","B");
        letterMap.put("e_1_2","U");
        letterMap.put("e_1_3","R");
        letterMap.put("e_3_2","L");
        letterMap.put("e_2_1","B");
        letterMap.put("e_2_2","L");
        letterMap.put("e_2_4","R");
        letterMap.put("e_3_1","S");
        letterMap.put("e_3_3","U");
        letterMap.put("e_4_1","R");
        letterMap.put("e_4_2","U");
        letterMap.put("e_5_1","B");
        letterMap.put("e_5_2","U");
        letterMap.put("e_5_3","S");
    }

    private void populateGuessInput(TextView guessInput, String letter) {
        // Append the selected letter to the StringBuilder
        selectedLetters.append(letter);
        // Set the text of the guessInput TextView to the current selection
        guessInput.setText(selectedLetters.toString());
    }
}