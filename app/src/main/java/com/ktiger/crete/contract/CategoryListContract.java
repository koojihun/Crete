package com.ktiger.crete.contract;

import android.content.Context;

import com.ktiger.crete.model.Category;

import java.util.List;

public interface CategoryListContract
{
    Context getContext();
    void setCategoryList(List<Category> categoryList);
    void showAddCategoryDialog();
    void deleteCategory(Category category);
}
