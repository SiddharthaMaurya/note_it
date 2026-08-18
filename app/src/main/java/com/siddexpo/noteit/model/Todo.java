package com.siddexpo.noteit.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "tasks")
public class Todo {


    @PrimaryKey(autoGenerate = true)
    private int id;

    private String task;

    private String priority;

    private boolean completed;

    private long updatedAt;


    @Ignore
    public Todo(String task , String priority , boolean completed, long updatedAt){
        this.task = task;
        this.priority = priority;
        this.completed = completed;
        this.updatedAt = updatedAt;
    }


    public Todo(int id, String task , String priority , boolean completed , long updatedAt){
        this.id = id;
        this.task = task;
        this.priority = priority;
        this.completed = completed;
        this.updatedAt = updatedAt;
    }


    public String getTask(){
        return task;
    }

    public String getPriority(){
        return priority;
    }

    public boolean isCompleted(){
        return completed;
    }



    public void setTask(String task){
        this.task = task;
    }

    public void setPriority(String priority){
        this.priority = priority;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

}
