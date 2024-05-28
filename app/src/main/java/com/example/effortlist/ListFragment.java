package com.example.effortlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ListFragment extends Fragment {
    Button newList;
    Button[] arrList;
    Button[] arrListDelete;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        functions();
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    void functions() {
        newList.setOnClickListener(v -> {

        });
        for (int i = 0; i < 10; i++) {
            arrList[i].setOnClickListener(v -> {

            });
            arrListDelete[i].setOnClickListener(v -> {

            });
        }
    }
}