package com.ktiger.crete.contract;

import android.content.Context;

public interface CategoryAddDialogContract
{
    Context getDialogContext();
    String getTitleInput();
    void appendErrorMessage(String msg);
    void close();
}
