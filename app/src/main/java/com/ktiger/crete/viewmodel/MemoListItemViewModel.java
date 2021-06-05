package com.ktiger.crete.viewmodel;

import android.view.View;

import androidx.databinding.ObservableField;

import com.ktiger.crete.contract.MemoListItemContract;
import com.ktiger.crete.model.Memo;

public class MemoListItemViewModel
{

    private MemoListItemContract memoListItemContract;
    private Memo memo;
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> contents = new ObservableField<>();
    public ObservableField<String> time = new ObservableField<>();

    public MemoListItemViewModel(MemoListItemContract memoListItemContract)
    {
        this.memoListItemContract = memoListItemContract;
    }

    public void loadMemo(Memo memo)
    {
        this.memo = memo;
        title.set(memo.getTitle());
        contents.set(memo.getContents());
        time.set(memo.getTime());
    }

    public void click(View view)
    {
        MemoListViewModel memoListViewModel = MainViewModel.getMemoListViewModel();
        memoListViewModel.startWriteMemoActivity(memo);
    }

    public boolean onLongClick()
    {
        memoListItemContract.showDeleteDialog(memo);
        return true;
    }
}
