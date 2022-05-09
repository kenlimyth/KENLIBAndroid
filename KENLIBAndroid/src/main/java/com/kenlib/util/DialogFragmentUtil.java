package com.kenlib.util;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.kenlib.android.R;

public class DialogFragmentUtil extends AppCompatDialogFragment {

    private boolean isSetCanceledOnTouchOutside = true;
    private int gravity = Gravity.CENTER;
    private int layoutId = R.layout.dialog8;
    private int width = WindowManager.LayoutParams.MATCH_PARENT;
    private ILifecycle iLifecycle;
    private IView iView;

    public interface ILifecycle {
        void onStart();

        void onResume();

        void onDestroy();
    }

    public interface IView {
        void setView(View view, AppCompatDialogFragment dialogFragment);
    }

    public static DialogFragmentUtil getInstance() {
        return new DialogFragmentUtil();
    }

    public DialogFragmentUtil() {
    }

    public DialogFragmentUtil setWidth(int width) {
        this.width = width;
        return this;
    }

    public DialogFragmentUtil setIsSetCanceledOnTouchOutside(boolean isSetCanceledOnTouchOutside) {
        this.isSetCanceledOnTouchOutside = isSetCanceledOnTouchOutside;
        return this;
    }

    public DialogFragmentUtil setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public DialogFragmentUtil setLayoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public DialogFragmentUtil setIView(IView iView) {
        this.iView = iView;
        return this;
    }

    public DialogFragmentUtil setILifecycle(ILifecycle iLifecycle) {
        this.iLifecycle = iLifecycle;
        return this;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (iLifecycle != null) {
            iLifecycle.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (iLifecycle != null) {
            iLifecycle.onDestroy();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ActionSheetDialogStyle);//全屏dialog
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(layoutId, container);
        if (iView != null) {
            iView.setView(view, this);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.2f;//Dialog外透明度
        window.setLayout(width, windowParams.WRAP_CONTENT); //高度自适应，宽度全屏
        windowParams.gravity = gravity; //显示位置
        if (gravity == Gravity.TOP) {
            windowParams.windowAnimations = R.style.top_dialog_anim;
        } else {
            windowParams.windowAnimations = R.style.bottom_dialog_anim;
        }
        window.setAttributes(windowParams);

        getDialog().setCanceledOnTouchOutside(isSetCanceledOnTouchOutside);

        if (iLifecycle != null) {
            iLifecycle.onStart();
        }
    }
}
