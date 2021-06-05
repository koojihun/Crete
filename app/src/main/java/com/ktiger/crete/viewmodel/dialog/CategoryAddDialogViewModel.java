package com.ktiger.crete.viewmodel.dialog;

import android.annotation.SuppressLint;
import android.view.View;

import com.ktiger.crete.contract.CategoryAddDialogContract;
import com.ktiger.crete.database.CreteDatabase;
import com.ktiger.crete.model.Category;
import com.ktiger.crete.viewmodel.CategoryListViewModel;
import com.ktiger.crete.viewmodel.MainViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoryAddDialogViewModel
{
    private CategoryAddDialogContract view;

    public CategoryAddDialogViewModel(CategoryAddDialogContract view)
    {
        this.view = view;
    }

    @SuppressLint({"CheckResult", "LongLogTag"})
    public void add(View button)
    {
        String input = view.getTitleInput();

        Category newCategory = new Category(input);

        CategoryListViewModel categoryListViewModel = MainViewModel.getCategoryListViewModel();

        CreteDatabase.getDbInstance(view.getDialogContext()).categoryDao()
                .insert(newCategory)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            categoryListViewModel.setCategoryList();
                            view.close();
                        },
                        throwable -> {
                            view.appendErrorMessage("이미 존재하는 이름입니다.");
                        }
                );
    }

    public void cancel(View button)
    {
        view.close();
    }

}
