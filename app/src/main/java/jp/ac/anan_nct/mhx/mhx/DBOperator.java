package jp.ac.anan_nct.mhx.mhx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class DBOperator {
    private DBOpenHelper helper = null;
    SQLiteDatabase db = null;
    final private String HOMEWORK_TABLE_NAME = "homeworks";

    DBOperator(Context context){
        helper = new DBOpenHelper(context);
    }

    public Data selectData(int id){ //idを指定してデータ取得
        db = helper.getReadableDatabase();
        Data d;

        // Cursorを確実にcloseするために、try{}～finally{}にする
        Cursor cursor = null;
        try{
            //SQL文
            String sql = "SELECT * FROM " + HOMEWORK_TABLE_NAME + " WHERE id = ?;";

            //SQL文の実行
            cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
            cursor.moveToNext();
            d = readCursor(cursor);

            return d;
        }
        finally{
            if(cursor != null){
                cursor.close();
            }
        }

    }

    public Data[] selectData(String column){ //項目名を指定してデータ取得
        db = helper.getReadableDatabase();
        Data[] d;

        // Cursorを確実にcloseするために、try{}～finally{}にする
        Cursor cursor = null;
        try{
            //SQL文の実行
            String sql = "SELECT " + column + " FROM " + HOMEWORK_TABLE_NAME + ";";
            cursor = db.rawQuery(sql, null);

            d = new Data[cursor.getCount()];
            for(int i = 0; i < d.length; i++){
                cursor.moveToNext();
                d[i] = readCursor(cursor);
            }

            return d;
        }
        finally{
            if(cursor != null) {
                cursor.close();
            }
        }
    }

    public Data[] selectDataBySQL(String sql){ //SQL文でデータ取得
        db = helper.getReadableDatabase();
        Data[] d;

        // Cursorを確実にcloseするために、try{}～finally{}にする
        Cursor cursor = null;
        try{
            //SQL文の実行
            cursor = db.rawQuery(sql, null);

            d = new Data[cursor.getCount()];
            for(int i = 0; i < d.length; i++){
                cursor.moveToNext();
                d[i] = readCursor(cursor);
            }

            return d;
        }
        finally{
        }
    }


    private Data readCursor(Cursor cursor){
        Data d = new Data();
        int index;

        index = cursor.getColumnIndex("id");
        if(!cursor.isNull(index)){
            d.id = cursor.getInt(index);
            d.setEmp(0, true);
        }

        index = cursor.getColumnIndex("name");
        if(!cursor.isNull(index)){
            d.name = cursor.getString(index);
            d.setEmp(1, true);
        }

        index = cursor.getColumnIndex("image");
        if(!cursor.isNull(index)){
            byte[] b = cursor.getBlob(index);
            d.image = BitmapFactory.decodeByteArray(b, 0, b.length);
            d.setEmp(2, true);
        }

        index = cursor.getColumnIndex("tag");
        if(!cursor.isNull(index)){
            d.tag = cursor.getInt(index);
            d.setEmp(3, true);
        }

        index = cursor.getColumnIndex("deadline");
        if(!cursor.isNull(index)){
            d.deadline = cursor.getInt(index);
            d.setEmp(4, true);
        }

        index = cursor.getColumnIndex("isComp");
        if(!cursor.isNull(index)) {
            d.isComp = cursor.getInt(index);
            d.setEmp(5, true);
        }

        index = cursor.getColumnIndex("notif");
        if(!cursor.isNull(index)){
            d.notif = cursor.getString(index);
            d.setEmp(6, true);
        }

        index = cursor.getColumnIndex("memo");
        if(!cursor.isNull(index)){
            d.memo = cursor.getString(index);
            d.setEmp(7, true);
        }

        return d;
    }


    public void insertData(Data d){
        db = helper.getWritableDatabase();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            d.image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();

            ContentValues cv = new ContentValues();
            cv.put("name", d.name);
            cv.put("image", b);
            cv.put("tag", d.tag);
            cv.put("deadline", d.deadline);
            cv.put("isComp", d.isComp);
            cv.put("notif", d.notif);
            cv.put("memo", d.memo);

            db.insert(HOMEWORK_TABLE_NAME, null, cv);
        } finally {

        }
    }

    public void updateData(int id, Data d){
        db = helper.getWritableDatabase();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            d.image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();

            ContentValues cv = new ContentValues();
            cv.put("name", d.name);
            cv.put("image", b);
            cv.put("tag", d.tag);
            cv.put("deadline", d.deadline);
            cv.put("isComp", d.isComp);
            cv.put("notif", d.notif);
            cv.put("memo", d.memo);

            db.update(HOMEWORK_TABLE_NAME, cv, "id = " + id, null);
        } finally {

        }
    }

    public void deleteData(int id){
        db = helper.getWritableDatabase();
        try {
            db.delete(HOMEWORK_TABLE_NAME, "id = " + id, null);
        } finally {

        }
    }
}
