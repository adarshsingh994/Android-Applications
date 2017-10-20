package com.adarsh.notes.features.explorer.presenter;

import com.adarsh.notes.database.model.Note;
import com.adarsh.notes.features.explorer.Presenter;

/**
 * Created by adars on 10/20/2017.
 */

public interface NotesExplorerPresenter extends Presenter {

    void save(Note note, String titleText, String bodyText);
    void delete(long id);
}
