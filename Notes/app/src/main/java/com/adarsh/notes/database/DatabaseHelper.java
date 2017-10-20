package com.adarsh.notes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adarsh.notes.database.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adars on 10/19/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASENAME = "notes.db";
    private static final int DATABASEVERSION = 1;

    private static final String NOTE_TABLE = "notes";

    private static final String NOTE_ID = "note_id";
    private static final String NOTE_TITLE = "note_title";
    private static final String NOTE_BODY = "note_body";

    public DatabaseHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + NOTE_TABLE + " ("
                + NOTE_ID + " INTEGER PRIMARY KEY,"
                + NOTE_TITLE + " TEXT,"
                + NOTE_BODY + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public List<Note> getNotes(){
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + NOTE_TABLE, null);

        if (cursor.moveToLast()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(NOTE_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(NOTE_TITLE)));
                note.setBody(cursor.getString(cursor.getColumnIndex(NOTE_BODY)));
                notes.add(note);
            } while (cursor.moveToPrevious());
        }

        return notes;
    }

    public Note getNote(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(NOTE_TABLE, null, NOTE_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Note note = new Note();
        note.setId(cursor.getInt(cursor.getColumnIndex(NOTE_ID)));
        note.setTitle(cursor.getString(cursor.getColumnIndex(NOTE_TITLE)));
        note.setBody(cursor.getString(cursor.getColumnIndex(NOTE_BODY)));
        return note;
    }

    public void addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTE_ID, note.getId());
        values.put(NOTE_TITLE, note.getTitle());
        values.put(NOTE_BODY, note.getBody());
        db.insert(NOTE_TABLE, null, values);
        db.close();
    }

    public void updateNote(long id, Note note){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTE_TITLE, note.getTitle());
        values.put(NOTE_BODY, note.getBody());
        db.update(NOTE_TABLE, values, NOTE_ID + " = ?", new String[] { String.valueOf(id)});
    }

    public void deleteNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTE_TABLE, NOTE_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
}
