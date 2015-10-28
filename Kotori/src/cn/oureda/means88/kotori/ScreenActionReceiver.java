package cn.oureda.means88.kotori;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;

public class ScreenActionReceiver extends BroadcastReceiver {

	//private String TAG = "ScreenActionReceiver";
	private boolean isRegisterReceiver = false;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		if(!MainService.isImageEnable(context)) {
			return;
		}
		
		final Context fn_context = context;
		final Handler handler = new Handler();
		String action = intent.getAction();
		
		if(action.equals(Intent.ACTION_USER_PRESENT)) {
			Log.d("Broadcast", "Intent.ACTION_USER_PRESENT");
			new Thread() {
				
				@Override
				public void run() { 
					
					handler.post(new Runnable() {

						@Override
						public void run() {
							Intent intent = new Intent(fn_context, MainService.class);
							fn_context.startService(intent);
						}
						
					});
				}
			}.start();
			
		}
	}

	public void registerScreenActionReceiver(Context mContext) {
		if(!isRegisterReceiver) {
			isRegisterReceiver = true;
			IntentFilter filter = new IntentFilter();
			filter.addAction(Intent.ACTION_USER_PRESENT);
			mContext.registerReceiver(ScreenActionReceiver.this, filter);
		}
	}

	public void unRegisterScreenActionReceiver(Context mContext) {
		if(isRegisterReceiver) {
			isRegisterReceiver = false;
			mContext.unregisterReceiver(ScreenActionReceiver.this);
		}
	}

}
