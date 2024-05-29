package com.example.effortlist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.effortlist.AddNewTodo;
import com.example.effortlist.ListFragment;
import com.example.effortlist.Model.ListModel;
import com.example.effortlist.Model.NoteModel;
import com.example.effortlist.R;
import com.example.effortlist.Utils.DBHelper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<NoteModel> noteModel;
    private ListFragment activity;
    private DBHelper db;

    public NotesAdapter(DBHelper db, ListFragment activity) {
        this.db = db;
        this.activity = activity;
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);
        return new ListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        NoteModel item = noteModel.get(position);
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
        noteModel = db.getAllNoteItems();
        Collections.sort(noteModel, new Comparator<NoteModel>() {
            @Override
            public int compare(NoteModel o1, NoteModel o2) {
                return Integer.compare(o1.getStatus(), o2.getStatus());
            }
        });
        notifyDataSetChanged();
    }



    private boolean toBoolean(int n) {
        return n != 0;
    }

    public void setTodoList(List<NoteModel> noteModel) {
        this.noteModel = noteModel;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return noteModel.size();
    }

    public void editItem(int position) {
        NoteModel item = noteModel.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTodo());
        bundle.putString("date", item.getDate()); // Add date
        AddNewTodo fragment = new AddNewTodo();
        fragment.setArguments(bundle);
        fragment.show(activity.getChildFragmentManager(), AddNewTodo.TAG);
    }

    public void deleteItem(int position) {
        NoteModel item = noteModel.get(position);
        db.deleteTodo(item.getId());
        noteModel.remove(position);
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
