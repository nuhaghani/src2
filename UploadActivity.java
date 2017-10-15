package com.example.theone;


import com.example.theone.AndroidMultiPartEntity.ProgressListener;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.ImageReader.OnImageAvailableListener;
import android.net.wifi.p2p.WifiP2pManager.UpnpServiceResponseListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ArrayAdapter;
import android.view.Menu; 
import android.view.MenuItem;

public class UploadActivity extends Activity implements OnItemSelectedListener {
	
	
String item9[] = {"Good","Major","Minor","Critical"};
	
	
String PoDitemISP1[] = {"ABD","Cable Feeder (ISP)", "ODF", "ODP/FAT","ODC Passive", "Manhole","Joint Closure","Pole"};
String PoDitemOSP1[] = {"ABD", "Manhole", "Joint Closure", "ODF", "Cable Feeder (OSP)","Pole","ODC Passive"};
String PoDitemCO1[] = {"ABD", "CO Room", "OLT System", "Power System"};
String PoDitemODC1[] = {"ABD", "ODC Supporting Facilities", "OLT System", "Power System", "ODF"};
String PoDitemISPCA1[] = {"ABD", "Mini Pit", "YC Connector", "Access Pole", "Vertical cable duct", "Horizontal cable duct", "Handhole Ceiling"};
String PoDitemHC1[] = {"ABD", "Indoor DW cable duct", "Outdoor DW cable duct", "DW", "Wall Outlet"};
String PoDitemISP2[] = {"Segment ODF to ODP"};
String PoDitemOSP2[] = {"Segment OSP"};
String PoDitemCO2[] = {"CO Room", "OLT System", "Power System"};
String PoDitemODC2[] = {"ODC Supporting Facilities", "OLT System", "Power System"};
String PoDitemISPCA2[] = {"Segment ODP to Wall Outlet"};
String PoDitemHC2[] = {"Segment ODF to Wall Outlet","Segment ODP to Wall Outlet"};
String PoDST[] = {"ONT Configuration","Bandwidth","Line"};

String ClassitemISP1[] = {"Design", "Material/Equipment Specification", "Installation", "Labels & Schematic", "Sealing", "Wiring", "Splicing & Jointing", "Lock System", "Reinstatement", "Site Cleaning" };
String ClassitemOSP1[] = {"Design", "Material/Equipment Specification", "Installation", "Labels & Schematic", "Sealing", "Wiring", "Splicing & Jointing", "Lock System", "Reinstatement", "Site Cleaning"};
String ClassitemCO1[] = {"Design", "Material/Equipment Specification", "Installation", "Labels & Schematic", "Sealing", "Wiring", "Splicing & Jointing", "Lock System", "Reinstatement", "Site Cleaning", "Air Conditining", "Grounding" };
String ClassitemODC1[] = {"Design", "Material/Equipment Specification", "Installation", "Labels & Schematic", "Sealing", "Wiring", "Lock System", "Reinstatement", "Site Cleaning",  "Grounding", "Others" };
String ClassitemISPCA1[] = {"Design", "Material/Equipment Specification", "Installation", "Labels & Schematic", "Sealing", "Wiring", "Splicing & Jointing",  "Reinstatement", "Site Cleaning" };
String ClassitemHC1[] = {"Design", "Material/Equipment Specification", "Installation", "Labels & Schematic", "Sealing", "Wiring", "Splicing & Jointing",  "Reinstatement", "Site Cleaning"};
String ClassitemISP2[] = {"OPM Measurement Result", "OTDR Measurement Result"};
String ClassitemOSP2[] = {"OPM Measurement Result", "OTDR Measurement Result"};
String ClassitemCO2[] = {"Hardware Failure", "Function Failure"};
String ClassitemODC2[] = {"Hardware Failure", "Function Failure"};
String ClassitemISPCA2[] = {""};
String ClassitemHC2[] = {""};
String ClassitemST[] = {"WAN", "Tx/Rx", "Speedtest"};
	
	String lantaiISP[] = {"Floor", "ODP"};
	String lantaiOSP[] = {"MH", "Pole","ODF","OTB"};
	String lantaiCO[] = {"Floor"};
	String lantaiISPCA[] = {"ODP","YC", "MP", "Pole"};
	String lantaiHC[] = {"ODP", "Unit"};
	String lantaiODC[] = {"ODCA"};
	
	// LogCat tag
	private static final String TAG = MainActivity.class.getSimpleName();

