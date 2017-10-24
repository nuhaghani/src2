package com.example.theone;

import com.example.theone.AndroidMultiPartEntity.ProgressListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Awal extends Activity {
	
	private SharedPreferences loginPrefs;
	private SharedPreferences.Editor loginEditor;
	private String userName,password,kelompok;
	private EditText et;
	
	// LogCat tag
	private static final String TAG = MainActivity.class.getSimpleName();
	
 
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    private static final int IMAGE_GALLERY_REQUEST_CODE = 300;
    
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int MEDIA_TYPE_GALLERY = 3;
    
    private ProgressBar progressBar;
    long totalSize = 0;
   
 
    private Uri fileUri; // file url to store image/video
    private String weleh,headere;
    
    private Button btnCapturePicture, btnRecordVideo,btnGallery,button1,button2,button3,button4,button5,button6;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //access to hompage interface
        
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean first = settings.getBoolean("first", false);
        
        /**if(settings.getBoolean("displayQ", true)){
        	Toast.makeText(getApplicationContext(),
                    "display is checked",
                    Toast.LENGTH_LONG).show();
        }else{
        	Toast.makeText(getApplicationContext(),
                    "display is un-checked",
                    Toast.LENGTH_LONG).show();
        }*/
        
        SharedPreferences loginPrefs = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String username = loginPrefs.getString("username", "");
        
        Toast.makeText(getApplicationContext(),
                "Welcome "+username+" ^_^",
                Toast.LENGTH_LONG).show();
        
        
        
        // Changing action bar background color
        // These two lines are not needed
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
 
        btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
        btnRecordVideo = (Button) findViewById(R.id.btnRecordVideo);
        btnGallery = (Button)findViewById(R.id.btnGallery);
        button1 = (Button) findViewById(R.id.button1);
        //button2 = (Button) findViewById(R.id.button2);
        //button3 = (Button)findViewById(R.id.button3);
        //button4 = (Button) findViewById(R.id.button4);
        //button5 = (Button)findViewById(R.id.button5);
        button6 = (Button)findViewById(R.id.button6);
        
        //button2.setEnabled(false);
        //button4.setEnabled(false);
        
        
        //SharedPreferences tag = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		//String siteid = tag.getString("siteid", "");
        
        
        
        
        
       
        
        
        
        button6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences historis = getSharedPreferences("mypref",0);
				Toast.makeText(getApplicationContext(), "SiteID: "+historis.getString("settingNotification",""), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(Awal.this,MainActivity2.class);
				startActivity(intent);
			}
        	
        });
        
 
        /**
         * Capture image button click event
         */
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // capture picture
                captureImage();
            }
        });
        
 
        /**
         * Record video button click event
         */
        btnRecordVideo.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // record video
                recordVideo();
            }
        });
        
        /**
         * Access Gallery click event
         */
        
        btnGallery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openGallery();
			}
		});
        
        /**
         * Pop Up preference page
         */
        button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences historis = getSharedPreferences("mypref",0);
				Toast.makeText(getApplicationContext(), "SiteID: "+historis.getString("settingNotification",""), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(Awal.this,Preferences.class);
				startActivity(intent);
			}
		});
 
        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }
    }
 
    /**
     * Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
 
    /**
     * Launching camera app to capture image
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
 
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
 
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
 
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    
    /**
     * Launching camera app to record video
     */
    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
 
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
 
        // set video quality
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
                                                            // name
 
        // start the video capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }
    
    /**
     * Launching gallery to choose media
     */
    private void openGallery(){
    	Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    	
    	fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
    	
    	intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
    	
    	// start open access gallery
    	startActivityForResult(intent,IMAGE_GALLERY_REQUEST_CODE);
    }
 
    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
 
        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }
 
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
 
        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }
 
    
 
    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                
            	// successfully captured the image
                // launching upload activity
            	launchUploadActivity(true);
            	
            	
            } else if (resultCode == RESULT_CANCELED) {
                
            	// user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        
        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                
            	// video successfully recorded
                // launching upload activity
            	launchUploadActivity(false);
            
            } else if (resultCode == RESULT_CANCELED) {
                
            	// user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();
            
            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == IMAGE_GALLERY_REQUEST_CODE) {
        	if (resultCode == RESULT_OK) {
        		// successfully captured the image
                // launching upload activity
        		Uri selectedImage = data.getData();
        		String[] filePathColumn = {MediaStore.Images.Media.DATA};
        		
        		Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        		cursor.moveToFirst();
        		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        		weleh = cursor.getString(columnIndex);
        		cursor.close();
        		
        		launchUploadActivity_gallery(true);
        	} else if (resultCode == RESULT_CANCELED) {
        		// user cancelled open gallery
        		Toast.makeText(getApplicationContext(),
        				"User cancelled opening gallery", Toast.LENGTH_SHORT)
        				.show();
        	} else {
        		// failed to open gallery
        		Toast.makeText(getApplicationContext(),
        				"Sorry! Failed to open gallery", Toast.LENGTH_SHORT)
        				.show();
        	}
        }
    }
    
    private void launchUploadActivity(boolean isImage){
    	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        kelompok = settings.getString("kelompok", "");
    	if ((kelompok.equals("OSP")) || kelompok.equals("landis")){
    		
    		Intent i = new Intent(Awal.this, UploadActivity2.class);
    		i.putExtra("filePath", fileUri.getPath());
    		i.putExtra("isImage", isImage);
    		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    		startActivity(i);
    	}else{
    		
    		Intent i = new Intent(Awal.this, UploadActivity.class);
            i.putExtra("filePath", fileUri.getPath());
            i.putExtra("isImage", isImage);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
    	}
    }
    
    private void launchUploadActivity_gallery(boolean isImage){
    	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
    	kelompok = settings.getString("kelompok", "");
    	if (kelompok.equals("OSP") || kelompok.equals("landis")){
    		
    		Intent i = new Intent(Awal.this, UploadActivity2.class);
    		i.putExtra("filePath", weleh);
    		i.putExtra("isImage", isImage);
    		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    		startActivity(i);
    	}else{
    		
    		Intent i = new Intent(Awal.this, UploadActivity.class);
        	i.putExtra("filePath", weleh);
        	i.putExtra("isImage", isImage);
        	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        	startActivity(i);
    	}
    }
     
    /**
     * ------------ Helper Methods ---------------------- 
     * */
 
    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
 
    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {
 
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Config.IMAGE_DIRECTORY_NAME);
 
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
 
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
 
        return mediaFile;
    }
    
    //@Override
    //public void onBackPressed(){
    //	moveTaskToBack(true);
    //	  android.os.Process.killProcess(android.os.Process.myPid());
    //	  System.exit(1);
    //}
    
    private Boolean exit = false;
    @Override
    public void onBackPressed(){
    	if(exit){
    		finish(); //finish activity
    	}else{
    		Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
    		exit = true;
    		new Handler().postDelayed(new Runnable(){
    			@Override
    			public void run(){
    				exit = false;
    			}
    		},3 * 1000);
    	}
    }
    
    //private void proses(){
    //	SharedPreferences tag = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	//	String headere = tag.getString("siteid", "");
	//	String type = "header";
	//	Attach backgroundWorker = new Attach(this);
	//	backgroundWorker.execute(type,headere);
    //}
}