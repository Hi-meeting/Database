package com.gachon.ham.database;

import android.provider.BaseColumns;

// DataBase Table
public final class Databases {
    public static final class DatabaseUtil implements BaseColumns {
        public static final String USER_ID = "user_id";
        public static final String USER_NAME = "user_name";
        public static final String CONFERENCE_ID = "conderence_id";
        public static final String SPEECH_TYPE = "speech_type";
        public static final String SPEECH_CONTENT = "speech_content";
        public static final String _TABLENAME = "address";
        public static final String _CREATE =
                "create table "+_TABLENAME+"("
                        +_ID+" integer primary key autoincrement, "
                        +USER_ID+" text not null , "
                        +USER_NAME+" text not null , "
                        +CONFERENCE_ID+" text not null , "
                        +SPEECH_TYPE+" text not null"
                        +SPEECH_CONTENT+" text not null  );";
    }
}
