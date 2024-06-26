package com.example.effortlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.effortlist.Adapter.ListAdapter;
import com.example.effortlist.Adapter.TodoAdapter;
import com.example.effortlist.Model.TodoModel;
import com.example.effortlist.Model.ListModel;
import com.example.effortlist.Utils.DatabaseHandler;
import com.example.effortlist.Utils.DatabaseHandlerList;

import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recentTodosRecyclerView;
    private RecyclerView recentShoppingRecyclerView;
    private TodoAdapter recentTodoAdapter;
    private ListAdapter recentShoppingAdapter; // Use TodoAdapter for simplicity
    private DatabaseHandlerList db;
    private DatabaseHandler db1;
    private List<TodoModel> recentTodos;
    Button[] homeButtons = new Button[4];
    Button[] arrTask;
    Button[] arrTaskDelete;
    private List<ListModel> recentShopping;

    private TextView numberOfTodoTV;
    private TextView numberOfItemTV;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = new DatabaseHandlerList(getActivity());
        db.openDatabase();

        db1 = new DatabaseHandler((getActivity()));


        recentTodosRecyclerView = view.findViewById(R.id.recentTodosRecyclerView);
        recentTodosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recentShoppingRecyclerView = view.findViewById(R.id.recentShoppingRecyclerView);
        recentShoppingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        numberOfTodoTV = view.findViewById(R.id.numberOfTodoTV);
        numberOfItemTV = view.findViewById(R.id.numberOfItemTV);

        recentTodos = getRecentItemz(db1.getAllTodo());
        recentShopping = getRecentItems(db.getAllShoppingItems());

        updateCounts();

        recentTodoAdapter = new TodoAdapter(db1, null); // Passing null for the fragment as it's not used here
        recentTodoAdapter.setTodoList(recentTodos);
        recentTodosRecyclerView.setAdapter(recentTodoAdapter);

        recentShoppingAdapter = new ListAdapter(db, null); // Use TodoAdapter for simplicity
        recentShoppingAdapter.setTodoList(recentShopping);
        recentShoppingRecyclerView.setAdapter(recentShoppingAdapter);

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
    private List<TodoModel> getRecentItemz(List<TodoModel> items) {
        int itemCount = Math.min(items.size(), 3); // Limit to 3 items
        return items.subList(0, itemCount);
    }

    private List<ListModel> getRecentItems(List<ListModel> items) {
        int itemCount = Math.min(items.size(), 3); // Limit to 3 items
        return items.subList(0, itemCount);
    }

    private void updateCounts() {
        int todoCount = recentTodos.size();
        int itemCount = recentShopping.size();

        numberOfTodoTV.setText(todoCount + " Todo");
        numberOfItemTV.setText(itemCount + " Items");
    }
}

