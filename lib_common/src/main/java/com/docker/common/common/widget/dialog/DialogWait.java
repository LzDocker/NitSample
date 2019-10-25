package com.docker.common.common.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import com.docker.common.R;
import com.docker.common.databinding.CommonDialogWaitingBinding;

public class DialogWait extends BaseDialog<CommonDialogWaitingBinding> {
    public DialogWait(Context context) {
        super(context, R.style.DialogStyle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_dialog_waiting;
    }

    @Override
    public void setMessage(String message) {
        mBinding.setMessage(message);
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
    }


}
