package com.adarsh.notes.features.init.view;

import com.adarsh.notes.features.init.Screen;
import com.adarsh.notes.database.model.Note;

import java.util.List;

/**
 * Created by adars on 10/18/2017.
 */

public interface InitView extends Screen {

    void getNotes();
    void onGetNotes(List<Note> notes);

    void addNote(Note note);
    void onNoteAdded(Note note);

    void updateNote(long id, Note note);
    void onNoteUpdated(long id, Note note);
}
