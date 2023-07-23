package com.mohamed.barki.asl.lite;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import com.mohamed.barki.asl.lite.resactivity.ResActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings({"deprecation", "RedundantSuppression"})
public class Function extends Activity
{
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
	}
	public static boolean isNetworkConnected(Context getAppContext) {
		ConnectivityManager cm = (ConnectivityManager) getAppContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetwork() != null && cm.getNetworkCapabilities(cm.getActiveNetwork()) != null;
	}
	public static boolean isAdmin(Context context) {
		return Function.isPackageInstalled(context, context.getString(R.string.pkgadmin));
	}

	public static boolean isAdmin(Activity activity) {
		return Function.isPackageInstalled(activity, activity.getString(R.string.pkgadmin));
	}
	public static boolean isPackageInstalled(Context getAppContext, String packageName) {
		PackageManager packageManager = getAppContext.getPackageManager();
		try {
			packageManager.getPackageInfo(packageName, 0);
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}
	public static boolean validatePackageName(Context getAppContext){
		return (getAppContext.getPackageName().compareTo(Function.st+Function.stt+Function.sttt+Function.stttt)!= 0);
	}
	public static boolean validateApplicationName(Context getAppContext){
		return (!getApplicationName(getAppContext).equals(Function.sttttt));
	}
	@SuppressLint("SuspiciousIndentation")
	public static boolean validate(Context getAppContext, final  String text, final AutoCompleteTextView edt) {
        boolean valid = true;
		if (text.isEmpty()) {
            edt.setError(Html.fromHtml("<font color='red'>" +getAppContext.getString(R.string.error) +"</font>"));
			showError(getAppContext, "0");
			valid = false;
        } else {
            if (text.length()>100) {
                edt.setError(Html.fromHtml("<font color='red'>"+getAppContext.getString(R.string.error) +"</font>"));
				showError(getAppContext, "1");
				valid = false;
			} else {
				edt.setError(null);
			}
        }
        return valid;
    }
	public static void showError(Context getAppContext, String ss)
	{
		// TODO: Implement this method
		if (ss.equals("0")){
            Function.showToastMessage(getAppContext, getAppContext.getString(R.string.validate_empty));
        }
		if (ss.equals("1")){
            Function.showToastMessage(getAppContext, getAppContext.getString(R.string.validate_more));
        }
        if (ss.equals("2")){
            Function.showToastMessage(getAppContext, getAppContext.getString(R.string.validate_more_more));
        }
	}
    public static String st = "com.moh", stt = "amed.ba", sttt = "rki.a", stttt = "sl.lite", sttttt = "BarkiASL Lite";
	public static void showToastMessage(Context getAppContext, String text) {
		Toast.makeText(getAppContext, text, Toast.LENGTH_SHORT).show();
	}
	public static String getValue(Context getAppContext, String key) {
		SharedPreferences prefs = getAppContext.getSharedPreferences(getApplicationName(getAppContext), MODE_PRIVATE);
		return prefs.getString(key, "");
	}
	public static void saveFromText(Context getAppContext, String key, String text) {
		SharedPreferences prefs = getAppContext.getSharedPreferences(getApplicationName(getAppContext), MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, text).apply();
	}
	public static int getInt(Context getAppContext, String key) {
		return Integer.parseInt(getValue(getAppContext, key));
	}
	public static void saveInt(Context getAppContext, String key, int value) {
		saveFromText(getAppContext, key, String.valueOf(value));
	}
	public static boolean getBoolean(Context getAppContext, String key) {
		SharedPreferences prefs = getAppContext.getSharedPreferences(getApplicationName(getAppContext), MODE_PRIVATE);
		return prefs.getBoolean(key, false);
	}
	public static void saveFromBoolean(Context getAppContext, String key, boolean bool) {
		SharedPreferences prefs = getAppContext.getSharedPreferences(getApplicationName(getAppContext), MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(key, bool).apply();
	}
	//public static final int STORAGE_PERMISSION_CODE = 101;
	public static void takeScreenRecord(final Context getAppContext, String nameFile, String idFile) {
		Uri uri =  Uri.parse("https://drive.google.com/uc?export=download&id="+idFile);
		DownloadManager.Request request = new DownloadManager.Request(uri);
		request.setDescription(getAppContext.getString(R.string.slelected_video_is_being_downloaded));
		request.allowScanningByMediaScanner();
		request.setVisibleInDownloadsUi(false);
		request.setTitle(getAppContext.getString(R.string.downloading_video));
		request.setDestinationInExternalFilesDir(getAppContext, Environment.DIRECTORY_DOWNLOADS, nameFile); //To Store file in External Public Directory use "setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)"
		DownloadManager manager = (DownloadManager)getAppContext.getSystemService(Context.DOWNLOAD_SERVICE);
		manager.enqueue(request);
	}
	public static String getPathOfDownloadVideo(final Context getAppContext, String nameFile) {
		File rootDataDir = getAppContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
		if (!rootDataDir.exists()){
			return "nonVideo";
		}
		String path = rootDataDir.getPath()+File.separator+nameFile;
		File yourFile = new File(path);
		if (!yourFile.exists()){
			return "nonVideo";
		}
		return path;
	}
	public static void doCopy(Context getAppContext, String text)  {
		ClipboardManager clipboardManager;
		clipboardManager = (ClipboardManager) getAppContext.getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clipData = ClipData.newPlainText("text", text);
        clipboardManager.setPrimaryClip(clipData);
        if(getValue(getAppContext, "screen").equals("true")){
			showToastMessage(getAppContext, getAppContext.getString(R.string.copy_text_to_clip));
		}
    }
	public static void hideKeyboard(Context getAppContext, EditText edt) {
        // hide keyboard
        InputMethodManager inputManager = (InputMethodManager) getAppContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(edt.getWindowToken(), 0);
    }
	public static void showKeyboard(final Context getAppContext, final EditText edt) {
		edt.requestFocus();
		InputMethodManager imm = (InputMethodManager) getAppContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}
	public static String getApplicationName(Context getAppContext) {
        ApplicationInfo applicationInfo = getAppContext.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : getAppContext.getString(stringId);
    }
	public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
	public static int pxTodp(int px) {
		return (int) (px / Resources.getSystem().getDisplayMetrics().density);
	}
	/*public static void checkPermission(Activity getAppActivity, Context getAppContext)
	{
		// Checking if permission is not granted
		if (ContextCompat.checkSelfPermission(getAppContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(getAppActivity, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, STORAGE_PERMISSION_CODE);
		}
	}*/
	public static String PackageName, VersionName, LanguageName;
	public static int VersionCode;
	public static PermissionInfo[] Permissions;
	public static String[][] resList = ResActivity.resList();
	@SuppressLint("SuspiciousIndentation")
	public static void info(final Context getAppContext, final Activity myActivity) {
		MediaPlayer click = MediaPlayer.create(getAppContext, R.raw.click);
		final Dialog dialog = new Dialog(getAppContext, R.style.DialogStyle);
		dialog.setContentView(R.layout.dialog_information);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.findViewById(R.id.dialog_close).setOnClickListener(v -> {Function.startSongs(getAppContext, click); dialog.dismiss();});
		final LinearLayout lnyAll = dialog.findViewById(R.id.dialog_lny);
		final LinearLayout lnyAllBig = dialog.findViewById(R.id.dialog_lny_Big);
		String v3 = getAppContext.getString(R.string.page_about);
		String v4 = "<a href=\"fb://profile/3203834\">" + "Barki Mohamed"+"</a>"+"</p><p>"+"<a href=\"fb://profile/1049535328\">" +"محمد باركي"+"</a>";
		String v30 = getAppContext.getString(R.string.email_about);
		String v40 = "<font face=\"arial\" color=\"blue\">mohamedbarkimaths@gmail.com</font>";
		String v33 = getAppContext.getString(R.string.update_app);
		String v44 = "<a href=\"https://play.google.com/store/apps/details?id="+getAppContext.getPackageName()+"\">"+"Google Play</a>";
		String v333 = getAppContext.getString(R.string.get_app);
		String v444 = "<a href=\"https://play.google.com/store/apps/details?id="+getAppContext.getPackageName().replaceAll(".lite", "")+"\">"+"Google Play</a>";
		String v7 = "(Copyright 2023 M. Barki)";
		String value1 = "<html><p>"+v3+"</p><p>"+v4+"</p><p>"+v30+"</p><p>"+v40+"</p><p>"+v33+"</p><p>"+v44+"</p><p>"+v333+"</p><p>"+v444+"</p><p>"+v7+"</p></html>";
		((TextView) dialog.findViewById(R.id.dialog_info)).setText(Html.fromHtml(value1));
		((TextView) dialog.findViewById(R.id.dialog_info)).setMovementMethod(LinkMovementMethod.getInstance());
		PackageManager manager = getAppContext.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(getAppContext.getPackageName(), PackageManager.GET_ACTIVITIES);
			PackageName = info.packageName;
			VersionCode = info.versionCode;
			VersionName = info.versionName;
			Permissions = info.permissions;
			LanguageName = Locale.getDefault().getDisplayLanguage();
		} catch (PackageManager.NameNotFoundException ignored) {}
		String title;
		if(Function.getValue(getAppContext, "screen").equals("true")){
			title = getApplicationName(getAppContext)+/*" (Free) "+"["+String.valueOf(VersionCode)+"] "+*/" v"+VersionName;
		}else{
			title = getApplicationName(getAppContext)+/*" (Pro) "+"["+String.valueOf(VersionCode)+"] "+*/" {v"+VersionName+"}";
		}
		((TextView) dialog.findViewById(R.id.dialog_infoo)).setText(Html.fromHtml(title));
		String v1 =
			"</p><p>"+
			getAppContext.getString(R.string.title_about)
			+"</p><p>"+
			getAppContext.getString(R.string.text_about)
			+"</p>";
		TextView tv = new TextView(getAppContext);
		tv.setGravity(Gravity.START);
		tv.setTextIsSelectable(true);
		tv.setPadding(Function.dpToPx(10),Function.dpToPx(10),Function.dpToPx(10),Function.dpToPx(10));
		tv.setText(Html.fromHtml("<html>"+v1+"</html>"));
		tv.setMovementMethod(LinkMovementMethod.getInstance());
		tv.setTextColor(Color.WHITE);
		if(LanguageName.contains("rab") || LanguageName.contains("عربي")){
            tv.setGravity(Gravity.END);
            ((LinearLayout) dialog.findViewById(R.id.dialog_lny)).setGravity(Gravity.END);
            ((TextView) dialog.findViewById(R.id.dialog_info)).setGravity(Gravity.END);
			((TextView) dialog.findViewById(R.id.dialog_infoo)).setGravity(Gravity.END);
		}
		lnyAll.addView(tv);
		Function.saveFromText(getAppContext, "bolhelp", "true");
		Function.saveFromText(getAppContext, "bolref", "true");
		((ImageButton)dialog.findViewById(R.id.dialog_help)).setImageResource(R.drawable.ic_help);
		dialog.findViewById(R.id.dialog_help).setOnClickListener(v -> {
			Function.startSongs(getAppContext, click);
			boolean bol = Boolean.parseBoolean(Function.getValue(getAppContext, "bolhelp"));
			Function.saveFromText(getAppContext, "bolref", "true");
			((ImageButton)dialog.findViewById(R.id.dialog_ref)).setImageResource(R.drawable.ic_ref);
			if(bol){
				lnyAllBig.removeAllViews();
				final Button btn = new Button(getAppContext);
				btn.setContentDescription(getAppContext.getString(R.string.btn_info));
				btn.setGravity(Gravity.CENTER);
				btn.setBackgroundResource(R.drawable.dalil);
				Function.saveFromText(getAppContext, "boldalil", "true");
				btn.setOnClickListener(v2 -> {
					Function.startSongs(getAppContext, click);
					boolean bold = Boolean.parseBoolean(Function.getValue(getAppContext, "boldalil"));
					if(bold){
						btn.setBackgroundResource(R.drawable.help);
					}else{
						btn.setBackgroundResource(R.drawable.dalil);
					}
					Function.saveFromText(getAppContext, "boldalil", String.valueOf(!bold));
				});
				((TextView) dialog.findViewById(R.id.dialog_infoo)).setText("");
				((TextView) dialog.findViewById(R.id.dialog_info)).setText("");
				lnyAllBig.addView(btn);
				((ImageButton)dialog.findViewById(R.id.dialog_help)).setImageResource(R.drawable.ic_check);
			}else{
				dialog.dismiss();
				Function.info(getAppContext, myActivity);
			}
			Function.saveFromText(getAppContext, "bolhelp", String.valueOf(!bol));
		});
		dialog.findViewById(R.id.dialog_ref).setOnClickListener(v -> {
			Function.startSongs(getAppContext, click);
			boolean bol = Boolean.parseBoolean(Function.getValue(getAppContext, "bolref"));
			Function.saveFromText(getAppContext, "bolhelp", "true");
			((ImageButton)dialog.findViewById(R.id.dialog_help)).setImageResource(R.drawable.ic_help);
			if(bol){
				lnyAllBig.removeAllViews();
				final String v11 =
					"</p><p>"+
					getAppContext.getString(R.string.title_ref)
					+"</p><p>"+
					getAppContext.getString(R.string.text_ref_kamos)
					+"</p><p>"+
					getAppContext.getString(R.string.text_ref_kamos_video)
					+"</p><p>"+
					"<a href=\"https://www.facebook.com/Commentcasesigne\">" +
					"Comment ça se signe LSF"
					+"</a>"+"</p><p>"+
					"<a href=\"https://m.youtube.com/@signeslsf396\">" +
					"Signes LSF"
					+"</a>"+"</p>";
				TextView tv1 = new TextView(getAppContext);
				tv1.setGravity(Gravity.START);
				tv1.setTextIsSelectable(true);
				tv1.setPadding(Function.dpToPx(10),Function.dpToPx(10),Function.dpToPx(10),Function.dpToPx(10));
				tv1.setText(Html.fromHtml("<html>"+ v11 +"</html>"));
				tv1.setMovementMethod(LinkMovementMethod.getInstance());
				tv1.setTextColor(Color.WHITE);
				if(LanguageName.contains("rab") || LanguageName.contains("عربي")){
					tv1.setGravity(Gravity.END);
					((LinearLayout) dialog.findViewById(R.id.dialog_lny_Big)).setGravity(Gravity.END);
					((TextView) dialog.findViewById(R.id.dialog_info)).setGravity(Gravity.END);
					((TextView) dialog.findViewById(R.id.dialog_infoo)).setGravity(Gravity.END);
				}
				((TextView) dialog.findViewById(R.id.dialog_infoo)).setText("");
				((TextView) dialog.findViewById(R.id.dialog_info)).setText("");
				lnyAllBig.addView(tv1);
				((ImageButton)dialog.findViewById(R.id.dialog_ref)).setImageResource(R.drawable.ic_check);
			}else{
				dialog.dismiss();
				Function.info(getAppContext, myActivity);
			}
			Function.saveFromText(getAppContext, "bolref", String.valueOf(!bol));
		});
		dialog.show();
	}
	public static String serchFolder(String filename)
	{
		if(filename.endsWith("s")){filename = filename.substring(0, filename.length()-1);}
		String[] nameFoler = new String[]{"",
			"01Couleurs",
			"02Comportements",
			"03Objets",
			"04Terminologie",
			"05Wilayas",
			"06Formes",
			"07Sports",
			"08Festivites",
			"09Engins",
			"10Produits",
			"11Animaux",
			"12Structures",
			"13Geographique",
			"14Espace",
			"15Fonctions",
			"16Administration",
			"17Justice",
			"18Sante",
			"19Communication",
			"20Quantite",
			"21Verbes",
			"22Education",
			"23Religion",
			"24Temps",
			"25Habillement",
			"26Maison",
			"27Famille",
			"28Corps",
			"29Chiffres",
			"30Dactylologie",
			"31Autre",
		};
		String alpha = "abcdefghijklmnopqrstuvwxyz";
		if(filename.startsWith("zu")){return nameFoler[27];}
		if(filename.startsWith("zv")){return nameFoler[28];}
		if(filename.startsWith("zw")){return nameFoler[29];}
		if(filename.startsWith("zx")){return nameFoler[31];}
		if(filename.startsWith("0")){return nameFoler[30];}
		int i = 0;
		while(i<26){
			if(alpha.substring(i, i+1).equals(filename.substring(0, 1))){
				break;
			}else{i++;}
		}
		return nameFoler[i+1];
	}
	public static void startSongs(Context getAppContext, MediaPlayer songs)
	{
		if(Function.getBoolean(getAppContext, "song")){
			try {
				songs.prepare();
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			songs.seekTo(0);
			songs.start();
		}
	}
	public static void stopSongs(Context getAppContext, MediaPlayer songs)
	{
		if(Function.getBoolean(getAppContext, "song")){
			songs.pause();
			try {
				songs.prepare();
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			songs.seekTo(0);
		}
	}
	@SuppressWarnings("rawtypes")
	public static void startActivityFun(Context getAppContext, final Class ActivityClass) {
		getAppContext.startActivity(new Intent(getAppContext, ActivityClass));
	}
	public static String toNoPalWordAr(String original)
	{
		if (original.startsWith("ال") && original.length()>3){
			original = original.substring(2);
		}
		if (original.contains(" ال") && original.length()>3){
			original = original.replaceAll(" ال", " ");
		}
		return original;
	}
	public static String toNoPalWord(String original)
	{
		original = original.replaceAll("آ", "ا");
		original = original.replaceAll("أ", "ا");
		original = original.replaceAll("إ", "ا");
		original = original.replaceAll("é", "e");
		original = original.replaceAll("è", "e");
		original = original.replaceAll("ê", "e");
		original = original.replaceAll("ë", "e");
		original = original.replaceAll("à", "a");
		original = original.replaceAll("â", "a");
		original = original.replaceAll("î", "i");
		original = original.replaceAll("ï", "i");
		original = original.replaceAll("ô", "o");
		original = original.replaceAll("ö", "o");
		original = original.replaceAll("ù", "u");
		original = original.replaceAll("û", "u");
		original = original.replaceAll("œ", "oe");
		original = original.replaceAll("æ", "ae");
		original = original.replaceAll("ç", "c");
		return original;
	}
	//spinner
	public static String toNoPalArrayAr(String original)
	{
		original = original.replaceAll("آ", "ا");
		original = original.replaceAll("أ", "ا");
		original = original.replaceAll("إ", "ا");
		original = original.replaceAll("َ", "");
		original = original.replaceAll("ُ", "");
		original = original.replaceAll("ِ", "");
		original = original.replaceAll("ً", "");
		original = original.replaceAll("ٌ", "");
		original = original.replaceAll("ٍ", "");
		original = original.replaceAll("ّ", "");
		original = original.replaceAll("ْ", "");
		original = original.replaceAll("،", "");
		return original;
	}
	public static String toNoPalArrayArr(String original)
	{
		if (original.startsWith("ال") && original.length()>3){
			original = original.substring(2);
		}
		if (original.contains(" ال") && original.length()>3){
			original = original.replaceAll(" ال", " ");
		}
		original = original.replaceAll("آ", "ا");
		original = original.replaceAll("أ", "ا");
		original = original.replaceAll("إ", "ا");
		original = original.replaceAll("َ", "");
		original = original.replaceAll("ُ", "");
		original = original.replaceAll("ِ", "");
		original = original.replaceAll("ً", "");
		original = original.replaceAll("ٌ", "");
		original = original.replaceAll("ٍ", "");
		original = original.replaceAll("ّ", "");
		original = original.replaceAll("ْ", "");
		original = original.replaceAll("،", "");
		return original;
	}
	public static String toNoPalArrayFr(String original)
	{
		original = original.replaceAll("é", "e");
		original = original.replaceAll("è", "e");
		original = original.replaceAll("ê", "e");
		original = original.replaceAll("ë", "e");
		original = original.replaceAll("à", "a");
		original = original.replaceAll("â", "a");
		original = original.replaceAll("î", "i");
		original = original.replaceAll("ï", "i");
		original = original.replaceAll("ô", "o");
		original = original.replaceAll("ö", "o");
		original = original.replaceAll("ù", "u");
		original = original.replaceAll("û", "u");
		original = original.replaceAll("œ", "oe");
		original = original.replaceAll("æ", "ae");
		original = original.replaceAll("ç", "c");
		original = original.replaceAll(",", "");
		return original;
	}
	public static void trimCache(Context getAppContext) {
		try {
			File dir = getAppContext.getCacheDir();
			if (dir != null && dir.isDirectory()) {
				deleteDir(dir);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			assert children != null;
			for (String child : children) {
				boolean success = deleteDir(new File(dir, child));
				if (!success) {
					return false;
				}
			}
		}
		assert dir != null;
		return dir.delete();
	}
	public static void setThemeDark(Activity getActivity, int layout){
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
		getActivity.setContentView(layout);
	}
	public static void setThemeLight(Activity getActivity, int layout){
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		getActivity.setContentView(layout);
	}
	public static String setTime() {
		String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
		String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

		return  currentDate.replaceAll("-", "")+currentTime.replaceAll(":", "");
	}
}
