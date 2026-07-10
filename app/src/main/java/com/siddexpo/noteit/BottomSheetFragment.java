package com.siddexpo.noteit;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.siddexpo.noteit.database.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BottomSheetFragment extends BottomSheetDialogFragment {

    private Runnable onSaved;

    private EditText etTask;

    private Button btnSave;

    private boolean isEdit;
    private int editPosition;
    private Todo editTask;

    ChipGroup chipGroupPriority;

    Chip chipHigh;
    Chip chipMedium;
    Chip chipLow;

    private String priority = "Medium";

    private OnTaskAddedListner listner;
    public interface OnTaskAddedListner{
        void onTaskAdded(Todo todo);
        void onTaskUpdated(Todo task, int position);
    }

    public BottomSheetFragment(OnTaskAddedListner listner) {
        // Required empty public constructor
        this.listner = listner;
    }

    public BottomSheetFragment(Todo task, int position, OnTaskAddedListner listner){
        this.listner = listner;
        this.editTask = task;
        this.editPosition = position;
        isEdit = true;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view3 = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        etTask = view3.findViewById(R.id.etTask);
        btnSave = view3.findViewById(R.id.btnSave);
        chipGroupPriority = view3.findViewById(R.id.chipGroupPriority);
        chipHigh = view3.findViewById(R.id.chipHigh);
        chipMedium = view3.findViewById(R.id.chipMedium);
        chipLow = view3.findViewById(R.id.chipLow);


        if(isEdit){

            etTask.setText(editTask.getTask());

            switch (editTask.getPriority()) {
                case "High":
                    chipHigh.setChecked(true);
                    break;

                case "Medium":
                    chipMedium.setChecked(true);
                    break;

                case "Low":
                    chipLow.setChecked(true);
                    break;
            }
            btnSave.setText("Update Task");
            priority = editTask.getPriority();
        }

        if(!isEdit){
            chipMedium.setChecked(true);
            priority = "Medium";
        }


        chipGroupPriority.setOnCheckedStateChangeListener((group,checkIds) -> {
            if(checkIds.isEmpty()) return;

            int id = checkIds.get(0);

            if(id==R.id.chipHigh){
                priority = "High";
            } else if(id==R.id.chipMedium){
                priority = "Medium";
            } else{
                priority = "Low";
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String task = etTask.getText().toString().trim();


                if(task.isEmpty()){
                    etTask.setError("Enter Task");
                    return;
                }

                //Todo todo = new Todo( task , priority , false , System.currentTimeMillis());

                AppDatabase db = AppDatabase.getInstance(requireContext());

                ExecutorService executor = Executors.newSingleThreadExecutor();

                executor.execute(() ->{


                    if(isEdit){
                        //listner.onTaskUpdated(todo, editPosition);
                        Todo todo = new Todo(
                                editTask.getId(),
                                task,
                                priority,
                                editTask.isCompleted(),
                                System.currentTimeMillis()
                        );

                        db.todoDao().update(todo);
                    } else {
                        //listner.onTaskAdded(todo);
                        Todo todo = new Todo(
                                task,
                                priority,
                                false,
                                System.currentTimeMillis()
                        );

                        db.todoDao().insert(todo);
                    }

                    requireActivity().runOnUiThread(() ->{

                        if(onSaved != null){
                            onSaved.run();
                        }

                        dismiss();

                });

                });

            }
        });

        return view3;
    }

    public void setOnSavedListner(Runnable onSaved){
        this.onSaved = onSaved;
    }

}