package com.example.theone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.RestrictionsManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Main_Login extends Activity {
	
	
	//LogCat tag
	private static final String TAG = Main_Login.class.getSimpleName();

	private String username,password;
	private Button btnLogin;
	private EditText etUserName,etPassword;
	private CheckBox cbRememberMe;
	private SharedPreferences loginPrefs,loginPrefs2;
	private SharedPreferences.Editor loginEditor,loginEditor2;
	private boolean isSaved=false;
	
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.login); //access to login interface
		 
		 etUserName = (EditText)findViewById(R.id.etInspector);
		 etPassword = (EditText)findViewById(R.id.etPassword);
		 cbRememberMe = (CheckBox)findViewById(R.id.checkBox1);
		 
		 loginPrefs=getSharedPreferences("loginPrefs",Context.MODE_PRIVATE);
		 loginPrefs2=getSharedPreferences("loginPrefs",Context.MODE_PRIVATE);
		 loginEditor = loginPrefs.edit();
		 loginEditor2 = loginPrefs2.edit();
		 isSaved = loginPrefs.getBoolean("isSaved", false);
		 if(isSaved == true){
			 etUserName.setText(loginPrefs.getString("username", ""));
			 etPassword.setText(loginPrefs.getString("password", ""));
			 cbRememberMe.setChecked(true);
		 }
		 
		  
		 btnLogin = (Button)findViewById(R.id.btnLogin);
		 
		
		 btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				login();
			}
		});
	 
	 }
	
	
	private void login(){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(etUserName.getWindowToken(), 0);
		
		username = etUserName.getText().toString();
		password = etPassword.getText().toString();
		
		if(cbRememberMe.isChecked()){
			loginEditor2.putBoolean("isSaved", true);
			loginEditor.putString("username", username);
			loginEditor.putString("password", password);
			loginEditor2.putString("username", username);
			loginEditor2.putString("password", password);
			loginEditor.commit();
			loginEditor2.commit();
		}else{
			loginEditor2.clear();
			loginEditor2.commit();
		}
		
		loginEditor.putString("username", etUserName.getText().toString());
		loginEditor.putString("password", etPassword.getText().toString());
		loginEditor.apply();
	    Config.username = etUserName.getText().toString();
		 
		String type = "login";
		
		BackgroundWorker backgroundWorker = new BackgroundWorker(this);
		backgroundWorker.execute(type,username,password);
		
		
	}
	
	@Override
    public void onBackPressed(){
     		finish();
    }

	
	
}
