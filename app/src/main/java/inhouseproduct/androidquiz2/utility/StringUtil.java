package inhouseproduct.androidquiz2.utility;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;



import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import inhouseproduct.androidquiz2.R;


public class StringUtil {
	public static final int ALARM_SET_NOTIFICATION_ID = 1;
	public static final String PREFS_NAME = "eIqraPref";

	public static  int NOTIFICATION_ID=0;

	// Developer Log Strings
	public static final String LOG_MODE_ALL = "LOG_ALL"; // all on
	public static final String LOG_MODE_OFF = "LOG_OFF"; // all off
	public static final String LOG_MODE_MAJOR = "LOG_MAJOR";


	public static final String AudioFileUri="/data/data/com.e_iqra/";
	static Context context;

//	public static String prepareDir(Context context) {
//		String temp = Environment.getExternalStorageDirectory() + "/"
//				+ context.getResources().getString(R.string.external_dir) + "/";
//
//		return temp;
//	}

	public static void saveMap(Map<String,Boolean> inputMap, Activity activity){
		SharedPreferences pSharedPref = activity.getSharedPreferences("IqraSession", Context.MODE_PRIVATE);
		if (pSharedPref != null){
			JSONObject jsonObject = new JSONObject(inputMap);
			String jsonString = jsonObject.toString();
			SharedPreferences.Editor editor = pSharedPref.edit();
			editor.remove("My_map").commit();
			editor.putString("My_map", jsonString);
			editor.commit();
		}
	}

	public static HashMap<String,Boolean> loadMap(Activity activity){
		HashMap<String,Boolean> outputMap = new HashMap<String,Boolean>();
		SharedPreferences pSharedPref = activity.getSharedPreferences("IqraSession", Context.MODE_PRIVATE);
		try{
			if (pSharedPref != null){
				String jsonString = pSharedPref.getString("My_map", (new JSONObject()).toString());
				JSONObject jsonObject = new JSONObject(jsonString);
				Iterator<String> keysItr = jsonObject.keys();
				while(keysItr.hasNext()) {
					String key = keysItr.next();
					Boolean value = (Boolean) jsonObject.get(key);
					outputMap.put(key, value);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return outputMap;
	}

	public final static boolean isNull(String target) {
		if (target == null || target.equals("null") || target.equals("NULL")
				|| target.equals("Null") || target.equals("")) {
			return true;
		} else {
			return false;
		}
	}



	public static String getCurrentTimezoneOffset() {

		String offsetStr[];
		TimeZone tz = TimeZone.getDefault();
		Calendar cal = GregorianCalendar.getInstance(tz);
		int offsetInMillis = tz.getOffset(cal.getTimeInMillis());

		String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
		offset = (offsetInMillis >= 0 ? "+" : "-") + offset;
		offsetStr=offset.split(":");

		offset=offsetStr[0]+"."+offsetStr[1];
		return offset;
	}



	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}

	public static boolean validatePassword(final String password){
		 Pattern pattern = null;
		 Matcher matcher;

		String PASSWORD_PATTERN =
				"((?=.*[A-Za-z])(?=.*[#?!@$%^&*-]).{8,})";

		pattern = Pattern.compile(PASSWORD_PATTERN);
		matcher = pattern.matcher(password);
		return matcher.matches();

	}


	public static boolean validateMobileNo(final String mobileno){
		Pattern pattern = null;
		Matcher matcher;

		String MOBILENO_PATTERN =
				"^6?01\\d{9}$";

		pattern = Pattern.compile(MOBILENO_PATTERN);
		matcher = pattern.matcher(mobileno);
		return matcher.matches();

	}



	public static String convertStringToHex(String str) {

		char[] chars = str.toCharArray();

		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			hex.append(Integer.toHexString((int) chars[i]));
		}

		return hex.toString();
	}

	public static boolean isConnected(Context context) {
		boolean status = false;
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getNetworkInfo(0);
			if (netInfo != null
					&& netInfo.getState() == NetworkInfo.State.CONNECTED) {
				status = true;
			} else {
				netInfo = cm.getNetworkInfo(1);
				if (netInfo != null
						&& netInfo.getState() == NetworkInfo.State.CONNECTED)
					status = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return status;

	}


	public static String EncodeString(String ToEncode) {

		try {
			ToEncode = URLEncoder.encode(ToEncode, "utf-8");

		} catch (UnsupportedEncodingException e) {
			Log.e("EncodingException Erro",
					e.getMessage());
		}

		return ToEncode;
	}

	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			Log.e("e_iqra" +
					" Error", e.getMessage());
		}
		return "";
	}

	public static String md5Hashing(String str)
	{
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());

			byte byteData[] = md.digest();

			//convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			System.out.println("Digest(in hex format):: " + sb.toString());

