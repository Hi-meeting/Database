package com.gachon.ham.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper {
    private static final String DATABASE_NAME = "speechDB.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private UserDatabaseHelper mDBHelper;
    private Context mContext;

    // tag for debugging
    public static final String TAG = "TAG";

    public class UserDatabaseHelper extends SQLiteOpenHelper {

        public UserDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public UserDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Drop table if exist
            String DROP_SQL = "drop table if exists " + Databases.UserDatabaseUtil._TABLE_NAME;
            db.execSQL(DROP_SQL);

            //Create table
            db.execSQL(Databases.UserDatabaseUtil._CREATE);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Databases.UserDatabaseUtil._TABLE_NAME);
            onCreate(db);
            Log.w(TAG, "Upgrading database from version" + oldVersion + "to " + newVersion + ".");
        }

        @Override
        public synchronized void close() {
            super.close();
        }
    }

    public class ConferenceDatabaseHelper extends SQLiteOpenHelper{

        public ConferenceDatabaseHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public synchronized void close() {
            super.close();
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


    public DbOpenHelper(Context context) {
        this.mContext = context;
    }

    public DbOpenHelper open() throws SQLException {
        mDBHelper = new UserDatabaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDB.close();
    }

    // User database
    // Insert DB
    public long insertColumn(String _userId, String _userName, String _userPassword, String _userPhoneNumber){
        ContentValues values = new ContentValues();
        values.put(Databases.UserDatabaseUtil.USER_ID, _userId);
        values.put(Databases.UserDatabaseUtil.USER_NAME, _userName);
        values.put(Databases.UserDatabaseUtil.USER_PASSWORD, _userPassword);
        values.put(Databases.UserDatabaseUtil.USER_PHONE_NUMBER, _userPhoneNumber);
        return mDB.insert(Databases.UserDatabaseUtil._TABLE_NAME, null, values);
    }

    // Update DB
    public boolean updateColumn(String _userId, String _userName, String _userPassword, String _userPhoneNumber){
        ContentValues values = new ContentValues();
        values.put(Databases.UserDatabaseUtil.USER_ID, _userId);
        values.put(Databases.UserDatabaseUtil.USER_NAME, _userName);
        values.put(Databases.UserDatabaseUtil.USER_PASSWORD, _userPassword);
        values.put(Databases.UserDatabaseUtil.USER_PHONE_NUMBER, _userPhoneNumber);
        return mDB.update(Databases.UserDatabaseUtil._TABLE_NAME, values, "USER_ID="+_userId, null)>0;
    }

    // ID 컬럼 얻어 오기
    public Cursor getColumn(long id){
        Cursor c = mDB.query(Databases.UserDatabaseUtil._TABLE_NAME, null,
                "_id="+id, null, null, null, null);
        if(c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }


}
