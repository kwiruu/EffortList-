package com.example.effortlist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.effortlist.AddNewTodo;
import com.example.effortlist.Model.NoteModel;
import com.example.effortlist.NotesFragment;
import com.example.effortlist.R;
import com.example.effortlist.Utils.DatabaseHandlerNote;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<NoteModel> noteModel;
    private NotesFragment activity;
    private DatabaseHandlerNote db;

    public NotesAdapter(DatabaseHandlerNote db, NotesFragment activity) {
        this.db = db;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NoteModel item = noteModel.get(position);
        holder.task.setText(item.getTITLE());
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
        bundle.putString("title", item.getTITLE());
        bundle.putString("content", item.getTEXT()); // Add date
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
        TextView task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.noteTv);
        }
    }
}
