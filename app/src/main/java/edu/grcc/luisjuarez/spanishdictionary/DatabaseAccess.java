package edu.grcc.luisjuarez.spanishdictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context, String databaseName) {
        this.openHelper = new DatabaseOpenHelper(context, databaseName);

    }

    public static DatabaseAccess getInstance(Context context, String databaseName) {
        if (instance == null) {
            instance = new DatabaseAccess(context, databaseName);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public List<String> getString(String tableName, String fieldName) {
        List<String> list = new ArrayList<>();
        String SQL = "SELECT " + tableName + " FROM " + fieldName;
        Cursor cursor = database.rawQuery("SELECT " + fieldName + " FROM " + tableName, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<Integer> getInteger(String tableName, String fieldName) {
        List<Integer> list = new ArrayList<>();
        String SQL = "SELECT " + tableName + " FROM " + fieldName;
        Cursor cursor = database.rawQuery("SELECT " + fieldName + " FROM " + tableName, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getInt(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<Double> getDouble(String tableName, String fieldName) {
        List<Double> list = new ArrayList<>();
        String SQL = "SELECT " + tableName + " FROM " + fieldName;
        Cursor cursor = database.rawQuery("SELECT " + fieldName + " FROM " + tableName, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getDouble(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


    class DatabaseOpenHelper extends SQLiteAssetHelper {
        private static final int DATABASE_VERSION = 1;

        public DatabaseOpenHelper(Context context, String databaseName) {
            super(context, databaseName, null, DATABASE_VERSION);
        }
    }
}
