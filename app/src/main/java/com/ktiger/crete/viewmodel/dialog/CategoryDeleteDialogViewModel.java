package com.ktiger.crete.viewmodel.dialog;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import com.ktiger.crete.contract.CategoryDeleteDialogContract;
import com.ktiger.crete.database.CreteDatabase;
import com.ktiger.crete.model.Category;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoryDeleteDialogViewModel
{
    private CategoryDeleteDialogContract view;
    private Category category;

    public CategoryDeleteDialogViewModel(CategoryDeleteDialogContract view, Category category)
    {
        this.view = view;
        this.category = category;
    }

    @SuppressLint("CheckResult")
    public void delete(View button) {
        CreteDatabase.getDbInstance(view.getActivityContext()).categoryDao()
                .delete(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.deleteCategory(category);
                            view.close();
                        },
                        throwable -> {
                            Log.d("CategoryListViewModel", throwable.getMessage());
                        });
    }

    public void cancel(View button)
    {
        view.close();
    }

}
