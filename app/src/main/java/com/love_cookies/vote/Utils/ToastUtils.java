package com.love_cookies.vote.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;

/**
 * Created by xiekun on 2016/01/10 0010.
 */
public class ToastUtils {

	public static final int DURATION = Toast.LENGTH_SHORT;

	public static final void show(Context context, int resId) {
		show(context, context.getResources().getString(resId));
	}

	public static final void show(Context context, String text) {
		if (TextUtils.isEmpty(text)) {
			return;
		}
//        SuperToast superToast = SuperToast.create(context, text, SuperToast.Duration.MEDIUM,
//                Style.getStyle(Style.GRAY, SuperToast.Animations.FADE));
//        superToast.show();
		Toast.makeText(context, text, DURATION).show();
	}

	public static void showSuper(Context context, String text) {
		// TODO SuperToast
		SuperToast.create(context, text, SuperToast.Duration.MEDIUM,
                Style.getStyle(Style.GREEN, SuperToast.Animations.FLYIN)).show();
	}

	public static void showSuper(Context context, int resId) {
		showSuper(context, context.getResources().getString(resId));
	}

}
