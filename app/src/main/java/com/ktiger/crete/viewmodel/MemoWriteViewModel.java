package com.ktiger.crete.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.ktiger.crete.contract.MemoWriteContract;
import com.ktiger.crete.database.CreteDatabase;
import com.ktiger.crete.model.Memo;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class MemoWriteViewModel {

    private MemoWriteContract view;

    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> contents = new MutableLiveData<>();

    public MemoWriteViewModel(MemoWriteContract view) { this.view = view; }

    @SuppressLint("CheckResult")
    public void loadCategories()
    {
        CreteDatabase.getDbInstance(view.getContext()).categoryDao()
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categories -> {
                            view.setCategories(categories);
                        },
                        throwable -> {
                            Log.d("MemoWriteViewModel", throwable.getMessage());
                        }
                );
    }

    public void loadMemo() {
        title.setValue("test title");
        contents.setValue("test contents");
    }

    @SuppressLint("CheckResult")
    public void saveMemo(Editable title, Editable contents)
    {
        Memo memo = new Memo(title.toString(), contents.toString(), view.getCurrentCategory().getId());
        CreteDatabase.getDbInstance(view.getContext()).memoDao()
                .insert(memo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.finishViewWithResult(RESULT_OK);
                        },
                        throwable -> {
                            Log.d("MemoWriteViewModel", throwable.getMessage());
                        }
                );
    }

    @SuppressLint("CheckResult")
    public void onCategoryItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String categoryName = (String) parent.getItemAtPosition(position);
        CreteDatabase.getDbInstance(view.getContext()).categoryDao()
                .loadByName(categoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        category -> {
                            this.view.setCurrentCategory(category);
                        },
                        throwable -> {
                            Log.d("MemoWriteViewModel", throwable.getMessage());
                        }
                );
    }

}
