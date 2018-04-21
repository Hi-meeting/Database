package com.gachon.ham.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper {
    private static final String DATABASE_NAME = "speechDB.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mContext;

    public static final String TABLE_NAME = "speechTable";
    public static final String USER_ID = "user_id";
    public static final String CONFERENCE_ID = "conference_id";
    public static final String SPEECH_TYPE = "speech_type";
    public static final String SPEECH_CONTENT = "speech_content";

    // tag for debugging
    public static final String TAG = "TAG";

    public class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public DatabaseHelper(Context context, String name,
                              CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Drop table if exist
            String DROP_SQL = "drop table if exists " + Databases.DatabaseUtil._TABLENAME;
            db.execSQL(DROP_SQL);

            //Create table
            db.execSQL(Databases.DatabaseUtil._CREATE);
        }

        private void exe_SQL(String sql, SQLiteDatabase db, String type) {
            try {
                db.execSQL(sql);
            } catch (Exception ex) {
                Log.e(TAG, "Exception in " + type + "_SQL", ex);
            }
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Databases.DatabaseUtil._TABLENAME);
            onCreate(db);
            Log.w(TAG, "Upgrading database from version" + oldVersion + "to " + newVersion + ".");
        }
    }

    public DbOpenHelper(Context context) {
        this.mContext = context;
    }

    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDB.close();
    }

    // Insert DB
    public long insertColumn(String _userId, String _userName, String _conferenceId, String _speechType, String _speechContent){
        ContentValues values = new ContentValues();
        values.put(Databases.DatabaseUtil.USER_ID, _userId);
        values.put(Databases.DatabaseUtil.USER_NAME, _userId);
        values.put(Databases.DatabaseUtil.CONFERENCE_ID, _conferenceId);
        values.put(Databases.DatabaseUtil.SPEECH_TYPE, _speechType);
        values.put(Databases.DatabaseUtil.SPEECH_CONTENT, _speechContent);
        return mDB.insert(Databases.DatabaseUtil._TABLENAME, null, values);
    }

    // Update DB
    public boolean updateColumn(String _userId, String _conferenceId, String _speechType, String _speechContent){
        ContentValues values = new ContentValues();
        values.put(Databases.DatabaseUtil.USER_ID, _userId);
        values.put(Databases.DatabaseUtil.USER_NAME, _userId);
        values.put(Databases.DatabaseUtil.CONFERENCE_ID, _conferenceId);
        values.put(Databases.DatabaseUtil.SPEECH_TYPE, _speechType);
        values.put(Databases.DatabaseUtil.SPEECH_CONTENT, _speechContent);
        return mDB.update(Databases.DatabaseUtil._TABLENAME, values, "USER_ID="+_userId, null)>0;
    }

    // ID 컬럼 얻어 오기
    public Cursor getColumn(long id){
        Cursor c = mDB.query(Databases.DatabaseUtil._TABLENAME, null,
                "_id="+id, null, null, null, null);
        if(c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }
}
