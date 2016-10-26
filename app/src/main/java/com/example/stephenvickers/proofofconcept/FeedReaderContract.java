//package com.example.stephenvickers.proofofconcept;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.provider.BaseColumns;
//
///**
// * Created by stephenvickers on 10/12/16.
// *
// * Contract for the SQL DataBase
// */
//public final class FeedReaderContract {
//    /**
//     * This is used to make and use the the SQLite database for the questions.
//     * Constructor is private to prevent someone from accidentally instantiating
//     * the contact class.
//     */
//    private FeedReaderContract() {
//    }
//
//
//    /**
//     * Inner class that defines the table contents for the SQL DB
//     */
//    public static class FeedEntry implements BaseColumns {
//        public static final String TABLE_NAME = "Questions and Answers";
//        public static final String COLUMN_QUESTION_TITLE = "Questions";
//        public static final String COLUMN_ANSWER_A_TITLE = "Answer A";
//        public static final String COLUMN_ANSWER_B_TITLE = "Answer B";
//        public static final String COLUMN_ANSWER_C_TITLE = "Answer C";
//        public static final String COLUMN_ANSWER_D_TITLE = "Answer D";
//        public static final String COLUMN_CORRECT_ANSWER_TITLE = "Correct Answer";
//    }
//
//    //Values used to create the SQL Table
//    private static final String TEXT_TYPE = "Text";
//    private static final String COMMA_SEP = ",";
//    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE" + FeedEntry.TABLE_NAME +
//            " (" + FeedEntry._ID + "INTEGER PRIMARY KEY, " + FeedEntry.COLUMN_QUESTION_TITLE +
//            FeedEntry.COLUMN_ANSWER_A_TITLE + TEXT_TYPE + COMMA_SEP + FeedEntry.COLUMN_ANSWER_B_TITLE +
//            TEXT_TYPE + COMMA_SEP + FeedEntry.COLUMN_ANSWER_C_TITLE + TEXT_TYPE + COMMA_SEP +
//            FeedEntry.COLUMN_ANSWER_D_TITLE + TEXT_TYPE + COMMA_SEP + FeedEntry.COLUMN_CORRECT_ANSWER_TITLE +
//            TEXT_TYPE + " )";
//
//    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
//
//    /**
//     * Inner Class that extends SQLiteOpenHelper to make use of the dataBase
//     */
//    public class FeedReaderDBHelper extends SQLiteOpenHelper {
//
//
//        public static final int DATABASE_VERSION = 1;
//
//
//
//        public static final String DATABASE_NAME = "FeedReader.db";
//
//        public FeedReaderDBHelper(Context context) {
//            super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase sqLiteDatabase) {
//            sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//            //this database is only a cache for online data, so it's upgrade policy is
//            //simply to discard the data and start over
//
//            //delete the dataBase then Call onCreate to create the DataBase again.
//            sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
//            onCreate(sqLiteDatabase);
//        }
//    }
//}
//
