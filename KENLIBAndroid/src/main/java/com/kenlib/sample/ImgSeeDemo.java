package com.kenlib.sample;

import android.os.Bundle;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.android.R;
import com.kenlib.sample.photo.dto.FileInfo;
import com.kenlib.view.imgsee.ImgSeeView;

/**
 * Glide + PhotoView + ViewPager
 * 图片浏览
 */
public class ImgSeeDemo extends AppCompatActivity {

    ArrayList<FileInfo> arrayList;
    int currIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.img_see_demo);

        arrayList = new ArrayList<>();
        FileInfo fileInfo = new FileInfo();
        fileInfo.type= FileInfo.Type.img;
        fileInfo.localImageUrl="https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png";
        arrayList.add(fileInfo);
        currIndex = 1;


        ImgSeeView imgSeeView = (ImgSeeView) findViewById(R.id.img_see);
        imgSeeView.create(new ImgSeeView.Ido() {
            @Override
            public ArrayList<FileInfo> setData() {
                return arrayList;
            }

            @Override
            public void setVPScroll(int position) {
                currIndex = position;
            }
        });
        imgSeeView.setSelected(currIndex);


    }


}
