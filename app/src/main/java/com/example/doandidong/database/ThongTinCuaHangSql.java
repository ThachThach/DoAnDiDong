package com.example.doandidong.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class ThongTinCuaHangSql extends SQLiteOpenHelper {

    private final String NAME_TABLE = "thongtincuahang";
    private final String NAME_TABLE_USER = "user";
    private final String NAME_TABLE_CHU_CUA_HANG = "ischu";

    public ThongTinCuaHangSql(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + NAME_TABLE + "(" +
                "Id VARCHAR(50) PRIMARY KEY, " +
                "tencuahang VARCHAR(50))";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void InsertThonTin(String Id, String tenCuaHang) {
        createTable();
        String sql = "INSERT INTO " + NAME_TABLE + " VALUES('" + Id + "', '" + tenCuaHang + "')";

        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor selectThongTin() {
        String sql = "SELECT * FROM " + NAME_TABLE;
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);

    }

    public void UpdateCuaHang(String IdNew, String IdOld, String tenCuaHang) {
        String sql = "UPDATE " + NAME_TABLE + " SET Id = '" + IdNew + "', tencuahang = '" + tenCuaHang + "' WHERE Id= '" + IdOld + "'";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }



    public void createTableUser() {
        String sql = "CREATE TABLE IF NOT EXISTS " + NAME_TABLE_USER + "(" +
                "Id VARCHAR(50), " +
                "ten VARCHAR(50), " +
                "email VARCHAR(50), " +
                "phone VARCHAR(50), " +
                "quyen VARCHAR(50))";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void InsertUser(String Id, String ten, String email, String phone, String quyen) {
        createTableUser();
        String sql = "INSERT INTO " + NAME_TABLE_USER + " VALUES('" + Id + "', '" + ten + "', '" + email + "', '"+phone+"', '" + quyen + "')";

        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor selectUser() {
        createTableUser();
        String sql = "SELECT * FROM " + NAME_TABLE_USER;
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);

    }

    public void UpdateUser(String IdNew, String IdOld, String ten, String email,String phone, String quyen) {
        String sql = "UPDATE " + NAME_TABLE_USER + " SET Id = '" + IdNew + "', ten = '"+ ten +"', email = '"+ email +"', phone = '"+phone+"', quyen='"+quyen+"' WHERE Id= '" + IdOld + "'";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void createTableChuCuaHang() {
        String sql = "CREATE TABLE IF NOT EXISTS " + NAME_TABLE_CHU_CUA_HANG + "(" +
                "Id VARCHAR(10) PRIMARY KEY, " +
                "ischu VARCHAR(10))";

        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void InsertChuCuaHang(String ischu) {
        createTableChuCuaHang();
        String sql = "INSERT INTO " + NAME_TABLE_CHU_CUA_HANG + " VALUES('ID', '" + ischu + "')";

        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor selectChuCuaHang() {
        createTableChuCuaHang();
        String sql = "SELECT * FROM " + NAME_TABLE_CHU_CUA_HANG;
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
    public void UpdateChuCuaHang(String ischu) {
        createTableChuCuaHang();
        String sql = "UPDATE " + NAME_TABLE_CHU_CUA_HANG + " SET ischu = '" + ischu + "' WHERE Id= 'ID'";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
