package com.ktiger.crete.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import com.ktiger.crete.contract.MemoListContract;
import com.ktiger.crete.database.CreteDatabase;
import com.ktiger.crete.model.Category;
import com.ktiger.crete.model.Memo;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MemoListViewModel {

    private MemoListContract view;
    private Category currentCategory;
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
                            currentCategory = category;
                            loadMemosOfCurrentCategory();
                        },
                        throwable -> {
                            Log.d("MemoListViewModel", throwable.getMessage());
                        }
                );
    }

    @SuppressLint("CheckResult")
    public void setCurrentCategoryAndLoadMemos(Category category)
    {
        CreteDatabase.getDbInstance(view.getContext()).memoDao()
                .loadByCategoryId(category.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        memos -> {
                            currentCategory = category;
                            view.setMemoList(memos);
                            view.closeDrawer();
                        },
                        throwable -> {
                            Log.d("MemoListViewModel", throwable.getMessage());
                        }
                );
    }

    @SuppressLint("CheckResult")
    public void loadMemosOfCurrentCategory() {
        int categoryId = currentCategory.getId();
        CreteDatabase.getDbInstance(view.getContext()).memoDao()
                .loadByCategoryId(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        memos -> view.setMemoList(memos),
                        throwable -> {
                            Log.d("MemoListViewModel", throwable.getMessage());
                        }
                );
    }

    public void startWriteMemoActivity(View button)
    {
        view.openWriteActivity();
    }

    public void startWriteMemoActivity(Memo memo)
    {
        view.openWriteActivity(memo);
    }

    public Category getCurrentCategory()
    {
        return currentCategory;
    }

}
