package com.mlabs.bbm.digiletter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDatabaseAdapter {
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;

    static final String DATABASE_CREATE = "create table " + "LOGIN" +
            "( " + "ID" + " integer primary key autoincrement," + "FIRSTNAME text, LASTNAME text," +
            "USERNAME text UNIQUE, EMAIL text UNIQUE, PASSWORD text); ";

    public SQLiteDatabase db;
    private final Context context;
    private DatabaseHelper dbHelper;

    public LoginDatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public LoginDatabaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {

        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {

        return db;
    }

    public void insertEntry(String fname, String lname, String uname, String email, String password) {
        ContentValues newValues = new ContentValues();
        newValues.put("FIRSTNAME", fname);
        newValues.put("LASTNAME", lname);
        newValues.put("USERNAME", uname);
        newValues.put("EMAIL", email);
        newValues.put("PASSWORD", password);
        db.insert("LOGIN", null, newValues);
    }

    public int deleteEntry(String email) {
        String where = "EMAIL=?";
        int numberOFEntriesDeleted = db.delete("LOGIN", where, new String[]{email});
        return numberOFEntriesDeleted;
    }

    public String getSingleEntryUname(String uname) {
        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{uname}, null, null, null);
        if (cursor.getCount() < 1)
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }

    public String getSingleEntryEmail(String email) {
        Cursor cursor = db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
        if (cursor.getCount() < 1)
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }

    public String getSignUpUsername(String uname) {


        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{uname}, null, null, null);
        if (cursor.getCount() < 1)
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("USERNAME"));
        cursor.close();
        return password;
    }

    public String getSignUpEmail(String email) {


        Cursor cursor = db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
        if (cursor.getCount() < 1)
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("EMAIL"));
        cursor.close();
        return password;
    }

    public void updateEntry(String email, String password) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("EMAIL", email);
        updatedValues.put("PASSWORD", password);
        String where = "EMAIL = ?";
        db.update("LOGIN", updatedValues, where, new String[]{email});
    }
}
