package com.siddexpo.noteit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siddexpo.noteit.R;
import com.siddexpo.noteit.model.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public interface OnNoteClickListener{
        void onNoteClick(Note note);
    }

    private final OnNoteClickListener listener;


    private Context context;
    private ArrayList<Note> noteList;


    public NoteAdapter(Context context, ArrayList<Note> noteList , OnNoteClickListener listener){
        this.context = context;
        this.noteList = noteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_notes,parent,false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {

        Note note = noteList.get(position);

        holder.txtTitle.setText(note.getTitle());
        holder.txtContent.setText(note.getContent());

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy • hh:mm a", Locale.getDefault());
        String formattedDate = sdf.format(new Date(note.getUpdatedAt()));

        holder.tvDate.setText(formattedDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onNoteClick(note);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtContent;
        TextView tvDate;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
