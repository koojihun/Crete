package com.ktiger.crete.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ktiger.crete.R;
import com.ktiger.crete.contract.CategoryListContract;
import com.ktiger.crete.contract.MemoListContract;
import com.ktiger.crete.database.CreteDatabase;
import com.ktiger.crete.databinding.ActivityMemoListBinding;
import com.ktiger.crete.databinding.CategoryListBinding;
import com.ktiger.crete.model.Category;
import com.ktiger.crete.model.Memo;
import com.ktiger.crete.view.dialog.CategoryAddDialog;
import com.ktiger.crete.view.dialog.CategoryDeleteDialog;
import com.ktiger.crete.viewmodel.CategoryListViewModel;
import com.ktiger.crete.viewmodel.MainViewModel;
import com.ktiger.crete.viewmodel.MemoListViewModel;
import com.wajahatkarim3.roomexplorer.RoomExplorer;

import java.util.List;

public class MemoListActivity extends AppCompatActivity implements MemoListContract, CategoryListContract
{

    final int MEMO_LIST_ACTIVITY_CODE = 0;

    private MemoAdapter memoAdapter;
    private CategoryAdapter categoryAdapter;

    private MemoListViewModel memoListViewModel;
    private CategoryListViewModel categoryListViewModel;

    private View emptyListTextView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final ActivityMemoListBinding memoListBinding = DataBindingUtil.setContentView(this, R.layout.activity_memo_list);
        final CategoryListBinding categoryListBinding = DataBindingUtil.findBinding(findViewById(R.id.category_list_drawer_layout));

        memoListViewModel = new MemoListViewModel(this);
        categoryListViewModel = new CategoryListViewModel(this);

        MainViewModel.setMemoListViewModel(memoListViewModel);
        MainViewModel.setCategoryListViewModel(categoryListViewModel);

        memoListBinding.setViewModel(memoListViewModel);
        categoryListBinding.setViewModel(categoryListViewModel);

        setUpViews();

        memoListViewModel.setCurrentCategoryAndLoadMemos(Category.DEFAULT_CATEGORY_NAME);
        categoryListViewModel.setCategoryList();

        RoomExplorer.show(this, CreteDatabase.class, "crete.db");
    }

    private void setUpViews()
    {
        emptyListTextView = findViewById(R.id.empty_memo_text);
        drawerLayout = findViewById(R.id.drawer_layout);
        setUpMemoList();
        setUpCategoryList();
        setUpActionBar();
    }

    private void setUpMemoList()
    {
        RecyclerView memoRecyclerView = findViewById(R.id.memo_recycler_view);
        StaggeredGridLayoutManager memoLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        memoRecyclerView.setLayoutManager(memoLayoutManager);
        memoAdapter = new MemoAdapter(this);
        memoRecyclerView.setAdapter(memoAdapter);
    }

    private void setUpCategoryList()
    {
        RecyclerView categoryRecyclerView = findViewById(R.id.category_recycler_view);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        categoryAdapter = new CategoryAdapter(this);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setUpActionBar()
    {
        ActionBar ab = getSupportActionBar();
        ab.setTitle("crete 나만의 메모");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_navi);
    }

    @Override
    public void openWriteActivity()
    {
        final Intent intent = new Intent(this, MemoWriteActivity.class);
        intent.putExtra("category", memoListViewModel.getCurrentCategory());

        startActivityForResult(intent, MEMO_LIST_ACTIVITY_CODE);
    }

    @Override
    public void openWriteActivity(Memo memo)
    {
        final Intent intent = new Intent(this, MemoWriteActivity.class);
        intent.putExtra("category", memoListViewModel.getCurrentCategory());
        intent.putExtra("memo", memo);

        startActivityForResult(intent, MEMO_LIST_ACTIVITY_CODE);
    }

    @Override
    public void setMemoList(List<Memo> memoList)
    {
        if (memoList.isEmpty())
            emptyListTextView.setVisibility(View.VISIBLE);
        else
            emptyListTextView.setVisibility(View.INVISIBLE);
        memoAdapter.setMemosAndRefresh(memoList);
    }

    @Override
    public void setCategoryList(List<Category> categoryList)
    {
        categoryAdapter.setCategoryListAndRefresh(categoryList);
    }

    @Override
    public Context getContext()
    {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MEMO_LIST_ACTIVITY_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                memoListViewModel.loadMemosOfCurrentCategory();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT))
                    drawerLayout.closeDrawer(Gravity.LEFT);
                else
                    drawerLayout.openDrawer(Gravity.LEFT);
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void closeDrawer()
    {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT))
            drawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void showAddCategoryDialog()
    {
        new CategoryAddDialog(this, R.style.RoundedCornersDialog).show();
    }

    @Override
    public void deleteCategory(Category category)
    {
        int deletedIdx = categoryAdapter.deleteCategory(category);
        if (category.getId() == memoListViewModel.getCurrentCategory().getId())
        {
            Category beforeCategory = categoryAdapter.getCategory(deletedIdx - 1);
            memoListViewModel.setCurrentCategoryAndLoadMemos(beforeCategory);
        }
    }

}