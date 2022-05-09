package com.kenlib.view.signatureview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.android.R;
import com.kenlib.util.FileUtil;

public class LinePathViewDemo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_path_view_demo);

        LinePathView linePathView = findViewById(R.id.line_path);

        Button cleanBtn = findViewById(R.id.btn_clear);
        Button saveBtn = findViewById(R.id.btn_save);
        cleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linePathView.clear();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = FileUtil.getRootFilePath();
                try {
                    linePathView.save(path + "1.png");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
