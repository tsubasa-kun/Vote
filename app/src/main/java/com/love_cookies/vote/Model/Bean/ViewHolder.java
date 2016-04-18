package com.love_cookies.vote.Model.Bean;

import android.content.Context;
import android.text.Html;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.love_cookies.vote.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by xiekun on 2016/01/10 0010.
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context mContext;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        mContext = context;
        mPosition = position;
        mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public static ViewHolder getViewHolder(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
            return viewHolder;
        }
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getPosition() {
        return mPosition;
    }

    /**
     * 设置TextView资源文本
     *
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setText(int viewId, int resId) {
        TextView textView = getView(viewId);
        textView.setText(resId);
        return this;
    }

    /**
     * 设置TextView文本
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    /**
     * 设置TextView文本(HTML)
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setHtmlText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(Html.fromHtml(text));
        return this;
    }

    /**
     * 设置ImageView资源图片
     *
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setImage(int viewId, int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    /**
     * 设置图片（配合xUtils框架）
     *
     * @param viewId
     * @param imageUrl
     * @return
     */
    public ViewHolder setImage(int viewId, String imageUrl) {
        ImageView imageView = getView(viewId);
        if (imageUrl.contains("default") || imageUrl.contains("null")) {
            imageView.setImageResource(R.drawable.default_img);
        } else {
            ImageOptions imageOptions = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setLoadingDrawableId(R.drawable.default_img)
                    .setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setFailureDrawableId(R.drawable.default_img)
                    .build();
            x.image().bind(imageView, imageUrl, imageOptions);
        }
        return this;
    }
}
