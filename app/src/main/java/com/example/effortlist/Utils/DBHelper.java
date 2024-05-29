package com.example.effortlist.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.effortlist.Model.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DBname = "dbEffortlist";
    private static final String tblUser = "tblUseraccount";
    private static final String userID = "userID";
    private static final String TODO_TABLE = "tbltodo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String DATE = "date";
    private static final String LIST_TABLE = "tbllist";
    private static final String listID = "id";
    private static final String listTASK = "task";
    private static final String listSTATUS = "status";

    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK + " TEXT, "
            + STATUS + " INTEGER, "
            + DATE + " TEXT, "
            + userID + " INTEGER REFERENCES " + tblUser + "(" + userID + "))";

    private static final String CREATE_LIST_TABLE = "CREATE TABLE " + LIST_TABLE + "("
            + listID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + listTASK + " TEXT, "
            + listSTATUS + " INTEGER, "
            + userID + " INTEGER REFERENCES " + tblUser + "(" + userID + "))";

    public DBHelper(@Nullable Context context) {
        super(context, DBname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + tblUser + "(" + userID + " INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT, password TEXT, fname TEXT, lname TEXT, isLogin INTEGER)");
        db.execSQL(CREATE_LIST_TABLE);
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tblUser);
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LIST_TABLE);
        onCreate(db);
    }

    public boolean inserData(String username, String email, String password, String fname, String lname, int isLogin) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("fname", fname);
        contentValues.put("lname", lname);
        contentValues.put("isLogin", isLogin);
        long result = myDB.insert(tblUser, null, contentValues);
        return result != -1;
    }

    public Cursor getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + tblUser + " WHERE username = ? AND password = ?", new String[]{username, password});
    }

    public int getLoggedInUserID() {
        int userID = -1; // Default value if no user is logged in
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + userID + " FROM " + tblUser + " WHERE isLogin = 1", null);
        if (cursor.moveToFirst()) {
            userID = cursor.getInt(cursor.getColumnIndexOrThrow(String.valueOf(userID)));
        }
        cursor.close();
        return userID;
    }


    public String getUsername() {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT username FROM " + tblUser + " WHERE isLogin = 1", null);
        if (cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            cursor.close();
            return username;
        }
        cursor.close();
        return null;
    }

    // Methods for handling the TODO_TABLE
    public void insertTodo(TodoModel task, int userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTodo());
        cv.put(DATE, task.getDate());
        cv.put(STATUS, 0);
        cv.put(this.userID, userID);
        db.insert(TODO_TABLE, null, cv);
    }

    public List<TodoModel> getAllTodo() {
        List<TodoModel> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        TodoModel task = new TodoModel();
                        task.setId(cur.getInt(cur.getColumnIndexOrThrow(ID)));
                        task.setTodo(cur.getString(cur.getColumnIndexOrThrow(TASK)));
                        task.setStatus(cur.getInt(cur.getColumnIndexOrThrow(STATUS)));
                        task.setDate(cur.getString(cur.getColumnIndexOrThrow(DATE)));
                        taskList.add(task);
                    } while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            if (cur != null) {
                cur.close();
            }
        }
        return taskList;
    }

    public void updateStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void updateTodo(int id, String task, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        cv.put(DATE, date);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void deleteTodo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODO_TABLE, ID + "= ?", new String[]{String.valueOf(id)});
    }
}
