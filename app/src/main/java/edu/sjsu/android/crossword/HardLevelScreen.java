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

public class HardLevelScreen extends Fragment {
    HashMap<String, String> letterMap = new HashMap<>();
    private int numHintsUsed = 0;
    CrosswordHelper helper;
    private StringBuilder selectedLetters = new StringBuilder();
    Set<String> submittedAnswers = new HashSet<>();
    int correctAnswerSubmitted = 0;
    int score = 0;

    private CountDownTimer timer;

    private CrosswordDataManager dbManager;

    String[] correctWords = {"UNREAL", "TUNER", "NATURE","LUNATE","ALERT","ULTRA","RENTAL","NEAT","NEUTRAL"};


    public HardLevelScreen() {
        // Required empty public constructor
    }
    public static HardLevelScreen newInstance(String param1, String param2) {
        HardLevelScreen fragment = new HardLevelScreen();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hard_level_screen, container, false);
        pupulateHashMap();
        final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.bingo);
        final MediaPlayer complete = MediaPlayer.create(getContext(), R.raw.complete);
        final MediaPlayer wrong = MediaPlayer.create(getContext(), R.raw.wrong_answer);
        final MediaPlayer contain = MediaPlayer.create(getContext(), R.raw.error_sound);

        TextView letterN = view.findViewById(R.id.h_N);
        TextView letterA = view.findViewById(R.id.h_A);
        TextView letterT = view.findViewById(R.id.h_T);
        TextView letterU = view.findViewById(R.id.h_U);
        TextView letterR = view.findViewById(R.id.h_R);
        TextView letterE = view.findViewById(R.id.h_E);
        TextView letterL = view.findViewById(R.id.h_L);
        TextView guessInput = view.findViewById(R.id.guessInput_h);
        Button submitButton = view.findViewById(R.id.submitBtn_h);
        TextView h_score = view.findViewById(R.id.h_score);
        TextView delete = view.findViewById(R.id.delete);
        TextView timerTextView = view.findViewById(R.id.h_timer);
        TextView hintButton = view.findViewById(R.id.hintBtn);
        timer = new CountDownTimer(120000, 1000) {

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

        dbManager = new CrosswordDataManager(getContext());
        helper = new CrosswordHelper(getContext());
        View.OnClickListener letterClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String letter = ((TextView) v).getText().toString();
                populateGuessInput(guessInput, letter);
            }
        };

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (selectedLetters.length() >0){
                    selectedLetters.deleteCharAt(selectedLetters.length()-1);
                    guessInput.setText(selectedLetters.toString());
                }
            }
        });

        letterR.setOnClickListener(letterClickListener);
        letterU.setOnClickListener(letterClickListener);
        letterL.setOnClickListener(letterClickListener);
        letterN.setOnClickListener(letterClickListener);
        letterA.setOnClickListener(letterClickListener);
        letterE.setOnClickListener(letterClickListener);
        letterT.setOnClickListener(letterClickListener);

        TextView one1 = view.findViewById(R.id.h_1_1);
        TextView one2 = view.findViewById(R.id.h_1_2);
        TextView one3 = view.findViewById(R.id.h_1_3);
        TextView one5 = view.findViewById(R.id.h_1_5);
        TextView one6 = view.findViewById(R.id.h_1_6);
        TextView two1 = view.findViewById(R.id.h_2_1);
        TextView two2 = view.findViewById(R.id.h_2_2);
        TextView two3 = view.findViewById(R.id.h_2_3);
        TextView two4 = view.findViewById(R.id.h_2_4);
        TextView two5 = view.findViewById(R.id.h_2_5);
        TextView three1 = view.findViewById(R.id.h_3_1);
        TextView three2 = view.findViewById(R.id.h_3_2);
        TextView three3 = view.findViewById(R.id.h_3_3);
        TextView three4 = view.findViewById(R.id.h_3_4);
        TextView three6 = view.findViewById(R.id.h_3_6);
        TextView four1 = view.findViewById(R.id.h_4_1);
        TextView four2 = view.findViewById(R.id.h_4_2);
        TextView four4 = view.findViewById(R.id.h_4_4);
        TextView four5 = view.findViewById(R.id.h_4_5);
        TextView five1 = view.findViewById(R.id.h_5_1);
        TextView five2 = view.findViewById(R.id.h_5_2);
        TextView five3 = view.findViewById(R.id.h_5_3);
        TextView five5 = view.findViewById(R.id.h_5_5);
        TextView six1 = view.findViewById(R.id.h_6_1);
        TextView six2 = view.findViewById(R.id.h_6_2);
        TextView six3 = view.findViewById(R.id.h_6_3);
        TextView six4 = view.findViewById(R.id.h_6_4);
        TextView seven1 = view.findViewById(R.id.h_7_1);
        TextView seven2 = view.findViewById(R.id.h_7_2);
        TextView seven3 = view.findViewById(R.id.h_7_3);
        TextView seven4 = view.findViewById(R.id.h_7_4);
        TextView seven5 = view.findViewById(R.id.h_7_5);
        TextView seven6 = view.findViewById(R.id.h_7_6);
        TextView eight1 = view.findViewById(R.id.h_8_1);
        TextView eight2 = view.findViewById(R.id.h_8_2);
        TextView eight3 = view.findViewById(R.id.h_8_3);
        TextView eight4 = view.findViewById(R.id.h_8_4);
        TextView nine1 = view.findViewById(R.id.h_9_1);
        TextView nine3 = view.findViewById(R.id.h_9_3);
        TextView nine5 = view.findViewById(R.id.h_9_5);
        TextView nine6 = view.findViewById(R.id.h_9_7);
        TextView nine7 = view.findViewById(R.id.h_9_6);

        TextView noHint = view.findViewById(R.id.noHint);
        TextView[] textViews = {one1, one2, one3, one5,
                one6, two1, two2, two3,two4,two5,three1,
                three2,three3,three4,three6,four1,four2,
                four4,four5,five1,five2,five3,five5,six1,
                six2,six3,six4,seven1,seven2,seven3,seven4,
                seven5,seven6,eight1,eight2,eight3,eight4,
                nine1,nine3,nine5,nine6,nine7};

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (numHintsUsed <4) {
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
                    score -= 100;
                    h_score.setText("Score: " + score);
                    if (isCrosswordLevelComplete()) {
                        timer.cancel();
                        complete.start();
                        FragmentActivity activity = getActivity();
                        ScoreFragment scoreFragment = new ScoreFragment();
                        // Set any data that you want to pass to the fragment using arguments
                        Bundle args = new Bundle();
                        args.putInt("score", score);
                        scoreFragment.setArguments(args);
                        // Show the fragment using the FragmentManager
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        scoreFragment.show(fragmentManager, "score");
                        dbManager.updateScoreIfHigher(1, "hard", score);
                    }

                    if (numHintsUsed ==4){
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
                        mp.start();
                        int remainingTime = Integer.parseInt(timerTextView.getText().toString().substring(5));
                        score += (remainingTime * 20 + guess.length() * 10);
                        h_score.setText("Score: " + score);
                        switch (correctWords[correctIndex]){
                            case "ULTRA":
                                helper.updateTextView(two1, "U");
                                helper.updateTextView(two2, "L");
                                helper.updateTextView(two3, "T");
                                helper.updateTextView(two4, "R");
                                helper.updateTextView(two5, "A");
                                break;
                            case "LUNATE":
                                helper.updateTextView(one1, "L");
                                helper.updateTextView(one2, "U");
                                helper.updateTextView(one3, "N");
                                helper.updateTextView(five1, "A");
                                helper.updateTextView(one5, "T");
                                helper.updateTextView(one6, "E");
                                break;
                            case "UNREAL":
                                helper.updateTextView(three1, "U");
                                helper.updateTextView(three2, "N");
                                helper.updateTextView(three3, "R");
                                helper.updateTextView(three4, "E");
                                helper.updateTextView(six2, "A");
                                helper.updateTextView(three6, "L");
                                break;
                            case "TUNER":
                                helper.updateTextView(four1, "T");
                                helper.updateTextView(four2, "U");
                                helper.updateTextView(three2, "N");
                                helper.updateTextView(four4, "E");
                                helper.updateTextView(four5, "R");
                                break;
                            case "ALERT":
                                helper.updateTextView(five1, "A");
                                helper.updateTextView(five2, "L");
                                helper.updateTextView(five3, "E");
                                helper.updateTextView(two4, "R");
                                helper.updateTextView(five5, "T");
                                break;
                            case "NATURE":
                                helper.updateTextView(six1, "N");
                                helper.updateTextView(six2, "A");
                                helper.updateTextView(six3, "T");
                                helper.updateTextView(six4, "U");
                                helper.updateTextView(seven1, "R");
                                helper.updateTextView(one6, "E");
                                break;
                            case "RENTAL":
                                helper.updateTextView(seven1, "R");
                                helper.updateTextView(seven2, "E");
                                helper.updateTextView(seven3, "N");
                                helper.updateTextView(seven4, "T");
                                helper.updateTextView(seven5, "A");
                                helper.updateTextView(seven6, "L");
                                break;
                            case "NEAT":
                                helper.updateTextView(eight1, "N");
                                helper.updateTextView(eight2, "E");
                                helper.updateTextView(eight3, "A");
                                helper.updateTextView(eight4, "T");
                                break;

                            case "NEUTRAL":
                                helper.updateTextView(nine1, "N");
                                helper.updateTextView(eight2, "E");
                                helper.updateTextView(nine3, "U");
                                helper.updateTextView(seven4, "T");
                                helper.updateTextView(nine5, "R");
                                helper.updateTextView(nine6, "A");
                                helper.updateTextView(nine7, "L");
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
                                dbManager.updateScoreIfHigher(1, "hard", score);
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
        letterMap.put("h_2_1","U");
        letterMap.put("h_2_2","L");
        letterMap.put("h_2_3","T");
        letterMap.put("h_2_4","R");
        letterMap.put("h_2_5","A");
        letterMap.put("h_1_1","L");
        letterMap.put("h_1_2","U");
        letterMap.put("h_1_3","N");
        letterMap.put("h_5_1","A");
        letterMap.put("h_1_5","T");
        letterMap.put("h_1_6","E");
        letterMap.put("h_3_1","U");
        letterMap.put("h_3_2","N");
        letterMap.put("h_3_3","R");
        letterMap.put("h_3_4","E");
        letterMap.put("h_3_6","L");
        letterMap.put("h_4_1","T");
        letterMap.put("h_4_2","U");
        letterMap.put("h_4_4","E");
        letterMap.put("h_4_5","R");
        letterMap.put("h_5_2","L");
        letterMap.put("h_5_3","E");
        letterMap.put("h_5_5","T");
        letterMap.put("h_6_1","N");
        letterMap.put("h_6_2","A");
        letterMap.put("h_6_3","T");
        letterMap.put("h_6_4","U");
        letterMap.put("h_7_1","R");
        letterMap.put("h_7_2","E");
        letterMap.put("h_7_3","N");
        letterMap.put("h_7_4","T");
        letterMap.put("h_7_5","A");
        letterMap.put("h_7_6","L");
        letterMap.put("h_8_1","N");
        letterMap.put("h_8_2","E");
        letterMap.put("h_8_3","A");
        letterMap.put("h_8_4","T");
        letterMap.put("h_9_1","N");
        letterMap.put("h_9_3","U");
        letterMap.put("h_9_5","R");
        letterMap.put("h_9_6","L");
        letterMap.put("h_9_7","A");

    }
    private void populateGuessInput(TextView guessInput, String letter) {
        // Append the selected letter to the StringBuilder
        selectedLetters.append(letter);

        // Set the text of the guessInput TextView to the current selection
        guessInput.setText(selectedLetters.toString());
    }
}
