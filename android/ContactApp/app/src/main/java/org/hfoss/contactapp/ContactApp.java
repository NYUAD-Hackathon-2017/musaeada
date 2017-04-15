package org.hfoss.contactapp;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Menu.Item;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ContactApp extends ListActivity {
	private static final int CONTACT_CREATE = 0;
	private static final int CONTACT_EDIT = 1;

	//select the second one, Android view menu
	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;

	private DBHelper dbHelper;
	private Cursor c;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.contact_list);
		dbHelper = new DBHelper(this);
		//passes the application's context
		fillData();
	}

	private void fillData() {
		c = dbHelper.fetchAllRows();
		startManagingCursor(c);
		ListAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.contact_row, c, new String[] { DBHelper.KEY_NAME,
						DBHelper.KEY_MOBILE }, new int[] { R.id.name,
						R.id.phonenumber });
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, R.string.menu_insert);
		menu.add(0, DELETE_ID, R.string.menu_delete);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, Item item) {
		super.onMenuItemSelected(featureId, item);
		switch (item.getId()) {
		case INSERT_ID:
			createContact();
			break;
		case DELETE_ID:
			dbHelper.deleteRow(c.getLong(c.getColumnIndex(DBHelper.KEY_ROW_ID)));
			fillData();
			break;
		}
		return true;
	}

	private void createContact() {
		Intent i = new Intent(this, ContactEdit.class);
		startSubActivity(i, CONTACT_CREATE);
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent i = new Intent(this, ContactEdit.class);
		i.putExtra(DBHelper.KEY_ROW_ID, c.getLong(c.getColumnIndex(DBHelper.KEY_ROW_ID)));
		i.putExtra(DBHelper.KEY_NAME, c.getString(c.getColumnIndex(DBHelper.KEY_NAME)));
		i.putExtra(DBHelper.KEY_ADDRESS, c.getString(c.getColumnIndex(DBHelper.KEY_ADDRESS)));
		i.putExtra(DBHelper.KEY_MOBILE, c.getString(c.getColumnIndex(DBHelper.KEY_MOBILE)));
		i.putExtra(DBHelper.KEY_HOME, c.getString(c.getColumnIndex(DBHelper.KEY_HOME)));
		startSubActivity(i, CONTACT_EDIT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			String data, Bundle extras) {
		super.onActivityResult(requestCode, resultCode, data, extras);
		if (resultCode == RESULT_OK) {
			String name = extras.getString(DBHelper.KEY_NAME);
			String address = extras.getString(DBHelper.KEY_ADDRESS);
			String mobile = extras.getString(DBHelper.KEY_MOBILE);
			String home = extras.getString(DBHelper.KEY_HOME);
			switch (requestCode) {
			case CONTACT_CREATE:
				dbHelper.createRow(name, address, mobile, home);
				fillData();
				break;
			case CONTACT_EDIT:
				Long rowId = extras.getLong(DBHelper.KEY_ROW_ID);
				if (rowId != null){
					dbHelper.updateRow(rowId, name, address, mobile, home);
				}
				fillData();
				break;
			}
		}
	}
}
