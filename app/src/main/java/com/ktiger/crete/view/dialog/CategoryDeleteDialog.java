package com.ktiger.crete.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.ktiger.crete.R;
import com.ktiger.crete.contract.CategoryDeleteDialogContract;
import com.ktiger.crete.contract.CategoryListContract;
import com.ktiger.crete.databinding.CategoryDeleteDialogBinding;
import com.ktiger.crete.model.Category;
import com.ktiger.crete.viewmodel.dialog.CategoryDeleteDialogViewModel;

public class CategoryDeleteDialog extends Dialog implements CategoryDeleteDialogContract
{

    private Context context;
    private Category category;

    public CategoryDeleteDialog(Context context, int themResId, Category category)
    {
        super(context, themResId);
        this.context = context;
        this.category = category;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setUpView();

        CategoryDeleteDialogBinding binding = CategoryDeleteDialogBinding.bind(findViewById(R.id.delete_category_layout));
        CategoryDeleteDialogViewModel viewModel = new CategoryDeleteDialogViewModel(this, category);
        binding.setViewModel(viewModel);
    }

    private void setUpView()
    {
        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.category_delete_dialog);
        TextView title = findViewById(R.id.category_title_textview);
        title.setText(category.getName() + " 카테고리와 " + "\n 포함된 메모들을 삭제 할까요?");
    }

    @Override
    public void close()
    {
        dismiss();
    }

    @Override
    public Context getActivityContext()
    {
        return context;
    }

    @Override
    public void deleteCategory(Category category)
    {
        ((CategoryListContract)context).deleteCategory(category);
    }
}
