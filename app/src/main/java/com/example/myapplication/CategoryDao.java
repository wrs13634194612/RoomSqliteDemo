package com.example.myapplication;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

/**
 * @author acampbell
 */
@Dao
public interface CategoryDao {

    @Insert
    long insert(Category category);

    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Delete
    void deleteAll(Category categories);
}
