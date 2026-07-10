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

        @Ignore
        public Note( String title, String content, long updatedAt) {
            this.title = title;
            this.content = content;
            this.updatedAt = updatedAt;
        }

    public Note(int id, String title, String content, long updatedAt) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.updatedAt = updatedAt;
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
    }

