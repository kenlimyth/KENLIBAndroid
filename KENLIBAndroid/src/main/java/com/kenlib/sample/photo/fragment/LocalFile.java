package com.kenlib.sample.photo.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog;

import com.kenlib.android.R;
import com.kenlib.sample.photo.util.Config;
import com.kenlib.sample.photo.view.CoverPicRecyclerViewItemDecoration;
import com.kenlib.sample.photo.ImgLookActivity;
import com.kenlib.sample.photo.adapter.LocalFileAdapter;
import com.kenlib.sample.photo.dto.FileInfo;
import com.kenlib.util.AppExecutors;
import com.kenlib.util.FileUtil;
import com.kenlib.util.RequestPermissionUtil;
import com.kenlib.util.SPUtil;
import com.kenlib.util.Util;

import static com.kenlib.sample.photo.PhotoActivity.getFileListFinish;


public class LocalFile extends Fragment {

    RecyclerView recyclerView;
    LocalFileAdapter localFileAdapter;
    int mColumns = 3;
    public ArrayList<FileInfo> mImageList = new ArrayList<>();
    MiniLoadingDialog progressDialog;

    //分隔线宽度
    private static final int GRID_WIDTH = 8;

    LinearLayout linearLayout;
    ImageView buttonSub;
    EditText editTextTag;
    TextView txtMsg;

    public Boolean isFinishFragment = false;
    ImageView date;

    boolean pSD,pfd;

    public static LocalFile newInstance(Boolean isFinishFragment) {
        LocalFile f = new LocalFile();
        Bundle args = new Bundle();
        args.putBoolean("isFinishFragment", isFinishFragment);
        f.setArguments(args);
        return f;
    }


    public interface IAfterGetList {
        void afterGetList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        isFinishFragment = getArguments().getBoolean("isFinishFragment");

        View view = inflater.inflate(R.layout.local_file_demo, null);
        recyclerView = view.findViewById(R.id.recycler_view);
        linearLayout = view.findViewById(R.id.menu);
        editTextTag = view.findViewById(R.id.tag);
        buttonSub = view.findViewById(R.id.btn_sub);
        txtMsg = view.findViewById(R.id.txt_msg);
        date = view.findViewById(R.id.btn_date_system);

//        setTextViewHandler();
        addTagHandler();
        setR(view);
        selectDate();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();

    }

