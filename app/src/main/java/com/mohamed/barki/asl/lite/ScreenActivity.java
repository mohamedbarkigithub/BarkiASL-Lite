package com.mohamed.barki.asl.lite;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohamed.barki.asl.lite.DataBase.DatabaseSupport;


import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"deprecation", "RedundantSuppression", "SpellCheckingInspection", "DuplicateExpressions", "unused", "CallToPrintStackTrace", "ReassignedVariable", "ResultOfMethodCallIgnored", "SwitchStatementWithTooFewBranches", "LocalVariableUsedAndDeclaredInDifferentSwitchBranches", "EnhancedSwitchBackwardMigration", "StatementWithEmptyBody", "rawtypes", "StatementWithEmptyBody", "ConstantConditions", "EqualsBetweenInconvertibleTypes", "SuspiciousIndentAfterControlStatement"})
@SuppressLint({"NonConstantResourceId", "SuspiciousIndentation", "SetTextI18n", "StaticFieldLeak", "InflateParams", "MissingInflatedId", "MissingSuperCall", "NewApi", "NotifyDataSetChanged", "UseCompatTextViewDrawableApis", "SuspiciousIndentation", "ClickableViewAccessibility", "UseCompatTextViewDrawableApis", "ResourceAsColor", "CheckResult", "SetJavaScriptEnabled"})
public class ScreenActivity extends AppCompatActivity implements OnClickListener, NavigationView.OnNavigationItemSelectedListener {
	private boolean boolExit;
	private Intent intent;
	ImageButton btn_dark, btn_rign, btn_song;
	private MediaPlayer click, clickScreen;
	boolean boolUpdate = true, boolEmail = true;
	DatabaseReference updateReference, emailReference;
	ValueEventListener valueEventListener, valueEventListenerEmail;
	DrawerLayout drawer;
	private String LanguageName;
	public ScreenActivity() {}
	@Override
	public void onClick(View p1)
	{
		boolExit = false;
		switch (p1.getId()) {
			case R.id.dark_button:
				startSongs(click);
				Function.saveFromBoolean(this, "dark", !Function.getBoolean(this, "dark"));
				recreate();
				break;
			case R.id.rign_button:
				startSongs(click);
				Function.saveFromBoolean(this, "notify", !Function.getBoolean(this, "notify"));
				Function.saveFromText(this, "message", Function.setTime());
				recreate();
				break;
			case R.id.song_button:
				startSongs(click);
				Function.saveFromBoolean(this, "song", !Function.getBoolean(this, "song"));
				recreate();
				break;
			case R.id.screenButton1:
				startSongs(clickScreen);
				if(Function.getValue(this, "startGame").isEmpty()){
					Function.saveFromText(this, "startGame", "true");
				}
				if(Function.isNetworkConnected(this)) updateReference.removeEventListener(valueEventListener);
				startActivityFun(intent, "1");
				break;
			case R.id.screenButton2:
				startSongs(clickScreen);
				if(Function.isNetworkConnected(this)) updateReference.removeEventListener(valueEventListener);
				startActivityFun(intent, "2"); break;
			case R.id.screenButton4:
				startSongs(click);
				drawer.openDrawer((LanguageName.contains("rab") || LanguageName.contains("عربي")) ? GravityCompat.END : GravityCompat.START);
				break;
			case R.id.screenButton5:
				startSongs(click);
				Function.share(ScreenActivity.this, ScreenActivity.this); break;
		}
	}