	private ProgressBar progressBar;
	private String filePath = null;
	private TextView txtPercentage;
	private ImageView imgPreview;
	private VideoView vidPreview;
	private Button btnUpload,chgLantai;
	private RadioGroup rg,rg2;
	private RadioButton rb,rb2;
	private Spinner spinLantai,spin2,spin3,spin4,spin5;
	private String result,category,kelompok,type;
	private EditText etLantai;


	//private String result,category;
	long totalSize = 0;
	
	private SharedPreferences loginPrefs;
	//private String userName,password;
	//private final static String USERNAME_KEY="username";
	
	//private final static String SAVED_KEY="saved";
	private boolean isSaved=false;
	
	//EditText userName;
    DBController controller = new DBController(this);
 // GPSTracker class
 	GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync2);
		txtPercentage = (TextView) findViewById(R.id.txtPercentage);
		btnUpload = (Button) findViewById(R.id.btnUpload);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		imgPreview = (ImageView) findViewById(R.id.imgPreview);
		vidPreview = (VideoView) findViewById(R.id.videoPreview);
		etLantai = (EditText)findViewById(R.id.txtLantai);
		chgLantai = (Button)findViewById(R.id.button1);
		//etLatitude = (EditText)findViewById(R.id.latitude);
		//etLongitude = (EditText)findViewById(R.id.longitude);
		etLantai.setEnabled(false);
		//etLatitude.setEnabled(false);
		//etLongitude.setEnabled(false);
		spinLantai = (Spinner)findViewById(R.id.lantai);
		spinLantai.setEnabled(false);
		
		SharedPreferences tag = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String header = Config.settingNotification;
		//String floor = tag.getString("floor", "");
		type = tag.getString("type", "");
		kelompok = tag.getString("kelompok", "");
		
		//loginPrefs = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
		//String username = loginPrefs.getString("username", "");
		
		SharedPreferences historis = getSharedPreferences("mypref",0);
		String floor = historis.getString("lantai", "");
		Toast.makeText(getApplicationContext(), "SiteID: "+historis.getString("settingNotification",""), Toast.LENGTH_SHORT).show();
		TextView judul = (TextView)findViewById(R.id.tag);
		judul.setText(historis.getString("namaaja","")+"/"+floor);
		etLantai.setText(historis.getString("lantai", ""));
		//etLatitude.setText(historis.getString("latitude", ""));
		//etLongitude.setText(historis.getString("longitude", ""));
		
		/**rg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int selectedOption = rg.getCheckedRadioButtonId();
				rb = (RadioButton)findViewById(selectedOption);
				
			}
		});
		
		
		rg2 = (RadioGroup) findViewById(R.id.radioGroup2);
		//rb2 = (RadioButton)findViewById(R.id.radio2);
		
		rg2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int selectedOption = rg2.getCheckedRadioButtonId();
				rb2 = (RadioButton)findViewById(selectedOption);
				
			}
		});*/
		//group
		
		
		spin2 = (Spinner) findViewById(R.id.spinner2);
		spin3 = (Spinner) findViewById(R.id.spinner3);
		//spin2.setOnItemSelectedListener(this);
		
		if(kelompok.equals("CO") && (type.equals("Pre-ATP") || (type.equals("Progress")))){
			ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,PoDitemCO1);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			ArrayAdapter<String>adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ClassitemCO1);
			adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin2.setAdapter(adapter2);
			spin3.setAdapter(adapter3);
		}else if(kelompok.equals("ISP") && (type.equals("Pre-ATP") || (type.equals("Progress")))){
			ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,PoDitemISP1);
			ArrayAdapter<String>adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ClassitemISP1);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin2.setAdapter(adapter2);
			spin3.setAdapter(adapter3);
		}else if(kelompok.equals("OSP") && (type.equals("Pre-ATP")|| (type.equals("Progress")))){
			ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,PoDitemOSP1);
			ArrayAdapter<String>adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ClassitemOSP1);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin2.setAdapter(adapter2);
			spin3.setAdapter(adapter3);
		}else if(kelompok.equals("ODC Active") && (type.equals("Pre-ATP")|| (type.equals("Progress")))){
			ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,PoDitemODC1);
			ArrayAdapter<String>adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ClassitemODC1);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin2.setAdapter(adapter2);
			spin3.setAdapter(adapter3);
		}else if(kelompok.equals("ISP Customer Access") && (type.equals("Pre-ATP")|| (type.equals("Progress")))){
			ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,PoDitemISPCA1);
			ArrayAdapter<String>adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ClassitemISPCA1);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin2.setAdapter(adapter2);
			spin3.setAdapter(adapter3);
		}else if(kelompok.equals("Home Connect") && (type.equals("Pre-ATP")|| (type.equals("Progress")))){
			ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,PoDitemHC1);
			ArrayAdapter<String>adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ClassitemHC1);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin2.setAdapter(adapter2);
			spin3.setAdapter(adapter3);
		}else if(kelompok.equals("CO") && type.equals("ATP")){
			ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,PoDitemCO2);
			ArrayAdapter<String>adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ClassitemCO2);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin2.setAdapter(adapter2);
			spin3.setAdapter(adapter3);
		}else if(kelompok.equals("ISP") && type.equals("ATP")){
			ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,PoDitemISP2);
			ArrayAdapter<String>adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ClassitemISP2);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin2.setAdapter(adapter2);
			spin3.setAdapter(adapter3);
		}else if(kelompok.equals("OSP") && type.equals("ATP")){
			ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,PoDitemOSP2);
			ArrayAdapter<String>adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ClassitemOSP2);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin2.setAdapter(adapter2);
			spin3.setAdapter(adapter3);
		}else if(kelompok.equals("ODC Active") && type.equals("ATP")){
			ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,PoDitemODC2);
			ArrayAdapter<String>adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ClassitemODC2);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin2.setAdapter(adapter2);
			spin3.setAdapter(adapter3);
		}else if(kelompok.equals("ISP Customer Access") && type.equals("Service Test")){
			ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,PoDST);
			ArrayAdapter<String>adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ClassitemST);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin2.setAdapter(adapter2);
			spin3.setAdapter(adapter3);
		}
		else if(kelompok.equals("ISP Customer Access") && type.equals("ATP")){
			ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,PoDitemISPCA2);
			ArrayAdapter<String>adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ClassitemISPCA2);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin2.setAdapter(adapter2);
			spin3.setAdapter(adapter3);
		}else if(kelompok.equals("Home Connect") && type.equals("ATP")){
			ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,PoDitemHC2);
			ArrayAdapter<String>adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ClassitemHC2);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin2.setAdapter(adapter2);
			spin3.setAdapter(adapter3);
		}
		
		
		//defect detail
		/**Spinner spin3 = (Spinner) findViewById(R.id.spinner3);
		spin3.setOnItemSelectedListener(this);
		
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_dropdown_item,item8);
		
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spin3.setAdapter(adapter3);*/
		
		//Good || Major || Minor
		spin4 = (Spinner) findViewById(R.id.spinner4);
		spin4.setOnItemSelectedListener((OnItemSelectedListener)this);
		
		ArrayAdapter<String> adapter4 = new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_dropdown_item,item9);
		adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin4.setAdapter(adapter4);
		
		
		//ArrayAdapter<String> adapter5 = new ArrayAdapter<String>
		//(this,android.R.layout.simple_spinner_dropdown_item,item3);
		
		//adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//spin5.setAdapter(adapter5);

		// Changing action bar background color
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor(getResources().getString(
						R.color.action_bar))));

		// Receiving the data from previous activity
		Intent i = getIntent();

		// image or video path that is captured in previous activity
		filePath = i.getStringExtra("filePath");

		// boolean flag to identify the media type, image or video
		boolean isImage = i.getBooleanExtra("isImage", true);

		if (filePath != null) {
			// Displaying the image or video on the screen
			previewMedia(isImage);
		} else {
			Toast.makeText(getApplicationContext(),
					"Sorry, file path is missing!", Toast.LENGTH_LONG).show();
		}

		/**btnUpload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// uploading the file to server
				new UploadFileToServer().execute();
			}
		});*/

	}
	
	/**
	 * Inspection result
	 */
	

	/**
	 * Displaying captured image/video on the screen
	 * */
	private void previewMedia(boolean isImage) {
		// Checking whether captured media is image or video
		if (isImage) {
			imgPreview.setVisibility(View.VISIBLE);
			vidPreview.setVisibility(View.GONE);
			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();

			// down sizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 8;

			final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

			imgPreview.setImageBitmap(bitmap);
		} else {
			imgPreview.setVisibility(View.GONE);
			vidPreview.setVisibility(View.VISIBLE);
			vidPreview.setVideoPath(filePath);
			// start playing
			vidPreview.start();
		}
	}
	
	public void addNewUser(View view){
		SharedPreferences historis = getSharedPreferences("mypref",0);
		SharedPreferences.Editor editor = historis.edit();
		editor.putString("lantai", etLantai.getText().toString());
		//editor.putString("latitude", etLatitude.getText().toString());
		//editor.putString("longitude", etLongitude.getText().toString());
        editor.commit();
		new UploadFileToServer().execute();
	}
	
	
	/**
	 * load setting preference
	 */

	/**
	 * Uploading the file to server
	 * */
	private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			// setting progress bar to zero
			progressBar.setProgress(0);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			// Making progress bar visible
			progressBar.setVisibility(View.VISIBLE);

			// updating progress bar value
			progressBar.setProgress(progress[0]);

			// updating percentage value
			txtPercentage.setText(String.valueOf(progress[0]) + "%");
		}
		

		@Override
		protected String doInBackground(Void... params) {
			return uploadFile();
		}

		@SuppressWarnings("deprecation")
		private String uploadFile() {
			String responseString = null;

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);
			
			Calendar cal = Calendar.getInstance();
			Date currentTime = cal.getTime();
			DateFormat date = new SimpleDateFormat("HH:mm:ss a");
			DateFormat date2 = new SimpleDateFormat("yyyy-MM-dd");
			String waktu = date.format(currentTime);
			String tanggal = date2.format(currentTime);
			

			try {
				AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
						new ProgressListener() {

							@Override
							public void transferred(long num) {
								publishProgress((int) ((num / (float) totalSize) * 100));
							}
						});
				
				SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				loginPrefs = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
				SharedPreferences historis = getSharedPreferences("mypref",0);
				
				String siteid = historis.getString("settingSiteID", "");
				
				String inspector = loginPrefs.getString("username", "");
				
				String vendor = settings.getString("vendor", "");
				String subcon = settings.getString("subcon", "");
				
				String floor = historis.getString("lantai", "");
				String type = settings.getString("type", "");
				String sitemng = settings.getString("sitemng", "");
				String kelompok = settings.getString("kelompok", "");
				
				//String latitude = historis.getString("latitude", "");
				//String longitude = historis.getString("longitude", "");
				
				File sourceFile = new File(filePath);
				EditText e1 = (EditText)findViewById(R.id.etRemark);
				
				EditText e4 = (EditText)findViewById(R.id.rekomendasi);
				
				
				
				//Spinner spin = (Spinner) findViewById(R.id.spinner1);
				Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
				Spinner spin3 = (Spinner) findViewById(R.id.spinner3);
				Spinner spin4 = (Spinner) findViewById(R.id.spinner4);
				//Spinner spin5 = (Spinner) findViewById(R.id.spinner5);
				String remark = e1.getText().toString();
				//String point = e2.getText().toString();
				//String lokasi = e3.getText().toString();
				String rekomendasi = e4.getText().toString();
				//String tgl_open = e5.getText().toString();
				//String kelompok = spin.getSelectedItem().toString();
				String point = spin2.getSelectedItem().toString();
				String detail = spin3.getSelectedItem().toString();
				String category = spin4.getSelectedItem().toString();
				//String result = spin4.getSelectedItem().toString();
				
				//loadPreferences();

				// Adding file data to http body
				entity.addPart("image", new FileBody(sourceFile));
				entity.addPart("remark", new StringBody(remark, ContentType.TEXT_PLAIN));
				entity.addPart("point", new StringBody(point, ContentType.TEXT_PLAIN));
				//entity.addPart("lokasi", new StringBody(lokasi, ContentType.TEXT_PLAIN));
				entity.addPart("rekomendasi", new StringBody(rekomendasi, ContentType.TEXT_PLAIN));
				//entity.addPart("tgl_open", new StringBody(tgl_open, ContentType.TEXT_PLAIN));
				entity.addPart("kelompok", new StringBody(kelompok, ContentType.TEXT_PLAIN));
				entity.addPart("result", new StringBody(result, ContentType.TEXT_PLAIN));
				entity.addPart("category", new StringBody(category, ContentType.TEXT_PLAIN));
				entity.addPart("detail", new StringBody(detail, ContentType.TEXT_PLAIN));
				
				
				entity.addPart("siteid", new StringBody(siteid,ContentType.TEXT_PLAIN));
				entity.addPart("date_inspection", new StringBody(tanggal,ContentType.TEXT_PLAIN));
				entity.addPart("waktu", new StringBody(waktu, ContentType.TEXT_PLAIN));
				entity.addPart("inspector", new StringBody(inspector,ContentType.TEXT_PLAIN));
				entity.addPart("vendor", new StringBody(vendor,ContentType.TEXT_PLAIN));
				entity.addPart("subcon", new StringBody(subcon,ContentType.TEXT_PLAIN));
				//entity.addPart("tower", new StringBody(tower,ContentType.TEXT_PLAIN));
				entity.addPart("floor", new StringBody(floor,ContentType.TEXT_PLAIN));
				//entity.addPart("longitude", new StringBody(longitude,ContentType.TEXT_PLAIN));
				//entity.addPart("latitude", new StringBody(latitude,ContentType.TEXT_PLAIN));
				entity.addPart("type", new StringBody(type,ContentType.TEXT_PLAIN));
				entity.addPart("sitemng", new StringBody(sitemng,ContentType.TEXT_PLAIN));

				// Extra parameters if you want to pass to server
				

				totalSize = entity.getContentLength();
				httppost.setEntity(entity);

				// Making server call
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity r_entity = response.getEntity();

				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					// Server response
					responseString = EntityUtils.toString(r_entity);
				} else {
					responseString = "Error occurred! Http Status Code: "
							+ statusCode;
				}

			} catch (ClientProtocolException e) {
				responseString = e.toString();
			} catch (IOException e) {
				responseString = e.toString();
			}

			return responseString;

		}

		@Override
		protected void onPostExecute(String result) {
			Log.e(TAG, "Response from server: " + result);

			// showing the server response in an alert dialog
			showAlert(result);

			super.onPostExecute(result);
			Intent i = new Intent(UploadActivity.this,Awal.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
		}

	}

	/**
	 * Method to show alert dialog
	 * */
	private void showAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setTitle("Response from Servers")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// do nothing
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
if(parent.equals(spinLantai)){
			
			if(kelompok.equals("CO")){
				etLantai.setText(spinLantai.getSelectedItem().toString());
				etLantai.setSelection(etLantai.getText().length());
				
			}
			else if(kelompok.equals("ISP")){
				etLantai.setText(spinLantai.getSelectedItem().toString());
				etLantai.setSelection(etLantai.getText().length());
				
			}
			else if(kelompok.equals("OSP")){
				etLantai.setText(spinLantai.getSelectedItem().toString());
				etLantai.setSelection(etLantai.getText().length());
				
			}
			else if(kelompok.equals("ODC Active")){
				etLantai.setText(spinLantai.getSelectedItem().toString());
				etLantai.setSelection(etLantai.getText().length());
				
			}
			else if(kelompok.equals("ISP Customer Access")){
				etLantai.setText(spinLantai.getSelectedItem().toString());
				etLantai.setSelection(etLantai.getText().length());
				
			}else if(kelompok.equals("Home Connect")){
				etLantai.setText(spinLantai.getSelectedItem().toString());
				etLantai.setSelection(etLantai.getText().length());
				
			}
		}else
		if(parent.equals(spin4)){
			//spin5.setEnabled(true);
			if(spin4.getSelectedItem().equals("Good")){
				result = "QC Pass";
			}
			else if(spin4.getSelectedItem().equals("Major")){
				result = "QC Not Pass";
			}else if(spin4.getSelectedItem().equals("Minor")){
				result = "QC Not Pass";
			}
			else if(spin4.getSelectedItem().equals("Critical")){
				result = "QC Not Pass";
			}
		}
		
	}
	
	public void addLantai(View view){
    	spinLantai = (Spinner)findViewById(R.id.lantai);
		spinLantai.setEnabled(true);
		etLantai.setEnabled(true);
		
		spinLantai.setOnItemSelectedListener((OnItemSelectedListener)this);
		
		if(kelompok.equals("CO")){
			ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,lantaiCO);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinLantai.setAdapter(adapter);
		}else if(kelompok.equals("ISP")){
			ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,lantaiISP);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinLantai.setAdapter(adapter);
		}else if(kelompok.equals("OSP")){
			ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,lantaiOSP);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinLantai.setAdapter(adapter);
		}else if(kelompok.equals("ISP Customer Access")){
			ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,lantaiISPCA);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinLantai.setAdapter(adapter);
		}else if(kelompok.equals("Home Connect")){
			ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,lantaiHC);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinLantai.setAdapter(adapter);
		}else if(kelompok.equals("ODC Active")){
			ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,lantaiODC);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinLantai.setAdapter(adapter);
		}
    }
	
	/*public void getLocation(View view){
		gps = new GPSTracker(UploadActivity.this);
		
		//check if GPS enabled
		if(gps.canGetLocation()){
			etLatitude.setText(Double.toString(gps.getLatitude()));
			etLongitude.setText(Double.toString(gps.getLongitude()));
		}else{
			gps.showSettingsAlert();
		}
	}*/
	
	public void onBackPressed(){
    	Intent i = new Intent(UploadActivity.this,Awal.class);
    	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    	startActivity(i);
    }

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}