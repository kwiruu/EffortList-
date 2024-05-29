package com.example.effortlist.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.effortlist.Model.ListModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerList extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "shoppingListDatabase";
    private static final String LIST_TABLE = "list";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + LIST_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK + " TEXT, "
            + STATUS + " INTEGER)"; // Add date field to table creation statement

    private SQLiteDatabase db;

    public DatabaseHandlerList(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + LIST_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertTodo(ListModel task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTodo());
        cv.put(STATUS, 0);
        db.insert(LIST_TABLE, null, cv);
    }

    public List<ListModel> getAllTodo() {
        List<ListModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            // Query to get all tasks ordered by status
            cur = db.query(LIST_TABLE, null, null, null, null, null, STATUS + " ASC", null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        ListModel task = new ListModel();
                        task.setId(cur.getInt(cur.getColumnIndexOrThrow(ID)));
                        task.setTodo(cur.getString(cur.getColumnIndexOrThrow(TASK)));
                        task.setStatus(cur.getInt(cur.getColumnIndexOrThrow(STATUS)));
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

    public List<ListModel> getAllShoppingItems() {
        List<ListModel> itemList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            // Query to get all items ordered by status
            cur = db.query(LIST_TABLE, null, null, null, null, null, STATUS + " ASC", null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        ListModel item = new ListModel();
                        item.setId(cur.getInt(cur.getColumnIndexOrThrow(ID)));
                        item.setTodo(cur.getString(cur.getColumnIndexOrThrow(TASK)));
                        item.setStatus(cur.getInt(cur.getColumnIndexOrThrow(STATUS)));
                        itemList.add(item);
                    } while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            if (cur != null) {
                cur.close();
            }
        }
        return itemList;
    }

    public void deleteAllShoppingItems() {
        db.delete(LIST_TABLE, null, null); // Delete all rows
    }



    public List<ListModel> updateStatus(int id, int status) {
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(LIST_TABLE, cv, ID + "= ?", new String[]{String.valueOf(id)});
        return getAllTodo(); // Return the updated list
    }


    public void updateTodo(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(LIST_TABLE, cv, ID + "= ?", new String[]{String.valueOf(id)});
    }


    public void deleteTodo(int id) {
        db.delete(LIST_TABLE, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void deleteAllTodos() {
        db.delete(LIST_TABLE, null, null); // Delete all rows
    }
}
