package com.ktiger.crete.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ktiger.crete.R;
import com.ktiger.crete.contract.CategoryAddDialogContract;
import com.ktiger.crete.databinding.CategoryAddDialogBinding;
import com.ktiger.crete.viewmodel.dialog.CategoryAddDialogViewModel;

public class CategoryAddDialog extends Dialog implements CategoryAddDialogContract
{

    private Context context;

    public CategoryAddDialog(Context context, int themResId)
    {
        super(context, themResId);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setUpView();

        CategoryAddDialogBinding binding = CategoryAddDialogBinding.bind(findViewById(R.id.add_category_layout));
        CategoryAddDialogViewModel viewModel = new CategoryAddDialogViewModel(this);
        binding.setViewModel(viewModel);
    }

    private void setUpView()
    {
        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.category_add_dialog);

        EditText categoryNameEditText = findViewById(R.id.category_name_edit_text);
        categoryNameEditText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void close()
    {
        dismiss();
    }

    @Override
    public String getTitleInput()
    {
        EditText titleEditText = findViewById(R.id.category_name_edit_text);
        return titleEditText.getText().toString();
    }

    @Override
    public Context getDialogContext()
    {
        return context;
    }

    @Override
    public void appendErrorMessage(String msg)
    {
        TextView titleTextView = findViewById(R.id.category_title_textview);
        String text = titleTextView.getText().toString();
        if (!text.contains(msg))
            titleTextView.setText(titleTextView.getText().toString() + "\n" + msg);
    }
}
