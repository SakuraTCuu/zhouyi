package com.qicheng.zhouyi.utils;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.qicheng.zhouyi.R;

/**
 * Created by Sakura on 2020-01-14 14:19
 * 自动垂直滚动的TextView
 */
public class AutoVerticalScrollTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private Context mContext;

    //mInUp,mOutUp分别构成向下翻页的进出动画
    private Rotate3dAnimation mInUp;
    private Rotate3dAnimation mOutUp;

    public AutoVerticalScrollTextView(Context context) {
        this(context, null);
    }

    public AutoVerticalScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        init();

    }

    private void init() {

        setFactory(this);
        setInAnimation(showTextAnimation());//当View显示时动画资源ID
        setOutAnimation(dismessTextAnimation());//当View隐藏是动画资源ID。

    }

    private Rotate3dAnimation createAnim(boolean turnIn, boolean turnUp) {

        Rotate3dAnimation rotation = new Rotate3dAnimation(turnIn, turnUp);
        rotation.setDuration(1200);//执行动画的时间
        rotation.setFillAfter(false);//是否保持动画完毕之后的状态
        rotation.setInterpolator(new AccelerateInterpolator());//设置加速模式

        return rotation;
    }


    //这里返回的TextView，就是我们看到的View,可以设置自己想要的效果
    public View makeView() {

//        TextView tv = new TextView(mContext);
////        设置居中，子view大小不一样时可能位置不居中问题
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT);
//        lp.gravity = Gravity.CENTER_VERTICAL;
//        tv.setLayoutParams(lp);
//        tv.setTextSize(12);
//        tv.setTextColor(getResources().getColor(R.color.mBlack_50));
//        return tv;

        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.LEFT);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setLineSpacing(20, 1);

        return textView;
    }

    //定义动作，向上滚动翻页
    public void next() {
        //显示动画
        if (getInAnimation() != mInUp) {
            setInAnimation(showTextAnimation());
        }
        //隐藏动画
        if (getOutAnimation() != mOutUp) {
            setOutAnimation(dismessTextAnimation());
        }
    }

    class Rotate3dAnimation extends Animation {
        private float mCenterX;
        private float mCenterY;
        private final boolean mTurnIn;
        private final boolean mTurnUp;
        private Camera mCamera;

        public Rotate3dAnimation(boolean turnIn, boolean turnUp) {
            mTurnIn = turnIn;
            mTurnUp = turnUp;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mCenterY = getHeight();
            mCenterX = getWidth();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;
            final int derection = mTurnUp ? 1 : -1;

            final Matrix matrix = t.getMatrix();

            camera.save();
            if (mTurnIn) {
                camera.translate(0.0f, derection * mCenterY * (interpolatedTime - 1.0f), 0.0f);
            } else {
                camera.translate(0.0f, derection * mCenterY * (interpolatedTime), 0.0f);
            }
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }

    public AnimationSet showTextAnimation() {
        mInUp = createAnim(true, true);
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(mInUp);
        animationSet.setDuration(1000);

        return animationSet;
    }

    public AnimationSet dismessTextAnimation() {
        mOutUp = createAnim(false, true);
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(mOutUp);
        animationSet.setDuration(1000);

        return animationSet;
    }
}

