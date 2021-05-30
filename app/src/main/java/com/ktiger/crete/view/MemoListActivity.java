package com.ktiger.crete.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ktiger.crete.R;
import com.ktiger.crete.contract.MemoListContract;
import com.ktiger.crete.database.CreteDatabase;
import com.ktiger.crete.databinding.ActivityMemoListBinding;
import com.ktiger.crete.model.Category;
import com.ktiger.crete.model.Memo;
import com.ktiger.crete.viewmodel.MemoListViewModel;
import com.wajahatkarim3.roomexplorer.RoomExplorer;

import java.util.List;

public class MemoListActivity extends AppCompatActivity implements MemoListContract {

    final int MEMO_LIST_ACTIVITY_CODE = 0;

    private MemoAdapter memoAdapter;
    private MemoListViewModel viewModel;
    private Category currentCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityMemoListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_memo_list);
        viewModel = new MemoListViewModel(this);

        binding.setViewModel(viewModel);

        setUpViews();

        viewModel.setCurrentCategoryAndLoadMemos(Category.DEFAULT_CATEGORY_NAME);

        RoomExplorer.show(this, CreteDatabase.class, "crete.db");
    }

    private void setUpViews() {
        RecyclerView memoRecyclerView = findViewById(R.id.memo_recycler_view);

        StaggeredGridLayoutManager memoLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        memoAdapter = new MemoAdapter(this);

        memoRecyclerView.setLayoutManager(memoLayoutManager);
        memoRecyclerView.setAdapter(memoAdapter);
    }

    @Override
    public void openWriteActivity()
    {
        final Intent intent = new Intent(this, MemoWriteActivity.class);
        intent.putExtra("category", currentCategory);

        startActivityForResult(intent, MEMO_LIST_ACTIVITY_CODE);
    }

    @Override
    public void setCurrentCategory(Category category)
    {
        this.currentCategory = category;
    }

    @Override
    public void setMemos(List<Memo> memos)
    {
        memoAdapter.setMemosAndRefresh(memos);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MEMO_LIST_ACTIVITY_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                viewModel.loadMemos(currentCategory.getId());
            }
        }

    }
}