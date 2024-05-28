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
import android.widget.Button;

import com.example.effortlist.Adapter.TodoAdapter;
import com.example.effortlist.Model.TodoModel;
import com.example.effortlist.Utils.DatabaseHandler;

import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recentTodosRecyclerView;
    private TodoAdapter recentTodoAdapter;
    private DatabaseHandler db;
    private List<TodoModel> recentTodos;
    Button[] homeButtons = new Button[4];
    Button[] arrTask;
    Button[] arrTaskDelete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        recentTodosRecyclerView = view.findViewById(R.id.recentTodosRecyclerView);
        recentTodosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recentTodos = db.getAllTodo();
        recentTodoAdapter = new TodoAdapter(db, null); // Passing null for the fragment as it's not used here
        recentTodoAdapter.setTodoList(recentTodos);
        recentTodosRecyclerView.setAdapter(recentTodoAdapter);

        return view;
    }

    void functions() {
        for (int i = 0; i < 4; i++) {
            homeButtons[i].setOnClickListener(v -> {

            });
        }
        for (int i = 0; i < 10; i++) {
            arrTask[i].setOnClickListener(v -> {

            });
            arrTaskDelete[i].setOnClickListener(v -> {

            });
        }
    }
}
