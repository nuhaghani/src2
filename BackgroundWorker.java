package com.example.theone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class BackgroundWorker extends AsyncTask<String, Void, String> {
	
	Context context;
	AlertDialog alertDialog;
	BackgroundWorker (Context ctx){
		context = ctx;
	}
	
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		
		String type = params[0];
		String login_url = "http://localhost/login_apk.php";
		if(type.equals("login")){
			try {
				String user = params[1];
				String pass = params[2];
				URL url = new URL(login_url);
				HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
				httpURLConnection.setRequestMethod("POST");
				httpURLConnection.setDoOutput(true);
				httpURLConnection.setDoInput(true);
				OutputStream outputStream = httpURLConnection.getOutputStream();
				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
				String post_data = URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")+"&"
						+URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
				bufferedWriter.write(post_data);
				bufferedWriter.flush();
				bufferedWriter.close();
				outputStream.close();
				InputStream inputStream = httpURLConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
				String result = "";
				String line = "";
				while((line = bufferedReader.readLine()) != null){
					result += line;
				}
				bufferedReader.close();
				inputStream.close();
				httpURLConnection.disconnect();
				return result;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	@Override
	protected void onPreExecute(){
		alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle("Login Status");
	}
	
	protected void onPostExecute(String result){
		
		if(result.equals("Login success!")){
			Intent i = new Intent(context, Awal.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			context.startActivity(i);
		}else{
			alertDialog.setMessage(result);
			alertDialog.show();
			Toast.makeText(context, result,Toast.LENGTH_SHORT).show();
		}
		
	}
	
	

	protected void onProgressUpdate(Void...values){
		super.onProgressUpdate(values);
	}

}
