package com.adarsh.notes.features.explorer.model;

import android.content.Context;

import com.adarsh.notes.database.DatabaseHelper;
import com.adarsh.notes.database.model.Note;
import com.adarsh.notes.utils.IDGenerator;

import java.util.List;

/**
 * Created by adars on 10/20/2017.
 */

public class NotesExplorerModel {

    DatabaseHelper sqliteDatabaseHelper;

    public NotesExplorerModel(Context context){
        sqliteDatabaseHelper = new DatabaseHelper(context);
    }

    public Note save(Note note, String titleText, String bodyText){
        if(note == null) {
            note = new Note();
            note.setId(IDGenerator.generateRandomId());
        }
        note.setTitle(titleText);
        note.setBody(bodyText);
        sqliteDatabaseHelper.addNote(note);
        return note;
    }

    public void delete(long id){
        sqliteDatabaseHelper.deleteNote(id);
    }
}
