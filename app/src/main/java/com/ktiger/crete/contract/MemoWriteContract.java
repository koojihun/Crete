package com.ktiger.crete.contract;

import android.content.Context;

import com.ktiger.crete.model.Category;

import java.util.List;

public interface MemoWriteContract {

    void setCurrentCategory(Category category);

    Category getCurrentCategory();

    void setCategories(List<Category> categories);

    Context getContext();

    void finishViewWithResult(int result);

}
