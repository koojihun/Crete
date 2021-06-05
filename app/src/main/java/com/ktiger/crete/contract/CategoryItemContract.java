package com.ktiger.crete.contract;

import com.ktiger.crete.model.Category;

public interface CategoryItemContract
{
    void showToast(String msg);
    void showDeleteDialog(Category category);
}
