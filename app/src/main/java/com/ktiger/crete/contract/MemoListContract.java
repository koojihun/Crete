package com.ktiger.crete.contract;

import android.content.Context;

import com.ktiger.crete.model.Category;
import com.ktiger.crete.model.Memo;

import java.util.List;

public interface MemoListContract {

    void setMemoList(List<Memo> memoList);
    Context getContext();
    void openWriteActivity();
    void openWriteActivity(Memo memo);
    void closeDrawer();
}
