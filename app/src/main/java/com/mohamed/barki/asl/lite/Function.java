package com.mohamed.barki.asl.lite;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
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
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
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
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
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
	public static boolean validate(Context getAppContext, final String text, final AutoCompleteTextView edt) {
		boolean valid = true;
		if (text.isEmpty()) {
			edt.setError(Html.fromHtml("<font color='red'>" + getAppContext.getString(R.string.error) + "</font>"));
			showError(getAppContext, "0");
			valid = false;
		} else {
			if (text.length() > 100) {
				edt.setError(Html.fromHtml("<font color='red'>" + getAppContext.getString(R.string.error) + "</font>"));
				showError(getAppContext, "1");
				valid = false;
			} else {
				edt.setError(null);
			}
		}
		return valid;
	}

	public static void showError(Context getAppContext, String ss) {
		// TODO: Implement this method
		if (ss.equals("0")) {
			Function.showToastMessage(getAppContext, getAppContext.getString(R.string.validate_empty));
		}
		if (ss.equals("1")) {
			Function.showToastMessage(getAppContext, getAppContext.getString(R.string.validate_more));
		}
		if (ss.equals("2")) {
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
	public static boolean getBoolean(Context getAppContext, String key) {
		SharedPreferences prefs = getAppContext.getSharedPreferences(getApplicationName(getAppContext), MODE_PRIVATE);
		return prefs.getBoolean(key, false);
	}

	public static void saveFromBoolean(Context getAppContext, String key, boolean bool) {
		SharedPreferences prefs = getAppContext.getSharedPreferences(getApplicationName(getAppContext), MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(key, bool).apply();
	}
	public static int getInt(Context getAppContext, String key) {
		return Integer.parseInt(getValue(getAppContext, key));
	}

	public static void saveInt(Context getAppContext, String key, int value) {
		saveFromText(getAppContext, key, String.valueOf(value));
	}
	public static void doCopy(Context getAppContext, String text) {
		ClipboardManager clipboardManager;
		clipboardManager = (ClipboardManager) getAppContext.getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clipData = ClipData.newPlainText("text", text);
		clipboardManager.setPrimaryClip(clipData);
		if (getValue(getAppContext, "screen").equals("true")) {
			showToastMessage(getAppContext, getAppContext.getString(R.string.copy_text_to_clip));
		}
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
	public static void mute(Context context) {
		AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
	}
	public static void unmute(Context context) {
		AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		int mute_volume = Function.getInt(context, "volume");
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mute_volume, 0);
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

	@SuppressLint("SuspiciousIndentation")
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
		final LinearLayout lnyAll = dialog.findViewById(R.id.dialog_lny);
		final LinearLayout lnyAllBig = dialog.findViewById(R.id.dialog_lny_Big);
		String v3 = getAppContext.getString(R.string.page_about);
		String v4 = "<a href=\"fb://profile/3203834\">" + "Barki Mohamed" + "</a>" +
				"</p><p>" + "<a href=\"fb://profile/1049535328\">" + "محمد باركي" + "</a>" +
				"</p><p>" + "<a href=\"fb://profile/100063588016416\">" + "صفحة فضاء التربية والتعليم في الوسط المتخصص" + "</a>" +
				"</p><p>" + "<a href=\"fb://group/309682893728684\">" + "مجموعة فضاء التربية والتعليم في الوسط المتخصص" + "</a>";
		String v30 = getAppContext.getString(R.string.email_about);
		String v40 = "<font face=\"arial\" color=\"blue\">mohamedbarkimaths@gmail.com</font>";
		String v33 = getAppContext.getString(R.string.update_app);
		String v44 = "<a href=\"https://play.google.com/store/apps/details?id=" + getAppContext.getPackageName() + "\">" + "Google Play</a>";
		String v7 = "(Copyright " + Calendar.getInstance().get(Calendar.YEAR) + " M. Barki)";
		String value1 = "<html><p>" + v3 + "</p><p>" + v4 + "</p><p>" + v30 + "</p><p>" + v40 + "</p><p>" + v33 + "</p><p>" + v44 + "</p></html>";
		((TextView) dialog.findViewById(R.id.dialog_infox)).setText(v7);
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
		} catch (PackageManager.NameNotFoundException ignored) {
		}
		Typeface facee = Typeface.createFromAsset(getAppContext.getAssets(), "fonts/casual.ttf");
		if (LanguageName.contains("rab") || LanguageName.contains("عربي")) {
			facee = Typeface.createFromAsset(getAppContext.getAssets(), "fonts/naskh.ttf");
		}
		((TextView) dialog.findViewById(R.id.dialog_info)).setTypeface(facee);
		String title;
		if (Function.getValue(getAppContext, "screen").equals("true")) {
			title = getApplicationName(getAppContext) +/*" (Free) "+"["+String.valueOf(VersionCode)+"] "+*/" v" + VersionName;
		} else {
			title = getApplicationName(getAppContext) +/*" (Pro) "+"["+String.valueOf(VersionCode)+"] "+*/" {v" + VersionName + "}";
		}
		((TextView) dialog.findViewById(R.id.dialog_infoo)).setText(Html.fromHtml(title));
		String v1 =
				"</p><p>" +
						getAppContext.getString(R.string.title_about)
						+ "</p><p>" +
						getAppContext.getString(R.string.text_about)
						+ "</p>";
		if (Function.maxRam(myActivity) >= 1) {
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
		TextView tv = new TextView(getAppContext);
		tv.setGravity(Gravity.START);
		tv.setTextIsSelectable(true);
		tv.setPadding(Function.dpToPx(10), Function.dpToPx(10), Function.dpToPx(10), Function.dpToPx(10));
		tv.setText(Html.fromHtml("<html>" + v1 + "</html>"));
		tv.setMovementMethod(LinkMovementMethod.getInstance());
		tv.setTextColor(Color.WHITE);
		Typeface face = Typeface.createFromAsset(getAppContext.getAssets(), "fonts/casual.ttf");
		if (LanguageName.contains("rab") || LanguageName.contains("عربي")) {
			tv.setGravity(Gravity.RIGHT);
			((LinearLayout) dialog.findViewById(R.id.dialog_lny)).setGravity(Gravity.RIGHT);
			((TextView) dialog.findViewById(R.id.dialog_info)).setGravity(Gravity.RIGHT);
			face = Typeface.createFromAsset(getAppContext.getAssets(), "fonts/naskh.ttf");
		}
		tv.setTextSize(20);
		tv.setTypeface(face);
		lnyAll.addView(tv);
		Function.saveFromText(getAppContext, "bolhelp", "true");
		Function.saveFromText(getAppContext, "bolref", "true");
		((ImageButton) dialog.findViewById(R.id.dialog_help)).setImageResource(R.drawable.ic_help);
		dialog.findViewById(R.id.dialog_help).setOnClickListener(v -> {
			Function.startSongs(getAppContext, click);
			boolean bol = Boolean.parseBoolean(Function.getValue(getAppContext, "bolhelp"));
			Function.saveFromText(getAppContext, "bolref", "true");
			((ImageButton) dialog.findViewById(R.id.dialog_ref)).setImageResource(R.drawable.ic_ref);
			if (bol) {
				lnyAllBig.removeAllViews();
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
				lnyAllBig.addView(btn);
				((ImageButton) dialog.findViewById(R.id.dialog_help)).setImageResource(R.drawable.ic_check);
			} else {
				dialog.dismiss();
				Function.info(getAppContext, myActivity);
			}
			Function.saveFromText(getAppContext, "bolhelp", String.valueOf(!bol));
		});
		dialog.findViewById(R.id.dialog_ref).setOnClickListener(v -> {
			Function.startSongs(getAppContext, click);
			boolean bol = Boolean.parseBoolean(Function.getValue(getAppContext, "bolref"));
			Function.saveFromText(getAppContext, "bolhelp", "true");
			((ImageButton) dialog.findViewById(R.id.dialog_help)).setImageResource(R.drawable.ic_help);
			if (bol) {
				lnyAllBig.removeAllViews();
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
				TextView tv1 = new TextView(getAppContext);
				tv1.setTextIsSelectable(true);
				tv1.setPadding(Function.dpToPx(10), Function.dpToPx(10), Function.dpToPx(10), Function.dpToPx(10));
				tv1.setText(Html.fromHtml("<html>" + v11 + "</html>"));
				tv1.setMovementMethod(LinkMovementMethod.getInstance());
				tv1.setTextColor(Color.WHITE);
				Typeface faceee = Typeface.createFromAsset(getAppContext.getAssets(), "fonts/casual.ttf");
				if (LanguageName.contains("rab") || LanguageName.contains("عربي")) {
					tv1.setGravity(Gravity.RIGHT);
					((LinearLayout) dialog.findViewById(R.id.dialog_lny_Big)).setGravity(Gravity.RIGHT);
					faceee = Typeface.createFromAsset(getAppContext.getAssets(), "fonts/naskh.ttf");
				}
				tv1.setTextSize(20);
				tv1.setTypeface(faceee);
				lnyAllBig.addView(tv1);
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
		getActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActivity.setContentView(layout);
	}

	public static void setThemeLight(Activity getActivity, int layout) {
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		getActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActivity.setContentView(layout);
	}

	public static String setTime() {
		String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
		String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

		return currentDate.replaceAll("-", "") + currentTime.replaceAll(":", "");
	}
	public static int getScreenWidth() {
		return Resources.getSystem().getDisplayMetrics().widthPixels;
	}
}