package com.adarsh.notes.features.explorer.view;

import com.adarsh.notes.database.model.Note;
import com.adarsh.notes.features.explorer.Screen;

/**
 * Created by adars on 10/20/2017.
 */

public interface NotesExplorerView extends Screen {

    void save(Note save, String titleText, String bodyText);
    void onSaved(Note note);

    void delete(long id);
    void onDeleted(long id);
}
