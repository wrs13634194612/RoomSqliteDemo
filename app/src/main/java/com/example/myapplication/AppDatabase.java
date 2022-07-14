package com.example.myapplication;


import androidx.room.Database;
import androidx.room.RoomDatabase;


/**
 * @author acampbell
 */
@Database(exportSchema = false, entities = {Note.class, Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "app_db";

    public abstract NoteDao getNoteDao();

    public abstract CategoryDao getCategoryDao();

}
