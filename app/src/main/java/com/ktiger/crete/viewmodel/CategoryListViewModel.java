package com.ktiger.crete.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import com.ktiger.crete.contract.CategoryListContract;
import com.ktiger.crete.database.CreteDatabase;
import com.ktiger.crete.model.Category;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoryListViewModel
{

    private CategoryListContract view;

    public CategoryListViewModel(CategoryListContract view)
    {
        this.view = view;
    }

    public void addCategory(View button)
    {
        view.showAddCategoryDialog();
    }

    @SuppressLint("CheckResult")
    public void setCategoryList()
    {
        CreteDatabase.getDbInstance(view.getContext()).categoryDao()
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categoryList -> view.setCategoryList(categoryList),
                        throwable -> {
                            Log.d("CategoryListViewModel", throwable.getMessage());
                        });
    }

}
