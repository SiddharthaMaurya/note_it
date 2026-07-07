package com.siddexpo.noteit;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class taskFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Task_title> arrTask = new ArrayList<>();

    RecyclerTaskAdapter adapter;

    FloatingActionButton btnAdd;
    public taskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        btnAdd = view.findViewById(R.id.btnAdd);

         btnAdd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 BottomSheetFragment sheet = new BottomSheetFragment(new BottomSheetFragment.OnTaskAddedListner() {
                     @Override
                     public void onTaskAdded(Task_title task_title) {
                         arrTask.add(task_title);
                         adapter.notifyItemInserted(arrTask.size()-1);
                     }

                     @Override
                     public void onTaskUpdated(Task_title task, int position) {

                     }
                 });

                 sheet.show(getChildFragmentManager(),"AddTask");
             }
         });


        adapter = new RecyclerTaskAdapter(requireContext(), arrTask , position -> {
            Task_title task = arrTask.get(position);

            BottomSheetFragment sheet = new BottomSheetFragment(task, position, new BottomSheetFragment.OnTaskAddedListner() {
                @Override
                public void onTaskAdded(Task_title task_title) {

                }

                @Override
                public void onTaskUpdated(Task_title task, int position) {
                    arrTask.set(position, task);
                    adapter.notifyItemChanged(position);
                }
            });

            sheet.show(getChildFragmentManager(),"Edit");
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {

            ColorDrawable background = new ColorDrawable(Color.parseColor("#FF8A80"));
            Drawable deleteicon = ContextCompat.getDrawable(requireContext(),R.drawable.icon_delete);

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                Task_title deletedTask = arrTask.get(position);
                arrTask.remove(position);
                adapter.notifyItemRemoved(position);


                Snackbar.make(recyclerView,"Task Deleted",Snackbar.LENGTH_LONG)
                        .setAction("UNDO",v-> {
                            arrTask.add(position,deletedTask);
                            adapter.notifyItemInserted(position);
                        })
                        .show();
            }


            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;

                int iconMargin = (itemView.getHeight()- deleteicon.getIntrinsicHeight())/2;

                if(dX > 0){
                    background.setBounds(
                            itemView.getLeft(),
                            itemView.getTop(),
                            itemView.getLeft()+(int) dX,
                            itemView.getBottom());

                    deleteicon.setBounds(
                            itemView.getLeft()+iconMargin,
                            itemView.getTop()+iconMargin,
                            itemView.getLeft()+iconMargin+deleteicon.getIntrinsicWidth(),
                            itemView.getBottom()-iconMargin
                    );
                } else {
                    background.setBounds(itemView.getRight()+(int)dX,
                            itemView.getTop(),
                            itemView.getRight(),
                            itemView.getBottom());

                    deleteicon.setBounds(
                            itemView.getRight()-iconMargin-deleteicon.getIntrinsicWidth(),
                            itemView.getTop()+iconMargin,
                            itemView.getRight()-iconMargin,
                            itemView.getBottom()-iconMargin
                    );

                }

                background.draw(c);
                deleteicon.draw(c);

                super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);

            }
        };



        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        return view;

    }
}