package com.ktiger.crete.viewmodel;

import android.view.View;

import androidx.databinding.ObservableField;

import com.ktiger.crete.contract.CategoryItemContract;
import com.ktiger.crete.model.Category;

public class CategoryListItemViewModel
{
    private CategoryItemContract view;
    private Category category;
    public ObservableField<String> title = new ObservableField<>();

    public CategoryListItemViewModel(CategoryItemContract view)
    {
        this.view = view;
    }

    public void delete(View button)
    {
        if (category.getName().equals(Category.DEFAULT_CATEGORY_NAME))
        {
            view.showToast("기본 카테고리는 삭제할 수 없습니다.");
            return;
        }

        view.showDeleteDialog(category);
    }

    public void load(Category category)
    {
        this.category = category;
        this.title.set(category.getName());
    }

    public void select(View textView)
    {
        MemoListViewModel memoListViewModel = MainViewModel.getMemoListViewModel();
        memoListViewModel.setCurrentCategoryAndLoadMemos(category);
    }
}
