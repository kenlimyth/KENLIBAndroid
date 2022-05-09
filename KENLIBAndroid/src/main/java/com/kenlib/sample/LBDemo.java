package com.kenlib.sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.android.R;
import com.kenlib.view.LB;

/**
 * 轮播图
 */
public class LBDemo extends AppCompatActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lb_demo);

		LB lb = (LB) findViewById(R.id.lbBB);
		lb.create(new LB.Ido() {
			@Override
			public ArrayList<View> setViewList() {


				ArrayList<View> list = new ArrayList<View>();

				View view =  LayoutInflater.from(LBDemo.this).inflate(
						R.layout.lay1, null);
                View view2 = LayoutInflater.from(LBDemo.this).inflate(
                        R.layout.lay1, null);

				list.add(view);
                list.add(view2);
				return list;
			}

			@Override
			public void finishtodo() {
			}

			@Override
			public void gundongjt(int pagenum) {

			}
		});

	}

}
