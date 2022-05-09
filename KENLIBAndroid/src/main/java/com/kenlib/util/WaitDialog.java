package com.kenlib.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.kenlib.android.R;


/**
 * 显示Wait对话框。
 */
public class WaitDialog extends Dialog {
    Context context;

    /**
     * 无对话框消息的对话框。
     *
     * @param context
     */
    public WaitDialog(Context context) {
        super(context, R.style.Theme_WaitDialog_NoMessage);
        this.context = context;
        setContentView(R.layout.wait_progress_dialog);
        setCancelable(false);
    }

    /**
     * 带有信息的对话框
     *
     * @param context
     * @param message
     */
    public WaitDialog(Context context, String message) {
        super(context, R.style.Theme_WaitDialog);
        this.context = context;
        setContentView(R.layout.custom_progress_dialog);
        ((TextView) findViewById(R.id.dialogLabel)).setText(message);
        setCancelable(false);
    }

    @Override
    public void show() {
        try {

            if (!((Activity) context).isFinishing() && !this.isShowing()) {
                super.show();
            }
        } catch (IllegalArgumentException ignore) {
        }
    }

    @Override
    public void dismiss() {
        try {
            if (!((Activity) context).isFinishing() && this.isShowing()) {
                super.dismiss();
            }
        } catch (IllegalArgumentException ignore) {
        }
    }

}
