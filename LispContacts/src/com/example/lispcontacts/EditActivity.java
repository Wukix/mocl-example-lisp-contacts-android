package com.example.lispcontacts;

import mocl.CL;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends Activity {
	EditText txtName;
	EditText txtEmail;
	EditText txtMobile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		// Show the Up button in the action bar.
		setupActionBar();
		
		txtName = (EditText)findViewById(R.id.txtName);
		txtEmail = (EditText)findViewById(R.id.txtEmail);
		txtMobile = (EditText)findViewById(R.id.txtMobile);
		
		txtName.setText(CL.get_contact_name());
		txtEmail.setText(CL.get_contact_email());
		txtMobile.setText(CL.get_contact_mobile());
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
		getMenuInflater().inflate(R.menu.edit, menu);
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
			//NavUtils.navigateUpFromSameTask(this);
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onDeleteTap(View view) {
		CL.delete_contact();
		
		MainActivity.instance.refresh();
		
		NavUtils.navigateUpFromSameTask(this);
	}


	public void onDoneTap(MenuItem m) {
		CL.update_contact(txtName.getText(), txtMobile.getText(), txtEmail.getText());
		CL.save_contacts();
		
		if (!CL.is_new_contact()) 
			ViewActivity.instance.refresh();
		MainActivity.instance.refresh();
	
		this.finish();
	}
}
