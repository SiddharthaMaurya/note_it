package com.siddexpo.noteit;

public class Task_title {



    private String task;

    private String priority;
    private boolean completed;

    public Task_title(String task , String priority , boolean completed){
        this.task = task;
        this.priority = priority;
        this.completed = completed;
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

}
