package com.love_cookies.vote.View.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;

import com.love_cookies.vote.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.Alpha;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Side;
import su.levenetc.android.textsurface.contants.TYPE;

/**
 * Created by xiekun on 2016/01/10 0010.
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

	@ViewInject(R.id.text_surface)
	TextSurface textSurface;

	private final int SPLASH_DISPLAY_LENGHT = 2000;
	Looper looper = Looper.myLooper();
	private Handler handler = new Handler(looper);

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		handler.postDelayed(runnable, SPLASH_DISPLAY_LENGHT);
	}

	public void initView() {
		Text textOne = TextBuilder
				.create("投")
				.setSize(100)
				.setAlpha(0)
				.setColor(Color.WHITE)
				.setPosition(Align.SURFACE_CENTER)
				.build();

		Text textTwo = TextBuilder
				.create("你所爱")
				.setSize(30)
				.setAlpha(100)
				.setColor(Color.WHITE)
				.setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textOne)
				.build();

		textSurface.play(
				TYPE.SEQUENTIAL,
				Alpha.show(textOne, 700),
				Slide.showFrom(Side.TOP, textTwo, 1000)

		);

	}

	@Override
	public void widgetClick(View v) {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			handler.removeCallbacks(runnable);
		}
		return super.onKeyDown(keyCode, event);
	}
}
