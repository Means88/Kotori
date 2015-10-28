package cn.oureda.means88.kotori;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	CheckBox cb_enable = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        cb_enable = (CheckBox) findViewById(R.id.cb_enable);
        cb_enable.setChecked(MainService.isImageEnable(this));
        cb_enable.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean checked) {
				MainService.setImageEnable(MainActivity.this, checked); 
				if(checked) {
					Toast.makeText(MainActivity.this, "已启动", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "已关闭", Toast.LENGTH_SHORT).show();
				}
			}
		});

    }


}