	private void startActivityFun(Intent intent, String p1) {
		stopHandler();
		Function.saveFromText(this, "intent", p1);
		startActivity(intent);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if(Function.getBoolean(this, "dark")) Function.setThemeDark(this, R.layout.screen);
		else Function.setThemeLight(this, R.layout.screen);

		Function.removeTakeScreenshot(this);

		if(Function.getBoolean(this, "screen"))
			Function.showToastMessage(this, getString(R.string.ahlenWsahlen)+" 😊😊");
		Function.saveFromBoolean(this, "screen", false);

		if (Function.maxRam(ScreenActivity.this)>=4) {
			if (Function.getValue(ScreenActivity.this, "numImage").isEmpty()) {
				String numImage = Function.numIllustrations(this);
				Function.saveFromText(ScreenActivity.this, "numImage", numImage);
			}
		}else
			Function.saveFromText(ScreenActivity.this, "numImage", "0");


		click = MediaPlayer.create(ScreenActivity.this, R.raw.click);
		clickScreen = MediaPlayer.create(ScreenActivity.this, R.raw.click_screen);

		boolExit = false;

		intent = new Intent(ScreenActivity.this, ASLActivity.class);

		Typeface face = Typeface.createFromAsset(getAssets(), "fonts/"+
				((Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ? "MolhimBold" : "handwriter")+
				".ttf");

		Button btn_game = findViewById(R.id.screenButton1);
		btn_game.setTypeface(face);
		btn_game.setOnClickListener(this);
		btn_game.setBackgroundResource(R.drawable.button_screen);
		Button btn_search = findViewById(R.id.screenButton2);
		btn_search.setTypeface(face);
		btn_search.setOnClickListener(this);
		btn_search.setBackgroundResource(R.drawable.button_screen);

		ImageButton btn_info = findViewById(R.id.screenButton4);
		btn_info.setOnClickListener(this);
		ImageButton btn_share = findViewById(R.id.screenButton5);
		btn_share.setOnClickListener(this);

		btn_dark = findViewById(R.id.dark_button);
		btn_dark.setOnClickListener(this);
		btn_rign = findViewById(R.id.rign_button);
		btn_rign.setOnClickListener(this);
		btn_song = findViewById(R.id.song_button);
		btn_song.setOnClickListener(this);

		if(!Function.getBoolean(this, "dark")){
			btn_dark.setImageResource(R.drawable.ic_dark);
		}else{
			btn_dark.setImageResource(R.drawable.ic_light);
		}
		if(!Function.getBoolean(this, "notify")){
			btn_rign.setImageResource(R.drawable.ic_bell_off);
		}else{
			btn_rign.setImageResource(R.drawable.ic_bell_ring);
		}
		if(!Function.getBoolean(this, "song")){
			btn_song.setImageResource(R.drawable.group_song_mute);
		}else{
			btn_song.setImageResource(R.drawable.group_song);
		}
		timerepond = MediaPlayer.create(this, R.raw.timerepond);
		handler = new Handler();
		r = () -> {
			Function.startSongs(ScreenActivity.this, timerepond);
			startHandler();
		};
		drawer = findViewById(R.id.drawer_layout);
		drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

		LanguageName = Locale.getDefault().getDisplayLanguage();
		NavigationView navigationView = findViewById((LanguageName.contains("rab") || LanguageName.contains("عربي")) ? R.id.nav_viewR : R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		hideItem(navigationView);
		View headerView = navigationView.getHeaderView(0);
		((TextView) headerView.findViewById(R.id.textView)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/barkiasl.ttf"));
	}
	private void hideItem(@NonNull NavigationView navigationView) {
		initialitationItem(this);
		Menu nav_Menu = navigationView.getMenu();
		for (int i=0;i<itemId.length;i++) {
			applyFontToMenuItem(nav_Menu.findItem(itemId[i]), i);
		}
		if(!Function.isAdmin(this))
			nav_Menu.findItem(R.id.nav_admin).setVisible(false);
	}
	private String[] itemTitle;
	private int[] itemId;
	private int[] itemDrawable;
	private void initialitationItem(Context c) {
		itemTitle = new String[]{
				//	c.getString(R.string.admininfo),
				//	c.getString(R.string.action_settings),
				(Function.isAdmin(c)) ? c.getString(R.string.support) : c.getString(R.string.support_view),
				c.getString(R.string.facebook),
				c.getString(R.string.facebookk),
				c.getString(R.string.facebookpage),
				c.getString(R.string.facebookpageG),
				c.getString(R.string.insta),
				c.getString(R.string.mytelegram),
				c.getString(R.string.telegram),
				c.getString(R.string.email),
				//	c.getString(R.string.phone),
				c.getString(R.string.info),
				c.getString(R.string.update_app),
				(getPackageName().endsWith("e")) ? c.getString(R.string.full_app) : c.getString(R.string.lite_app),
				c.getString(R.string.braille_app),
				c.getString(R.string.policy),
		};
		itemId = new int[]{
				//	R.id.nav_admininfo,
				//	R.id.nav_setting,
				R.id.nav_support,
				R.id.nav_facebook,
				R.id.nav_facebook2,
				R.id.nav_facebookpage,
				R.id.nav_facebookpagee,
				R.id.nav_insta,
				R.id.nav_mytelegram,
				R.id.nav_telegram,
				R.id.nav_email,
				//	R.id.nav_telephone,
				R.id.nav_info,
				R.id.nav_update,
				R.id.nav_lite,
				R.id.nav_braille,
				R.id.nav_policy,
		};
		itemDrawable = new int[]{
				//	R.drawable.ic_admin_info,
				//	R.drawable.ic_setting,
				(Function.isAdmin(c)) ? R.drawable.ic_menu_support_view_admin : R.drawable.ic_menu_support_view_user,
				R.drawable.ic_menu_facebook,
				R.drawable.ic_menu_facebook,
				R.drawable.ic_menu_facebook_page_barki,
				R.drawable.ic_menu_facebook_group,
				R.drawable.ic_menu_insta,
				R.drawable.mytelegram,
				R.drawable.ic_menu_telegram,
				R.drawable.ic_menu_email,
				//R.drawable.ic_menu_telephone,
				R.drawable.ic_menu_info,
				R.drawable.ic_menu_update_app,
				R.mipmap.icon_asl,
				R.mipmap.icon_braille,
				R.drawable.ic_menu_policy,
		};
	}
	private void applyFontToMenuItem(MenuItem mi, int i) {
		Typeface font = Typeface.createFromAsset(getAssets(),
				(Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ?
						"fonts/ArefRuqaa.ttf" : "fonts/Balton.ttf"
		);
		ImageButton IconL = Objects.requireNonNull(mi.getActionView()).findViewById(R.id.itemImageButtonL);
		ImageButton IconR = Objects.requireNonNull(mi.getActionView()).findViewById(R.id.itemImageButtonR);
		TextView Title = mi.getActionView().findViewById(R.id.itemTextView);
		Title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		Title.setSingleLine(true);
		Title.setHorizontallyScrolling(true);
		Title.setHorizontalFadingEdgeEnabled(true);
		Title.setMarqueeRepeatLimit(-1);
		Title.setSelected(true);
		SpannableString mNewTitle = new SpannableString(itemTitle[i]);
		mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		Title.setText(mNewTitle);
		if(Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")){
			if(itemId[i]==R.id.nav_lite || itemId[i]==R.id.nav_braille)
				IconR.setImageDrawable(Function.resizeImage(this, itemDrawable[i],108,108));
			else IconR.setImageResource(itemDrawable[i]);
			IconL.setVisibility(View.GONE);
			IconR.setVisibility(View.VISIBLE);
			((LinearLayout) mi.getActionView()).setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
		}else{
			if(itemId[i]==R.id.nav_lite || itemId[i]==R.id.nav_braille)
				IconL.setImageDrawable(Function.resizeImage(this, itemDrawable[i],108,108));
			else IconL.setImageResource(itemDrawable[i]);
			IconR.setVisibility(View.GONE);
			IconL.setVisibility(View.VISIBLE);
			((LinearLayout) mi.getActionView()).setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
		}
	}
	private MediaPlayer timerepond;
	private Handler handler;
	private Runnable r;
	final long REPEAT_USER_DELAY = 25;
	@Override
	public void onUserInteraction() {
		super.onUserInteraction();
		stopHandler();
		Function.stopSongs(ScreenActivity.this, timerepond);
		startHandler();
	}
	public void stopHandler() {
		handler.removeCallbacks(r);
	}
	public void startHandler() {
		handler.postDelayed(r, REPEAT_USER_DELAY *1000);
	}
	private String lastMessage;
	boolean boolMessage = true;
	DatabaseReference messageReference;
	ChildEventListener valueEventListenerMessage;
	@Override
	protected void onStart() {
		super.onStart();
		if(Function.isNetworkConnected(this)){
			updateReference = FirebaseDatabase.getInstance().getReference().child("update");
			valueEventListener = new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					if (snapshot.exists() && boolUpdate) {
						long old_version = Long.parseLong(Function.getValue(ScreenActivity.this, "update"));
						long new_version = Long.parseLong(Objects.requireNonNull(snapshot.getValue(String.class)));
						if (old_version < new_version) openDialog();
					}
				}
				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			};
			String name = Function.getValue(this, "name");
			String email = Function.getValue(this, "email");
			messageReference = (Function.isAdmin(this))
					? FirebaseDatabase.getInstance(DatabaseSupport.getInstance(this)).getReference().child("support")
					: FirebaseDatabase.getInstance(DatabaseSupport.getInstance(this)).getReference().child("support").child(name);
			valueEventListenerMessage = new ChildEventListener() {
				@Override
				public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {
					if(!Function.isAdmin(ScreenActivity.this)){
						if(dataSnapshot.exists() && boolMessage){
							if(testSnapshotTime(dataSnapshot.child("time")) && dataSnapshot.child("name").exists()){
								long old_version = Long.parseLong(Function.getValue(ScreenActivity.this, "message"));
								long new_version = Long.parseLong(Objects.requireNonNull(dataSnapshot.child("time").getValue(String.class)));
								if (old_version < new_version && !Objects.equals(dataSnapshot.child("name").getValue(String.class), Function.getValue(ScreenActivity.this, "name"))){
									lastMessage = dataSnapshot.getKey();
									boolMessage = false;
									openDialogMessage(name, email, dataSnapshot.child("name").getValue(String.class));
								}else dialogMessage = null;
							}
						}
					}else{
						if(dataSnapshot.exists() && boolMessage){
							for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
								if(testSnapshotTime(snapshot.child("time")) && snapshot.child("name").exists()){
									long old_version = Long.parseLong(Function.getValue(ScreenActivity.this, "message"));
									long new_version = Long.parseLong(Objects.requireNonNull(snapshot.child("time").getValue(String.class)));
									if (old_version < new_version && !Objects.equals(snapshot.child("name").getValue(String.class), Function.getValue(ScreenActivity.this, "name"))){
										boolMessage = false;
										openDialogMessage(dataSnapshot.getKey(), Function.setEmail(dataSnapshot.getKey()), dataSnapshot.getKey());
										break;
									}else dialogMessage = null;
								}
							}
						}
					}
				}
				@Override
				public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {
					if(Function.isAdmin(ScreenActivity.this)){
						if(dataSnapshot.exists() && boolMessage){
							for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
								long old_version = Long.parseLong(Function.getValue(ScreenActivity.this, "message"));
								long new_version = Long.parseLong(Objects.requireNonNull(snapshot.child("time").getValue(String.class)));
								if (old_version < new_version && !Objects.equals(snapshot.child("name").getValue(String.class), Function.getValue(ScreenActivity.this, "name"))){
									boolMessage = false;
									openDialogMessage(dataSnapshot.getKey(), Function.setEmail(dataSnapshot.getKey()), dataSnapshot.getKey());
									break;
								}else dialogMessage = null;
							}
						}
					}
				}
				@Override
				public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
					if(dialogMessage!=null && dataSnapshot.exists() && !Function.isAdmin(ScreenActivity.this)){
						if(Objects.equals(dataSnapshot.getKey(), lastMessage)){
							boolMessage = true;
							dialogMessage.dismiss();
						}
					}
				}
				@Override
				public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {
				}
				@Override
				public void onCancelled(@NonNull DatabaseError databaseError) {
				}
			};
			if(!Function.getBoolean(this, "notify")){
				removeAllEventListener();
				Function.saveFromText(this, "message", Function.setTime());
				boolMessage = true;
			}else{
				updateReference.addValueEventListener(valueEventListener);
				messageReference.addChildEventListener(valueEventListenerMessage);
			}
			emailReference = FirebaseDatabase.getInstance().getReference().child("all-users");
			valueEventListenerEmail = new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					if(boolEmail){
						if(!snapshot.hasChild(name)){
							Map<String, Object> map = new HashMap<>();
							String block = "unblock";
							if(Function.isAdmin(ScreenActivity.this)) block = "admin";
							map.put("state", block);
							map.put("date", Function.setTime());
							emailReference.child(name).updateChildren(map).addOnSuccessListener(unused -> boolEmail = false);
						}else{
							if(Objects.equals(snapshot.child(name).child("state").getValue(String.class), "block")){
								removeAllEventListener();
								Function.removeAllSaveText(ScreenActivity.this);
								Function.showToastMessage(ScreenActivity.this, ScreenActivity.this.getString(R.string.sorry_you_blocked));
								finish();
								finishAffinity();
								finishAndRemoveTask();
							}else
								emailReference.child(name).child("date").setValue(Function.setTime()).addOnSuccessListener(unused ->{
									if(Function.isAdmin(ScreenActivity.this))
										emailReference.child(name).child("state").setValue("admin").addOnSuccessListener(u -> boolEmail = false);
									else
										emailReference.child(name).child("state").setValue("unblock").addOnSuccessListener(u -> boolEmail = false);
								});
						}
					}
				}
				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			};
			emailReference.addValueEventListener(valueEventListenerEmail);
		}
		startHandler();
	}
	private boolean testSnapshotTime(DataSnapshot dataSnapshot) {
		if(!dataSnapshot.exists()) return false;
		if(dataSnapshot.getValue(String.class)==null) return false;
		if(Objects.requireNonNull(dataSnapshot.getValue(String.class)).length()!=14) return false;
		return NumberUtils.isParsable(dataSnapshot.getValue(String.class));
	}
	private Dialog dialogMessage;
	private void openDialogMessage(String nameMessage, String emailMessage, String nameRecive) {
		Function.startSongs(this, MediaPlayer.create(this, R.raw.new_message));
		dialogMessage = new Dialog(ScreenActivity.this, R.style.DialogStyle);
		dialogMessage.setContentView(R.layout.dialog);
		dialogMessage.setCanceledOnTouchOutside(false);
		dialogMessage.setCancelable(false);
		((TextView) dialogMessage.findViewById(R.id.dialog_info)).setText(getString(R.string.new_message));
		((TextView) dialogMessage.findViewById(R.id.dialog_infoo)).setText(getString(R.string.new_message_text)+" "+nameRecive);
		((TextView) dialogMessage.findViewById(R.id.dialog_infooo)).setText(getString(R.string.new_message_test));

		Typeface typeface = Typeface.createFromAsset(getAssets(),
				(Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ?
						"fonts/ArefRuqaa.ttf" : "fonts/casual.ttf"
		);

		((TextView) dialogMessage.findViewById(R.id.dialog_info)).setTypeface(typeface);
		((TextView) dialogMessage.findViewById(R.id.dialog_infoo)).setTypeface(typeface);
		((TextView) dialogMessage.findViewById(R.id.dialog_infooo)).setTypeface(typeface);
		((ImageButton)dialogMessage.findViewById(R.id.dialog_ok)).setImageResource(R.drawable.popup_message);
		dialogMessage.findViewById(R.id.dialog_ok).setOnClickListener(v -> {
			startSongs(MediaPlayer.create(this, R.raw.click));
			dialogMessage.dismiss();
			dialogMessage = null;
			removeAllEventListener();
			Function.saveFromText(this, "message", Function.setTime());
			Intent intent = new Intent(ScreenActivity.this, (Function.isAdmin(this)) ? ChatAdminActivity.class : ChatActivity.class);
			Bundle extra = new Bundle();
			extra.putString("activity", "ScreenActivity");
			extra.putString("name", nameMessage);
			extra.putString("email", emailMessage);
			intent.putExtras(extra);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		});
		((ImageButton)dialogMessage.findViewById(R.id.dialog_cancel)).setImageResource(R.drawable.popup_message_off);
		dialogMessage.findViewById(R.id.dialog_cancel).setOnClickListener(v ->{
			startSongs(MediaPlayer.create(this, R.raw.click));
			Function.saveFromText(this, "message", Function.setTime());
			dialogMessage.dismiss();
			dialogMessage = null;
			boolMessage = true;
		});
		if(!this.isFinishing()){
			dialogMessage.show();
		}
	}
	private void removeAllEventListener() {
		if(valueEventListenerMessage!=null && messageReference!=null) messageReference.removeEventListener(valueEventListenerMessage);
		if(valueEventListener!=null && updateReference!=null) updateReference.removeEventListener(valueEventListener);
	}
	@Override
	protected void onStop() {
		super.onStop();
		removeAllEventListener();
		stopHandler();
	}
	private void openDialog() {
		Function.startSongs(this, MediaPlayer.create(this, R.raw.update));
		boolUpdate = false;
		final Dialog dialog = new Dialog(ScreenActivity.this, R.style.DialogStyle);
		dialog.setContentView(R.layout.dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		((TextView) dialog.findViewById(R.id.dialog_info)).setText(getString(R.string.save_app));
		((TextView) dialog.findViewById(R.id.dialog_infoo)).setText(getString(R.string.save_this_app));
		((TextView) dialog.findViewById(R.id.dialog_infooo)).setText(getString(R.string.save_app_offline));
		Typeface typeface = Typeface.createFromAsset(ScreenActivity.this.getAssets(),
				(Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ?
						"fonts/naskh.ttf" : "fonts/casual.ttf"
		);
		((TextView) dialog.findViewById(R.id.dialog_info)).setTypeface(typeface);
		((TextView) dialog.findViewById(R.id.dialog_infoo)).setTypeface(typeface);
		((TextView) dialog.findViewById(R.id.dialog_infooo)).setTypeface(typeface);
		((ImageButton)dialog.findViewById(R.id.dialog_ok)).setImageResource(R.drawable.popup_download);
		dialog.findViewById(R.id.dialog_ok).setOnClickListener(v -> {
			startSongs(MediaPlayer.create(this, R.raw.click));
			Function.saveFromText(this, "update", Function.setTime());
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName()));
			startActivity(browserIntent);
			dialog.dismiss();
			finishAffinity();
			finishAndRemoveTask();
		});
		((ImageButton)dialog.findViewById(R.id.dialog_cancel)).setImageResource(R.drawable.popup_download_off);
		dialog.findViewById(R.id.dialog_cancel).setOnClickListener(v -> {
			startSongs(MediaPlayer.create(this, R.raw.click));
			Function.saveFromText(this, "update", Function.setTime());
			dialog.dismiss();
			boolUpdate = true;
		});
		if(!this.isFinishing()){
			Function.saveFromText(this, "update", Function.setTime());
			dialog.show();
		}
	}
	private void openDialogLite() {
		boolUpdate = false;
		final Dialog dialog = new Dialog(ScreenActivity.this, R.style.DialogStyle);
		dialog.setContentView(R.layout.dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		((TextView) dialog.findViewById(R.id.dialog_info)).setText(getString((getPackageName().endsWith("e")) ? R.string.install_full : R.string.install_lite));
		((TextView) dialog.findViewById(R.id.dialog_infoo)).setText(getString((getPackageName().endsWith("e")) ? R.string.save_this_full : R.string.save_this_lite));
		((TextView) dialog.findViewById(R.id.dialog_infooo)).setText(getString((getPackageName().endsWith("e")) ? R.string.save_full_offline : R.string.save_lite_offline));
		Typeface typeface = Typeface.createFromAsset(ScreenActivity.this.getAssets(),
				(Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ?
						"fonts/naskh.ttf" : "fonts/casual.ttf"
		);
		((TextView) dialog.findViewById(R.id.dialog_info)).setTypeface(typeface);
		((TextView) dialog.findViewById(R.id.dialog_infoo)).setTypeface(typeface);
		((TextView) dialog.findViewById(R.id.dialog_infooo)).setTypeface(typeface);
		((ImageButton)dialog.findViewById(R.id.dialog_ok)).setImageResource(R.drawable.popup_download);
		dialog.findViewById(R.id.dialog_ok).setOnClickListener(v -> {
			startSongs(MediaPlayer.create(this, R.raw.click));
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+
					((getPackageName().endsWith("e")) ? getPackageName().replace(".lite", "") : getPackageName()+".lite")
			));
			startActivity(browserIntent);
			dialog.dismiss();
			finishAffinity();
			finishAndRemoveTask();
		});
		((ImageButton)dialog.findViewById(R.id.dialog_cancel)).setImageResource(R.drawable.popup_download_off);
		dialog.findViewById(R.id.dialog_cancel).setOnClickListener(v -> {
			startSongs(MediaPlayer.create(this, R.raw.click));
			dialog.dismiss();
			boolUpdate = true;
		});
		if(!this.isFinishing()){
			dialog.show();
		}
	}
	private void openDialogBraille() {
		boolUpdate = false;
		final Dialog dialog = new Dialog(ScreenActivity.this, R.style.DialogStyle);
		dialog.setContentView(R.layout.dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		((TextView) dialog.findViewById(R.id.dialog_info)).setText(getString(R.string.install_braille));
		((TextView) dialog.findViewById(R.id.dialog_infoo)).setText(getString(R.string.save_this_braille));
		((TextView) dialog.findViewById(R.id.dialog_infooo)).setText(getString(R.string.save_braille_offline));
		Typeface typeface = Typeface.createFromAsset(ScreenActivity.this.getAssets(),
				(Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ?
						"fonts/naskh.ttf" : "fonts/casual.ttf"
		);
		((TextView) dialog.findViewById(R.id.dialog_info)).setTypeface(typeface);
		((TextView) dialog.findViewById(R.id.dialog_infoo)).setTypeface(typeface);
		((TextView) dialog.findViewById(R.id.dialog_infooo)).setTypeface(typeface);
		((ImageButton)dialog.findViewById(R.id.dialog_ok)).setImageResource(R.drawable.popup_download);
		dialog.findViewById(R.id.dialog_ok).setOnClickListener(v -> {
			startSongs(MediaPlayer.create(this, R.raw.click));
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getString(R.string.pkgBraille)));
			startActivity(browserIntent);
			dialog.dismiss();
			finishAffinity();
			finishAndRemoveTask();
		});
		((ImageButton)dialog.findViewById(R.id.dialog_cancel)).setImageResource(R.drawable.popup_download_off);
		dialog.findViewById(R.id.dialog_cancel).setOnClickListener(v -> {
			startSongs(MediaPlayer.create(this, R.raw.click));
			dialog.dismiss();
			boolUpdate = true;
		});
		if(!this.isFinishing()){
			dialog.show();
		}
	}
	@Override
	public void onBackPressed() {
		if (boolExit) {
			removeAllEventListener();
			if(valueEventListenerEmail!=null && emailReference!=null) emailReference.removeEventListener(valueEventListenerEmail);
			if(Function.toast != null) Function.toast.cancel();
			stopHandler();
			finish();
			finishAffinity();
			finishAndRemoveTask();
		} else {
			Function.showToastMessage(this, getString(R.string.re_exit));
			boolExit = true;
			new android.os.Handler().postDelayed(() -> boolExit = true, 5000);
		}
	}
	private void startSongs(MediaPlayer songs){
		if(Function.getBoolean(this, "song")){
			songs.start();
		}
	}
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		boolExit = false;
		int id = item.getItemId();
		Intent intent = new Intent(Intent.ACTION_VIEW);
		startSongs(MediaPlayer.create(this, R.raw.click));
		if (id == R.id.nav_info) {
			Function.info(this, this);
		} else if (id == R.id.nav_email) {
			((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
			emailOpen();
		} /*else if (id == R.id.nav_telephone) {
			((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
			phoneOpen();
		} */else if (id == R.id.nav_update) {
			((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
			openDialog();
		} else if (id == R.id.nav_lite) {
			((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
			if(Function.isLiteFull(this)) {
				Intent launchIntent = new Intent(Intent.ACTION_VIEW);
				launchIntent.setComponent(new ComponentName(getString(R.string.pkgapp),getString(R.string.pkgapp)+".lite"+".LoginActivity"));
				startActivity(launchIntent);
			}else openDialogLite();
		} else if (id == R.id.nav_braille) {
			((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
			if(Function.isBraille(this)) {
				Intent launchIntent = new Intent(Intent.ACTION_VIEW);
				launchIntent.setComponent(new ComponentName(getString(R.string.pkgBraille),getString(R.string.pkgBraille)+".LoginActivity"));
				startActivity(launchIntent);
			}else openDialogBraille();
		} else if (id == R.id.nav_facebookpage) {
			((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
			intent.setData(Uri.parse(getString(R.string.idfacebookpageappbarki)));
			String pkg = getString(R.string.pkgfacebooklite);
			if (Function.isPackageInstalled(this, getString(R.string.pkgfacebook)))
				pkg = getString(R.string.pkgfacebook);
			intent.setPackage(pkg);
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.idfacebookpagebarki))));
			}
		} else if (id == R.id.nav_facebookpagee) {
			((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
			intent.setData(Uri.parse(getString(R.string.idfacebookgroupapp)));
			String pkg = getString(R.string.pkgfacebooklite);
			if (Function.isPackageInstalled(this, getString(R.string.pkgfacebook)))
				pkg = getString(R.string.pkgfacebook);
			intent.setPackage(pkg);
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.idfacebookgroup))));
			}
		} else if (id == R.id.nav_facebook) {
			((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
			intent.setData(Uri.parse(getString(R.string.idfacebookdevlopperapp)));
			String pkg = getString(R.string.pkgfacebooklite);
			if (Function.isPackageInstalled(this, getString(R.string.pkgfacebook)))
				pkg = getString(R.string.pkgfacebook);
			intent.setPackage(pkg);
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.idfacebookdevlopper))));
			}
		} else if (id == R.id.nav_facebook2) {
			((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
			intent.setData(Uri.parse(getString(R.string.idfacebookdevlopper2app)));
			String pkg = getString(R.string.pkgfacebooklite);
			if (Function.isPackageInstalled(this, getString(R.string.pkgfacebook)))
				pkg = getString(R.string.pkgfacebook);
			intent.setPackage(pkg);
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.idfacebookdevlopper2))));
			}
		} else if (id == R.id.nav_insta) {
			((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
			intent.setData(Uri.parse(getString(R.string.idistagram)));
			String pkg = getString(R.string.pkginstagramlite);
			if (Function.isPackageInstalled(this, getString(R.string.pkginstagram)))
				pkg = getString(R.string.pkginstagram);
			intent.setPackage(pkg);
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.idistagram))));
			}
		} else if (id == R.id.nav_mytelegram) {
			((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
			intent.setData(Uri.parse(getString(R.string.idtelegramapp)));
			String pkg = getString(R.string.pkgtelegramweb);
			if (Function.isPackageInstalled(this, getString(R.string.pkgtelegram)))
				pkg = getString(R.string.pkgtelegram);
			intent.setPackage(pkg);
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.idtelegram))));
			}
		} else if (id == R.id.nav_telegram) {
			((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
			intent.setData(Uri.parse(getString(R.string.idtelegramappG)));
			String pkg = getString(R.string.pkgtelegramweb);
			if (Function.isPackageInstalled(this, getString(R.string.pkgtelegram)))
				pkg = getString(R.string.pkgtelegram);
			intent.setPackage(pkg);
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.idtelegramG))));
			}
		} else if (id == R.id.nav_support) {
			((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
			if(Function.isAdmin(this))
				Function.launchActivityAdmin(this, "SupportViewActivity");
			else{
				intent = new Intent(ScreenActivity.this, ChatActivity.class);
				Bundle extra = new Bundle();
				extra.putString("activity", "ScreenActivity");
				extra.putString("email", Function.getValue(ScreenActivity.this, "email"));
				extra.putString("name", Function.getValue(ScreenActivity.this, "name"));
				intent.putExtras(extra);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		} else if (id == R.id.nav_policy) {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy))));
		} else if (id == R.id.nav_list_user) {
			((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
			removeAllEventListener();
			Function.launchActivityAdmin(this, "BlockUserActivity");
		} else if (id == R.id.nav_list_crash) {
			((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
			removeAllEventListener();
			Function.launchActivityAdmin(this, "CrashViewActivity");
		} else if (id == R.id.nav_upload_app) {
			if(Function.isNetworkConnected(this)){
				String update = Function.setTime();
				FirebaseDatabase.getInstance().getReference().child("update")
						.setValue(update)
						.addOnSuccessListener(unused -> Function.showToastMessage(this, getString(R.string.success_update)))
						.addOnFailureListener(e -> Function.showToastMessage(this, getString(R.string.failure_update)));
			}
		}
		return true;
	}
	private void emailOpen() {
		String txt = getString(R.string.email_admin_txt),
				email = getString(R.string.email_dev);
		final String emailF = email;
		final Dialog dialog = new Dialog(this, R.style.DialogStyle);
		dialog.setContentView(R.layout.dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		((TextView) dialog.findViewById(R.id.dialog_info)).setText(txt);
		Typeface typeface = Typeface.createFromAsset(ScreenActivity.this.getAssets(),
				(Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ?
						"fonts/naskh.ttf" : "fonts/casual.ttf"
		);
		((TextView) dialog.findViewById(R.id.dialog_info)).setTypeface(typeface);
		((TextView) dialog.findViewById(R.id.dialog_infoo)).setText(email);
		dialog.findViewById(R.id.dialog_infooo).setVisibility(View.GONE);
		((ImageButton) dialog.findViewById(R.id.dialog_ok)).setImageResource(R.drawable.popup_copy);
		dialog.findViewById(R.id.dialog_ok).setOnClickListener(v -> {
			startSongs(MediaPlayer.create(this, R.raw.click));
			Function.doCopy(ScreenActivity.this, emailF, getString(R.string.copy_email_to_clip));
			dialog.dismiss();
		});
		((ImageButton) dialog.findViewById(R.id.dialog_cancel)).setImageResource(R.drawable.ic_close);
		dialog.findViewById(R.id.dialog_cancel).setOnClickListener(v -> {
			startSongs(MediaPlayer.create(this, R.raw.click));
			dialog.dismiss();
		});
		dialog.show();
	}
	private void phoneOpen() {
		String txt = getString(R.string.phone_admin_txt),
				email = getString(R.string.phone_dev);
		final String emailF = email;
		final Dialog dialog = new Dialog(this, R.style.DialogStyle);
		dialog.setContentView(R.layout.dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		((TextView) dialog.findViewById(R.id.dialog_info)).setText(txt);
		Typeface typeface = Typeface.createFromAsset(ScreenActivity.this.getAssets(),
				(Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ?
						"fonts/naskh.ttf" : "fonts/casual.ttf"
		);
		((TextView) dialog.findViewById(R.id.dialog_info)).setTypeface(typeface);
		((TextView) dialog.findViewById(R.id.dialog_infoo)).setText(email);
		dialog.findViewById(R.id.dialog_infooo).setVisibility(View.GONE);
		((ImageButton) dialog.findViewById(R.id.dialog_ok)).setImageResource(R.drawable.popup_copy);
		dialog.findViewById(R.id.dialog_ok).setOnClickListener(v -> {
			startSongs(MediaPlayer.create(this, R.raw.click));
			Function.doCopy(ScreenActivity.this, emailF, getString(R.string.copy_phone_to_clip));
			dialog.dismiss();
		});
		((ImageButton) dialog.findViewById(R.id.dialog_cancel)).setImageResource(R.drawable.ic_close);
		dialog.findViewById(R.id.dialog_cancel).setOnClickListener(v -> {
			startSongs(MediaPlayer.create(this, R.raw.click));
			dialog.dismiss();
		});
		dialog.show();
	}
}
