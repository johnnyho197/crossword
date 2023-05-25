package edu.sjsu.android.crossword;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CrosswordDataManager {

    private CrosswordDatabaseHelper dbHelper;

    public CrosswordDataManager(Context context) {
        dbHelper = new CrosswordDatabaseHelper(context);
    }

    public long addLevelWithWord(int level, String difficulty, String id, String word, String definition) {
        long numLevel = -1, numWord = -1;
        numLevel = addLevel(level, difficulty);
        numWord = addWord(id, level, word, definition);
        if(numLevel == -1 || numWord == -1)
            return -1;
        else
            return 1;
    }

    public long addLevel(int level, String difficulty) {
        if(isLevelExists(level, difficulty))
            return 0;
        return dbHelper.insertLevel(level, difficulty);
    }

    public long addWord(String id, long levelId, String word, String description) {
        return dbHelper.insertWord(id, levelId, word, description);
    }

    public boolean isLevelExists(int level, String difficulty) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                CrosswordDatabaseContract.CrosswordLevels.COLUMN_NAME_LEVEL
        };

        String selection = CrosswordDatabaseContract.CrosswordLevels.COLUMN_NAME_LEVEL + " = ? AND " +
                CrosswordDatabaseContract.CrosswordLevels.COLUMN_NAME_DIFFICULTY + " = ?";
        String[] selectionArgs = { String.valueOf(level), difficulty };

        Cursor cursor = db.query(
                CrosswordDatabaseContract.CrosswordLevels.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean exists = cursor.moveToFirst();

        cursor.close();

        return exists;
    }


    public boolean checkWordForId(long id, String word) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { CrosswordDatabaseContract.CrosswordWords.COLUMN_NAME_WORD };
        String selection = CrosswordDatabaseContract.CrosswordWords._ID + " = ? AND " +
                CrosswordDatabaseContract.CrosswordWords.COLUMN_NAME_WORD + " = ?";
        String[] selectionArgs = { Long.toString(id), word };
        Cursor cursor = db.query(
                CrosswordDatabaseContract.CrosswordWords.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
        boolean result = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        return result;
    }

    public boolean updateScoreIfHigher(int level, String difficulty, int newScore) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(!isLevelExists(level, difficulty))
            addLevel(level, difficulty);
        int oldScore = getScore(db, level, difficulty);

        if (newScore > oldScore) {
            ContentValues values = new ContentValues();
            values.put(CrosswordDatabaseContract.CrosswordLevels.COLUMN_NAME_SCORE, newScore);

            String selection = CrosswordDatabaseContract.CrosswordLevels.COLUMN_NAME_LEVEL + " = ? AND " + CrosswordDatabaseContract.CrosswordLevels.COLUMN_NAME_DIFFICULTY + " = ?";
            String[] selectionArgs = { String.valueOf(level), difficulty };

            int count = db.update(
                    CrosswordDatabaseContract.CrosswordLevels.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs
            );

            return count == 1;
        } else {
            return false;
        }
    }

    public int getScore(SQLiteDatabase db, int level, String difficulty) {
        String[] projection = { CrosswordDatabaseContract.CrosswordLevels.COLUMN_NAME_SCORE };
        String selection = CrosswordDatabaseContract.CrosswordLevels.COLUMN_NAME_LEVEL + " = ? AND " + CrosswordDatabaseContract.CrosswordLevels.COLUMN_NAME_DIFFICULTY + " = ?";
        String[] selectionArgs = { String.valueOf(level), difficulty };

        Cursor cursor = db.query(
                CrosswordDatabaseContract.CrosswordLevels.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int score = 0;
        if (cursor.moveToFirst()) {
            score = cursor.getInt(cursor.getColumnIndexOrThrow(CrosswordDatabaseContract.CrosswordLevels.COLUMN_NAME_SCORE));
        }
        cursor.close();

        return score;
    }

}
