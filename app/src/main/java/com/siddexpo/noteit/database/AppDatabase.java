package com.siddexpo.noteit.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import androidx.room.Database;


import com.siddexpo.noteit.Todo;
import com.siddexpo.noteit.model.Note;

@Database(
        entities = {
                Note.class,
                Todo.class
        },
        version = 2,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();
    public abstract TodoDao todoDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "note_database"
            ).fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
    
}
