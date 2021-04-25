package com.ktiger.crete.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ktiger.crete.R;
import com.ktiger.crete.contract.MemoListContract;
import com.ktiger.crete.database.CreteDatabase;
import com.ktiger.crete.databinding.ActivityMemoListBinding;
import com.ktiger.crete.model.Memo;
import com.ktiger.crete.viewmodel.MemoListViewModel;
import com.wajahatkarim3.roomexplorer.RoomExplorer;

import java.util.List;

public class MemoListActivity extends AppCompatActivity implements MemoListContract {

    private MemoAdapter memoAdapter;
    private MemoListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityMemoListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_memo_list);
        viewModel = new MemoListViewModel(this);

        binding.setViewModel(viewModel);

        setUpViews();

        RoomExplorer.show(this, CreteDatabase.class, "crete.db");
    }

    private void setUpViews() {
        RecyclerView memoRecyclerView = findViewById(R.id.memo_recycler_view);

        StaggeredGridLayoutManager memoLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        memoAdapter = new MemoAdapter(this);

        memoRecyclerView.setLayoutManager(memoLayoutManager);
        memoRecyclerView.setAdapter(memoAdapter);

        viewModel.loadMemos();
    }

    @Override
    public void setMemos(List<Memo> memos) {
        memoAdapter.setMemosAndRefresh(memos);
    }
}