package com.example.lispcontacts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import mocl.CL;

public class MainActivity extends Activity {
	public static MainActivity instance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		instance = this;
		
		View layout = this.findViewById(R.id.mainLayout);
		layout.setBackgroundColor(Color.WHITE);
		
		CL.cl_init();
		try {
			String dataDir = getDataDir();
			CL.set_temp_dir(dataDir);
			CL.set_doc_dir(dataDir);
			CL.set_font_path(getAssetPath("DejaVuSans.ttf"));
			CL.load_contacts();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		CL.set_view_dimensions(metrics.widthPixels, metrics.heightPixels);
	
		ImageView view = (ImageView)findViewById(R.id.imgListView);
		view.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					boolean valid =  CL.handle_list_tap(event.getY());
					if (valid) startViewActivity();
					return false;
				}
				else
					return true;
			}
		});
		
		refresh();
	}
	
	public void refresh() {
		ImageView view = (ImageView)findViewById(R.id.imgListView);
		String filename = CL.draw_contact_list();
		view.setImageBitmap(BitmapFactory.decodeFile(filename));
	}
	
	public void startEditActivity() {
		this.startActivity(new Intent(this, EditActivity.class));
	}
	
	public void startViewActivity() {
		this.startActivity(new Intent(this, ViewActivity.class));
	}
	
	public void onPlusTap(MenuItem item) {
		CL.add_new_contact();
		startEditActivity();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public String getDataDir() throws Exception 
    { 
        return getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0).applicationInfo.dataDir;
    }
	
	public String getAssetPath(String assetFileName) throws Exception
	{
		String newFilePath = getDataDir() + "/" + assetFileName;
		File file = new File(newFilePath);
		if (!(file.exists())) {
			InputStream stream = getAssets().open(assetFileName);
			FileOutputStream f = new FileOutputStream(file);
			byte[] buf = new byte[4096];
			int count = stream.read(buf);
			while (count >= 0) {
				f.write(buf, 0, count);
				count = stream.read(buf);
			}
			f.close();
		}
		return newFilePath;
	}
}
