package com.adarsh.notes.features.explorer.presenter;

import android.content.Context;

import com.adarsh.notes.database.model.Note;
import com.adarsh.notes.features.explorer.model.NotesExplorerModel;
import com.adarsh.notes.features.explorer.view.NotesExplorerView;
import com.adarsh.notes.features.explorer.Screen;

import java.util.UUID;

/**
 * Created by adars on 10/20/2017.
 */

public class NotesExplorerPresenterImpl implements NotesExplorerPresenter {

    private UUID uuid;
    private NotesExplorerView view;
    private NotesExplorerModel model;

    public NotesExplorerPresenterImpl(NotesExplorerView view, Context context) {
        this.uuid = UUID.randomUUID();
        this.view = view;
        this.model = new NotesExplorerModel(context);
    }

    @Override
    public void save(Note note, String titleText, String bodyText) {
        view.onSaved(model.save(note, titleText, bodyText));
    }

    @Override
    public void delete(long id) {
        model.delete(id);
        view.onDeleted(id);
    }


    @Override
    public void setScreen(Screen screen) {
        this.view = (NotesExplorerView) screen;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }
}
