package com.ktiger.crete.contract;

import com.ktiger.crete.model.Memo;

public interface MemoListItemContract
{
    void showDeleteDialog(Memo memo);
    void deleteMemo(Memo memo);
}
