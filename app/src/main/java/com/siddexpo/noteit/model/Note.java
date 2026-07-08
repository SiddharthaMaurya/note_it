package com.siddexpo.noteit.model;


    public class Note {
        private int id;
        private String title;
        private String content;
        private long updatedAt;

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

