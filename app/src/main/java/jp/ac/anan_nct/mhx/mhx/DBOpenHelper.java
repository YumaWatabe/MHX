package jp.ac.anan_nct.mhx.mhx;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbOpenHelper";
    private static final String DATABASE_NAME = "homework.db";
    private static final String HOMEWORK_TABLE_NAME = "homeworks";
    private static final String TAGS_TABLE_NAME = "tags";
    private static final int DATABASE_VERSION = 15;

    public DBOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //createHomeworkTable(db);
        db.execSQL("CREATE TABLE " + HOMEWORK_TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " name TEXT,"
                + " image BLOB,"
                + " tag INTEGER,"
                + " deadline INTEGER,"
                + " isComp INTEGER,"
                + " notif TEXT,"
                + " memo TEXT"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + HOMEWORK_TABLE_NAME + ";");
        onCreate(db);
    }
}
