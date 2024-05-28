package com.example.effortlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NotesFragment extends Fragment {
    Button newNote;
    Button[] arrNote;
    Button[] arrNoteDelete;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        functions();
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }
    void functions() {
        newNote.setOnClickListener(v -> {

        });
        for (int i = 0; i < 10; i++) {
            arrNote[i].setOnClickListener(v -> {

            });
            arrNoteDelete[i].setOnClickListener(v -> {

            });
        }
    }
}