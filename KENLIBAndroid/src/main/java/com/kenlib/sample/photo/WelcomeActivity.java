package com.kenlib.sample.photo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kenlib.android.R;
import com.kenlib.util.RequestPermissionUtil;
import com.kenlib.util.StatusBarUtil;
import com.kenlib.util.Util;

public class WelcomeActivity extends AppCompatActivity {

    boolean pSD, POv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.titleHide(this);
        StatusBarUtil.setWindowStatusBarColor(this, R.color.hs);
        setContentView(R.layout.welcome_photo_activity);

//        RequestPermissionUtil.getPermissionSD(WelcomeActivity.this, new RequestPermissionUtil.IHandler() {
//            @Override
//            public void isHavePermission() {
//                pSD = true;
//                RequestPermissionUtil.getPermissionCanDrawOverlays(WelcomeActivity.this, new RequestPermissionUtil.IHandler() {
//                    @Override
//                    public void isHavePermission() {
//                        POv = true;
//                        go();
//                    }
//                });
//            }
//        });

    }

    void go() {
        if (pSD && POv) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(WelcomeActivity.this, PhotoActivity.class));
                    finish();
                }
            }, 1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        RequestPermissionUtil.getPermissionSD(this, new RequestPermissionUtil.IHandler() {
            @Override
            public void isHavePermission() {
//                Util.showToast(WelcomeActivity.this,"you2");
                pSD = true;
                RequestPermissionUtil.getPermissionCanDrawOverlays(WelcomeActivity.this, new RequestPermissionUtil.IHandler() {
                    @Override
                    public void isHavePermission() {
                        POv = true;
                        go();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RequestPermissionUtil.getPermissionCanDrawOverlays(WelcomeActivity.this, new RequestPermissionUtil.IHandler() {
            @Override
            public void isHavePermission() {
                POv = true;
                go();
            }
        });

    }
}
