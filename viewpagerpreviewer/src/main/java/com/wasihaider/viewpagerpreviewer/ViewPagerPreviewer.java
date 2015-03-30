package com.wasihaider.viewpagerpreviewer;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Wasi on 3/29/2015.
 */
public class ViewPagerPreviewer extends HorizontalScrollView {

    private LinearLayout linearLayout;
    private ImageView [] imageviews;
    private ViewPager pager;
    private int itemWidth;
    private int numPreviews;
    private boolean enableOnClick;
    private Context mContext;
    private PreviewItemListener mListener;

    public ViewPagerPreviewer(Context context) {
        super(context);
        init(context, null);

    }

    public ViewPagerPreviewer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ViewPagerPreviewer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private int oldWidth = 0;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Just pray this doesn't happen too many times.
        if (getWidth() > 0 && getWidth() != oldWidth){
            setupUI();
            oldWidth = getWidth();
        }
    }

    public void setupUI(){

        //In case this has already been called
        this.removeAllViews();
        imageviews = null;
        linearLayout = null;
        numPreviews = pager.getOffscreenPageLimit();

        imageviews = new ImageView[numPreviews];
        linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < imageviews.length; i++){
            imageviews[i] = new ImageView(mContext);
            imageviews[i].setScaleType(ImageView.ScaleType.FIT_XY);
            imageviews[i].setLayoutParams(new ViewGroup.LayoutParams(itemWidth, ViewGroup.LayoutParams.MATCH_PARENT));

            if (enableOnClick) {
                final int position = i;
                imageviews[i].setClickable(true);
                imageviews[i].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.OnItemClicked(position);
                    }
                });
            }


            linearLayout.addView(imageviews[i]);
        }



        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(linearLayout);

        this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              drawImageViews(position, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void drawImageViews(int position, int positionOffsetPixels){
        FragmentPagerAdapter adapter = (FragmentPagerAdapter) pager.getAdapter();

        for (int i = 0; i < imageviews.length; i++) {
            int pos_to_show = position + i + 1;

            if (pos_to_show >= adapter.getCount()) {
                imageviews[i].setImageDrawable(new ColorDrawable(Color.GRAY));
                return;
            }

            Fragment f = adapter.getItem(position + i + 1);

            View v = f.getView();
            if (v != null && !v.isDrawingCacheEnabled()) {
                v.setDrawingCacheEnabled(true);
                v.buildDrawingCache();
            } else if (v == null) {
                return;
            }

            double pagerWidth = pager.getWidth();
            imageviews[i].setImageDrawable(new BitmapDrawable(v.getDrawingCache()));

            ViewPagerPreviewer.this.setScrollX((int) (((double) positionOffsetPixels) / pagerWidth * itemWidth));
        }
    }


    public void setPager(ViewPager _pager){
        pager = _pager;
        pager.setDrawingCacheEnabled(true);
    }

    public void init(Context context, AttributeSet attrs){
        mContext = context;
        setHorizontalScrollBarEnabled(false);
        if (attrs != null){
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.ViewPagerPreviewer,
                    0, 0);

            try {
                itemWidth = a.getDimensionPixelSize(R.styleable.ViewPagerPreviewer_itemWidth, 200);
                enableOnClick = a.getBoolean(R.styleable.ViewPagerPreviewer_enableOnClickEvent, false);
            }catch(Exception e){
                Log.d("ViewPagerPreviewer", "Bad attributes" + e.getMessage());
            } finally {
                a.recycle();

            }

        }else{

            Resources r = getResources();
            itemWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, r.getDisplayMetrics());
            enableOnClick = true;
        }

        enableOnClick = true;
        mListener = new PreviewItemListener() {
            @Override
            public void OnItemClicked(int position) {
                PagerAdapter adapter = pager.getAdapter();
                if (1 + position + pager.getCurrentItem() < adapter.getCount()) {
                    pager.setCurrentItem(1 + position + pager.getCurrentItem());
                }
            }
        };

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

}



    public PreviewItemListener getListener() {
        return mListener;
    }

    public void setListener(PreviewItemListener mListener) {
        this.mListener = mListener;
    }

    public boolean isOnClickEnabled() {
        return enableOnClick;
    }

    public void enableOnClick(boolean enable) {
        this.enableOnClick = enable;
    }

    public int getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }



    public interface PreviewItemListener{
        public void OnItemClicked(int position);
    }
}
