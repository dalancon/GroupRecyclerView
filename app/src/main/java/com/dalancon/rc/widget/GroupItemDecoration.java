package com.dalancon.rc.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.dalancon.rc.bean.SectionBean;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 分组RecyclerView 组头实现
 * Created by dalancon on 2019/4/15.
 */

public class GroupItemDecoration<T extends SectionBean> extends RecyclerView.ItemDecoration {

    private Context mContext;

    private List<T> mLists;

    private Comparator<T> mComparator;

    // 默认group高度
    private int DEFAULT_GROUP_HEIGHT = 77;

    // 默认文字大小
    private int DEFAULT_TEXT_SIZE = 18;

    // 默认圆的半径
    private int DEFAULT_CIRCLE_RADIUS = 27;

    //默认左边距
    private int DEFAULT_LEFT_MARGIN = 21;

    private Paint mPaint, mCirclePaint, mPaintWhite;

    private List<Integer> mGroupIndexs = new ArrayList<>();

    GroupItemDecoration(Context context, List<T> lists, Comparator<T> comparator) {
        this.mContext = context;
        this.mLists = lists;
        this.mComparator = comparator;

    }

    /**
     * 初始化画笔
     */
    void initPaints() {
        DEFAULT_TEXT_SIZE = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE, mContext.getResources().getDisplayMetrics());
        DEFAULT_GROUP_HEIGHT = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_GROUP_HEIGHT, mContext.getResources().getDisplayMetrics());
        DEFAULT_CIRCLE_RADIUS = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_CIRCLE_RADIUS, mContext.getResources().getDisplayMetrics());
        DEFAULT_LEFT_MARGIN = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_LEFT_MARGIN, mContext.getResources().getDisplayMetrics());

        //设置组名paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(DEFAULT_TEXT_SIZE);

        //设置组首字母paint
        mPaintWhite = new Paint();
        mPaintWhite.setAntiAlias(true);
        mPaintWhite.setDither(true);
        mPaintWhite.setColor(Color.WHITE);
        mPaintWhite.setTextSize(DEFAULT_TEXT_SIZE);

        //设置圆形背景paint
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);
        mCirclePaint.setColor(Color.parseColor("#BFBFBF"));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position == 0) {// 如果index == 0 直接显示group
            outRect.top = DEFAULT_GROUP_HEIGHT;
            mGroupIndexs.add(position);
        } else {// 如果index ！= 0 和上一项比较是否是同一组
            if (mComparator.compare(mLists.get(position), mLists.get(position - 1)) != 0) {// 如果属于同一组就不显示group 否则显示group
                outRect.top = DEFAULT_GROUP_HEIGHT;
                mGroupIndexs.add(position);
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {

            if (mGroupIndexs.contains(i)) {//显示组头
                Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();

                int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
                int y = DEFAULT_GROUP_HEIGHT / 2 + dy;


                String firstLetter = mLists.get(i).groupName.substring(0, 1);
                Rect rect = new Rect();
                mPaintWhite.getTextBounds(firstLetter, 0, firstLetter.length(), rect);


                Paint.FontMetricsInt firstFontMetricsInt = mPaintWhite.getFontMetricsInt();
                int first_dy = (firstFontMetricsInt.bottom - firstFontMetricsInt.top) / 2 - firstFontMetricsInt.bottom;
                int baseLine = DEFAULT_GROUP_HEIGHT / 2 + first_dy;


                if (i == 0) {
                    //画圆
                    canvas.drawCircle(DEFAULT_LEFT_MARGIN + DEFAULT_CIRCLE_RADIUS, DEFAULT_GROUP_HEIGHT / 2, DEFAULT_CIRCLE_RADIUS, mCirclePaint);
                    //画首字母
                    canvas.drawText(firstLetter, DEFAULT_LEFT_MARGIN + DEFAULT_CIRCLE_RADIUS - rect.width() / 2,
                            baseLine, mPaintWhite);
                    //画组名
                    canvas.drawText(mLists.get(i).groupName, DEFAULT_LEFT_MARGIN + 2 * DEFAULT_CIRCLE_RADIUS + 50, y, mPaint);
                } else {
                    canvas.drawCircle(DEFAULT_LEFT_MARGIN + DEFAULT_CIRCLE_RADIUS, parent.getChildAt(i - 1).getBottom() + DEFAULT_GROUP_HEIGHT / 2, DEFAULT_CIRCLE_RADIUS, mCirclePaint);
                    //画首字母
                    canvas.drawText(firstLetter, DEFAULT_LEFT_MARGIN + DEFAULT_CIRCLE_RADIUS - rect.width() / 2,
                            parent.getChildAt(i - 1).getBottom() + baseLine, mPaintWhite);

                    canvas.drawText(mLists.get(i).groupName, DEFAULT_LEFT_MARGIN + 2 * DEFAULT_CIRCLE_RADIUS + 50, parent.getChildAt(i - 1).getBottom() + y, mPaint);
                }
            }
        }
    }

    public void setGroupHeight(int groupHeight) {
        this.DEFAULT_GROUP_HEIGHT = groupHeight;
    }

    public void setLeftMargin(int leftMargin) {
        this.DEFAULT_LEFT_MARGIN = leftMargin;
    }

    public void setCircleRadius(int radius) {
        this.DEFAULT_CIRCLE_RADIUS = radius;
    }

    public void setTextSize(int textSize) {
        this.DEFAULT_TEXT_SIZE = textSize;
    }

    public static class Builder<T> {
        private DecorationParams<T> P = null;

        public Builder(Context context, List<T> list, Comparator<T> comparator) {
            P = new DecorationParams(context, list, comparator);
        }

        /**
         * 设置组头高度
         *
         * @param groupHeight
         * @return
         */
        public Builder<T> setDefaultGroupHeight(int groupHeight) {
            P.mGroupHeight = groupHeight;
            return this;
        }

        /**
         * 设置组头左边距
         *
         * @param leftMargin
         * @return
         */
        public Builder<T> setLeftMargin(int leftMargin) {
            P.mLeftMargin = leftMargin;
            return this;
        }

        /**
         * 设置组头的圆的半径
         *
         * @param radius
         * @return
         */
        public Builder<T> setRadius(int radius) {
            P.mCircleRadius = radius;
            return this;
        }

        public Builder<T> setTextSize(int textSize) {
            P.mTextSize = textSize;
            return this;
        }

        public GroupItemDecoration builder() {
            GroupItemDecoration groupItemDecoration = new GroupItemDecoration(P.mContextReference.get(), P.mLists, P.mComparator);
            P.apply(groupItemDecoration);

            return groupItemDecoration;
        }

        static class DecorationParams<T> {
            public WeakReference<Context> mContextReference = null;

            public List<T> mLists;
            public Comparator<T> mComparator;

            // 默认group高度
            public int mGroupHeight = 77;

            //默认左边距
            public int mLeftMargin = 21;

            // 默认圆的半径
            public int mCircleRadius = 27;

            //组头文字大小
            public int mTextSize = 18;

            DecorationParams(Context context, List<T> list, Comparator<T> comparator) {
                this.mContextReference = new WeakReference<>(context);
                this.mLists = list;
                this.mComparator = comparator;
            }

            public void apply(GroupItemDecoration groupItemDecoration) {
                groupItemDecoration.setGroupHeight(mGroupHeight);
                groupItemDecoration.setLeftMargin(mLeftMargin);
                groupItemDecoration.setCircleRadius(mCircleRadius);
                groupItemDecoration.setTextSize(mTextSize);
                groupItemDecoration.initPaints();
            }
        }
    }

}
