package cn.oureda.means88.kotori;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import cn.oureda.means88.anime.AnimeFrame;

public class MainService extends Service {

	public static final int IMG_NULL = -1;
	public static final int IMG_ENABLE = 0;
	public static final int IMG_DISABLE = 1;

	private static int imageEnable = IMG_NULL;
	private static AnimeFrame[] showFrams;
	
	static {
		showFrams = new AnimeFrame[] {
				new AnimeFrame(R.drawable.img_kotori_0, 80),
				new AnimeFrame(R.drawable.img_kotori_1, 80),
				new AnimeFrame(R.drawable.img_kotori_2, 200),
				new AnimeFrame(R.drawable.img_kotori_3, 80),
				new AnimeFrame(R.drawable.img_kotori_4, 120),
				new AnimeFrame(R.drawable.img_kotori_5, 160),
		};
	}
	
	private WindowManager windowManager;
	private MainImageView imageView;
	private ScreenActionReceiver screenReceiver = new ScreenActionReceiver();
	//private IntentFilter filter = new IntentFilter();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("Service", "onStartCommand");
		if(isImageEnable(this)) {
			imageView.showPicture();
		}
		return START_NOT_STICKY;
	}
	
	public static boolean isImageEnable(Context context) {
		if(imageEnable==IMG_NULL) {
			Log.d("Service", "getSP");
			SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
			imageEnable = sp.getInt("enable", IMG_DISABLE);
		}
		return imageEnable==IMG_ENABLE;
	}

	public static void setImageEnable(Context context, boolean imageEnable) {
		if(imageEnable) {
			MainService.imageEnable = IMG_ENABLE;
		} else {
			MainService.imageEnable = IMG_DISABLE;
		}
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("enable", MainService.imageEnable);
		editor.commit();
	}

	@Override
	public void onCreate() {
		Log.d("Service", "onCreate");
		initImage();
	}

	private void initImage() {
		windowManager = 
				(WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);
		WindowManager.LayoutParams params=MainImageView.params;
		params.type = LayoutParams.TYPE_SYSTEM_ALERT
				| LayoutParams.TYPE_SYSTEM_OVERLAY;
		params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		params.width = 300;
		params.height = 300;
		params.alpha = 1;
		params.gravity=Gravity.LEFT | Gravity.BOTTOM;
		params.x = 0;
		params.y = 0;
		
		imageView = new MainImageView(this, showFrams);
		windowManager.addView(imageView, params);
	}
	
	@Override
	public void onDestroy() {
		Log.d("Service", "onDestroy");
		WindowManager windowManager = (WindowManager) getApplicationContext()
				.getSystemService(WINDOW_SERVICE);

		if(imageView != null) {
			Log.d("Service", "removeView");
			windowManager.removeViewImmediate(imageView);
		}
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
