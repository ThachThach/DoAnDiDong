package com.example.doandidong.SQL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseSQL extends SQLiteOpenHelper {

    private final String NAME_TABLE_USER = "user";

    public DatabaseSQL(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void InsertUser() {
        String sql = "CREATE TABLE IF NOT EXISTS "+NAME_TABLE_USER+"(" +
                "Id VARCHAR(50) PRIMARY KEY, " +
                "username VARCHAR(50), " +
                "chucvu VARCHAR(20), " +
                "email VARCHAR(50))";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void UpdateUsername(String UsernameNew, String id) {
        String sql = "UPDATE "+NAME_TABLE_USER+" SET username = '"+UsernameNew+"' WHERE Id= '"+id+"'";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void UpdateUser(String idOld, String IdNew, String username, String chucvu, String email) {
        String sql = "UPDATE "+NAME_TABLE_USER+" SET Id = '"+IdNew+"', username = '"+username+"', chucvu = '"+chucvu+"', email = '"+email+"' WHERE Id= '"+idOld+"'";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor Select() {
        String sql = "SELECT * FROM " + NAME_TABLE_USER;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        return cursor;
    }


    //Insert, update, delete
    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //select
    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
