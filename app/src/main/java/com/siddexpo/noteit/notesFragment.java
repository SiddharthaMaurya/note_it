package com.siddexpo.noteit;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.siddexpo.noteit.adapter.NoteAdapter;
import com.siddexpo.noteit.database.AppDatabase;
import com.siddexpo.noteit.model.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class notesFragment extends Fragment {

    private RecyclerView recyclerViewNote;
    private NoteAdapter adapter;
    private ArrayList<Note> noteList;

    private FloatingActionButton btnNote;

    public notesFragment() {
        // Required empty public constructor
    }

    private LinearLayout emptyLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notes, container, false);

        noteList = new ArrayList<>();

        recyclerViewNote = view.findViewById(R.id.recyclerViewNote);
        emptyLayout = view.findViewById(R.id.emptyLayout);



        btnNote = view.findViewById(R.id.btnNote);

        btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openEditor(null);

            }
        });


        adapter = new NoteAdapter(requireContext(), noteList, new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {

                openEditor(note);

            }
        });


        recyclerViewNote.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewNote.setAdapter(adapter);

        loadNotes();


        return view;
    }

    private void loadNotes(){

        AppDatabase db = AppDatabase.getInstance(requireContext());

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() ->{

            List<Note> notes = db.noteDao().getAllNotes();

            noteList.clear();
            noteList.addAll(notes);

            requireActivity().runOnUiThread(() ->{
                adapter.notifyDataSetChanged();

                if(noteList.isEmpty()){
                    emptyLayout.setVisibility(VISIBLE);
                    recyclerViewNote.setVisibility(GONE);
                }
                else{
                    emptyLayout.setVisibility(GONE);
                    recyclerViewNote.setVisibility(VISIBLE);
                }
            });
        });


    }


    @Override
    public void onResume(){
        super.onResume();

        loadNotes();
        if(noteList.isEmpty()){
            emptyLayout.setVisibility(VISIBLE);
            recyclerViewNote.setVisibility(GONE);
        }
        else{
            emptyLayout.setVisibility(GONE);
            recyclerViewNote.setVisibility(VISIBLE);
        }
    }


    private void openEditor(Note note) {
        Intent intent = new Intent(requireContext(), NoteEditorActivity.class);

        if (note != null) {
            intent.putExtra("id", note.getId());
            intent.putExtra("title", note.getTitle());
            intent.putExtra("content", note.getContent());
            intent.putExtra("pinned",note.getPinned());
        }

        startActivity(intent);
    }

}