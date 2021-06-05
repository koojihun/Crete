package com.ktiger.crete.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ktiger.crete.R;
import com.ktiger.crete.contract.MemoListItemContract;
import com.ktiger.crete.databinding.MemoListItemBinding;
import com.ktiger.crete.model.Memo;
import com.ktiger.crete.view.dialog.MemoDeleteDialog;
import com.ktiger.crete.viewmodel.MemoListItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> implements MemoListItemContract
{

    private Context context;
    private List<Memo> memoList;

    public MemoAdapter(Context context) {
        this.context = context;
        this.memoList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MemoListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.memo_list_item, parent, false);
        binding.setViewModel(new MemoListItemViewModel(this));
        return new ViewHolder(binding.getRoot(), binding.getViewModel());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Memo memo = memoList.get(position);
        holder.loadMemo(memo);
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    public void setMemosAndRefresh(List<Memo> memoList) {
        this.memoList = memoList;
        notifyDataSetChanged();
    }

    @Override
    public void showDeleteDialog(Memo memo)
    {
        new MemoDeleteDialog(context, R.style.RoundedCornersDialog,this , memo).show();
    }

    @Override
    public void deleteMemo(Memo memo)
    {
        int idx = memoList.indexOf(memo);
        if (idx != -1)
        {
            memoList.remove(idx);
            notifyItemRemoved(idx);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MemoListItemViewModel viewModel;
        private Memo memo;

        public ViewHolder(@NonNull View itemView, MemoListItemViewModel viewModel) {
            super(itemView);
            this.viewModel = viewModel;
        }

        public void loadMemo(Memo memo) {
            this.memo = memo;
            viewModel.loadMemo(memo);

            itemView.setOnLongClickListener(v -> viewModel.onLongClick() );
        }

    }

}
