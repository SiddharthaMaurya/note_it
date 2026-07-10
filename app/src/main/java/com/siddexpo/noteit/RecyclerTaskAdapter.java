package com.siddexpo.noteit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RecyclerTaskAdapter extends RecyclerView.Adapter<RecyclerTaskAdapter.ViewHolder> {


    Context context;

    ArrayList<Todo> arrTask = new ArrayList<>();
//    RecyclerTaskAdapter(Context context){
//        this.context = context;
//    }

    public interface OnEditClickListener{
        void onEditClick(int position);
    }

    private OnEditClickListener listener;

    public RecyclerTaskAdapter(Context context , ArrayList<Todo> arrTask , OnEditClickListener listener) {
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
