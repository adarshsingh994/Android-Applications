package com.adarsh.notes.features.init.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.adarsh.notes.features.init.Screen;
import com.adarsh.notes.features.init.model.InitModel;
import com.adarsh.notes.database.model.Note;
import com.adarsh.notes.features.init.view.InitView;

import java.util.List;
import java.util.UUID;

/**
 * Created by adars on 10/18/2017.
 */

public class InitPresenterImpl implements InitPresenter {

    private UUID uuid;
    private InitView view;
    private InitModel model;

    public InitPresenterImpl(InitView view, Context context) {
        this.uuid = UUID.randomUUID();
        this.view = view;
        this.model = new InitModel(context);
    }

    @Override
    public void getNotes() {
        new AsyncTask<Void, Void, List<Note>>() {

            @Override
            protected List<Note> doInBackground(Void... params) {
                return model.getNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                view.onGetNotes(notes);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void addNote(Note note) {
        model.addNote(note);
        view.onNoteAdded(note);
    }

    @Override
    public void updateNote(long id, Note note) {
        model.updateNote(id, note);
        view.onNoteUpdated(id, note);
    }

    @Override
    public void setScreen(Screen screen) {
        this.view = (InitView) screen;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }
}
