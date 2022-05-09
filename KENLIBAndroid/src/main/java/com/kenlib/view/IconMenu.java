package com.kenlib.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;
import com.kenlib.android.R;

/**
 * 按下图片改变背景色
 *
 */
public class IconMenu extends AppCompatImageView {

    private Paint mPaint;
    private Bitmap mBitmap;
    private int PressedColor;
    private int NormalColor;
    Bitmap bitmap;

    public void setBitmapImg(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public IconMenu(Context context) {
        this(context, null);
    }

    public IconMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public IconMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.IconMunu);
//                context.getTheme().obtainStyledAttributes(attrs, R.styleable.IconMunu, defStyle, 0);
        PressedColor = a.getColor(R.styleable.IconMunu_PressedColor, 0x222222);
        NormalColor = a.getColor(R.styleable.IconMunu_NormalColor, 0xff35353a);
        int resId = a.getResourceId(R.styleable.IconMunu_icon, R.drawable.icon_img);
        mBitmap = BitmapFactory.decodeResource(getResources(), resId);
        a.recycle();
        init();
    }

    public Bitmap resizeBitmap(Bitmap bitmap, int w, int h) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float scaleWight = ((float) w) / width;
            float scaleHeight = ((float) h) / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWight, scaleHeight);
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        } else {
            return null;
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("ken","widthMeasureSpec");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = getPaddingLeft() + getPaddingRight() + mBitmap.getWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getPaddingTop() + getPaddingBottom() + mBitmap.getHeight();
        }
        setMeasuredDimension(width, height);
    }

    boolean flag = true;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (flag) {
            flag = false;
            bitmap = resizeBitmap(mBitmap, w, h);
        }
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPaint.setColorFilter(new LightingColorFilter(0xff555555, PressedColor));
                break;

            case MotionEvent.ACTION_UP:
                // 简单的setColorFilter(null)就可以
                mPaint.setColorFilter(new LightingColorFilter(0xffffffff, 0));
                break;
            default: {

            }
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(NormalColor);
//        canvas.drawBitmap(mBitmap, null, mPaint);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
    }


}
