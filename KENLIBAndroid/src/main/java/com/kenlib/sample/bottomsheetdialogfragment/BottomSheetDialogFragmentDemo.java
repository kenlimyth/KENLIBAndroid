package com.kenlib.sample.bottomsheetdialogfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kenlib.android.R;
import com.kenlib.util.DialogUtil;

//对话框
public class BottomSheetDialogFragmentDemo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.baopo_demo1);

        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showBottomSheetDialog(BottomSheetDialogFragmentDemo.this,R.layout.bookshelf_size_dialog, new BaseBottomSheetDialog.IView() {
                    @Override
                    public void viewHandler(Context context, View mView, BottomSheetDialogFragment bottomSheetDialogFragment) {

                        mView.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomSheetDialogFragment.dismiss();
                            }
                        });
                    }
                });

            }
        });


    }


}
