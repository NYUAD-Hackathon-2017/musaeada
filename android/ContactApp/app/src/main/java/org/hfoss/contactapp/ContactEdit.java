package org.hfoss.contactapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ContactEdit extends Activity{
	private EditText nameText,
					 addressText,
					 mobileText,
					 homeText;
	private Long rowId;
	
	protected void onPause(){
		super.onPause();
		setResult(RESULT_CANCELED);
		finish();
	}

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.contact_edit);
		nameText = (EditText) findViewById(R.id.textName);
		addressText = (EditText) findViewById(R.id.textAddress);
		mobileText  = (EditText) findViewById(R.id.textMobile);
		homeText 	= (EditText) findViewById(R.id.textHome);
		Button submitButton = (Button) findViewById(R.id.BtnSave);
		rowId = null;
		
		Bundle extras = getIntent().getExtras();
		
		if (extras != null){
			String name = extras.getString(DBHelper.KEY_NAME);
			String address = extras.getString(DBHelper.KEY_ADDRESS);
			String mobile = extras.getString(DBHelper.KEY_MOBILE);
			String home = extras.getString(DBHelper.KEY_HOME);
			rowId = extras.getLong(DBHelper.KEY_ROW_ID);
			 Log.e("what","result set");
			if (name !=null){
				nameText.setText(name);
			}
			if (address != null){
				addressText.setText(address);
			}
			if (mobile != null){
				mobileText.setText(mobile);
			}
			if (home != null){
				homeText.setText(home);
			}
			
		}
		submitButton.setOnClickListener(confirmButtonListener);
	}
	private OnClickListener confirmButtonListener = new OnClickListener(){
		public void onClick(View v){
			Bundle bundle = new Bundle();

            bundle.putString(DBHelper.KEY_NAME, nameText.getText().toString());
            bundle.putString(DBHelper.KEY_ADDRESS, addressText.getText().toString());
            bundle.putString(DBHelper.KEY_MOBILE, mobileText.getText().toString());
            bundle.putString(DBHelper.KEY_HOME, homeText.getText().toString());
            if (rowId != null) {
                bundle.putLong(DBHelper.KEY_ROW_ID, rowId);
            }
            setResult(RESULT_OK, null, bundle);
            Log.e("what","result set");
            finish();
		}
	};
}
