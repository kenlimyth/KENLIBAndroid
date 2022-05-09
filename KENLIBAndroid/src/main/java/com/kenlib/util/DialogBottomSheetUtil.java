package com.kenlib.util;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kenlib.android.R;

/**
 * 底部对话框
 * BottomSheetDialogFragment 相对于其它对话框有着以下的优势：
 * 1、拥有自己的生命周期；
 * 2、可对整个页面进行折叠、展开和销毁；
 * 3、可灵活使用自定义样式。
 */
public class DialogBottomSheetUtil extends BottomSheetDialogFragment {

    private AppCompatActivity mContext;

    private int mLayoutId;

    //滚动标志
    private boolean mScrollFlag = true;

    //对话框版式
    private FrameLayout mBottomSheetLayout;

    //视图
    private View mView;

    //行动控制器
    private BottomSheetBehavior<FrameLayout> mBottomSheetBehavior;

    public interface IView {
        void viewHandler(Context context, View mView, BottomSheetDialogFragment bottomSheetDialogFragment);
    }

    private DialogBottomSheetUtil(AppCompatActivity context, int layoutId) {
        this.mContext = context;
        this.mLayoutId = layoutId;
    }

    public static DialogBottomSheetUtil getInstance(AppCompatActivity context, int layoutId) {
        return new DialogBottomSheetUtil(context, layoutId);
    }

    public DialogBottomSheetUtil setIView(IView iView) {
        if (iView != null) {
            iView.viewHandler(mContext, mView, this);
        }
        return this;
    }

    public DialogBottomSheetUtil setScrollFlag(boolean scrollFlag) {
        this.mScrollFlag = scrollFlag;
        return this;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(mLayoutId, null);
        }
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        mBottomSheetLayout = dialog.getDelegate().findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if (mBottomSheetLayout != null) {
            mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            mBottomSheetBehavior.setHideable(mScrollFlag);
            mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (!mScrollFlag) {
                        if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                            //设置缩小
                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    if (slideOffset < 0 && slideOffset < -0.5) {
                        dismiss();
                    } else if (slideOffset > 0 && slideOffset < 0.5) {
                        dismiss();
                    }
                }
            });
        }
    }

    @Override
    public int getTheme() {
        return R.style.ActionSheetDialogStyle;
    }

    /**
     * 显示对话框
     */
    public void show() {
        if (mContext == null) {
            return;
        }
        show(mContext.getSupportFragmentManager(), "BaseBottomSheetDialog");
    }

    public void close() {
        if (mBottomSheetLayout != null && mBottomSheetBehavior != null) {
            dismiss();
        }
    }


}

