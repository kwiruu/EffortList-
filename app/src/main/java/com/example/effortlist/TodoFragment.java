package com.example.effortlist;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.effortlist.Adapter.TodoAdapter;
import com.example.effortlist.Model.TodoModel;
import com.example.effortlist.Utils.DBHelper;
import com.example.effortlist.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodoFragment extends Fragment implements DialogCloseListener {
    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private List<TodoModel> todoList;
    private DBHelper db;
    private Button addNewTodoButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        db = new DBHelper(getActivity());
        db.openDatabase();
        todoList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        todoAdapter = new TodoAdapter(db, this);
        recyclerView.setAdapter(todoAdapter);

        addNewTodoButton = view.findViewById(R.id.addNewTodoButton);

        todoList = db.getAllTodo();
        Collections.reverse(todoList);
        todoAdapter.setTodoList(todoList);

        addNewTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("thing pressed");
                AddNewTodo.newInstance().show(getChildFragmentManager(), AddNewTodo.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(todoAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        todoList = db.getAllTodo();
        Collections.reverse(todoList);
        todoAdapter.setTodoList(todoList);
        todoAdapter.notifyDataSetChanged();
    }
}