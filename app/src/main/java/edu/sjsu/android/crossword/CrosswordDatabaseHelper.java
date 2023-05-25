package edu.sjsu.android.crossword;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CrosswordDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "crossword.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_CROSSWORD_LEVELS =
            "CREATE TABLE crossword_levels (" +
                    "id INTEGER PRIMARY KEY," +
                    "level INTEGER," +
                    "difficulty TEXT," +
                    "score INTEGER" +
                    ");";

    private static final String CREATE_TABLE_CROSSWORD_WORDS =
            "CREATE TABLE crossword_words (" +
                    "id TEXT PRIMARY KEY," +
                    "level_id INTEGER," +
                    "word TEXT," +
                    "description TEXT," +
                    "FOREIGN KEY(level_id) REFERENCES crossword_levels(id)" +
                    ");";

    private static final String DROP_CROSSWORD_LEVELS =
            "DROP TABLE IF EXISTS " + CrosswordDatabaseContract.CrosswordLevels.TABLE_NAME;

    private static final String DROP_CROSSWORD_WORDS =
            "DROP TABLE IF EXISTS " + CrosswordDatabaseContract.CrosswordWords.TABLE_NAME;

    public CrosswordDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CROSSWORD_LEVELS);
        db.execSQL(CREATE_TABLE_CROSSWORD_WORDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_CROSSWORD_LEVELS);
        db.execSQL(DROP_CROSSWORD_WORDS);
        onCreate(db);
    }

    public long insertLevel(int level, String difficulty) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("level", level);
        values.put("difficulty", difficulty);
        values.put("score", 0);

        long levelId = db.insert("crossword_levels", null, values);
        return levelId;
    }

    public long insertWord(String id, long levelId, String word, String description) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("level_id", levelId);
        values.put("word", word);
        values.put("description", description);

        long num = db.insert("crossword_words", null, values);
        return num;
    }

}
