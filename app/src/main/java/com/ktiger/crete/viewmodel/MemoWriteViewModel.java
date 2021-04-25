package com.ktiger.crete.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.ktiger.crete.contract.MemoWriteContract;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MemoWriteViewModel {

    private MemoWriteContract view;

    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> contents = new MutableLiveData<>();

    public MemoWriteViewModel(MemoWriteContract view) { this.view = view; }

    public void loadMemo() {
        title.setValue("test title");
        contents.setValue("test contents");

        ArrayList<String> categories = new ArrayList<>();

        Observable.just("a", "b", "c")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> categories.add(s),
                        e -> { },
                        () -> {
                            view.setCategories(categories.toArray(new String[categories.size()]));
                        }
                );
    }

}
