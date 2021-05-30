package com.ktiger.crete.contract;

import android.content.Context;

import com.ktiger.crete.model.Category;
import com.ktiger.crete.model.Memo;

import java.util.List;

public interface MemoListContract {

    void setMemos(List<Memo> memos);

    Context getContext();

    Category getCurrentCategory();

    void setCurrentCategory(Category category);

    void openWriteActivity();

}
