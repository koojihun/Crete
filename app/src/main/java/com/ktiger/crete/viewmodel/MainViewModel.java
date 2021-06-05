package com.ktiger.crete.viewmodel;

public class MainViewModel
{

    private static CategoryListViewModel categoryListViewModel;
    private static MemoListViewModel memoListViewModel;

    public static CategoryListViewModel getCategoryListViewModel()
    {
        return categoryListViewModel;
    }

    public static void setCategoryListViewModel(CategoryListViewModel categoryListViewModel)
    {
        MainViewModel.categoryListViewModel = categoryListViewModel;
    }

    public static void setMemoListViewModel(MemoListViewModel memoListViewModel)
    {
        MainViewModel.memoListViewModel = memoListViewModel;
    }

    public static MemoListViewModel getMemoListViewModel()
    {
        return memoListViewModel;
    }

}
