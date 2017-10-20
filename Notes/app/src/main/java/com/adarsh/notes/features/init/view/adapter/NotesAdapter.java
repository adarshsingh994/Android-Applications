package com.adarsh.notes.features.init.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adarsh.notes.R;
import com.adarsh.notes.database.model.Note;

import java.util.List;

/**
 * Created by adars on 10/18/2017.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {


    List<Note> notes;
    OnClickNote onClickNote;

    public interface OnClickNote{
        void onNoteClicked(int position, Note clickedNote);
    }

    public NotesAdapter(List<Note> notes, Activity activity){
        this.notes = notes;
        onClickNote = (OnClickNote) activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = notes.get(position);
        if(note == null) {
            holder.title.setText("__null__");
            holder.body.setText("__null__");
        }else{
            holder.title.setText(note.getTitle());
            holder.body.setText(note.getBody());
        }
    }

    @Override
    public int getItemCount() {
        if(notes == null)
            return 0;
        else
            return notes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, body;
        LinearLayout noteContainer;
        public ViewHolder(View itemView) {
            super(itemView);
            noteContainer = itemView.findViewById(R.id.note_container);
            title = itemView.findViewById(R.id.note_title);
            body = itemView.findViewById(R.id.note_body);
            noteContainer.setOnClickListener(view -> {
                int clickedPosition = getAdapterPosition();
                if(clickedPosition != -1){
                    onClickNote.onNoteClicked(clickedPosition, notes.get(clickedPosition));
                }
            });
        }
    }
}
