package com.example.kioiduncan.errands;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kioiduncan.errands.utilities.Utils;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private LinearLayout mLinearLayout;
    private slider_adapter mslider_adapter;
    private TextView[] mDots;

    private Button mNextbtn;
    private Button mBackbtn;
    private Button mFinish;
    private int mcurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mLinearLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        mNextbtn =(Button)findViewById(R.id.Next);
        mBackbtn=(Button)findViewById(R.id.back);
        mFinish =(Button) findViewById(R.id.finish);

        mslider_adapter = new slider_adapter(this);

        mViewPager.setAdapter(mslider_adapter);
        addDotsIndicator(0);
        mViewPager.addOnPageChangeListener(viewListener);

        //onClickListeners
        mNextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mcurrentPage + 1);

            }
        });
        mBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();

            }
        });
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                Utils.saveSharedSetting(MainActivity.this,ChooseActivity.PREF_USER_FIRST_TIME, "false");
            }
        });
    }

    public void addDotsIndicator(int position) {
        mDots = new TextView[3];
        // remove all views to avoid the dots repeating themselves in all views
        mLinearLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mLinearLayout.addView(mDots[i]);

        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    //Indicator telling us which page we are currently on

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mcurrentPage = i;

            if (i == 0) {

                mNextbtn.setEnabled(true);
                mBackbtn.setEnabled(false);
                mBackbtn.setVisibility(View.INVISIBLE);

                mNextbtn.setText("skip");
                mBackbtn.setText("");

            } else if (i == mDots.length - 1) {
                mNextbtn.setEnabled(true);
                mBackbtn.setEnabled(true);
                mNextbtn.setVisibility(i == 2 ? View.GONE : View.VISIBLE);
                mFinish.setVisibility(i ==2 ? View.VISIBLE : View.GONE);
                mBackbtn.setVisibility(View.VISIBLE);

                mNextbtn.setText("Finish");
                mBackbtn.setText("skip");
            } else {
                mNextbtn.setEnabled(true);
                mBackbtn.setEnabled(true);
                mBackbtn.setVisibility(View.VISIBLE);

                mNextbtn.setText("Next");
                mBackbtn.setText("skip");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {


        }
    };}

