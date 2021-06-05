package com.ktiger.crete.contract;

import android.content.Context;

import com.ktiger.crete.model.Category;

public interface CategoryDeleteDialogContract
{
    Context getActivityContext();
    void close();
    void deleteCategory(Category category);
}
