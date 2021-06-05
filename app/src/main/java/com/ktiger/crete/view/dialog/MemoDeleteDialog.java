package com.ktiger.crete.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.ktiger.crete.R;
import com.ktiger.crete.contract.CategoryDeleteDialogContract;
import com.ktiger.crete.contract.CategoryListContract;
import com.ktiger.crete.contract.MemoDeleteDialogContract;
import com.ktiger.crete.contract.MemoListItemContract;
import com.ktiger.crete.databinding.CategoryDeleteDialogBinding;
import com.ktiger.crete.databinding.MemoDeleteDialogBinding;
import com.ktiger.crete.model.Category;
import com.ktiger.crete.model.Memo;
import com.ktiger.crete.view.MemoAdapter;
import com.ktiger.crete.viewmodel.dialog.CategoryDeleteDialogViewModel;
import com.ktiger.crete.viewmodel.dialog.MemoDeleteDialogViewModel;

public class MemoDeleteDialog extends Dialog implements MemoDeleteDialogContract
{

    private Context context;
    private MemoListItemContract view;
    private Memo memo;

    public MemoDeleteDialog(Context context, int themResId, MemoListItemContract view, Memo memo)
    {
        super(context, themResId);
        this.context = context;
        this.view = view;
        this.memo = memo;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setUpView();

        MemoDeleteDialogBinding binding = MemoDeleteDialogBinding.bind(findViewById(R.id.delete_memo_layout));
        MemoDeleteDialogViewModel viewModel = new MemoDeleteDialogViewModel(this, memo);
        binding.setViewModel(viewModel);
    }

    private void setUpView()
    {
        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.memo_delete_dialog);
        TextView title = findViewById(R.id.memo_title_textview);
        title.setText(memo.getTitle() + "\n 메모를 삭제 할까요?");
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
    public void deleteMemo(Memo Memo)
    {
        view.deleteMemo(memo);
    }
}
