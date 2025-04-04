package com.mohamed.barki.asl.lite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Locale;

@SuppressWarnings({"deprecation", "RedundantSuppression", "SpellCheckingInspection", "DuplicateExpressions", "unused", "CallToPrintStackTrace", "ReassignedVariable", "ResultOfMethodCallIgnored", "SwitchStatementWithTooFewBranches", "LocalVariableUsedAndDeclaredInDifferentSwitchBranches", "EnhancedSwitchBackwardMigration", "StatementWithEmptyBody", "rawtypes", "StatementWithEmptyBody", "ConstantConditions", "EqualsBetweenInconvertibleTypes", "SuspiciousIndentAfterControlStatement"})
@SuppressLint({"NonConstantResourceId", "SuspiciousIndentation", "SetTextI18n", "StaticFieldLeak", "InflateParams", "MissingInflatedId", "MissingSuperCall", "NewApi", "NotifyDataSetChanged", "UseCompatTextViewDrawableApis", "SuspiciousIndentation", "ClickableViewAccessibility", "UseCompatTextViewDrawableApis", "ResourceAsColor", "CheckResult", "SetJavaScriptEnabled"})
public class LoginActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if(Function.getValue(this, "start").isEmpty()){
			int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
			if (mode == Configuration.UI_MODE_NIGHT_YES) Function.setThemeDark(this, R.layout.login);
			else Function.setThemeLight(this, R.layout.login);
			Function.saveFromBoolean(this, "dark", (mode == Configuration.UI_MODE_NIGHT_YES));
			Function.saveFromText(this, "start", "true");
		}else{
			if(Function.getBoolean(this, "dark")) Function.setThemeDark(this, R.layout.login);
			else Function.setThemeLight(this, R.layout.login);
		}

		initialisation();
	}
	private final String str1 = Function.st, str2 = Function.stt, str3 = Function.sttt, str4 = Function.stttt, str5 = Function.sttttt;
	@Override
	public void onBackPressed() {super.onBackPressed();}
	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	@Override
	protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		initialisation();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			Function.trimCache(LoginActivity.this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void initialisation() {
		Typeface face = Typeface.createFromAsset(getAssets(), "fonts/BlackHistory.otf");
		if(Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")){
			face = Typeface.createFromAsset(getAssets(), "fonts/ArefRuqaa.ttf");
		}
		((TextView) findViewById(R.id.screeneText1)).setTypeface(face);
		((TextView) findViewById(R.id.screeneText2)).setTypeface(face);

		Glide.with(this).asGif().load(R.raw.gear_duo).into((ImageView) findViewById(R.id.webView));

		if(getPackageName().compareTo(str1+str2+str3+str4) != 0){
			Function.showToastMessage(this, getString(R.string.worng_package));
		}else if(!Function.getApplicationName(this).equals(str5)){
			Function.showToastMessage(this, getString(R.string.worng_name));
		}else if(!Function.isNetworkConnected(this)){
			Function.showToastMessage(this, getString(R.string.h4));
		}else {
			Function.saveFromBoolean(this, "screen", true);
			if (Function.getValue(this, "scor").isEmpty()) {
				Function.saveFromText(this, "scor", "0");
			}
			if (Function.getValue(this, "update").isEmpty()) {
				Function.saveFromText(this, "update", Function.setTime());
			}
			if (Function.getValue(this, "email").isEmpty()) {
				Function.saveFromText(this, "email", Function.setEmail(Function.getDeviceName(this)));
			}
			if (Function.getValue(this, "name").isEmpty()) {
				Function.saveFromText(this, "name", Function.getDeviceName(this));
			}
			if(Function.getValue(this, "message").isEmpty()){
				Function.saveFromText(this, "message", Function.setTime());
			}
			new android.os.Handler().postDelayed(() -> {
				startActivity(new Intent(LoginActivity.this, ScreenActivity.class));
				finish();
			}, 2800);
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	}
	@Override
	protected void onStop() {super.onStop();}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
	@Override
	public void onConfigurationChanged(@NonNull Configuration newConfig) {super.onConfigurationChanged(newConfig);}
}