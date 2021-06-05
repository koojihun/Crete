package com.ktiger.crete.viewmodel.dialog;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import com.ktiger.crete.contract.MemoDeleteDialogContract;
import com.ktiger.crete.database.CreteDatabase;
import com.ktiger.crete.model.Memo;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MemoDeleteDialogViewModel
{
    private MemoDeleteDialogContract view;
    private Memo memo;

    public MemoDeleteDialogViewModel(MemoDeleteDialogContract view, Memo memo)
    {
        this.view = view;
        this.memo = memo;
    }

    @SuppressLint({"CheckResult", "LongLogTag"})
    public void delete(View button) {
        CreteDatabase.getDbInstance(view.getActivityContext()).memoDao()
                .delete(memo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.deleteMemo(memo);
                            view.close();
                        },
                        throwable -> {
                            Log.d("MemoDeleteDialogViewModel", throwable.getMessage());
                        });
    }

    public void cancel(View button)
    {
        view.close();
    }

}
