package com.example.hospital;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Database.Constants;
import com.example.Database.Database;
import com.example.Database.Patient;

public class PersonActivity extends ActionBarActivity {
	int position =0;
	TextView title = null;
	TextView back = null;
	Spinner sp_name = null;
	EditText et_age = null;
	EditText et_sex = null;
	EditText et_hosphouse = null;
	EditText et_hospbed = null;
	EditText et_startdate = null;
	EditText et_leftdate = null;
	EditText et_accname = null;
	EditText et_acctel = null;
	EditText et_masterdoctor = null;
	Button bn_ok = null;
	String name=null;
	int age=0;
	String sex = null;
	String hosphouse = null;
	String hospbed = null;
	String startdate = null;
	String leftdate = null;
	String accname = null;
	String acctel = null;
	String masterdoctor = null;
	Database database = null;
	Cursor c= null;
	SimpleCursorAdapter adapter=null;
	String choose=null;
	Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg) 
		{
			if(msg.what==0x123)
			{
				sp_name.setAdapter(adapter);
			}
			if(msg.what==0x111)
			{
				Toast.makeText(PersonActivity.this, "修改数据成功", Toast.LENGTH_LONG).show();
				finish();
			}
			if(msg.what==0x222)
			{
				update(choose);
			}
			if(msg.what==0x333)
			{
				updateUI();
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		sp_name = (Spinner)findViewById(R.id.name);
		et_age = (EditText)findViewById(R.id.age);
		et_sex = (EditText)findViewById(R.id.sex);
		et_hosphouse = (EditText)findViewById(R.id.hosphouse);
		et_hospbed = (EditText)findViewById(R.id.hospbed);
		et_startdate = (EditText)findViewById(R.id.startdate);
		et_leftdate = (EditText)findViewById(R.id.leftdate);
		et_accname = (EditText)findViewById(R.id.accname);
		et_acctel = (EditText)findViewById(R.id.acctel);
		et_masterdoctor = (EditText)findViewById(R.id.masterdoctor);
		bn_ok = (Button)findViewById(R.id.ok);
		title = (TextView)findViewById(R.id.tv_top_title);
		title.setText("个人资料登记");
		back = (TextView)findViewById(R.id.btn_title_back);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		new Thread()
		{
			public void run() 
			{
				SQLiteDatabase db = database.getReadableDatabase();
				c = db.rawQuery("select * from patient", null);
			    adapter = new SimpleCursorAdapter(PersonActivity.this,
						android.R.layout.simple_dropdown_item_1line,
						c,new String[]{ Patient.name },
						new int[]{ android.R.id.text1 });
			    handler.sendEmptyMessage(0x123);
			    
						
			};
		}.start();
		sp_name.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				position = arg2;
				choose = sp_name.getAdapter().getItem(arg2).toString(); 
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		bn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Thread()
				{
					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						age=Integer.parseInt(et_age.getText().toString());
						sex=et_sex.getText().toString();
						hosphouse=et_hosphouse.getText().toString();
						hospbed=et_hospbed.getText().toString();
						startdate=et_startdate.getText().toString();
						leftdate=et_leftdate.getText().toString();
						accname=et_accname.getText().toString();
						acctel=et_acctel.getText().toString();
						masterdoctor=et_masterdoctor.getText().toString();
						SQLiteDatabase db=database.getReadableDatabase();
						ContentValues values = new ContentValues();
						/**
					age = Integer.parseInt(c.getString(c.getColumnIndex(Patient.age)));
					sex = c.getString(c.getColumnIndex(Patient.sex));
					hosphouse = c.getString(c.getColumnIndex(Patient.ward));
					hospbed = c.getString(c.getColumnIndex(Patient.ward_no));
					startdate = c.getString(c.getColumnIndex(Patient.in_date));
					leftdate = c.getString(c.getColumnIndex(Patient.out_date));
					accname = c.getString(c.getColumnIndex(Patient.he_name));
					acctel = c.getString(c.getColumnIndex(Patient.he_tel));
					masterdoctor = c.getString(c.getColumnIndex(Patient.doctor));
						 */
						values.put(Patient.age, age);
						values.put(Patient.sex, sex);
						values.put(Patient.ward, hosphouse);
						values.put(Patient.ward_no, hospbed);
						values.put(Patient.in_date, startdate);
						values.put(Patient.out_date,leftdate);
						values.put(Patient.he_name, accname);
						values.put(Patient.he_tel, acctel);
						values.put(Patient.doctor, masterdoctor);
						db.update("patient", values, Patient.name+"=?",new String[]{sp_name.getAdapter().getItem(position).toString()});
						
					}
				}.start();
				
			}
		});
			
	}
	
	public void update(String choose)
	{
		final String se=choose;
		new Thread()
		{
			public void run() 
			{
				SQLiteDatabase db = database.getReadableDatabase();
				c = db.rawQuery("select * from patient where name=?", new String[]{se});
				if(c.getCount()>0)
				{
					age = Integer.parseInt(c.getString(c.getColumnIndex(Patient.age)));
					sex = c.getString(c.getColumnIndex(Patient.sex));
					hosphouse = c.getString(c.getColumnIndex(Patient.ward));
					hospbed = c.getString(c.getColumnIndex(Patient.ward_no));
					startdate = c.getString(c.getColumnIndex(Patient.in_date));
					leftdate = c.getString(c.getColumnIndex(Patient.out_date));
					accname = c.getString(c.getColumnIndex(Patient.he_name));
					acctel = c.getString(c.getColumnIndex(Patient.he_tel));
					masterdoctor = c.getString(c.getColumnIndex(Patient.doctor));
					handler.sendEmptyMessage(0x333);
				}
			};
		}.start();
	}
	/**
	 * String name=null;
		int age=0;
	String sex = null;
	String hosphouse = null;
	String hospbed = null;
	String startdate = null;
	String leftdate = null;
	String accname = null;
	String acctel = null;
	String masterdoctor = null;
	EditText et_age = null;
	EditText et_sex = null;
	EditText et_hosphouse = null;
	EditText et_hospbed = null;
	EditText et_startdate = null;
	EditText et_leftdate = null;
	EditText et_accname = null;
	EditText et_acctel = null;
	EditText et_masterdoctor = null;
	Button bn_ok = null;
	 * @param choose
	 */
	public void  updateUI()
	{
		et_age.setText(age);
		et_sex.setText(sex);
		et_hosphouse.setText(hosphouse);
		et_hospbed.setText(hospbed);
		et_startdate.setText(startdate);
		et_leftdate.setText(leftdate);
		et_accname.setText(accname);
		et_acctel.setText(acctel);
		et_masterdoctor.setText(masterdoctor);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.person, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
