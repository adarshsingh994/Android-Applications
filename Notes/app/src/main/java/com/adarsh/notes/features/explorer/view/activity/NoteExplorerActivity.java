package com.adarsh.notes.features.explorer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.adarsh.notes.R;
import com.adarsh.notes.features.explorer.cache.Cache;
import com.adarsh.notes.database.model.Note;
import com.adarsh.notes.features.explorer.presenter.NotesExplorerPresenter;
import com.adarsh.notes.features.explorer.presenter.NotesExplorerPresenterImpl;
import com.adarsh.notes.features.explorer.view.NotesExplorerView;
import com.adarsh.notes.features.explorer.Presenter;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteExplorerActivity extends AppCompatActivity implements NotesExplorerView {


    @BindView(R.id.title_text) TextView titleText;
    @BindView(R.id.body_text) TextView bodyText;

    Note note;
    NotesExplorerPresenter presenter;
    String checkTitle = "", checkBody = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_explorer);

        ButterKnife.bind(this);

        note = getIntent().getParcelableExtra("note");
        if(note != null){
            titleText.setText(note.getTitle());
            bodyText.setText(note.getBody());

            checkTitle = note.getTitle();
            checkBody = note.getBody();
        }

        presenter = new NotesExplorerPresenterImpl(this, NoteExplorerActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_note_explorer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id._action_save:
                this.save(note, titleText.getText().toString(), bodyText.getText().toString());
                return true;
            case R.id._action_delete:
                if(note != null)
                    this.delete(note.getId());
                else{
                    Intent deleteIntent = new Intent();
                    deleteIntent.putExtra("deleted", true);
                    setResult(1, deleteIntent);
                    finish();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        this.save(note, titleText.getText().toString(), bodyText.getText().toString());
    }

    @Override
    public void cachePresenter(Presenter presenter) {
        Cache.getInstance().cachePresenterFor(presenter.getUuid(), presenter);
    }

    @Override
    public void restorePresenter(UUID uuid) {
        presenter = (NotesExplorerPresenter) Cache.getInstance().restorePresenterFor(uuid);
        presenter.setScreen(this);
    }

    @Override
    public void save(Note note, String titleText, String bodyText) {
        presenter.save(note, titleText, bodyText);
    }

    @Override
    public void onSaved(Note note) {
        Intent newNoteIntent = new Intent();
        newNoteIntent.putExtra("deleted", false);
        if(!checkTitle.equals(titleText.getText().toString()) || !checkBody.equals(bodyText.getText().toString())){
            newNoteIntent.putExtra("new_note", note);
        }
        setResult(1, newNoteIntent);
        finish();
    }

    @Override
    public void delete(long id) {
        presenter.delete(id);
    }

    @Override
    public void onDeleted(long id) {
        Intent deleteIntent = new Intent();
        deleteIntent.putExtra("deleted", true);
        deleteIntent.putExtra("id", id);
        setResult(1, deleteIntent);
        finish();
    }
}