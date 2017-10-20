package com.adarsh.notes.features.init.model;

import android.content.Context;
import android.util.Log;

import com.adarsh.notes.database.DatabaseHelper;
import com.adarsh.notes.database.model.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by adars on 10/18/2017.
 */

public class InitModel {

    DatabaseHelper sqliteDatabaseHelper;

    public InitModel(Context context){
        sqliteDatabaseHelper = new DatabaseHelper(context);
    }

    public List<Note> getNotes(){
        return sqliteDatabaseHelper.getNotes();
    }

    public void addNote(Note note){
        sqliteDatabaseHelper.addNote(note);
    }

    public void updateNote(long id, Note note){
        sqliteDatabaseHelper.updateNote(id, note);
    }
}
