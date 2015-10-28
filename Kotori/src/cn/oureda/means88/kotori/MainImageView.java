package cn.oureda.means88.kotori;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import cn.oureda.means88.anime.AnimeFrame;
import cn.oureda.means88.anime.AnimeUtils;

public class MainImageView extends ImageView {
	
	public static AnimationDrawable showAnime = null;
	public static AnimationDrawable hideAnime = null;
	public static WindowManager.LayoutParams params = new WindowManager.LayoutParams(
			LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, // 没有边界
            PixelFormat.TRANSLUCENT);

	private WindowManager windowManager;
	private boolean isShow=false;

	public MainImageView(Context context, AnimeFrame[] showFrams) {
		super(context);
		this.setScaleType(ImageView.ScaleType.CENTER_CROP);
		windowManager=(WindowManager)getContext().
				getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		if(showAnime==null) {
			showAnime = new AnimationDrawable();
			AnimeUtils.addFrams(this, showAnime, showFrams);
			hideAnime = new AnimationDrawable();
			AnimeUtils.addReverseFrams(this, hideAnime, showFrams);
		}
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		hidePicture();
		return true;
	}

	private void updatePosition(float x, float y) {
		// View的当前位置
		params.x = (int) x;
		params.y = (int) y;
		windowManager.updateViewLayout(this, params);
	}
	
	private int getStatusBarHeight() {
		Class c = null;
		Object obj = null;
		Field field = null;
		int x = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			return getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
			return 75;
		}
	}
	
	void hidePicture() {
		if(!isShow) {
			isShow=true;
			hideAnime.stop();
			this.setBackgroundDrawable(hideAnime);
			hideAnime.start();
			
			final Handler handler=new Handler();
			new Thread(){
				
				@Override
				public void run() {
					try {
						Thread.sleep(1200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							setVisibility(INVISIBLE);
							isShow=false;
						}
					});
				}
			}.start();
		}
	}
	
	void showPicture() {
		Log.d("ImageView", "show: "+this.getId());
		if(!isShow) {
			isShow=true;
			setVisibility(VISIBLE);
			showAnime.stop();
			this.setBackgroundDrawable(showAnime);
			showAnime.start();
			
			final Handler handler=new Handler();
			new Thread(){
				
				@Override
				public void run() {
					try {
						Thread.sleep(800);
						isShow=false;
						Thread.sleep(2200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							hidePicture();
						}
					});
				}
			}.start();
		}
	}
}
