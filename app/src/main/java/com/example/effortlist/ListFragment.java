package com.example.effortlist;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.effortlist.Adapter.ListAdapter;
import com.example.effortlist.Model.ListModel;
import com.example.effortlist.Utils.DatabaseHandlerList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListFragment extends Fragment implements DialogCloseListener {
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private List<ListModel> shoppingList;
    private DatabaseHandlerList db;
    private Button addNewListButton;
    private Button deleteAllButton;  // New button for deleting all data

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        db = new DatabaseHandlerList(getActivity());
        db.openDatabase();
        shoppingList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listAdapter = new ListAdapter(db, this);
        recyclerView.setAdapter(listAdapter);

        addNewListButton = view.findViewById(R.id.addNewListButton);
        deleteAllButton = view.findViewById(R.id.deleteAllButton); // Initialize the new button

        shoppingList = db.getAllTodo();
        Collections.reverse(shoppingList);
        listAdapter.setTodoList(shoppingList);

        addNewListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewList.newInstance().show(getChildFragmentManager(), AddNewList.TAG);
            }
        });

        deleteAllButton.setOnClickListener(new View.OnClickListener() { // Set click listener for the new button
            @Override
            public void onClick(View v) {
                db.deleteAllTodos(); // Call method to delete all todos
                shoppingList.clear(); // Clear the list
                listAdapter.setTodoList(shoppingList); // Update the adapter
                listAdapter.notifyDataSetChanged(); // Notify the adapter
            }
        });

        return view;
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        refreshList();
    }

    private void refreshList() {
        shoppingList = db.getAllTodo();
        Collections.sort(shoppingList, new Comparator<ListModel>() {
            @Override
            public int compare(ListModel o1, ListModel o2) {
                return Integer.compare(o1.getStatus(), o2.getStatus());
            }
        });
        listAdapter.setTodoList(shoppingList);
        listAdapter.notifyDataSetChanged();
    }



}
