package com.mohamed.barki.asl.lite;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mohamed.barki.asl.lite.resactivity.ResActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

@SuppressWarnings({"deprecation", "RedundantSuppression"})
public class Function extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	public static boolean isNetworkConnected(Context getAppContext) {
		ConnectivityManager cm = (ConnectivityManager) getAppContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
		return cm.getActiveNetwork() != null && cm.getNetworkCapabilities(cm.getActiveNetwork()) != null && activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	public static boolean isAdmin(Activity activity) {
		return Function.isPackageInstalled(activity, activity.getString(R.string.pkgadmin));
	}
	public static boolean isAdmin(Context context) {
		return Function.isPackageInstalled(context, context.getString(R.string.pkgadmin));
	}
	public static boolean isLiteFull(Context context) {
		return Function.isPackageInstalled(context, ((context.getPackageName().endsWith("e")) ? context.getPackageName().replace(".lite", "") : context.getPackageName()+".lite"));
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
	public static boolean validatePackageName(Context getAppContext) {
		return (getAppContext.getPackageName().compareTo(Function.st + Function.stt + Function.sttt + Function.stttt) != 0);
	}
	public static boolean validateApplicationName(Context getAppContext) {
		return (!getApplicationName(getAppContext).equals(Function.sttttt));
	}
	@SuppressLint("SuspiciousIndentation")
	public static boolean validate(Activity getActivity, final String text, final AutoCompleteTextView edt) {
		boolean valid = true;
		if (text.isEmpty()) {
			edt.setError(Html.fromHtml("<font color='red'>" + getActivity.getString(R.string.error) + "</font>"));
			showError(getActivity, "0");
			valid = false;
		} else {
			if (text.length() > 100) {
				edt.setError(Html.fromHtml("<font color='red'>" + getActivity.getString(R.string.error) + "</font>"));
				showError(getActivity, "1");
				valid = false;
			} else {
				edt.setError(null);
			}
		}
		return valid;
	}
	public static void showError(Activity getActivity, String ss) {
		Function.showToastMessage(getActivity, getActivity.getString(
				switch (ss){
					case "0"-> R.string.validate_empty;
					case "1"-> R.string.validate_more;
					default-> R.string.validate_more_more;
				}
		));
	}
	public static String st = "com.moh", stt = "amed.ba", sttt = "rki.a", stttt = "sl.lite", sttttt = "BarkiASL Lite";
	public static Toast toast;
	@SuppressLint({"InflateParams", "MissingInflatedId"})
	public static void showToastMessage(Context getContext, String message) {
		LayoutInflater inflater = (LayoutInflater) getContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.toast, null);
		TextView text = layout.findViewById(R.id.text);
		ImageView imageL = layout.findViewById(R.id.imageL);
		ImageView imageR = layout.findViewById(R.id.imageR);
		imageL.setImageResource(R.mipmap.roundicon);
		imageR.setImageResource(R.mipmap.roundicon);
		Typeface font = Typeface.createFromAsset(getContext.getAssets(),
				(Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ?
						"fonts/ArefRuqaa.ttf" : "fonts/Balton.ttf"
		);
		text.setTypeface(font);
		if(Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) {
			text.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
			imageR.setVisibility(View.VISIBLE);
		}else{
			imageL.setVisibility(View.VISIBLE);
		}
		text.setText(message);
		toast = new Toast(getContext);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
		toast = null;
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
	public static boolean getBoolean(Context getAppContext, String key) {
		SharedPreferences prefs = getAppContext.getSharedPreferences(getApplicationName(getAppContext), MODE_PRIVATE);
		return prefs.getBoolean(key, false);
	}
	public static void saveFromBoolean(Context getAppContext, String key, boolean bool) {
		SharedPreferences prefs = getAppContext.getSharedPreferences(getApplicationName(getAppContext), MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(key, bool).apply();
	}
	public static void doCopy(Context getContext, String text, String textShow) {
		if(textShow.isEmpty()) textShow = getContext.getString(R.string.copy_text_to_clip);
		ClipboardManager clipboardManager;
		clipboardManager = (ClipboardManager) getContext.getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clipData = ClipData.newPlainText("text", text);
		clipboardManager.setPrimaryClip(clipData);
		showToastMessage(getContext, textShow);
	}
	public static void doPast(Context getAppContext, EditText edt)  {
		ClipboardManager clipboard = (ClipboardManager) getAppContext.getSystemService(Context.CLIPBOARD_SERVICE);
		CharSequence textToPaste;
		try {
			textToPaste = Objects.requireNonNull(clipboard.getPrimaryClip()).getItemAt(0).getText();
		} catch (Exception e) {
			return;
		}
		edt.setText(textToPaste);
	}
	public static void hideKeyboard(Context getAppContext, EditText edt) {
		InputMethodManager inputManager = (InputMethodManager) getAppContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(edt.getWindowToken(), 0);
		Function.saveFromBoolean(getAppContext, "keyboard", false);
	}
	public static void showKeyboard(final Context getAppContext, final EditText edt) {
		edt.requestFocus();
		InputMethodManager imm = (InputMethodManager) getAppContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
		Function.saveFromBoolean(getAppContext, "keyboard", true);
	}
	public static boolean isSoftKeyboardShown(final Context getAppContext) {
		return Function.getBoolean(getAppContext, "keyboard");
	}
	public static String getApplicationName(Context getAppContext) {
		ApplicationInfo applicationInfo = getAppContext.getApplicationInfo();
		int stringId = applicationInfo.labelRes;
		return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : getAppContext.getString(stringId);
	}
	public static int dpToPx(int dp) {
		return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}
	public static String PackageName, VersionName, LanguageName;
	public static int VersionCode;
	public static PermissionInfo[] Permissions;
	public static String[][] resList = ResActivity.resList();
	@SuppressLint({"SuspiciousIndentation", "SetTextI18n"})
	public static void info(final Context getAppContext, final Activity myActivity) {
		MediaPlayer click = MediaPlayer.create(getAppContext, R.raw.click);
		final Dialog dialog = new Dialog(getAppContext, R.style.DialogStyle);
		dialog.setContentView(R.layout.dialog_information);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.findViewById(R.id.dialog_close).setOnClickListener(v -> {
			Function.startSongs(getAppContext, click);
			dialog.dismiss();
		});
		((TextView) dialog.findViewById(R.id.dialog_infox)).setText("(Copyright " + Calendar.getInstance().get(Calendar.YEAR) + " M. Barki)");
		PackageManager manager = getAppContext.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(getAppContext.getPackageName(), PackageManager.GET_ACTIVITIES);
			PackageName = info.packageName;
			VersionCode = info.versionCode;
			VersionName = info.versionName;
			Permissions = info.permissions;
			LanguageName = Locale.getDefault().getDisplayLanguage();
		} catch (PackageManager.NameNotFoundException ignored) {
		}
		Typeface facee = Typeface.createFromAsset(getAppContext.getAssets(), "fonts/casual.ttf");
		if (LanguageName.contains("rab") || LanguageName.contains("عربي")) {
			facee = Typeface.createFromAsset(getAppContext.getAssets(), "fonts/naskh.ttf");
		}
		((TextView) dialog.findViewById(R.id.dialog_info)).setTypeface(facee);
		String title = getApplicationName(getAppContext) +" v" + VersionName;
		((TextView) dialog.findViewById(R.id.dialog_infoo)).setText(Html.fromHtml(title));
		String v1 =
				"</p><p>" +
						getAppContext.getString(R.string.title_about)
						+ "</p><p>" +
						getAppContext.getString(R.string.text_about)
						+ "</p>";
		if (Function.maxRam(myActivity) >= 4) {
			if (Function.getValue(getAppContext, "numImage").isEmpty())
				Function.saveFromText(getAppContext, "numImage", numIllustrations(getAppContext));
			v1 = v1 +
					"<p>" +
					getAppContext.getString(R.string.contient) +
					" " + resList.length + " " +
					getAppContext.getString(R.string.sign)
					+ "</p><p>" +
					getAppContext.getString(R.string.contient) +
					" " + numVideo() + " " +
					getAppContext.getString(R.string.video)
					+ "</p>";
		}
		((TextView) dialog.findViewById(R.id.dialog_info)).setText(Html.fromHtml(v1));
		((TextView) dialog.findViewById(R.id.dialog_info)).setMovementMethod(LinkMovementMethod.getInstance());
		Typeface face = Typeface.createFromAsset(getAppContext.getAssets(), "fonts/casual.ttf");
		if (LanguageName.contains("rab") || LanguageName.contains("عربي")) {
			((TextView) dialog.findViewById(R.id.dialog_info)).setGravity(Gravity.RIGHT);
			((TextView) dialog.findViewById(R.id.dialog_info)).setGravity(Gravity.RIGHT);
			face = Typeface.createFromAsset(getAppContext.getAssets(), "fonts/naskh.ttf");
		}
		((TextView) dialog.findViewById(R.id.dialog_info)).setTypeface(face);
		Function.saveFromText(getAppContext, "bolhelp", "true");
		Function.saveFromText(getAppContext, "bolref", "true");
		((ImageButton) dialog.findViewById(R.id.dialog_help)).setImageResource(R.drawable.ic_help);
		dialog.findViewById(R.id.dialog_help).setOnClickListener(v -> {
			Function.startSongs(getAppContext, click);
			boolean bol = Boolean.parseBoolean(Function.getValue(getAppContext, "bolhelp"));
			Function.saveFromText(getAppContext, "bolref", "true");
			((ImageButton) dialog.findViewById(R.id.dialog_ref)).setImageResource(R.drawable.ic_ref);
			if (bol) {
				dialog.findViewById(R.id.dialog_info).setVisibility(TextView.GONE);
				dialog.findViewById(R.id.dialog_info_lny).setVisibility(TextView.GONE);
				dialog.findViewById(R.id.dialog_info_scrlv).setVisibility(TextView.GONE);
				final Button btn = new Button(getAppContext);
				btn.setContentDescription(getAppContext.getString(R.string.btn_info));
				btn.setGravity(Gravity.CENTER);
				btn.setBackgroundResource(R.drawable.dalil);
				Function.saveFromText(getAppContext, "boldalil", "true");
				btn.setOnClickListener(v2 -> {
					Function.startSongs(getAppContext, click);
					boolean bold = Boolean.parseBoolean(Function.getValue(getAppContext, "boldalil"));
					if (bold) {
						btn.setBackgroundResource(R.drawable.help);
					} else {
						btn.setBackgroundResource(R.drawable.dalil);
					}
					Function.saveFromText(getAppContext, "boldalil", String.valueOf(!bold));
				});
				((ImageButton) dialog.findViewById(R.id.dialog_help)).setImageResource(R.drawable.ic_check);
				((LinearLayout)dialog.findViewById(R.id.dialog_lny_Big)).addView(btn);
			} else {
				dialog.dismiss();
				Function.info(getAppContext, myActivity);
			}
			Function.saveFromText(getAppContext, "bolhelp", String.valueOf(!bol));
		});
		dialog.findViewById(R.id.dialog_ref).setOnClickListener(v -> {
			dialog.findViewById(R.id.dialog_info).setVisibility(TextView.VISIBLE);
			dialog.findViewById(R.id.dialog_info_lny).setVisibility(TextView.VISIBLE);
			dialog.findViewById(R.id.dialog_info_scrlv).setVisibility(TextView.VISIBLE);
			Function.startSongs(getAppContext, click);
			boolean bol = Boolean.parseBoolean(Function.getValue(getAppContext, "bolref"));
			Function.saveFromText(getAppContext, "bolhelp", "true");
			((ImageButton) dialog.findViewById(R.id.dialog_help)).setImageResource(R.drawable.ic_help);
			if (bol) {
				final String v11 =
						"</p><p>" +
								getAppContext.getString(R.string.title_ref)
								+ "</p><p>" +
								getAppContext.getString(R.string.text_ref_kamos)
								+ "</p><p>" +
								getAppContext.getString(R.string.text_ref_kamos_video)
								+ "</p><p>" +
								"<a href=\"https://www.facebook.com/Commentcasesigne\">" +
								"Comment ça se signe LSF"
								+ "</a>" + "</p><p>" +
								"<a href=\"https://m.youtube.com/@signeslsf396\">" +
								"Signes LSF"
								+ "</a>" + "</p>";
				((TextView) dialog.findViewById(R.id.dialog_info)).setText(Html.fromHtml("<html>" + v11 + "</html>"));
				((TextView) dialog.findViewById(R.id.dialog_info)).setMovementMethod(LinkMovementMethod.getInstance());
				Typeface faceee = Typeface.createFromAsset(getAppContext.getAssets(), "fonts/casual.ttf");
				if (LanguageName.contains("rab") || LanguageName.contains("عربي")) {
					((TextView) dialog.findViewById(R.id.dialog_info)).setGravity(Gravity.RIGHT);
					faceee = Typeface.createFromAsset(getAppContext.getAssets(), "fonts/naskh.ttf");
				}
				((TextView) dialog.findViewById(R.id.dialog_info)).setTypeface(faceee);
				((ImageButton) dialog.findViewById(R.id.dialog_ref)).setImageResource(R.drawable.ic_check);
			} else {
				dialog.dismiss();
				Function.info(getAppContext, myActivity);
			}
			Function.saveFromText(getAppContext, "bolref", String.valueOf(!bol));
		});
		dialog.show();
	}
	public static void share(final Context getAppContext, final Activity myActivity) {
		MediaPlayer click = MediaPlayer.create(getAppContext, R.raw.click);
		final Dialog dialog = new Dialog(getAppContext, R.style.DialogStyle);
		dialog.setContentView(R.layout.dialog_share);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.findViewById(R.id.dialog_close).setOnClickListener(v -> {
			Function.startSongs(getAppContext, click);
			dialog.dismiss();
		});
		int widthPX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getAppContext.getResources().getDisplayMetrics());
		int heightPX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getAppContext.getResources().getDisplayMetrics());
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(widthPX, heightPX);
		dialog.findViewById(R.id.dialog_lny).setLayoutParams(layoutParams);
		qr_code(getAppContext, dialog.findViewById(R.id.dialog_image));
		dialog.findViewById(R.id.dialog_button).setOnClickListener(v -> {
			Function.startSongs(getAppContext, click);
			Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getAppContext.getString(R.string.sharesubject));
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getAppContext.getString(R.string.app_url));
			myActivity.startActivity(Intent.createChooser(sharingIntent, getAppContext.getString(R.string.share)));
		});
		Typeface face = Typeface.createFromAsset(getAppContext.getAssets(), "fonts/casual.ttf");
		if (Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) {
			face = Typeface.createFromAsset(getAppContext.getAssets(), "fonts/naskh.ttf");
			((LinearLayout) dialog.findViewById(R.id.dialog_lny_Big)).setGravity(Gravity.RIGHT);
			((TextView) dialog.findViewById(R.id.dialog_share)).setGravity(Gravity.RIGHT);
		}
		((TextView) dialog.findViewById(R.id.dialog_share)).setTypeface(face);
		dialog.show();
	}
	/**
	 * @noinspection CallToPrintStackTrace
	 */
	public static void qr_code(final Context getAppContext, final ImageView img) {
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // H = 30% damage
		int size = 1024;
		QRCodeWriter writer = new QRCodeWriter();
		try {
			BitMatrix bitMatrix = writer.encode(getAppContext.getString(R.string.app_url), BarcodeFormat.QR_CODE, size, size, hintMap);
			int width = bitMatrix.getWidth();
			int height = bitMatrix.getHeight();
			Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
				}
			}
			img.setImageBitmap(bmp);
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
	private static String numVideo() {
		int nbr = 0;
		for (String[] strings : resList)
			if (!strings[1].equals("url"))
				nbr++;
		return String.valueOf(nbr);
	}
	public static String numIllustrations(Context getAppContext) {
		AssetManager assetManager = getAppContext.getAssets();
		InputStream inputStream = null;
		int nbr = 0;
		for (int j = 1; j < resList.length; j++) {
			try {
				inputStream = assetManager.open(serchFolder(resList[j][0]) + "/" + resList[j][0] + "s" + ".br");
				nbr++;
			} catch (IOException ignored) {
			}
		}
		if (null != inputStream) {
			try {
				inputStream.close();
			} catch (IOException ignored) {
			}
		}
		return String.valueOf(nbr);
	}
	public static Long maxRam(Activity myActivity) {
		ActivityManager activityManager = (ActivityManager) myActivity.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		activityManager.getMemoryInfo(mi);
		return Function.formatSize(mi.totalMem / 1048576L);
	}
	private static Long formatSize(Long size) {
		if (size >= 1024) {
			size = size / 1024;
			if (size >= 1024) {
				size = size / 1024;
			}
		}
		StringBuilder resultBuffer = new StringBuilder(Long.toString(size));
		int commaOffset = resultBuffer.length() - 3;
		while (commaOffset > 0) {
			resultBuffer.insert(commaOffset, ',');
			commaOffset -= 3;
		}
		return Long.parseLong(resultBuffer.toString());
	}
	public static String serchFolder(String filename) {
		if (filename.endsWith("s")) {
			filename = filename.substring(0, filename.length() - 1);
		}
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
		if (filename.startsWith("zu")) {
			return nameFoler[27];
		}
		if (filename.startsWith("zv")) {
			return nameFoler[28];
		}
		if (filename.startsWith("zw")) {
			return nameFoler[29];
		}
		if (filename.startsWith("zx")) {
			return nameFoler[31];
		}
		if (filename.startsWith("0")) {
			return nameFoler[30];
		}
		int i = 0;
		while (i < 26) {
			if (alpha.substring(i, i + 1).equals(filename.substring(0, 1))) {
				break;
			} else {
				i++;
			}
		}
		return nameFoler[i + 1];
	}
	/**
	 * @noinspection CallToPrintStackTrace
	 */
	public static void startSongs(Context getAppContext, MediaPlayer songs) {
		if (Function.getBoolean(getAppContext, "song")) {
			try {
				songs.prepare();
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			songs.seekTo(0);
			songs.start();
		}
	}
	/**
	 * @noinspection CallToPrintStackTrace
	 */
	public static void stopSongs(Context getAppContext, MediaPlayer songs) {
		if (Function.getBoolean(getAppContext, "song")) {
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
	public static String toNoPalWord(String original) {
		original = toNoPalArrayAr(original);
		original = toNoPalArrayFr(original);
		return original;
	}
	public static String toNoPalArrayAr(String original) {
		original = normalizeArabic(original);
		original = removeTashkeel(original);
		if (original.startsWith("ال") && original.length() > 3) {
			original = original.substring(2);
		}
		if (original.contains(" ال") && original.length() > 3) {
			original = original.replaceAll(" ال", " ");
		}
		return original;
	}
	public static String normalizeArabic(String original) {
		original = original.replaceAll("ؤ", "و");
		original = original.replaceAll("ئ", "ي");
		String pattern = "[ءأآإ]";
		String replacement = "ا";
		return Pattern.compile(pattern).matcher(original).replaceAll(replacement);
	}
	public static String removeTashkeel(String original) {
		String pattern = "[ؐ-ًؚ-ْ،,]";
		return original.replaceAll(pattern, "");
	}
	public static String toNoPalArrayFr(String original) {
		return Normalizer.normalize(original, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}
	/**
	 * @noinspection CallToPrintStackTrace
	 */
	public static void trimCache(Context getAppContext) {
		try {
			File dir = getAppContext.getCacheDir();
			if (dir != null && dir.isDirectory()) {
				deleteDir(dir);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	public static void setThemeDark(Activity getActivity, int layout) {
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
		getActivity.setContentView(layout);
	}
	public static void setThemeLight(Activity getActivity, int layout) {
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		getActivity.setContentView(layout);
	}
	public static String setTime() {
		String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
		String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
		return replaceArNumToFr(currentDate.replaceAll("-", "") + currentTime.replaceAll(":", ""));
	}
	private static String replaceArNumToFr(String s) {
		s = s.replaceAll("٠", "0");
		s = s.replaceAll("١", "1");
		s = s.replaceAll("٢", "2");
		s = s.replaceAll("٣", "3");
		s = s.replaceAll("٤", "4");
		s = s.replaceAll("٥", "5");
		s = s.replaceAll("٦", "6");
		s = s.replaceAll("٧", "7");
		s = s.replaceAll("٨", "8");
		s = s.replaceAll("٩", "9");
		return s;
	}
	public static String[] getTime(String date) {
		String[] time = new String[5];
		time[0] = date.substring(0,4);
		time[1] = date.substring(4,6);
		time[2] = date.substring(6,8);
		time[3] = date.substring(8,10);
		time[4] = date.substring(10,12);
		return time;
	}
	public static int getScreenWidth() {
		return Resources.getSystem().getDisplayMetrics().widthPixels;
	}
	public static void launchActivityAdmin(Activity getContext, String key) {
		if(Function.isAdmin(getContext)){
			String activity = "", adminDemand="";
			Intent launchIntent = new Intent(Intent.ACTION_VIEW);
			Bundle extra = new Bundle();
			switch (key){
				case "ChatAdminActivity":
					extra.putString("email", Function.getValue(getContext, "email"));
					extra.putString("name", Function.getValue(getContext, "name"));
					extra.putString("dark", String.valueOf(Function.getBoolean(getContext, "dark")));
					launchIntent.putExtras(extra);
					activity = getContext.getString(R.string.pkgadmin)+".barkiasl.support.activity"+".ChatAdminActivity";
					adminDemand = getContext.getString(R.string.adminchat);
					break;
				case "SupportViewActivity":
					extra.putString("email", Function.getValue(getContext, "email"));
					extra.putString("name", Function.getValue(getContext, "name"));
					extra.putString("dark", String.valueOf(Function.getBoolean(getContext, "dark")));
					launchIntent.putExtras(extra);
					activity = getContext.getString(R.string.pkgadmin)+".barkiasl"+".SupportViewActivity";
					adminDemand = getContext.getString(R.string.adminsupport);
					break;
			}
			Toast.makeText(getContext, adminDemand, Toast.LENGTH_LONG).show();
			launchIntent.setComponent(new ComponentName(getContext.getString(R.string.pkgadmin),activity));
			getContext.startActivity(launchIntent);
		}
	}
	public static String setEmail(String toString) {
		toString = toString.replaceAll(" ", "_");
		return toString+"@barkiasl.com";
	}
	public static String getDeviceName(Context getContext) {
		String name = Settings.System.getString(getContext.getContentResolver(), Settings.Secure.ANDROID_ID);
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		return (Function.isAdmin(getContext)) ? getContext.getString(R.string.app_name) : (((model.startsWith(manufacturer)) ? capitalize(model) : capitalize(manufacturer) + " " + model)+"_"+name);
	}
	private static String capitalize(String s) {
		if (s == null || s.isEmpty())
			return Function.setTime();
		char first = s.charAt(0);
		return (Character.isUpperCase(first)) ? s : Character.toUpperCase(first) + s.substring(1);
	}
}