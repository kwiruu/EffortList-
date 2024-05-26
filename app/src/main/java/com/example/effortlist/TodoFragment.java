package com.example.effortlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.effortlist.Adapter.TodoAdapter;
import com.example.effortlist.Model.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {
    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private List<TodoModel> todoList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        todoList = new ArrayList<>();
        // Get the RecyclerView from the inflated view
        recyclerView = view.findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        todoAdapter = new TodoAdapter(this);
        recyclerView.setAdapter(todoAdapter);

        TodoModel todo = new TodoModel();
        todo.setTodo("Finish OOP2 capstone gameover screen");
        todo.setStatus(0);
        todo.setId(1);

        todoList.add(todo);
        todoList.add(todo);
        todoList.add(todo);
        todoList.add(todo);
        todoList.add(todo);

        todoAdapter.setTodo(todoList);
        // If you have an adapter, set it here
        // recyclerView.setAdapter(new YourAdapter());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
