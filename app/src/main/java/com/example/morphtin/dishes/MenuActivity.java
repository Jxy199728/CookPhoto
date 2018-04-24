package com.example.morphtin.dishes;

/**
 * Created by 蒋小雨 on 2018/4/24.
 */

import android.app.Activity;
import android.support.v4.view.ViewPager;

        import android.app.Activity;
        import android.os.Bundle;
        import android.support.v4.view.PagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.support.v4.view.ViewPager.OnPageChangeListener;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.widget.ImageButton;
        import android.widget.LinearLayout;

        import java.util.ArrayList;
        import java.util.List;

public class MenuActivity extends Activity implements
        android.view.View.OnClickListener{

    private ViewPager mViewPager;                        // 用来放置界面切换
    private PagerAdapter mPagerAdapter;                  // 用来初始化View适配器
    private List<View> mViews = new ArrayList<View>();   //用来存放Tab01-Tab05

    // 5个Tab 每个Tab包含一个按钮
    private LinearLayout mTabFront;
    private LinearLayout mTabDiscovery;
    private LinearLayout mTabCircle;
    private LinearLayout mTabMessage;
    private LinearLayout mTabMe;

    // 5个按钮
    private ImageButton mFrontImg;
    private ImageButton mDiscoveryImg;
    private ImageButton mCircleImg;
    private ImageButton mMessageImg;
    private ImageButton mMeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initViewPage();
        initEvent();
    }

    private void initEvent() {
        mTabFront.setOnClickListener(this);
        mTabDiscovery.setOnClickListener(this);
        mTabCircle.setOnClickListener(this);
        mTabMessage.setOnClickListener(this);
        mTabMe.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
            /**
             * ViewPage左右滑动时
             * @param position
             * @param positionOffset
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int arg0) {
                int currentItem = mViewPager.getCurrentItem();
                switch (currentItem) {
                    case 0:
                        resetImg();
                        mFrontImg.setImageResource(R.drawable.tab_front_press);
                        break;
                    case 1:
                        resetImg();
                        mDiscoveryImg.setImageResource(R.drawable.tab_discovery_pressed);
                        break;
                    case 2:
                        resetImg();
                        mCircleImg.setImageResource(R.drawable.tab_circle_pressed);
                        break;
                    case 3:
                        resetImg();
                        mMessageImg.setImageResource(R.drawable.tab_message_pressed);
                        break;
                    case 4:
                        resetImg();
                        mMeImg.setImageResource(R.drawable.tab_me_pressed);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化设置
     */
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpage);
        // 初始化五个LinearLayout
        mTabFront = (LinearLayout) findViewById(R.id.id_tab_front);
        mTabDiscovery = (LinearLayout) findViewById(R.id.id_tab_discovery);
        mTabCircle = (LinearLayout) findViewById(R.id.id_tab_circle);
        mTabMessage = (LinearLayout) findViewById(R.id.id_tab_message);
        mTabMe = (LinearLayout) findViewById(R.id.id_tab_me);

        // 初始化五个按钮
        mFrontImg = (ImageButton) findViewById(R.id.id_tab_front_img);
        mDiscoveryImg = (ImageButton) findViewById(R.id.id_tab_discovery_img);
        mCircleImg = (ImageButton) findViewById(R.id.id_tab_circle_img);
        mMessageImg = (ImageButton) findViewById(R.id.id_tab_message_img);
        mMeImg = (ImageButton) findViewById(R.id.id_tab_me_img);
    }

    /**
     * 初始化ViewPage
     */
    private void initViewPage() {

        // 初始化五个布局
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        View tab01 = mLayoutInflater.inflate(R.layout.tab01, null);
        View tab02 = mLayoutInflater.inflate(R.layout.tab02, null);
        View tab03 = mLayoutInflater.inflate(R.layout.tab03, null);
        View tab04 = mLayoutInflater.inflate(R.layout.tab04, null);
        View tab05 = mLayoutInflater.inflate(R.layout.tab05, null);

        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);
        mViews.add(tab04);
        mViews.add(tab05);

        // 适配器初始化并设置
        mPagerAdapter = new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mViews.size();

            }
        };
        mViewPager.setAdapter(mPagerAdapter);
    }

    /**
     * 判断哪个要显示，及设置按钮图片
     */
    @Override
    public void onClick(View arg0) {

        switch (arg0.getId()) {
            case R.id.id_tab_front:
                mViewPager.setCurrentItem(0);
                resetImg();
                mFrontImg.setImageResource(R.drawable.tab_front_press);
                break;
            case R.id.id_tab_discovery:
                mViewPager.setCurrentItem(1);
                resetImg();
                mDiscoveryImg.setImageResource(R.drawable.tab_discovery_pressed);
                break;
            case R.id.id_tab_circle:
                mViewPager.setCurrentItem(2);
                resetImg();
                mCircleImg.setImageResource(R.drawable.tab_circle_pressed);
                break;
            case R.id.id_tab_message:
                mViewPager.setCurrentItem(3);
                resetImg();
                mMessageImg.setImageResource(R.drawable.tab_message_pressed);
                break;
            case R.id.id_tab_me:
                mViewPager.setCurrentItem(4);
                resetImg();
                mMeImg.setImageResource(R.drawable.tab_me_pressed);
                break;
            default:
                break;
        }
    }

    /**
     * 把所有图片变暗
     */
    private void resetImg() {
        mFrontImg.setImageResource(R.drawable.tab_front_normal);
        mDiscoveryImg.setImageResource(R.drawable.tab_discovery_normal);
        mCircleImg.setImageResource(R.drawable.tab_circle_normal);
        mMessageImg.setImageResource(R.drawable.tab_message_normal);
        mMeImg.setImageResource(R.drawable.tab_me_normal);

    }
}
