package com.ktiger.crete.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ktiger.crete.R;
import com.ktiger.crete.contract.CategoryItemContract;
import com.ktiger.crete.databinding.CategoryListItemBinding;
import com.ktiger.crete.model.Category;
import com.ktiger.crete.view.dialog.CategoryDeleteDialog;
import com.ktiger.crete.viewmodel.CategoryListItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements CategoryItemContract
{

    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context) {
        this.context = context;
        this.categoryList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.category_list_item, parent, false);
        binding.setViewModel(new CategoryListItemViewModel(this));
        return new CategoryAdapter.ViewHolder(binding.getRoot(), binding.getViewModel());
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.loadCategory(category);

        if (category.getName().equals(Category.DEFAULT_CATEGORY_NAME))
        {
            holder.itemView.findViewById(R.id.category_delete_btn).setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void setCategoryListAndRefresh(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    public int deleteCategory(Category category)
    {
        int idx = categoryList.indexOf(category);
        if (idx != -1)
        {
            categoryList.remove(idx);
            notifyItemRemoved(idx);
        }
        return idx;
    }

    public Category getCategory(int idx)
    {
        return categoryList.get(idx);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CategoryListItemViewModel viewModel;

        public ViewHolder(@NonNull View itemView, CategoryListItemViewModel viewModel) {
            super(itemView);
            this.viewModel = viewModel;
        }

        public void loadCategory(Category category) { viewModel.load(category); }

    }

    @Override
    public void showToast(String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDeleteDialog(Category category)
    {
        new CategoryDeleteDialog(context, R.style.RoundedCornersDialog, category).show();
    }
}
