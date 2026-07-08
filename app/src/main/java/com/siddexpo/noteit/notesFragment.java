package com.siddexpo.noteit;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siddexpo.noteit.adapter.NoteAdapter;
import com.siddexpo.noteit.model.Note;

import java.util.ArrayList;


public class notesFragment extends Fragment {

    private RecyclerView recyclerViewNote;
    private NoteAdapter adapter;
    private ArrayList<Note> noteList;


    public notesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notes, container, false);

        noteList = new ArrayList<>();

        recyclerViewNote = view.findViewById(R.id.recyclerViewNote);

        noteList.add(new Note(1,"Android Development", "Today I learned RecyclerView Adapter.", System.currentTimeMillis()));

        adapter = new NoteAdapter(requireContext(), noteList, new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                Intent intent = new Intent(requireContext(),NoteEditorActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewNote.setLayoutManager(new LinearLayoutManager(requireContext()));

        recyclerViewNote.setAdapter(adapter);

        return view;
    }

}