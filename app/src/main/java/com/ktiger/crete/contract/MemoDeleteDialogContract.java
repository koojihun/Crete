package com.ktiger.crete.contract;

import android.content.Context;

import com.ktiger.crete.model.Memo;

public interface MemoDeleteDialogContract
{
    Context getActivityContext();
    void close();
    void deleteMemo(Memo memo);
}
