package com.example.effortlist.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.effortlist.Model.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String DATE = "date"; // New date field
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK + " TEXT, "
            + STATUS + " INTEGER, "
            + DATE + " TEXT)"; // Add date field to table creation statement

    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        if (db == null || !db.isOpen()) {
            db = this.getWritableDatabase();
        }
    }

    public void insertTodo(TodoModel task) {
        openDatabase(); // Ensure database is open before operation
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTodo());
        cv.put(DATE, task.getDate()); // Add date field
        cv.put(STATUS, 0);
        db.insert(TODO_TABLE, null, cv);
    }

    public List<TodoModel> getAllTodo() {
        openDatabase(); // Ensure database is open before operation
        List<TodoModel> taskList = new ArrayList<>();
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
                        task.setDate(cur.getString(cur.getColumnIndexOrThrow(DATE))); // Add date field
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
        openDatabase(); // Ensure database is open before operation
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void updateTodo(int id, String task, String date) {
        openDatabase(); // Ensure database is open before operation
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        cv.put(DATE, date); // Add date field
        db.update(TODO_TABLE, cv, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void deleteTodo(int id) {
        openDatabase(); // Ensure database is open before operation
        db.delete(TODO_TABLE, ID + "= ?", new String[]{String.valueOf(id)});
    }
}
