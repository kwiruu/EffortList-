package com.example.effortlist.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.effortlist.Model.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerNote extends SQLiteOpenHelper {

    private static final String NOTES_TABLE = "notes";
    private static final String noteID = "id";
    private static final String noteTitle = "title";
    private static final String noteText = "content";
    private static final String DBname = "dbNote";

    private SQLiteDatabase db;

    private static final String CREATE_NOTES_TABLE = "CREATE TABLE IF NOT EXISTS " + NOTES_TABLE + "("
            + noteID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + noteTitle + " TEXT, "
            + noteText + " TEXT)";

    public DatabaseHandlerNote(@Nullable Context context) {
        super(context, DBname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTES_TABLE);
    }

    public void openDatabase() {
        if (db == null || !db.isOpen()) {
            db = this.getWritableDatabase();
        }
    }

    public List<NoteModel> getAllNoteItems() {
        List<NoteModel> itemList = new ArrayList<>();
        openDatabase(); // Ensure database is opened
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(NOTES_TABLE, null, null, null, null, null, null, null);
            if (cur != null && cur.moveToFirst()) {
                do {
                    NoteModel item = new NoteModel();
                    item.setId(cur.getInt(cur.getColumnIndexOrThrow(noteID)));
                    item.setTITLE(cur.getString(cur.getColumnIndexOrThrow(noteTitle)));
                    item.setTEXT(cur.getString(cur.getColumnIndexOrThrow(noteText)));
                    itemList.add(item);
                } while (cur.moveToNext());
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            if (cur != null) {
                cur.close();
            }
        }
        return itemList;
    }

    public void updateTodo(int id, String title, String text) {
        openDatabase(); // Ensure database is opened
        ContentValues cv = new ContentValues();
        cv.put(noteTitle, title);
        cv.put(noteText, text);
        db.update(NOTES_TABLE, cv, noteID + "= ?", new String[]{String.valueOf(id)});
    }

    public void insertNote(NoteModel task, String title, String text) {
        openDatabase(); // Ensure database is opened
        ContentValues cv = new ContentValues();
        cv.put(noteTitle, task.getTITLE());
        cv.put(noteText, task.getTEXT());
        db.insert(NOTES_TABLE, null, cv);
    }

    public void deleteTodo(int id) {
        openDatabase(); // Ensure database is opened
        db.delete(NOTES_TABLE, noteID + "= ?", new String[]{String.valueOf(id)});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
        onCreate(db);
    }
}
