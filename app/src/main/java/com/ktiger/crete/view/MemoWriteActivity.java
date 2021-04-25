package com.ktiger.crete.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ktiger.crete.R;
import com.ktiger.crete.contract.MemoWriteContract;
import com.ktiger.crete.database.CreteDatabase;
import com.ktiger.crete.databinding.ActivityWriteMemoBinding;
import com.ktiger.crete.viewmodel.MemoWriteViewModel;

public class MemoWriteActivity extends AppCompatActivity implements MemoWriteContract {

    private Spinner categoriesSpinner;
    private ArrayAdapter categoriesAdapter;

    public static void start(Context context) {
        final Intent intent = new Intent(context, MemoWriteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityWriteMemoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_write_memo);
        final MemoWriteViewModel viewModel = new MemoWriteViewModel(this);

        binding.setViewModel(viewModel);

        categoriesSpinner = findViewById(R.id.category_spinner);
        categoriesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categoriesSpinner.setAdapter(categoriesAdapter);

        viewModel.loadMemo();

        CreteDatabase.getDbInstance(this);
    }

    @Override
    public void setCategories(String[] categories) {
        categoriesAdapter.clear();
        categoriesAdapter.addAll(categories);
        categoriesAdapter.notifyDataSetChanged();

        categoriesSpinner.setSelection(1);
    }
}
