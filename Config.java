package com.example.theone;

import android.widget.EditText;

public class Config {
	
	public static String role;
	// File upload url (replace the ip with your server address)
	public static final String FILE_UPLOAD_URL = "http://localhost/fileUpload2.php";
	public static final String FILE_UPLOAD_URL_OSP = "http://localhost/fileUpload2_osp.php";
	public static final String FILE_UPLOAD_URL2 = "http://localhost/bufferInspection/insertuser.php";
	public static final String URL_GET_ALL = "http://localhost/getOpenNC.php";
	public static final String URL_GET_Detail = "http://localhost/getDetailNC.php";
	public static final String URL_Update = "http://localhost/updateAndroid.php";
	public static final String URL_Update2 = "http://localhost/updateNC.php";
	public static final String URL_Delete = "http://localhost/DeleteNC.php";
	public static final String URL_Pass = "http://localhost/ViewPass.php?headere=";
	public static final String URL_Approve = "http://localhost/allApprove.php?inspector=";
	public static final String URL_Reject = "http://localhost/reject.php";
	public static final String URL_vReject = "http://localhost/vReject.php?inspector=";
	
	//Keys that will be used to send the request to php scripts
	public static final String KEY_ID = "id";
	public static final String KEY_path = "path";
	public static final String KEY_koreksi = "koreksi";
	public static final String KEY_tower = "tower";
	public static final String KEY_floor = "floor";
	public static final String KEY_header = "header";
	public static final String KEY_rekomendasi = "rekomendasi";
	public static final String KEY_noteRecti = "koreksi2";
	public static final String KEY_path2 = "path2";
	public static final String KEY_subcon = "vendor_recti";
	public static final String KEY_rj_note = "rj_note";
	public static final String KEY_closer = "closer";
	
	
	
	// Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    
    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_ID = "userId";
    public static final String TAG_path = "path";
    public static final String TAG_koreksi = "koreksi"; 
    public static final String TAG_tower = "tower";
    public static final String TAG_floor = "floor";
    public static final String TAG_header = "header";
    public static final String TAG_rekomendasi = "rekomendasi";
    public static final String TAG_noteRecti = "koreksi2";
    public static final String TAG_path2 = "path2";
    public static final String TAG_subcon = "vendor_recti";
    public static final String TAG_rj_note = "rj_note";
    public static final String TAG_sitename_cari = "sitename_cari";
    public static final String TAG_tower_cari = "tower_cari";
    public static final String TAG_alamat_cari = "alamat";
    public static final String TAG_kode = "kode";
    public static final String TAG_gabung = "gabung";
    public static final String TAG_namaaja = "sitename";
    
    //inspection ID to pass with intent
    public static final String Insp_ID = "id";
    public static final String gabung_ID = "kode";

    public static String settingSiteID = "NOT SET";
    public static String settingNotification = "NOT SET";
    public static String username;
    public static String yanginiloh;
    public static Boolean yanginiloh2;
    public static String penanda = "";
}