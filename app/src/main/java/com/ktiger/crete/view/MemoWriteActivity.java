package com.ktiger.crete.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ktiger.crete.R;
import com.ktiger.crete.contract.MemoWriteContract;
import com.ktiger.crete.databinding.ActivityWriteMemoBinding;
import com.ktiger.crete.model.Category;
import com.ktiger.crete.viewmodel.MemoWriteViewModel;

import java.util.List;

public class MemoWriteActivity extends AppCompatActivity implements MemoWriteContract {

    private Category currentCategory;

    private MemoWriteViewModel viewModel;
    private Spinner categoriesSpinner;
    private ArrayAdapter categoriesAdapter;

    private EditText titleEditText;
    private EditText contentsEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.currentCategory = (Category) getIntent().getSerializableExtra("category");

        this.viewModel = new MemoWriteViewModel(this);

        final ActivityWriteMemoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_write_memo);

        binding.setViewModel(viewModel);

        setUpViews();

        viewModel.loadCategories();
    }

    private void setUpViews()
    {
        titleEditText = findViewById(R.id.title_edit_text);
        contentsEditText = findViewById(R.id.contents_edit_text);

        categoriesSpinner = findViewById(R.id.category_spinner);
        categoriesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categoriesSpinner.setAdapter(categoriesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write_action, menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_write_menu_button:
                viewModel.saveMemo(titleEditText.getText(), contentsEditText.getText());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setCategories(List<Category> categories) {
        categoriesAdapter.clear();
        for (int idx = 0; idx < categories.size(); idx++)
        {
            Category category = categories.get(idx);
            categoriesAdapter.add(category.getName());
            if (category.getId() == currentCategory.getId())
                categoriesSpinner.setSelection(idx);
        }
        categoriesAdapter.notifyDataSetChanged();
    }

    @Override
    public Context getContext()
    {
        return this;
    }

    @Override
    public Category getCurrentCategory()
    {
        return currentCategory;
    }

    @Override
    public void setCurrentCategory(Category category)
    {
        this.currentCategory = category;
    }

    @Override
    public void finishViewWithResult(int resultCode)
    {
        setResult(resultCode);
        finish();
    }
}
