package com.siddexpo.noteit.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.siddexpo.noteit.R;
import com.siddexpo.noteit.database.AppDatabase;
import com.siddexpo.noteit.model.Todo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {


    Context context;

    ArrayList<Todo> arrTask = new ArrayList<>();
//    RecyclerTaskAdapter(Context context){
//        this.context = context;
//    }

    public interface OnEditClickListener{
        void onEditClick(int position);
    }

    private OnEditClickListener listener;

    public TodoAdapter(Context context , ArrayList<Todo> arrTask , OnEditClickListener listener) {
        this.arrTask = arrTask;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view1 = LayoutInflater.from(context).inflate(R.layout.task_row,parent,false);

        ViewHolder viewHolder = new ViewHolder(view1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo task = arrTask.get(position);


        holder.txtContent.setText(task.getTask());
        holder.checked.setChecked(task.isCompleted());

        holder.checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton compoundButton, boolean b) {

                task.setCompleted(compoundButton.isChecked());

               if(task.isCompleted()){
                   holder.txtContent.setPaintFlags(holder.txtContent.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
               }
               else{
                   holder.txtContent.setPaintFlags(holder.txtContent.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
               }




                ExecutorService executor = Executors.newSingleThreadExecutor();

                executor.execute(()->{
                    AppDatabase db = AppDatabase.getInstance(context);
                    db.todoDao().update(task);
                });

            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy • hh:mm a", Locale.getDefault());
        String formatteDate = sdf.format(new Date(task.getUpdatedAt()));

        holder.todoDate.setText(formatteDate);

        holder.cardTask.setRadius(16f);
        switch(task.getPriority()){
            case "High":
                holder.cardTask.setCardBackgroundColor(0xFFFFB3B3);
                break;
            case "Medium":
                holder.cardTask.setCardBackgroundColor(0xFFFFE082);
                break;
            case "Low":
                holder.cardTask.setCardBackgroundColor(0xFFA5D6A7);
                break;
        }

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEditClick(position);
            }
        });

        task.setCompleted(holder.checked.isChecked());


    }

    @Override
    public int getItemCount() {
        return arrTask.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtContent;

        CardView cardTask;

        ImageView imgEdit;

        CheckBox checked;

        TextView todoDate;


        public ViewHolder(View itemView){
            super(itemView);

            txtContent = itemView.findViewById(R.id.txtContent);
            cardTask = itemView.findViewById(R.id.cardTask);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            checked = itemView.findViewById(R.id.checked);
            todoDate = itemView.findViewById(R.id.todoDate);
        }
    }

}
