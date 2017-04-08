package kz.elaman.gazservice.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;

import kz.elaman.gazservice.utils.Constants;

/**
 * Created by Myrzabek on 08/04/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private final String LOG_TAG = "DATABASE";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table "+ Constants.INDICATE_TABLE+" ("
                + "id integer primary key autoincrement,"
                + "date text,"
                + "price text,"
                + "indicate_value text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

