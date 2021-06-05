package com.ktiger.crete.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ktiger.crete.model.Memo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface MemoDao {

    @Query("SELECT * FROM memo WHERE category_id = :category_id")
    Single<List<Memo>> loadByCategoryId(int category_id);

    @Insert
    Completable insert(Memo memo);

    @Delete
    Completable delete(Memo memo);

    @Query("SELECT * FROM memo")
    List<Memo> getAll();

    @Query("SELECT * FROM memo WHERE id IN (:ids)")
    List<Memo> loadAllByIds(int[] ids);

    @Query("SELECT * FROM memo WHERE id = :id")
    List<Memo> loadById(int id);

    @Insert
    void insertAll(Memo... memos);

    @Delete
    int deleteSelected(Memo... memos);

    @Update
    Completable update(Memo memo);

}
