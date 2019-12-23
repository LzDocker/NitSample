package com.docker.common.common.widget.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.R;

/**
 * 确定样式Dialog
 */
public class ConfirmEdViewmDialog extends CommonBaseDialog {

    private String title;
    private String content;
    private String hint;
    private String btn1;
    private String btn2;

    public static ConfirmEdViewmDialog newInstance(String title, String content, String hint) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("content", content);
        bundle.putString("hint", hint);
        ConfirmEdViewmDialog dialog = new ConfirmEdViewmDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    public static ConfirmEdViewmDialog newInstance(String title, String content, String btn1, String btn2) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("content", content);
        bundle.putString("btn1", btn1);
        bundle.putString("btn2", btn2);
        ConfirmEdViewmDialog dialog = new ConfirmEdViewmDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        content = bundle.getString("content");
        title = bundle.getString("title");
        btn1 = bundle.getString("btn1");
        btn2 = bundle.getString("btn2");
        hint = bundle.getString("hint");
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.common_dialog_ed_confirm;
    }

    @Override
    public void convertView(ViewHolder holder, final CommonBaseDialog dialog) {

        holder.setText(R.id.title, title);
        holder.setText(R.id.message, content);

        if (!TextUtils.isEmpty(btn1)) {
            holder.setText(R.id.cancel, btn1);
            ((TextView) holder.getView(R.id.cancel)).setTextSize(16);
            ((TextView) holder.getView(R.id.cancel)).setTextColor(getResources().getColor(R.color.alivc_red));
        }
        if (!TextUtils.isEmpty(btn2)) {
            holder.setText(R.id.confirm, btn2);
            ((TextView) holder.getView(R.id.confirm)).setTextSize(16);
            ((TextView) holder.getView(R.id.confirm)).setTextColor(getResources().getColor(R.color.alivc_red));
        }
        holder.setOnClickListener(R.id.cancel, v -> {
            dialog.dismiss();
            if (confimLietener != null) {
                confimLietener.onCancle();
            }
        });
        holder.setOnClickListener(R.id.confirm, v -> {
            dialog.dismiss();
            if (confimLietener != null) {
                EditText editText = holder.getView(R.id.ed_uniu_key);
                String content = editText.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showShort(editText.getHint());
                    return;
                } else {
                    confimLietener.onConfim(content);
                }
            }
        });

        if (!TextUtils.isEmpty(hint)) {
            EditText editText = holder.getView(R.id.ed_uniu_key);
            editText.setHint(hint);
        }

    }


    @Override
    public void onDismiss(DialogInterface dialog) {

    }


    public interface ConfimLietener {
        void onCancle();

        void onConfim();

        void onConfim(String edit);
    }

    private ConfimLietener confimLietener;

    public CommonBaseDialog setConfimLietener(ConfimLietener confimLietener) {
        this.confimLietener = confimLietener;
        return this;
    }


}
