package com.example.effortlist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.effortlist.AddNewTodo;
import com.example.effortlist.Model.TodoModel;
import com.example.effortlist.R;
import com.example.effortlist.TodoFragment;
import com.example.effortlist.Utils.DatabaseHandler;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<TodoModel> todoList;
    private TodoFragment activity;
    private DatabaseHandler db;

    public TodoAdapter(DatabaseHandler db, TodoFragment activity) {
        this.db = db;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        db.openDatabase();
        TodoModel item = todoList.get(position);
        holder.task.setText(item.getTodo());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.date.setText(item.getDate()); // Set date
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    db.updateStatus(item.getId(), 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    public void setTodoList(List<TodoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void editItem(int position) {
        TodoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTodo());
        bundle.putString("date", item.getDate()); // Add date
        AddNewTodo fragment = new AddNewTodo();
        fragment.setArguments(bundle);
        fragment.show(activity.getChildFragmentManager(), AddNewTodo.TAG);
    }

    public void deleteItem(int position) {
        TodoModel item = todoList.get(position);
        db.deleteTodo(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public Context getContext() {
        return activity.getContext();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        TextView date; // New date TextView

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
            date = view.findViewById(R.id.todoDate); // Initialize date TextView
        }
    }
}
