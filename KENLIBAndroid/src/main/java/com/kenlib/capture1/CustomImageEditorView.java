package com.kenlib.capture1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.math.BigDecimal;

import androidx.annotation.Nullable;

import com.kenlib.android.R;
import com.kenlib.util.FileUtil;
import com.kenlib.util.ImgUtil;
import com.kenlib.util.Util;

/**
 * 图像编辑系统视图
 */
public class CustomImageEditorView extends View {

    private int mCurrentOrientation;

    private boolean changedFlag = false;

    //编辑区域宽度/高度的比率
    private static final float mTailoringRatio = 2.0f;

    //裁剪框颜色
    private static final int mBorderColor = Color.WHITE;

    //封面刷的透明度
    private static final int pathColorAlpha = 255 * 3 / 10;

    //默认刷透明度
    private static final int defaultPathColorAlpha = 255;

    //宽度
    private float mWidth;

    //高度
    private float mHeight;

    //触摸模式
    private TouchMode currentTouchMode = TouchMode.NONE;

    //转换矩阵
    private Matrix mCurrentMatrix;

    //开始点
    private PointF startPoint = new PointF();

    //上次移动距离
    private float mPreDistance = 0;

    //中点
    private PointF midPoint;

    //编辑区域的宽度
    private float mTailoringWidth;

    //编辑区域的高度
    private float mTailoringHeight;

    //油漆
    private Paint mPaint;

    //覆盖层颜色
    private int mMaskColor;

    //裁剪框油漆
    private Path mTailorPath;

    //覆盖层路径
    private Path mMaskPath;

    //编辑区域的线条路径
    private Path mBorderPath;

    //当前选择图像
    private Bitmap mBitmap;

    //修改后的选择图像
    private Bitmap mEditedBitmap;

    //修改后所选图像的宽度
    private int mEditedBitmapWidth;

    //修改后选定图像的高度
    private int mEditedBitmapHeight;

    //当前图像位置
    private RectF mPositionData;

    private float mMaxScale;

    private RectF mOutSideRectF;

    //位置调整需要与否标志
    private boolean mAdjustPosFlg;

    //裁剪标志
    private boolean mCutFlag;

