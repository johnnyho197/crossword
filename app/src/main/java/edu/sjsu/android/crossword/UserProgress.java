package edu.sjsu.android.crossword;

import java.util.HashMap;

public class UserProgress {
    private HashMap<Integer, String> wordProgress;

    public UserProgress() {
        wordProgress = new HashMap<>();
    }

    public void setProgress(int wordId, String lettersEntered) {
        wordProgress.put(wordId, lettersEntered);
    }

    public String getProgress(int wordId) {
        return wordProgress.get(wordId);
    }

    public boolean isWordComplete(int wordId) {
        String progress = getProgress(wordId);
        if (progress == null) {
            return false;
        }
        return progress.length() == getWordLength(wordId);
    }

    public int getWordLength(int wordId) {
        String progress = wordProgress.get(wordId);
        if (progress == null) {
            return 0;
        }
        return progress.length();
    }
}