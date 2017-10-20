package com.adarsh.notes.features.init.presenter;

import com.adarsh.notes.database.model.Note;
import com.adarsh.notes.features.init.Presenter;

/**
 * Created by adars on 10/18/2017.
 */

public interface InitPresenter extends Presenter {

    void getNotes();

    void addNote(Note note);

    void updateNote(long id, Note note);
}
