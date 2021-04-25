package com.ktiger.crete.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ktiger.crete.model.Category;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


@Dao
public interface CategoryDao {

    @Query("SELECT COUNT(*) FROM category")
    Observable<Integer> getCount();

    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Query("SELECT * FROM category WHERE id = :id")
    Category loadById(int id);

    @Insert
    void insertAll(Category... categories);

    @Insert
    Completable insert(Category category);

    @Delete
    int delete(Category category);

    @Delete
    int deleteSelected(Category... categories);

    @Update
    void update(Category category);

}