    //编辑区域的边框宽度
    private float mBorderWidth = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.0f, getContext().getResources().getDisplayMetrics());

    //裁剪框内部线条的宽度
    private float mLineWidth = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.0f, getContext().getResources().getDisplayMetrics());

    //最大比例
    private static final float MAX_SCALE_RATIO = 3;

    public CustomImageEditorView(Context context) {
        this(context, null);
    }

    public CustomImageEditorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageEditorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        getScreenOrientation();
        mOutSideRectF = new RectF();
        mBitmap = null;
        mMaskColor = getContext().getResources().getColor(R.color.cover_adjust_position_activity_mask_color);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMaskPath = new Path();
        mTailorPath = new Path();
        mBorderPath = new Path();
        mPositionData = new RectF();
        mEditedBitmap = null;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);
        getScreenOrientation();
        changedFlag = true;
        mAdjustPosFlg = true;
        mCutFlag = true;
        if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            mWidth = w;
            mHeight = h;
            mTailoringWidth = mWidth;
            mTailoringHeight = mWidth / mTailoringRatio;
            mMaskPath.reset();
            mTailorPath.reset();
            mBorderPath.reset();
            //掩码路径
            mMaskPath.addRect(-mWidth / 2, -mHeight / 2, mWidth / 2, -mTailoringHeight / 2, Path.Direction.CW);
            mMaskPath.addRect(-mWidth / 2, mTailoringHeight / 2, mWidth / 2, mHeight / 2, Path.Direction.CW);
            //修剪框
            RectF rect = new RectF(-mTailoringWidth / 2 + mBorderWidth / 2, -mTailoringHeight / 2,
                    mTailoringWidth / 2 - mBorderWidth / 2, mTailoringHeight / 2);
            mTailorPath.addRect(rect, Path.Direction.CW);
            mOutSideRectF.top = rect.top + mHeight / 2;
            mOutSideRectF.left = 0;
            mOutSideRectF.right = mWidth;
            mOutSideRectF.bottom = rect.bottom + mHeight / 2;

            float everyHeight = mTailoringHeight / 3;
            float everyWidth = mTailoringWidth / 3;
            //横线
            mBorderPath.moveTo(-mTailoringWidth / 2, everyHeight / 2);
            mBorderPath.lineTo(mTailoringWidth / 2, everyHeight / 2);
            mBorderPath.moveTo(-mTailoringWidth / 2, -everyHeight / 2);
            mBorderPath.lineTo(mTailoringWidth / 2, -everyHeight / 2);
            //垂直线条
            mBorderPath.moveTo(-everyWidth / 2, -mTailoringHeight / 2);
            mBorderPath.lineTo(-everyWidth / 2, mTailoringHeight / 2);
            mBorderPath.moveTo(everyWidth / 2, -mTailoringHeight / 2);
            mBorderPath.lineTo(everyWidth / 2, mTailoringHeight / 2);
        } else if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            mMaskPath.reset();
            mTailorPath.reset();
            mBorderPath.reset();
            mWidth = w;
            mHeight = h;
            if (mHeight > (mWidth / 2 + 2 * Util.convertDpToPixel(32.0f, getContext()))) {
                //horizontal
                mTailoringWidth = mWidth / 2;
                mTailoringHeight = mTailoringWidth / 2;
            } else {
                //vertical
                mTailoringHeight = mHeight - 2 * Util.convertDpToPixel(32.0f, getContext());
                mTailoringWidth = mTailoringHeight * 2;
            }

            //掩码路径
            mMaskPath.addRect(-mWidth / 2, -mHeight / 2 - mBorderWidth / 2, mWidth / 2,
                    -mTailoringHeight / 2 + mBorderWidth / 2, Path.Direction.CW);
            mMaskPath.addRect(-mWidth / 2, mTailoringHeight / 2, mWidth / 2, mHeight / 2, Path.Direction.CW);
            mMaskPath.addRect(-mWidth / 2, -mTailoringHeight / 2 - mBorderWidth / 2, -mTailoringWidth / 2,
                    mTailoringHeight / 2 + mBorderWidth / 2, Path.Direction.CW);
            mMaskPath
                    .addRect(mTailoringWidth / 2, -mTailoringWidth / 2 - mBorderWidth / 2, mWidth / 2,
                            mTailoringHeight / 2 + mBorderWidth / 2, Path.Direction.CW);
            //修剪框
            RectF rect = new RectF(-mTailoringWidth / 2 + mBorderWidth / 2, -mTailoringHeight / 2, mTailoringWidth / 2 - mBorderWidth / 2, mTailoringHeight / 2);
            mTailorPath.addRect(rect, Path.Direction.CW);

            mOutSideRectF.top = rect.top + mHeight / 2;
            mOutSideRectF.left = rect.left + mWidth / 2;
            mOutSideRectF.right = rect.right + mWidth / 2;
            mOutSideRectF.bottom = rect.bottom + mHeight / 2;

            float everyHeight = mTailoringHeight / 3;
            float everyWidth = mTailoringWidth / 3;
            //横线
            mBorderPath.moveTo(-mTailoringWidth / 2, everyHeight / 2);
            mBorderPath.lineTo(mTailoringWidth / 2, everyHeight / 2);
            mBorderPath.moveTo(-mTailoringWidth / 2, -everyHeight / 2);
            mBorderPath.lineTo(mTailoringWidth / 2, -everyHeight / 2);
            //垂直线条
            mBorderPath.moveTo(-everyWidth / 2, -mTailoringHeight / 2);
            mBorderPath.lineTo(-everyWidth / 2, mTailoringHeight / 2);
            mBorderPath.moveTo(everyWidth / 2, -mTailoringHeight / 2);
            mBorderPath.lineTo(everyWidth / 2, mTailoringHeight / 2);
        }

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null && (mCurrentMatrix == null || changedFlag)) {
            changedFlag = false;
            mCurrentMatrix = new Matrix();
            float width = mBitmap.getWidth();
            float ratio = mTailoringWidth / width;
            mCurrentMatrix.setScale(ratio, ratio);
            mEditedBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
                    mBitmap.getHeight(), mCurrentMatrix, true);
            mCurrentMatrix
                    .postTranslate(mWidth / 2F - mEditedBitmap.getWidth() / 2F, mHeight / 2 - mEditedBitmap.getHeight() / 2F);
            mMaxScale = ratio * MAX_SCALE_RATIO;
        }
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, mCurrentMatrix, mPaint);
            mEditedBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
                    mBitmap.getHeight(), mCurrentMatrix, true);
            //修改后所选图像的宽度和高度
            mEditedBitmapWidth = mEditedBitmap.getWidth();
            mEditedBitmapHeight = mEditedBitmap.getHeight();
            //修改后所选图像的位置
            mPositionData.setEmpty();
            mCurrentMatrix.mapRect(mPositionData);
        }
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        //掩码
        mPaint.setColor(mMaskColor);
        mPaint.setAlpha(pathColorAlpha);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mMaskPath, mPaint);
        //线条颜色
        mPaint.setColor(mBorderColor);
        mPaint.setAlpha(defaultPathColorAlpha);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth);
        //框架
        canvas.drawPath(mTailorPath, mPaint);
        mPaint.setStrokeWidth(mLineWidth);
        //线条
        canvas.drawPath(mBorderPath, mPaint);
        canvas.restore();
        adjustPosition();
    }

    /**
     * 检测边界值
     */
    private void adjustPosition() {
        //图像大小大于裁剪框
        if (mAdjustPosFlg && mBitmap != null && (mEditedBitmapWidth > (int) mTailoringWidth || mEditedBitmapHeight > (int) mTailoringHeight)) {
            float mTopY = mPositionData.top - (mHeight / 2 - mTailoringHeight / 2);
            float mBottomY = (mPositionData.top + mEditedBitmapHeight) - (mHeight / 2 + mTailoringHeight / 2);
            float mLeftX = mPositionData.left - (mWidth / 2 - mTailoringWidth / 2);
            float mRightX = (mPositionData.left + mEditedBitmapWidth) - (mWidth / 2 + mTailoringWidth / 2);
            if (mEditedBitmapWidth > (int) mTailoringWidth) {
                if (mEditedBitmapHeight < (int) mTailoringHeight) {
                    float centerY = mPositionData.top + (mEditedBitmapHeight / 2.0f);
                    mCurrentMatrix.postTranslate(0, mHeight / 2 - centerY);
                }
                if (mLeftX > 0) {
                    mCurrentMatrix.postTranslate(-mLeftX, 0);
                } else if (mRightX < 0) {
                    mCurrentMatrix.postTranslate(-mRightX, 0);
                }
            }
            if (mEditedBitmapHeight > (int) mTailoringHeight) {
                if (mEditedBitmapWidth < (int) mTailoringWidth) {
                    float centerX = mPositionData.left + (mEditedBitmapWidth / 2F);
                    mCurrentMatrix.postTranslate(mWidth / 2 - centerX, 0);
                }
                if (mTopY > 0) {
                    mCurrentMatrix.postTranslate(0, -mTopY);
                } else if (mBottomY < 0) {
                    mCurrentMatrix.postTranslate(0, -mBottomY);
                }
            }
            mAdjustPosFlg = false;
            invalidate();
        } else if (mAdjustPosFlg && mBitmap != null) {
            float centerX = mPositionData.left + (mEditedBitmapWidth / 2F);
            float centerY = mPositionData.top + (mEditedBitmapHeight / 2.0f);
            mCurrentMatrix.postTranslate(mWidth / 2 - centerX, mHeight / 2 - centerY);
            mAdjustPosFlg = false;
            invalidate();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mAdjustPosFlg = false;
        mCutFlag = false;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                currentTouchMode = TouchMode.Single;
                //開始ポイント
                startPoint.set(event.getRawX(), event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentTouchMode == TouchMode.Single) {
                    float dy = event.getRawY() - startPoint.y;
                    if (mEditedBitmapWidth > mTailoringWidth) {
                        float dx = event.getRawX() - startPoint.x;
                        if ((dy >= 0 && (mPositionData.top <= (mHeight / 2 - mTailoringHeight / 2))) || (dy <= 0
                                && ((mPositionData.top + mEditedBitmapHeight) >= (mHeight / 2
                                + mTailoringHeight / 2)))) {
                            mCurrentMatrix.postTranslate(0, dy);
                            startPoint.set(event.getRawX(), event.getRawY());
                        }
                        if ((dx >= 0 && (mPositionData.left <= (mWidth / 2 - mTailoringWidth / 2)) || (dx <= 0
                                && (mPositionData.left + mEditedBitmapWidth) >= (mWidth / 2 + mTailoringWidth / 2)))) {
                            mCurrentMatrix.postTranslate(dx, 0);
                            startPoint.set(event.getRawX(), event.getRawY());
                        }
                    } else {
                        if ((dy >= 0 && (mPositionData.top <= (mHeight / 2 - mTailoringHeight / 2))) || (dy <= 0
                                && ((mPositionData.top + mEditedBitmapHeight) >= (mHeight / 2
                                + mTailoringHeight / 2)))) {
                            startPoint.set(event.getRawX(), event.getRawY());
                            mCurrentMatrix.postTranslate(0, dy);
                        }
                    }
                } else if (currentTouchMode == TouchMode.Double) {
                    float dx = event.getRawX() - startPoint.x;
                    float dy = event.getRawY() - startPoint.y;
                    //二指伸缩
                    float mEndDistance = distance(event);
                    float changedDistance = Math.abs(mEndDistance - mPreDistance);
                    // 手指感的距离变更部分
                    boolean b10 = changedDistance < 8;
                    // 编辑后的图像宽度小于屏幕大小
                    boolean b11 = mEditedBitmapWidth < (int) mWidth;
                    // 向下移动，且区域上方边界线下方的情况
                    boolean b12 = dy >= 0 && (mPositionData.top <= (mHeight / 2 - mTailoringHeight / 2));
                    // 向上移动，且在区域下方的边界线上
                    boolean b13 = dy <= 0 && ((mPositionData.top + mEditedBitmapHeight) >= (mHeight / 2 + mTailoringHeight / 2));
                    if (b10 && (b11 || b12 || b13)) {
                        //二指スライド（Y軸）
                        mCurrentMatrix.postTranslate(0, dy);
                        startPoint.set(event.getRawX(), event.getRawY());
                    }

                    // 向右移动，且区域左侧边框左侧
                    boolean b21 = dx >= 0 && (mPositionData.left <= 0);
                    // 向左移动，且区域右边框右
                    boolean b22 = dx <= 0 && ((mPositionData.left + mEditedBitmapWidth) >= mWidth);
                    if (b10 && (b11 || b21 || b22)) {
                        //二指滑动（X轴）
                        mCurrentMatrix.postTranslate(dx, 0);
                        startPoint.set(event.getRawX(), event.getRawY());
                    }

                    // 编辑后的图像高度高于区域高度
                    boolean b31 = mEditedBitmapHeight > mTailoringHeight;
                    if (b10 && !b11 && b31) {
                        mCurrentMatrix.postTranslate(dx, dy);
                        startPoint.set(event.getRawX(), event.getRawY());
                    }

                    //从小到大
                    //高度>宽度
                    boolean b41 = (mPreDistance < mEndDistance && (mEditedBitmapWidth < mTailoringWidth
                            && mEditedBitmapHeight < mTailoringHeight)) || (mEditedBitmapWidth < mEditedBitmapHeight
                            && mEditedBitmapWidth < mBitmap.getWidth() * mMaxScale && mEditedBitmapHeight >= mTailoringHeight);
                    //高度<宽度
                    boolean b42 = (mPreDistance < mEndDistance && (mEditedBitmapWidth < mTailoringWidth
                            && mEditedBitmapHeight < mTailoringHeight)) || (mEditedBitmapWidth > mEditedBitmapHeight
                            && mPreDistance < mEndDistance && mEditedBitmapHeight < mBitmap.getHeight() * mMaxScale
                            && mEditedBitmapWidth >= mTailoringWidth);
                    //从大到小
                    boolean b43 = mPreDistance > mEndDistance && ((mEditedBitmapWidth > (int) mTailoringWidth) || (
                            mEditedBitmapHeight > (int) mTailoringHeight));

                    if (!b10 && (b41 || b42 || b43)) {
                        midPoint = mid(event);
                        float scale = mEndDistance / mPreDistance;
                        mPreDistance = mEndDistance;
                        mCurrentMatrix.postScale(scale, scale, midPoint.x, midPoint.y);
                    }
                }
                mPositionData.setEmpty();
                mCurrentMatrix.mapRect(mPositionData);
                break;
            case MotionEvent.ACTION_UP:
                mAdjustPosFlg = true;
                mCutFlag = true;
                currentTouchMode = TouchMode.NONE;
                if (mEditedBitmapWidth < (int) mTailoringWidth) {
                    float centerX = mPositionData.left + (mEditedBitmapWidth / 2F);
                    float centerY = mPositionData.top + (mEditedBitmapHeight / 2F);
                    if (mEditedBitmapHeight <= mTailoringHeight) {
                        mCurrentMatrix.postTranslate(mWidth / 2 - centerX, mHeight / 2 - centerY);
                        //长图
                        if (mEditedBitmapWidth > mEditedBitmapHeight) {
                            mCurrentMatrix
                                    .postScale(mTailoringWidth / mEditedBitmapWidth, mTailoringWidth / mEditedBitmapWidth,
                                            mWidth / 2, mHeight / 2);
                        } else {
                            mCurrentMatrix
                                    .postScale(mTailoringHeight / mEditedBitmapHeight, mTailoringHeight / mEditedBitmapHeight,
                                            mWidth / 2, mHeight / 2);
                        }
                    } else {
                        mCurrentMatrix.postTranslate(mWidth / 2 - centerX, 0);
                        float mTopY = mPositionData.top - (mHeight / 2 - mTailoringHeight / 2);
                        float mBottomY = (mPositionData.top + mEditedBitmapHeight) - (mHeight / 2 + mTailoringHeight / 2);
                        if (mTopY > 0) {
                            mCurrentMatrix.postTranslate(0, -mTopY);
                        } else if (mBottomY < 0) {
                            mCurrentMatrix.postTranslate(0, -mBottomY);
                        }
                    }
                } else {
                    if (mEditedBitmapHeight >= (mBitmap.getHeight() * (mMaxScale - 0.1F)) && midPoint != null) {
                        mCurrentMatrix.postScale((mBitmap.getHeight() * (mMaxScale - 0.1F)) / mEditedBitmapHeight,
                                (mBitmap.getHeight() * (mMaxScale - 0.1F)) / mEditedBitmapHeight,
                                midPoint.x, midPoint.y);
                        midPoint = null;
                    }
                    if (mEditedBitmapHeight > (int) mTailoringHeight) {
                        float mTopY = mPositionData.top - (mHeight / 2 - mTailoringHeight / 2);
                        float mBottomY = (mPositionData.top + mEditedBitmapHeight) - (mHeight / 2 + mTailoringHeight / 2);
                        float mLeftX = mPositionData.left - (mWidth / 2 - mTailoringWidth / 2);
                        float mRightX = (mPositionData.left + mEditedBitmapWidth) - (mWidth / 2 + mTailoringWidth / 2);
                        if (mTopY > 0) {
                            mCurrentMatrix.postTranslate(0, -mTopY);
                        } else if (mBottomY < 0) {
                            mCurrentMatrix.postTranslate(0, -mBottomY);
                        }
                        if (mLeftX > 0) {
                            mCurrentMatrix.postTranslate(-mLeftX, 0);
                        } else if (mRightX < 0) {
                            mCurrentMatrix.postTranslate(-mRightX, 0);
                        }
                    }
                }
                break;
            //从二指变更一指
            case MotionEvent.ACTION_POINTER_UP:
                currentTouchMode = TouchMode.NONE;
                break;
            //用手指按压
            case MotionEvent.ACTION_POINTER_DOWN:
                mPreDistance = distance(event);
                if (mPreDistance > 20) {
                    currentTouchMode = TouchMode.Double;
                    midPoint = mid(event);
                }
                break;
        }
        //重新绘制屏幕
        invalidate();
        return true;
    }

    /**
     * 取得二指中央的位置
     *
     * @param event 事件
     * @return 二指中央的位置
     */
    private PointF mid(MotionEvent event) {
        float midX = event.getX(1) + event.getX(0);
        float midY = event.getY(1) + event.getY(0);
        return new PointF(midX / 2, midY / 2);
    }

    /**
     * 取得二指的距离
     *
     * @param event 事件
     * @return 两指的距离
     */
    private float distance(MotionEvent event) {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 通过图像设置重新绘制屏幕
     *
     * @param bitmap 图像
     */
    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        mCurrentMatrix = null;
        invalidate();
    }

    /**
     * 生成裁剪后的图像。
     *
     * @return 剪下来的照片
     */
    private Bitmap GenerateCroppedImages() {
        Bitmap bitmap;
        mTailorPath.reset();
        mBorderPath.reset();
        mMaskPath.reset();
        postInvalidate();
        setDrawingCacheEnabled(true);
        buildDrawingCache();
        bitmap = Bitmap.createBitmap(getDrawingCache(), (int) (mWidth / 2 - mTailoringWidth / 2),
                (int) (mHeight / 2 - mTailoringHeight / 2),
                (int) mTailoringWidth, (int) mTailoringHeight);
        setDrawingCacheEnabled(false);
        return bitmap;
    }

    /**
     * 将编辑后的图像保存到本地文件
     */
    public void saveCroppedImages() {
        float ratio = new BigDecimal((float) mBitmap.getHeight() / mEditedBitmapHeight).setScale(3, BigDecimal.ROUND_HALF_UP)
                .floatValue();

//        RectF editBitmapRect = new RectF(mPositionData.left, mPositionData.top, mPositionData.left + mEditedBitmapWidth,
//                mPositionData.top + mEditedBitmapHeight);
//        RectF tailorRect = new RectF(mWidth / 2 - mTailoringWidth / 2, mHeight / 2 - mTailoringHeight / 2,
//                mWidth / 2 + mTailoringWidth / 2, mHeight / 2 + mTailoringHeight / 2);
//        //交叉矩形
//        RectF intersectRectangle = new RectF();
//        intersectRectangle.left = Math.max(editBitmapRect.left, tailorRect.left);
//        intersectRectangle.top = Math.max(editBitmapRect.top, tailorRect.top);
//        intersectRectangle.right = Math.min(editBitmapRect.right, tailorRect.right);
//        intersectRectangle.bottom = Math.min(editBitmapRect.bottom, tailorRect.bottom);
//
//        RectF newTailorRect = new RectF();
//        newTailorRect.left = (intersectRectangle.left - mPositionData.left) * ratio;
//        newTailorRect.top = (intersectRectangle.top - mPositionData.top) * ratio;
//
//        newTailorRect.right = newTailorRect.left + (intersectRectangle.right - intersectRectangle.left) * ratio;
//        newTailorRect.bottom = newTailorRect.top + (intersectRectangle.bottom - intersectRectangle.top) * ratio;

        if (mBitmap != null) {

            String path = FileUtil.getRootFileDir("t") + "1.jpg";
            ImgUtil.saveBitmap(GenerateCroppedImages(), path);

        }
    }

    /**
     * 判断是否裁断
     */
    public boolean getCutFlg() {
        return mCutFlag;
    }

    /**
     * 获取屏幕方向
     */
    private void getScreenOrientation() {
        Configuration mConfiguration = getResources().getConfiguration();
        mCurrentOrientation = mConfiguration.orientation;
    }

    /**
     * 触摸模式
     */
    private enum TouchMode {
        //シングルフィンガーモード
        Single,
        //ダブルフィンガーモード
        Double,
        //モードなし
        NONE
    }
}
