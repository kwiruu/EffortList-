package com.example.effortlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.effortlist.Adapter.ListAdapter;
import com.example.effortlist.Adapter.NotesAdapter;
import com.example.effortlist.Model.ListModel;
import com.example.effortlist.Model.NoteModel;
import com.example.effortlist.Utils.DatabaseHandlerNote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotesFragment extends Fragment {
    private RecyclerView recentNotesRecyclerView;
    private Button newNote;
    private Button addNewNoteButton;
    List<NoteModel> noteModels;
    private RecyclerView recyclerView;
    NotesAdapter notesAdapter;
    DatabaseHandlerNote db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        db = new DatabaseHandlerNote(getActivity());
        db.openDatabase();

        noteModels = new ArrayList<>();
        recyclerView = view.findViewById(R.id.taskRecyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notesAdapter = new NotesAdapter(db, this);
        recyclerView.setAdapter(notesAdapter);

//        newNote = view.findViewById(R.id.createNoteBtn);
//        newNote.setOnClickListener(v -> {
//            Intent intent = new Intent(requireActivity(), Editor.class);
//            startActivity(intent);
//            requireActivity().finish();
//        });
        addNewNoteButton = view.findViewById(R.id.createNoteBtn);
        noteModels = db.getAllNoteItems();
        Collections.reverse(noteModels);
        notesAdapter.setTodoList(noteModels);

        addNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewNote.newInstance().show(getChildFragmentManager(), AddNewNote.TAG);
            }
        });
        return view;
    }
}