package com.adarsh.notes.features.init.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.adarsh.notes.R;
import com.adarsh.notes.features.init.cache.Cache;
import com.adarsh.notes.features.explorer.view.activity.NoteExplorerActivity;
import com.adarsh.notes.features.init.Presenter;
import com.adarsh.notes.database.model.Note;
import com.adarsh.notes.features.init.presenter.InitPresenter;
import com.adarsh.notes.features.init.presenter.InitPresenterImpl;
import com.adarsh.notes.features.init.view.InitView;
import com.adarsh.notes.features.init.view.adapter.NotesAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements InitView, NotesAdapter.OnClickNote {


    @BindView(R.id.no_text) TextView noText;
    @BindView(R.id.notes_recycler_view) RecyclerView notesRecyclerView;
    @BindView(R.id.add_new_note) FloatingActionButton addNewNote;

    List<Note> notes;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    InitPresenter presenter;
    NotesAdapter notesAdapter;
    HashMap<Long, Integer> notes_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getApplication().getSharedPreferences("order", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ButterKnife.bind(this);
        notes = new ArrayList<>();
        notes_map = new HashMap<>();
        presenter = new InitPresenterImpl(this, MainActivity.this);
        addNewNote.setOnClickListener(view -> {
            Intent noteExplorerActivity = new Intent(MainActivity.this, NoteExplorerActivity.class);
            startActivityForResult(noteExplorerActivity, 1);
        });

        this.getNotes();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != 0 && data != null){
            if(resultCode == 1){
                Note n = data.getParcelableExtra("new_note");
                boolean deleted = data.getBooleanExtra("deleted", false);
                if(deleted){
                    long id = data.getLongExtra("id", 0);
                    if(id != 0){
                        int position = notes_map.get(id);
                        updateMapAfterDeleting(id);
                        this.notes.remove(position);
                        notesAdapter.notifyItemRemoved(position);

                        if(this.notes.size() <= 0){
                            noText.setVisibility(View.VISIBLE);
                            notesRecyclerView.setVisibility(View.GONE);
                        }
                    }
                }
                if(!deleted && n != null){
                    if(findNote(n.getId()) == null){
                        this.addNote(n);
                    }else{
                        this.updateNote(n.getId(), n);
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Iterator it = notes_map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            editor.putInt(String.valueOf(pair.getKey()), (int) pair.getValue());
        }
        editor.commit();
    }

    @Override
    public void cachePresenter(Presenter presenter) {
        Cache.getInstance().cachePresenterFor(presenter.getUuid(), presenter);
    }

    @Override
    public void restorePresenter(UUID uuid) {
        presenter = (InitPresenter) Cache.getInstance().restorePresenterFor(uuid);
        presenter.setScreen(this);
    }

    @Override
    public void getNotes() {
        presenter.getNotes();
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString(VIEW1, view1.getText().toString());
//        outState.putString(VIEW2, view2.getText().toString());
//        outState.putString(VIEW3, view3.getText().toString());
//        // Save presenter instance
//        outState.putString(PRESENTER, presenter.getUuid().toString());
//        cachePresenter(presenter);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        view1.setText(savedInstanceState.getString(VIEW1));
//        view2.setText(savedInstanceState.getString(VIEW2));
//        view3.setText(savedInstanceState.getString(VIEW3));
//        // Restore presenter instance
//        restorePresenter(UUID.fromString(savedInstanceState.getString(PRESENTER)));
//        super.onRestoreInstanceState(savedInstanceState);
//    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onNoteClicked(int position, Note clickedNote) {
        Intent noteExplorerActivity = new Intent(MainActivity.this, NoteExplorerActivity.class);
        noteExplorerActivity.putExtra("note", clickedNote);
        startActivityForResult(noteExplorerActivity, 1);
    }

    @Override
    public void onGetNotes(List<Note> notes) {
        if(notes != null){
            this.notes.addAll(notes);
            //organiseNotes();
            notesAdapter = new NotesAdapter(this.notes, MainActivity.this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            notesRecyclerView.setLayoutManager(layoutManager);
            notesRecyclerView.setAdapter(notesAdapter);
            if(this.notes.size() > 0){
                noText.setVisibility(View.INVISIBLE);
                notesRecyclerView.setVisibility(View.VISIBLE);
            }else{
                noText.setVisibility(View.VISIBLE);
                notesRecyclerView.setVisibility(View.INVISIBLE);
            }
        }else{
            noText.setVisibility(View.VISIBLE);
            notesRecyclerView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void addNote(Note note) {
        presenter.addNote(note);
    }

    @Override
    public void onNoteAdded(Note note) {
        noText.setVisibility(View.GONE);
        notesRecyclerView.setVisibility(View.VISIBLE);
        this.notes.add(0, note);
        updateMapAfterAdding();
        notes_map.put(note.getId(), 0);
        Iterator it = notes_map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Log.d("DEBUG", "Key: " + pair.getKey() + " Value: " + pair.getValue());
//            it.remove();
        }
        notesAdapter.notifyItemInserted(0);
    }

    @Override
    public void updateNote(long id, Note note) {
        presenter.updateNote(id, note);
    }

    @Override
    public void onNoteUpdated(long id, Note note) {
        Note oldNote = findNote(id);
        if(oldNote != null && notesAdapter != null){
            int pos = notes_map.get(Long.valueOf(oldNote.getId()));
            this.notes.remove(oldNote);
            this.notes.add(0, note);
            notesAdapter.notifyDataSetChanged();
            updateMapAfterUpdating(id);
        }
    }

    private Note findNote(long id){
        Note oldNote = notes.stream().
                filter(nn -> nn.getId() == id).
                findFirst()
                .orElse(null);
        return oldNote;
    }

    private void updateMapAfterAdding(){
        Iterator it = notes_map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int new_position = ((int) pair.getValue()) + 1;
            notes_map.put((Long) pair.getKey(), new_position);
        }
    }

    private void updateMapAfterUpdating(long id){
        Iterator it = notes_map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if((long) pair.getKey() == id)
                notes_map.put(id, 0);
            else{
                int new_position = ((int) pair.getValue()) + 1;
                notes_map.put((Long) pair.getKey(), new_position);
            }
        }
    }

    private void updateMapAfterDeleting(long id){
        boolean afterId = false;
        for(Note nn : notes){
            if(afterId){
                int pos = notes_map.get(nn.getId());
                notes_map.put(nn.getId(), pos - 1);
            }
            if(nn.getId() == id)
                afterId = true;
        }
        notes_map.remove(id);
    }

    void organiseNotes(){
        List<Note> temp = new ArrayList<>();
        boolean interrupt = false;
        temp.addAll(this.notes);
        for(Note n : temp){
            int pos = sharedPreferences.getInt(String.valueOf(n.getId()), -1);
            if(pos == -1){
                notes.clear();
                interrupt = true;
                break;
            }else{
                notes_map.put(n.getId(), pos);
                this.notes.add(pos, n);
                this.notes.remove(pos + 1);
            }
        }
        if(interrupt){
            int position = 0;
            notes.addAll(temp);
            for(Note nn : notes){
                notes_map.put(nn.getId(), position);
                position++;
            }
        }
    }
}