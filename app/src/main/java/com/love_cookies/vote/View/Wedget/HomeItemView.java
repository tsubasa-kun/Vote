package com.love_cookies.vote.View.Wedget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.love_cookies.vote.R;

/**
 * Created by xiekun on 2016/1/13 0013.
 */
public class HomeItemView extends FrameLayout {

    ImageView itemIcon;
    TextView itemTitle;

    public HomeItemView(Context context) {
        super(context);
    }

    public HomeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context, attrs);
    }

    public HomeItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context, attrs);
    }

    public HomeItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initLayout(context, attrs);
    }

    private void initLayout(Context context, AttributeSet attrs) {
        LayoutInflater factory = LayoutInflater.from(context);
        factory.inflate(R.layout.view_home_item, this);

        itemIcon = (ImageView)findViewById(R.id.item_icon);
        itemTitle = (TextView)findViewById(R.id.item_title);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.HomeItem);

//        Drawable icon = typedArray.getDrawable(R.styleable.HomeItem_Icon);
//        itemIcon.setImageDrawable(icon);

        CharSequence title = typedArray.getText(R.styleable.HomeItem_Title);
        itemTitle.setText(title);
    }

}
