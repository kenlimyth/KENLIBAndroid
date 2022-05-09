package com.kenlib.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.kenlib.android.R;

/**
 * 显示Wait对话框。
 */
public class DialogIng extends Dialog {
    private Context context;
    private static DialogIng waitDialog;

    private DialogIng(Context context, String message) {
        super(context, R.style.Theme_WaitDialog);
        this.context = context;
        setContentView(R.layout.custom_progress_dialog);
        if (message != null) {
            ((TextView) findViewById(R.id.dialogLabel)).setText(message);
        }
//        findViewById(R.id.ll).getBackground().setAlpha(100);
        setCancelable(false);
        getWindow().getAttributes().dimAmount = 0.2f;
    }

    public static DialogIng getInstance(Context context, String message) {
        if (waitDialog == null) {
            synchronized (DialogIng.class){
                if (waitDialog == null) {
                    waitDialog = new DialogIng(context, message);
                }
            }
        }
        return waitDialog;
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
