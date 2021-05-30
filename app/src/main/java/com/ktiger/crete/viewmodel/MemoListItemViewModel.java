package com.ktiger.crete.viewmodel;

import androidx.databinding.ObservableField;

import com.ktiger.crete.model.Memo;

public class MemoListItemViewModel {

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> contents = new ObservableField<>();
    public ObservableField<String> time = new ObservableField<>();

    public MemoListItemViewModel() {

    }

    public void loadMemo(Memo memo) {
        title.set(memo.getTitle());
        contents.set(memo.getContents());
        time.set(memo.getTime());
    }

}
