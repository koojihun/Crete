package com.ktiger.crete.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import com.ktiger.crete.contract.MemoListContract;
import com.ktiger.crete.database.CreteDatabase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MemoListViewModel {

    private MemoListContract view;

    public MemoListViewModel(MemoListContract view) { this.view = view; }

    @SuppressLint("CheckResult")
    public void setCurrentCategoryAndLoadMemos(String categoryName)
    {
        CreteDatabase.getDbInstance(view.getContext()).categoryDao()
                .loadByName(categoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        category -> {
                            view.setCurrentCategory(category);
                            loadMemos(category.getId());
                        },
                        throwable -> {
                            Log.d("MemoListViewModel", throwable.getMessage());
                        }
                );
    }

    @SuppressLint("CheckResult")
    public void loadMemos(int categoryId) {
        CreteDatabase.getDbInstance(view.getContext()).memoDao()
                .loadByCategoryId(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        memos -> view.setMemos(memos),
                        throwable -> {
                            Log.d("MemoListViewModel", throwable.getMessage());
                        }
                );
    }

    public void startWriteMemoActivity(View button)
    {
        view.openWriteActivity();
    }

}
