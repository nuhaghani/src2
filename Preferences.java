package com.example.theone;

import java.util.Calendar;

//import com.example.calendar.MainActivity;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.Dialog;

//import com.example.theone.AndroidMultiPartEntity.ProgressListener;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class Preferences extends PreferenceActivity { //implements DatePickerDialog.OnDateSetListener{
	//int year_x,month_x,day_x;
	//static final int DILOG_ID = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		Preference searchID = (Preference) findPreference("siteid");
		searchID.setOnPreferenceClickListener (new OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Preferences.this,Search.class);
				startActivity(intent);
				return false;
			}
		});
		
		CheckBoxPreference check = (CheckBoxPreference)getPreferenceManager().findPreference("displayQ");
		check.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				// TODO Auto-generated method stub
				if(newValue instanceof Boolean){
					Boolean boolVal = (Boolean)newValue;
					SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean("checked", boolVal);
					editor.commit();
				}
				return true;
			}
		});	
		
	}
	
	
}
