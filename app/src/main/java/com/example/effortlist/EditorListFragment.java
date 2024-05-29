package com.example.effortlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

public class EditorListFragment extends Fragment {
    TextInputLayout listField;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_editor, container, false);

        return view;
    }
    void init() {
        //listField = findViewById(R.id.listTextField);
    }
    void functions() {

    }
}