    void selectDate() {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(getContext(), DatePickerDialog.BUTTON_POSITIVE, Calendar.getInstance());
            }
        });
    }

    public void showDatePickerDialog(Context context, int themeResId, Calendar calendar) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context
                , themeResId
                , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editTextTag.setText(String.format("%d-%02d-%02d", year, (month + 1), dayOfMonth) + Config.Separate);
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(context.getColor(R.color.app_color_theme_1));
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(context.getColor(R.color.app_color_theme_1));
    }

    void setR(View view) {
        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableFooterFollowWhenNoMoreData(true);

        //样式
        //谷歌刷新头
        MaterialHeader materialHeader = new MaterialHeader(getActivity());
        materialHeader.setProgressBackgroundColorSchemeResource(R.color.app_color_theme_1);
        materialHeader.setColorSchemeResources(R.color.hs);
        refreshLayout.setRefreshHeader(materialHeader);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
                refreshLayout.finishRefresh();
                refreshLayout.resetNoMoreData();//setNoMoreData(false);//恢复上拉状态
            }
        });
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                getData();
//                refreshLayout.finishLoadMore();
//            }
//
//        });
    }


    public void getData() {

        String ls = SPUtil.getSharedPreferences(getContext()).getString("ls", "2");
        mColumns = Integer.parseInt(ls);

        RequestPermissionUtil.getPermissionSD(getActivity(), new RequestPermissionUtil.IHandler() {
            @Override
            public void isHavePermission() {
                getLocalPhotos();
            }
        });
    }

    void afterGetList() {
        setRecyclerViewHandler();
        localFileAdapter.notifySetListDataChanged(mImageList);
        setMsg(mImageList.size() + "");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        RequestPermissionUtil.getPermissionSD(getActivity(), new RequestPermissionUtil.IHandler() {
            @Override
            public void isHavePermission() {
                getLocalPhotos();
            }
        });

    }

    private void getLocalPhotos() {
        if (getActivity() == null) {
            return;
        }
        MiniLoadingDialog progressDialog = WidgetUtils.getMiniLoadingDialog(getActivity());
        progressDialog.updateMessage("图片加载中..");
        progressDialog.show();

        AppExecutors.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                mImageList.clear();

                if (!isFinishFragment) {
                    mImageList.addAll(Util.getLocalPhoto(getActivity()));
                    mImageList.addAll(Util.getLocalVideo(getActivity()));

                    ArrayList<FileInfo> arrayList = getFileListFinish();
                    Util.ListRemoveListAny(mImageList, arrayList);
                    pcFinish2(mImageList);

                    Collections.sort(mImageList, new Comparator<FileInfo>() {
                        @Override
                        public int compare(FileInfo o1, FileInfo o2) {
                            if (o1.modifyTime == null || o1.modifyTime == null) return 0;
                            return o2.modifyTime.compareTo(o1.modifyTime);//降序
                        }
                    });


                } else {

                    ArrayList<FileInfo> arrayList = getFileListFinish();
                    mImageList.addAll(arrayList);
                }


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        afterGetList();
                        progressDialog.dismiss();

                    }
                });



            }
        });
    }

    /**
     * 排除系统相册自动扫描已经整理过的图片
     */
    void pcFinish2(ArrayList<FileInfo> arrayList) {
        ArrayList<FileInfo> arrayList1 = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            FileInfo fileInfo = arrayList.get(i);
            if (fileInfo.VideoPath != null && fileInfo.VideoPath.indexOf(Config.FileUpDir) != -1) {
                arrayList1.add(fileInfo);
            }
            if (fileInfo.localImageUrl != null && fileInfo.localImageUrl.indexOf(Config.FileUpDir) != -1) {
                arrayList1.add(fileInfo);
            }
        }
        arrayList.removeAll(arrayList1);
    }

    void addTagHandler() {
        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tag = editTextTag.getText().toString();
                String dir = FileUtil.getRootFileDir(Config.FileUpDir);
                progressDialog = WidgetUtils.getMiniLoadingDialog(getActivity());
                progressDialog.updateMessage("图片处理中..");
                progressDialog.show();

                AppExecutors.getThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList arrayListDel = new ArrayList();
                        for (int i = 0; i < mImageList.size(); i++) {
                            FileInfo fileInfo = mImageList.get(i);
                            if (fileInfo.isCheck) {

                                String path = fileInfo.localImageUrl;
                                if (fileInfo.type == FileInfo.Type.mp4) {
                                    path = fileInfo.VideoPath;
                                }
                                String savePath = com.kenlib.sample.photo.util.Util.getSavePath(dir, tag, path);
                                FileUtil.copyFileUsingFileChannels(new File(path), new File(savePath));
                                FileUtil.delFileAndDir(path);

                                arrayListDel.add(fileInfo);
                            }
                        }
                        mImageList.removeAll(arrayListDel);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                progressDialog.dismiss();
                                localFileAdapter.notifySetListDataChanged(mImageList);
                                setMsg(mImageList.size() + "");
                            }
                        });
                    }
                });


            }
        });

    }

    void setMsg(String msg) {
        txtMsg.setText("文件数:" + msg);

    }

    void setRecyclerViewHandler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(15);
        recyclerView.setPadding(0, 0, 0, 10);

        localFileAdapter = new LocalFileAdapter(getContext(), mColumns, mImageList);
        recyclerView.setAdapter(localFileAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumns));

        //边框
        CoverPicRecyclerViewItemDecoration mCoverPicRecyclerViewItemDecoration = new CoverPicRecyclerViewItemDecoration(
                getContext(), GRID_WIDTH, false);
        if (recyclerView.getItemDecorationCount() == 0) {
            recyclerView.addItemDecoration(mCoverPicRecyclerViewItemDecoration);
        }
        localFileAdapter.setSelectImageListener(new LocalFileAdapter.onSelectImageListener() {
            @Override
            public void onClick(FileInfo info) {

                int index = mImageList.indexOf(info);
                Intent intent = new Intent(getContext(), ImgLookActivity.class);
                intent.putExtra("list", mImageList);
                intent.putExtra("index", index);
                startActivity(intent);
            }

            @Override
            public void setOnLongClickListener(FileInfo info) {
                localFileAdapter.isCKDisplay = true;
                localFileAdapter.setAllCheckBoxState();
            }
        });
        localFileAdapter.setCKListener(new LocalFileAdapter.OnCKListener() {
            @Override
            public void onCheckedChanged(FileInfo info, Boolean isCk) {
                if (isCk) {
                    linearLayout.setVisibility(View.VISIBLE);
//                    linearLayout.getBackground().setAlpha(150);
                } else {
                    linearLayout.setVisibility(View.GONE);
                }

            }
        });
    }


    void setTextViewHandler() {


//        textView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                setRecyclerViewHandler();
//            }
//        });
//        textView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mColumns = 2;
//                setRecyclerViewHandler();
//            }
//        });
//        textView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mColumns = 3;
//                setRecyclerViewHandler();
//            }
//        });
//        textView4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mColumns = 4;
//                setRecyclerViewHandler();
//            }
//        });
    }


}
