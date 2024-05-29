package com.example.effortlist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.effortlist.Model.NoteModel;
import com.example.effortlist.Utils.DatabaseHandlerList;
import com.example.effortlist.Utils.DatabaseHandlerNote;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewNote extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newNoteTitleText, newNoteText;
    private Button newTaskSaveButton;

    private DatabaseHandlerNote db;

    public static AddNewNote newInstance(){
        return new AddNewNote();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_note, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newNoteTitleText = requireView().findViewById(R.id.newNoteText);
        newNoteText = requireView().findViewById(R.id.newNoteText2);

        newTaskSaveButton = getView().findViewById(R.id.newNoteButton);

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            newNoteTitleText.setText(task);
            if(task != null && task.length() > 0)
                newTaskSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        }

        db = new DatabaseHandlerNote(getActivity());
        db.openDatabase();

        newNoteTitleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    newTaskSaveButton.setEnabled(false);
                    newTaskSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newTaskSaveButton.setEnabled(true);
                    newTaskSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final boolean finalIsUpdate = isUpdate;
        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titletext = newNoteTitleText.getText().toString();
                String text = newNoteTitleText.getText().toString();
                if(finalIsUpdate){
                    db.updateTodo(bundle.getInt("id"), titletext,text);
                }
                else {
                    NoteModel task = new NoteModel();
                    task.setTITLE(titletext);
                    task.setTEXT(text);
                    db.insertNote(task,titletext,text);
                }
                dismiss();
            }
        });
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener)
            ((DialogCloseListener)activity).handleDialogClose(dialog);
    }
}
