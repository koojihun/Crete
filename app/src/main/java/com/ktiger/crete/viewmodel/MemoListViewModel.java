package com.ktiger.crete.viewmodel;

import android.view.View;

import com.ktiger.crete.contract.MemoListContract;
import com.ktiger.crete.database.CreteDatabase;
import com.ktiger.crete.model.Memo;
import com.ktiger.crete.view.MemoWriteActivity;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MemoListViewModel {

    private MemoListContract view;

    public MemoListViewModel(MemoListContract view) { this.view = view; }

    public void loadMemos() {

        ArrayList<Memo> memos = new ArrayList<>();

        CreteDatabase.getDbInstance(((View)view).getContext()).memoDao()

        Observable.just("a", "b", "c")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> { },
                        e -> { },
                        () -> {
                            view.setMemos(memos);
                        }
                );
    }

    public void startWriteMemoActivity(View view) {
        MemoWriteActivity.start(view.getContext());
    }

}
