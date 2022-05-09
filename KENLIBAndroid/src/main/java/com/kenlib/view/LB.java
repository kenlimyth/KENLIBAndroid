package com.kenlib.view;

import android.app.Activity;
import android.content.Context;

import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.kenlib.android.R;
import com.kenlib.util.Util;

/**
 * 轮播图
 * ViewPager+View
 *
 */
public class LB extends LinearLayout {

    private LinearLayout linearLayout;
    private ViewPager viewPager;

    ArrayList<View> list;
    FragmentActivity context;
    Ido ido;
    public boolean isDisplayYuandian = true;// 是否显示提示点
    public boolean isAutoPlay = true;// 自动滚动
    public int sudu = 1000;// 速度单位毫秒
    public boolean isXunhuan = true;// 是否循环

    int pagenum = 0;
    Object lock = new Object();// 线程同步对象

    public interface Ido {
        ArrayList<View> setViewList();
        void finishtodo();
        void gundongjt(int pagenum);
    }

    public LB(Context context) {
        super(context);
        init(context);
    }

    public LB(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = (FragmentActivity) context;
        View view = View.inflate(context, R.layout.lb, this);

        linearLayout = (LinearLayout) view.findViewById(R.id.l1);
        viewPager = (ViewPager) view.findViewById(R.id.vPager);
    }

    public void create(Ido ido) {

        this.ido = ido;

        initVP();
        if (isAutoPlay) {
            autoPlay();
        }
    }

    private void initVP() {

        list = ido.setViewList();

        viewPager.setAdapter(new d());
        viewPager.setOnPageChangeListener(new op());
        if (isDisplayYuandian) {
            initTextView(list.size());
            setTextColor(0);
        }
    }

    void autoPlay() {

        new Thread(new Runnable() {
            boolean jiesu = false;

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    while (true) {
                        if (isAutoPlay == false) {
                            Util.showLogDebug("isAutoPlay=false");
                            break;
                        }
                        if (context == null) {
                            break;
                        }

                        Thread.sleep(sudu);

                        ((Activity) context).runOnUiThread(new Runnable() {
                            public void run() {

                                if (pagenum <= viewPager.getChildCount()) {
                                    viewPager.setCurrentItem(pagenum);
                                } else {
                                    if (!isXunhuan) {

                                        jiesu = true;
                                        ido.finishtodo();

                                    } else {
                                        viewPager.setCurrentItem(0);

                                    }
                                }
                                pagenum++;

                                synchronized (lock) {
                                    // 通知继续执行
                                    lock.notify();
                                }
                            }
                        });
                        // 线程同步
                        synchronized (lock) {
                            // 等待
                            lock.wait();
                            if (jiesu) {
                                break;
                            }
                        }

                    }

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();

    }


    /**
     * 设置显示位置
     */
    private void initTextView(int count) {
        linearLayout.removeAllViews();
        for (int i = 0; i < count; i++) {
            TextView nTv = new TextView(context);
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    Util.dp2Px(6),
                    Util.dp2Px(6));
            lparams.setMargins(10, 0, 0, 0);
            lparams.gravity = Gravity.CENTER;
            nTv.setLayoutParams(lparams);
            nTv.setId(i);

            linearLayout.addView(nTv);

            // Log.i("initTextView==", "nTv");
        }

    }

    /**
     * 设置颜色
     *
     * @param id
     */
    void setTextColor(int id) {
        if (linearLayout != null) {

            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                View v = linearLayout.getChildAt(i);
                if (v instanceof TextView) {
                    TextView textView = (TextView) linearLayout.getChildAt(i);
                    if (id == i) {
                        textView.setBackgroundResource(R.drawable.round_25);
                    } else {
                        // textView.setBackgroundColor(resources.getColor(R.color.h));
                        textView.setBackgroundResource(R.drawable.layer_round_25);
                    }
                    Log.i("instanceof==", "nTv");
                }

            }

        }

    }

    /**
     * 事件
     *
     * @author Administrator
     */
    class op implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub

            if (isDisplayYuandian) {
                setTextColor(arg0);
            }
            pagenum = arg0;
            ido.gundongjt(pagenum);

        }

    }

    /**
     * 填充数据
     *
     * @author Administrator
     */
    class d extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub

            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(View container, int position) {
            // TODO Auto-generated method stub

            ((ViewPager) container).addView(list.get(position), 0);
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }
    }


}
