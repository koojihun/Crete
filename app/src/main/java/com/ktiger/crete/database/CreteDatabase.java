package com.ktiger.crete.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ktiger.crete.database.dao.CategoryDao;
import com.ktiger.crete.database.dao.MemoDao;
import com.ktiger.crete.model.Category;
import com.ktiger.crete.model.Memo;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Database(entities = {Memo.class, Category.class}, version=1)
public abstract class CreteDatabase extends RoomDatabase {

    private static CreteDatabase database;

    public abstract MemoDao memoDao();
    public abstract CategoryDao categoryDao();

    public static CreteDatabase getDbInstance(Context context) {
        if(database == null){
            database = Room.databaseBuilder(context, CreteDatabase.class , "crete.db")
                    .build();
        }

        makeDefaultCategoryIfNeeded();

        return  database;
    }

    private static void makeDefaultCategoryIfNeeded() {
        database.categoryDao().getCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        count -> {
                            if (count == 0) {
                                database.categoryDao().insert(new Category(Category.DEFAULT_CATEGORY_NAME))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                () -> { Log.d("CreteDatabase", "DEFAULT CATEGORY ADDED"); },
                                                throwable -> { Log.d("CreteDatabase", "DEFAULT CATEGORY FAILED \n" + throwable.getMessage()); });
                                database.categoryDao().insert(new Category("abcd"))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                () -> { Log.d("CreteDatabase", "DEFAULT CATEGORY ADDED"); },
                                                throwable -> { Log.d("CreteDatabase", "DEFAULT CATEGORY FAILED \n" + throwable.getMessage()); });
                            }
                        });
    }

}