			//convert the byte to hex format method 2
			StringBuffer hexString = new StringBuffer();
			for (int i=0;i<byteData.length;i++) {
				String hex= Integer.toHexString(0xff & byteData[i]);
				if(hex.length()==1) hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			Log.e("e_iqra" +
					" Error", e.getMessage());
		}
		return "";
	}


	public static String getFormatDateFacebook(String Date, String formatStyle) {
		String formatteddate = "";

		try {
			java.util.Date date = new SimpleDateFormat("dd/mm/yyyy").parse(Date);
			Log.e("DateFB", "" + date);
			DateFormat newFormat = new SimpleDateFormat(formatStyle);
			formatteddate = newFormat.format(date);

		} catch (ParseException e) {
			e.printStackTrace(); // To change body of catch statement use File |
			// Settings | File Templates.
		}

		return formatteddate;

	}

	public static String getFormatDate(String Date, String formatStyle) {
		String formatteddate = "";

		try {
			java.util.Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Date);
			DateFormat newFormat = new SimpleDateFormat(formatStyle);
			formatteddate = newFormat.format(date);

		} catch (ParseException e) {
			e.printStackTrace(); // To change body of catch statement use File |
			// Settings | File Templates.
		}

		return formatteddate;

	}



	public static String getNotificationFormatDate(String Date, String formatStyle) {
		String formatteddate = "";

		try {
			java.util.Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(Date);
			DateFormat newFormat = new SimpleDateFormat(formatStyle);
			formatteddate = newFormat.format(date);

		} catch (ParseException e) {
			e.printStackTrace(); // To change body of catch statement use File |
			// Settings | File Templates.
		}

		return formatteddate;

	}
	public static String getFormatDate1(String Date, String formatStyle) {
		String formatteddate = "";

		try {
			java.util.Date date = new SimpleDateFormat("MMM dd, yyyy").parse(Date);
			DateFormat newFormat = new SimpleDateFormat(formatStyle);
			formatteddate = newFormat.format(date);

		} catch (ParseException e) {
			e.printStackTrace(); // To change body of catch statement use File |
			// Settings | File Templates.
		}

		return formatteddate;

	}

	public static String getFormatDate2(String Date, String formatStyle) {
		String formatteddate = "";

		try {
			java.util.Date date = new SimpleDateFormat("yyyy-M-dd").parse(Date);
			DateFormat newFormat = new SimpleDateFormat(formatStyle);
			formatteddate = newFormat.format(date);

		} catch (ParseException e) {
			e.printStackTrace(); // To change body of catch statement use File |
			// Settings | File Templates.
		}

		return formatteddate;

	}

	public static String getFormatTime(String Date, String formatStyle) {
		String formatteddate = "";

		try {
			java.util.Date date = new SimpleDateFormat("HH:mm:ss").parse(Date);
			DateFormat newFormat = new SimpleDateFormat(formatStyle);
			formatteddate = newFormat.format(date);

		} catch (ParseException e) {
			e.printStackTrace(); // To change body of catch statement use File |
			// Settings | File Templates.
		}

		return formatteddate;

	}

	public static String getFormatDate3(String Date, String formatStyle) {
		String formatteddate = "";

		try {
			java.util.Date date = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss").parse(Date);
			DateFormat newFormat = new SimpleDateFormat(formatStyle);
			formatteddate = newFormat.format(date);

		} catch (ParseException e) {
			e.printStackTrace(); // To change body of catch statement use File |
			// Settings | File Templates.
		}

		return formatteddate;

	}

	public static String getAge(String dateOfBirth) {
		String age = "";

		// /For Age Calculation:
		Date nowDate = new Date();

		int yearDOB = Integer.parseInt(dateOfBirth.substring(0, 4));
		Log.e("birth year", String.valueOf(yearDOB));

		int NowYear = Calendar.getInstance().get(Calendar.YEAR);

		int new_age = 0;

		new_age = NowYear - yearDOB;
		Log.e("this year", String.valueOf(NowYear));

		age = String.valueOf(new_age);
		Log.e("new Age", age);

		return age;

	}

	public static String getTime(long diff) {

		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);

		String timeDiference = diffHours + ":" + diffMinutes + ":"
				+ diffSeconds + ":";

		return timeDiference;

	}

	public static void disableStrictMode() {
		try {
			Class<?> strictModeClass = Class.forName("android.os.StrictMode",
					true, Thread.currentThread().getContextClassLoader());
			Class<?> threadPolicyClass = Class.forName(
					"android.os.StrictMode$ThreadPolicy", true, Thread
							.currentThread().getContextClassLoader());
			Class<?> threadPolicyBuilderClass = Class.forName(
					"android.os.StrictMode$ThreadPolicy$Builder", true, Thread
							.currentThread().getContextClassLoader());

			Method setThreadPolicyMethod = strictModeClass.getMethod(
					"setThreadPolicy", threadPolicyClass);

			Method detectAllMethod = threadPolicyBuilderClass
					.getMethod("detectAll");
			Method penaltyMethod = threadPolicyBuilderClass
					.getMethod("penaltyLog");
			Method buildMethod = threadPolicyBuilderClass.getMethod("build");

			Constructor<?> threadPolicyBuilderConstructor = threadPolicyBuilderClass
					.getConstructor();
			Object threadPolicyBuilderObject = threadPolicyBuilderConstructor
					.newInstance();

			Object obj = detectAllMethod.invoke(threadPolicyBuilderObject);

			obj = penaltyMethod.invoke(obj);
			Object threadPolicyObject = buildMethod.invoke(obj);
			setThreadPolicyMethod.invoke(strictModeClass, threadPolicyObject);
		} catch (Exception ex) {
			Log.e("disableStrictMode", "" + ex);
		}
	}


}
