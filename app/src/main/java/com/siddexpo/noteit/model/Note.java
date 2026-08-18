package com.siddexpo.noteit.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
    public class Note {
        @PrimaryKey(autoGenerate = true)
        private int id;
        private String title;
        private String content;
        private long updatedAt;

        private boolean pinned;


        public Note( String title, String content, long updatedAt ,boolean pinned) {
            this.title = title;
            this.content = content;
            this.updatedAt = updatedAt;
            this.pinned = pinned;
        }


        @Ignore
        public Note(int id, String title, String content, long updatedAt,boolean pinned) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.updatedAt = updatedAt;
            this.pinned = pinned;
        }



        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public long getUpdatedAt() {
            return updatedAt;
        }


        public boolean getPinned(){return pinned; }

        public void setId(int id){
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setUpdatedAt(long updatedAt) {
            this.updatedAt = updatedAt;
        }

        public void setPinned(boolean pinned){
            this.pinned = pinned;
        }
    }

