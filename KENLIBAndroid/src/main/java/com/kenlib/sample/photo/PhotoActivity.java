package com.kenlib.sample.photo;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.kenlib.android.R;
import com.kenlib.sample.photo.dto.FileInfo;
import com.kenlib.sample.photo.fileup.ExMultipartBody;
import com.kenlib.sample.photo.fragment.LocalFile;
import com.kenlib.sample.photo.util.Config;
import com.kenlib.sample.photo.view.PhotoXXKView;
import com.kenlib.util.AppExecutors;
import com.kenlib.util.DialogUtil;
import com.kenlib.util.FileUtil;
import com.kenlib.util.SPUtil;
import com.kenlib.util.StatusBarUtil;
import com.kenlib.util.Util;
import com.kenlib.util.WindowManagerUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * TabLayout + ViewPager + Fragment
 */
public class PhotoActivity extends AppCompatActivity {

    LocalFile localFile;
    LocalFile localFileFinish;
    ImageView ivUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.titleHide(this);
        StatusBarUtil.setWindowStatusBarColor(this, R.color.hs);
        setContentView(R.layout.photo_activity);
        ivUp = findViewById(R.id.ivUp);

        localFile = LocalFile.newInstance(false);
        localFileFinish = LocalFile.newInstance(true);


        setXXK();
        setIP();
        upFile();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Config.IsRresh) {
            localFile.getData();
            localFileFinish.getData();
            Config.IsRresh = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localFile = null;
        localFileFinish = null;
        System.gc();
    }

    private void setXXK() {
        PhotoXXKView xxk = findViewById(R.id.xxk);
        xxk.create(new PhotoXXKView.Ido() {
            @Override
            public List<Fragment> setFragments() {

                List<Fragment> list = new ArrayList<Fragment>();
                list.add(localFile);
                list.add(localFileFinish);
                return list;
            }

            @Override
            public boolean setTabs(TabLayout mTabTl) {
                return false;
            }

            @Override
            public void setVPgundong(int pagenum) {
            }
        });
    }

    void setIP() {
        ImageView txtMsg = findViewById(R.id.iv_menu);
        txtMsg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogUtil.showD(PhotoActivity.this, R.layout.update_url_view, new DialogUtil.Iview() {
                    @Override
                    public void setView(View view, AlertDialog alertDialog) {
                        alertDialog.setCancelable(true);
                        EditText url = view.findViewById(R.id.et_url);
                        EditText ls = view.findViewById(R.id.ls);
                        Button button = view.findViewById(R.id.btn_url);
                        url.setText(SPUtil.getSharedPreferences(PhotoActivity.this).getString("ip", Config.IP));
                        ls.setText(SPUtil.getSharedPreferences(PhotoActivity.this).getString("ls", "3"));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (url.getText() == null || url.getText().equals("")) {
                                    DialogUtil.showD(PhotoActivity.this, "不可以为空IP");
                                    return;
                                }
                                if (ls.getText() == null || ls.getText().equals("")) {
                                    DialogUtil.showD(PhotoActivity.this, "列数不可以为空");
                                    return;
                                }
                                SPUtil.getEdit(PhotoActivity.this).putString("ip", url.getText().toString()).commit();
                                SPUtil.getEdit(PhotoActivity.this).putString("ls", ls.getText().toString()).commit();
                                Util.showToast(PhotoActivity.this, "修改成功");
                                alertDialog.dismiss();

                                localFile.getData();
                            }
                        });
                    }
                });
            }
        });

    }

    public static ArrayList<FileInfo> getFileListFinish() {
        String dir = FileUtil.getRootFileDir(Config.FileUpDir);
        ArrayList<FileInfo> arrayList = new ArrayList();
        FileUtil.getFileListFromDir(dir, arrayList);
        return arrayList;
    }


    void window(String url){
        WindowManagerUtil.show(this, R.layout.fileup_view, new Util.ABSHandler() {
            @Override
            public void todo(View view, Util.ABSHandler close) {

                LinearLayout upLi = view.findViewById(R.id.upLi);
                ProgressBar pb_main_download = view.findViewById(R.id.pb_main_download);
                ProgressBar progressBarZ = view.findViewById(R.id.progressZ);
                TextView msg = view.findViewById(R.id.msg);
                TextView msgZ = view.findViewById(R.id.msg_z);

                AppExecutors.getThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {

                        ArrayList<FileInfo> fileInfoArrayList = getFileListFinish();
                        if (fileInfoArrayList.size() == 0) {

                            PhotoActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    close.todo();
//                                    alertDialog.dismiss();
                                }
                            });
                            return;
                        }

                        for (int i = 0; i < fileInfoArrayList.size(); i++) {

                            FileInfo fileInfo = fileInfoArrayList.get(i);
                            String path = fileInfo.localImageUrl;
                            if (fileInfo.type == FileInfo.Type.mp4) {
                                path = fileInfo.VideoPath;
                            }

                            File file = new File(path);
                            OkHttpClient httpClient = new OkHttpClient.Builder()
                                    .connectTimeout(5, TimeUnit.MINUTES)
                                    .pingInterval(5, TimeUnit.MINUTES)
                                    .writeTimeout(10, TimeUnit.MINUTES)
                                    .readTimeout(10, TimeUnit.MINUTES).build();
                            // 构建请求 Body , 这个我们之前自己动手写过
                            MultipartBody.Builder builder = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM);
                            builder.addFormDataPart("file", file.getName(),
                                    RequestBody.create(MediaType.parse("application/octet-stream"), file));


                            String finalPath = path;
                            int index = i + 1;

                            PhotoActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBarZ.setProgress((int) index);
                                    progressBarZ.setMax((int) fileInfoArrayList.size());
                                    msgZ.setText("总进度: " + index + "/" + fileInfoArrayList.size());
                                }
                            });

                            ExMultipartBody exMultipartBody = new ExMultipartBody(builder.build()
                                    , new ExMultipartBody.UploadProgressListener() {
                                @Override
                                public void onProgress(long total, long current) {
                                    PhotoActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            pb_main_download.setProgress((int) current);
                                            pb_main_download.setMax((int) total);
                                            msg.setText(finalPath);

                                        }
                                    });
                                }
                            });

                            // 构建一个请求
                            final Request request = new Request.Builder()
                                    .url(url)
                                    .post(exMultipartBody).build();

                            try {
                                Response response = httpClient.newCall(request).execute();
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    String code = jsonObject.get("code").toString();
                                    if ("00".equals(code)) {

                                        FileUtil.delFileAndDir(path);

                                        PhotoActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                if (index == fileInfoArrayList.size()) {
                                                    close.todo();
                                                    Config.IsRresh = true;
                                                    onResume();
                                                }
                                            }
                                        });
                                    }

                                } catch (JSONException e) {

                                    PhotoActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            close.todo();
                                            DialogUtil.showD(PhotoActivity.this, e.getMessage());
                                        }
                                    });
                                    break;
                                }


                            } catch (IOException e) {
                                PhotoActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        close.todo();
                                        DialogUtil.showD(PhotoActivity.this, e.getMessage());
                                    }
                                });
                                break;
                            }

                        }


                    }
                });

            }
        });
    }


    void upFile() {

        ivUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://" + SPUtil.getSharedPreferences(PhotoActivity.this).getString("ip", Config.IP) + "/api/upload";

                window(url);

            }
        });


    }

}
