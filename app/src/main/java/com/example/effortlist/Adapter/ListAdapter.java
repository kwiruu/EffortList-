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
import com.example.effortlist.ListFragment;
import com.example.effortlist.Model.ListModel;
import com.example.effortlist.R;
import com.example.effortlist.Utils.DatabaseHandlerList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListModel> listmodel;
    private ListFragment activity;
    private DatabaseHandlerList db;

    public ListAdapter(DatabaseHandlerList db, ListFragment activity) {
        this.db = db;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListModel item = listmodel.get(position);
        holder.task.setText(item.getTodo());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isPressed()) {
                    int newStatus = b ? 1 : 0;
                    db.updateStatus(item.getId(), newStatus);
                    refreshList();
                }
            }
        });
    }

    private void refreshList() {
        listmodel = db.getAllTodo();
        Collections.sort(listmodel, new Comparator<ListModel>() {
            @Override
            public int compare(ListModel o1, ListModel o2) {
                return Integer.compare(o1.getStatus(), o2.getStatus());
            }
        });
        notifyDataSetChanged();
    }



    private boolean toBoolean(int n) {
        return n != 0;
    }

    public void setTodoList(List<ListModel> listmodel) {
        this.listmodel = listmodel;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listmodel.size();
    }

    public void editItem(int position) {
        ListModel item = listmodel.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTodo());
        bundle.putString("date", item.getDate()); // Add date
        AddNewTodo fragment = new AddNewTodo();
        fragment.setArguments(bundle);
        fragment.show(activity.getChildFragmentManager(), AddNewTodo.TAG);
    }

    public void deleteItem(int position) {
        ListModel item = listmodel.get(position);
        db.deleteTodo(item.getId());
        listmodel.remove(position);
        notifyItemRemoved(position);
    }

    public Context getContext() {
        return activity.getContext();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.listCheckBox);
        }
    }
}
