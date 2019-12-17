package com.docker.cirlev2.ui.comment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;

/**
 * Created by showzeng on 17-8-11.
 * Email: kingstageshow@gmail.com
 * Github: https://github.com/showzeng
 */

public class CommentDialogFragment extends DialogFragment implements View.OnClickListener {

    private Dialog mDialog;
    private EditText commentEditText;
    private Button sendButton;
    private DialogFragmentDataCallback dataCallback;

    private String text, hint;

    public void setText(String text, String hint) {
        this.text = text;
        this.hint = hint;
        fillEditText();
    }

    public interface DialogFragmentDataCallback {
        void setCommentText(String commentTextTemp);

        void sendComment(String commentTextTemp);
    }

    public void setDataCallback(DialogFragmentDataCallback dataCallback) {
        this.dataCallback = dataCallback;
    }

//    @Override
//    public void onAttach(Context context) {
//        if (!(getActivity() instanceof DialogFragmentDataCallback)) {
//            throw new IllegalStateException("DialogFragment 所在的 activity 必须实现 DialogFragmentDataCallback 接口");
//        }
//        super.onAttach(context);
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDialog = new Dialog(getActivity(), R.style.BottomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.circlev2_dialog_comment);
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.windowAnimations = R.style.AnimBottom;
        layoutParams.dimAmount = 0;
        layoutParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(layoutParams);

        commentEditText = (EditText) mDialog.findViewById(R.id.edit_input);
        sendButton = (Button) mDialog.findViewById(R.id.btn_commit);
        fillEditText();
        setSoftKeyboard();
        sendButton.setOnClickListener(this);
        return mDialog;
    }

    private void fillEditText() {
        if (commentEditText != null) {
            commentEditText.setText(text);
            commentEditText.setSelection(commentEditText.length());
            commentEditText.setHint(hint);
        }
    }

    private void setSoftKeyboard() {
        commentEditText.setFocusable(true);
        commentEditText.setFocusableInTouchMode(true);
        commentEditText.requestFocus();
//        KeyboardUtils.toggleSoftInput();
    }

    @Override
    public void onResume() {
        super.onResume();
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager inManager = (InputMethodManager)commentEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        },200);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_commit){
            if (commentEditText.length() > 0) {
                dataCallback.sendComment(commentEditText.getText().toString());
                commentEditText.setText("");
                dismiss();
            } else {
                ToastUtils.showShort("请输入文字内容");
            }
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        dataCallback.setCommentText(commentEditText.getText().toString());
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dataCallback.setCommentText(commentEditText.getText().toString());
        super.onCancel(dialog);
    }
}
