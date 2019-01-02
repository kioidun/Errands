package com.example.kioiduncan.errands;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class slider_adapter extends PagerAdapter {

    Context context;

    LayoutInflater layoutInflater;

    public slider_adapter(Context context) {
        this.context = context;
    }

    // Arrays. What the viewpager will continue
    public int[] slide_Images = {
            R.drawable.eat_icon,
            R.drawable.code_icon,
            R.drawable.sleep_icon
    };

    public String[] slide_Headings = {
            "EAT",
            "SLEEP",
            "CODE"
    };

    public String[] slide_Decs = {
            "Lorem",
            "lorem",
            "lorem",
    };

    @Override
    public int getCount() {
        return slide_Headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    //Required to inflate all the things in the adapter
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        //Initialize all our textView

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_Image);
        TextView slide_Heading = (TextView) view.findViewById(R.id.slide_Heading);
        TextView slide_Description = (TextView) view.findViewById(R.id.slide_Description);

        slideImageView.setImageResource(slide_Images[position]);
        slide_Heading.setText(slide_Headings[position]);
        slide_Description.setText(slide_Decs[position]);

        container.addView(view);

        return view;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
