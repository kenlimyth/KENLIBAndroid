package com.kenlib.sample;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.blankj.utilcode.util.BarUtils;
import com.kenlib.android.R;
import com.kenlib.util.DialogFragmentUtil;

/**
 * 测试用 android
 */
public class DialogFragmentDemo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_demo);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DialogFragmentUtil.getInstance().setIsSetCanceledOnTouchOutside(false)
                        .setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
                        .show(getSupportFragmentManager(), null);

                DialogFragmentUtil.getInstance().setGravity(Gravity.TOP)
                        .setLayoutId(R.layout.dialog3)
                        .setIView(new DialogFragmentUtil.IView() {
                            @Override
                            public void setView(View view, AppCompatDialogFragment dialogFragment) {
                                view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogFragment.dismiss();
                                    }
                                });
                            }
                        }).show(getSupportFragmentManager(), null);

                DialogFragmentUtil.getInstance().setGravity(Gravity.BOTTOM)
                        .setLayoutId(R.layout.dialog3)
                        .setIView(new DialogFragmentUtil.IView() {
                            @Override
                            public void setView(View view, AppCompatDialogFragment dialogFragment) {
                                view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogFragment.dismiss();
                                    }
                                });
                            }
                        }).show(getSupportFragmentManager(), null);


            }
        });

        BarUtils.setStatusBarColor(DialogFragmentDemo.this, getResources().getColor(R.color.red1));
//        BarUtils.setNavBarColor(TestDemo.this, getResources().getColor(R.color.red1));
        BarUtils.setNavBarVisibility(DialogFragmentDemo.this, false);
//        StatusBarUtil.setNavigationBarColor(TestDemo.this, getResources().getColor(R.color.red1));


    }


}
