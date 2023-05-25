package edu.sjsu.android.crossword;

import android.provider.BaseColumns;

public final class CrosswordDatabaseContract {
    private CrosswordDatabaseContract() {}

    public static class CrosswordLevels implements BaseColumns {
        public static final String TABLE_NAME = "crossword_levels";
        public static final String COLUMN_NAME_LEVEL = "level";
        public static final String COLUMN_NAME_DIFFICULTY = "difficulty";
        public static final String COLUMN_NAME_SCORE = "score";
    }

    public static class CrosswordWords implements BaseColumns {
        public static final String TABLE_NAME = "crossword_words";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_LEVEL_ID = "level_id";
        public static final String COLUMN_NAME_WORD = "word";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String FOREIGN_KEY =
                "FOREIGN KEY(" + COLUMN_NAME_LEVEL_ID + ") REFERENCES " +
                        CrosswordLevels.TABLE_NAME + "(" + CrosswordLevels.COLUMN_NAME_LEVEL + ")";
    }

    // Create table statements
    public static final String SQL_CREATE_LEVELS_TABLE =
            "CREATE TABLE " + CrosswordLevels.TABLE_NAME + " (" +
                    CrosswordLevels._ID + " INTEGER PRIMARY KEY," +
                    CrosswordLevels.COLUMN_NAME_LEVEL + " INTEGER," +
                    CrosswordLevels.COLUMN_NAME_DIFFICULTY + " TEXT," +
                    CrosswordLevels.COLUMN_NAME_SCORE + " INTEGER" +
                    ")";

    public static final String SQL_CREATE_WORDS_TABLE =
            "CREATE TABLE " + CrosswordWords.TABLE_NAME + " (" +
                    CrosswordWords._ID + " INTEGER PRIMARY KEY," +
                    CrosswordWords.COLUMN_NAME_ID + " TEXT," +
                    CrosswordWords.COLUMN_NAME_LEVEL_ID + " INTEGER," +
                    CrosswordWords.COLUMN_NAME_WORD + " TEXT," +
                    CrosswordWords.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    CrosswordWords.FOREIGN_KEY +
                    ")";

    // Drop table statements
    public static final String SQL_DROP_LEVELS_TABLE =
            "DROP TABLE IF EXISTS " + CrosswordLevels.TABLE_NAME;

    public static final String SQL_DROP_WORDS_TABLE =
            "DROP TABLE IF EXISTS " + CrosswordWords.TABLE_NAME;
}