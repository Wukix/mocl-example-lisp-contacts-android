package com.example.lispcontacts;

import mocl.CL;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.support.v4.app.NavUtils;

public class ViewActivity extends Activity {
	public static ViewActivity instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		instance = this;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		// Show the Up button in the action bar.
		setupActionBar();
		
		View layout = this.findViewById(R.layout.activity_view);
		layout.setBackgroundColor(Color.WHITE);

		refresh();
	}
	
	public void refresh() {
		ImageView view = (ImageView)findViewById(R.id.imgDetailView);
		String filename = CL.draw_contact_item();
		view.setImageBitmap(BitmapFactory.decodeFile(filename));
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onEditTap(MenuItem item) {
		startEditActivity();
	}
	
	public void startEditActivity() {
		this.startActivity(new Intent(this, EditActivity.class));
	}
}
