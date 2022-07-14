package com.example.myapplication;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @author acampbell
 */
@Dao
public interface NoteDao {

    @Insert
    void insertAll(Note Notes);

    @Update
    void updateAll(Note Notes);

    @Query("SELECT * FROM Note")
    List<Note> getAll();

    @Query("SELECT Note.id, Note.title, Note.description, category.name as categoryName " +
            "FROM Note " +
            "LEFT JOIN category ON Note.category_id = category.id")
    List<CategoryNote> getCategoryNotes();

    @Query("SELECT Note.id, Note.title, Note.description, Note.category_id " +
            "FROM Note " +
            "LEFT JOIN category ON Note.category_id = category.id " +
            "WHERE Note.id = :NoteId")
    CategoryNote getCategoryNote(long NoteId);

    @Delete
    void deleteAll(Note... Notes);
}
