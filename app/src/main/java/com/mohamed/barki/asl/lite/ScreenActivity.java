package com.mohamed.barki.asl.lite;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

@SuppressWarnings({"deprecation", "RedundantSuppression"})
public class ScreenActivity extends AppCompatActivity implements OnClickListener
{
	private boolean boolExit;
	private Intent intent;
	ImageButton btn_dark, btn_rign, btn_song;
	private MediaPlayer click, clickScreen;
	boolean boolUpdate = true;
	DatabaseReference updateReference;
	ValueEventListener valueEventListener;
	public ScreenActivity() {}
	@SuppressLint("NonConstantResourceId")
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
			case R.id.screenButton3:
				startSongs(clickScreen);
				if(Function.isNetworkConnected(this)){
					String update = Function.setTime();
					FirebaseDatabase.getInstance().getReference().child("update")
							.setValue(update)
							.addOnSuccessListener(unused -> Function.showToastMessage(this, getString(R.string.success_update)))
							.addOnFailureListener(e -> Function.showToastMessage(this, getString(R.string.failure_update)));
				}
				break;
            case R.id.screenButton4:
				startSongs(click);
				Function.info(ScreenActivity.this, ScreenActivity.this); break;
        }
	}

	private void startActivityFun(Intent intent, String p1) {
		Function.saveFromText(this, "intent", p1);
		startActivity(intent);
	}
	@SuppressLint("SuspiciousIndentation")
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

		if(Function.getBoolean(this, "dark")) Function.setThemeDark(this, R.layout.screen);
		else Function.setThemeLight(this, R.layout.screen);

		//Function.checkPermission(ScreenActivity.this, ScreenActivity.this);

		if (Function.maxRam(ScreenActivity.this)>=1) {
			if (Function.getValue(ScreenActivity.this, "numImage").isEmpty()) {
				String numImage = Function.numIllustrations(this);
				Function.saveFromText(ScreenActivity.this, "numImage", numImage);
			}
		}

		click = MediaPlayer.create(ScreenActivity.this, R.raw.click);
		clickScreen = MediaPlayer.create(ScreenActivity.this, R.raw.click_screen);

		boolExit = false;
		
		intent = new Intent(ScreenActivity.this, ASLActivity.class);

		Button btn_game = findViewById(R.id.screenButton1);
		btn_game.setOnClickListener(this);
		btn_game.setBackgroundResource(R.drawable.button_screen);
		Button btn_search = findViewById(R.id.screenButton2);
		btn_search.setOnClickListener(this);
		btn_search.setBackgroundResource(R.drawable.button_screen);
		Button btn_update = findViewById(R.id.screenButton3);
		btn_update.setOnClickListener(this);

		ImageButton btn_info = findViewById(R.id.screenButton4);
		btn_info.setOnClickListener(this);

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
		if(Function.isAdmin(this)){
			btn_update.setVisibility(View.VISIBLE);
		}else{
			btn_update.setVisibility(View.GONE);
		}
    }

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
			updateReference.addValueEventListener(valueEventListener);
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		if(Function.isNetworkConnected(this)) updateReference.removeEventListener(valueEventListener);
	}
	private void openDialog() {
		boolUpdate = false;
		final Dialog dialog = new Dialog(ScreenActivity.this, R.style.DialogStyle);
		dialog.setContentView(R.layout.dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		((TextView) dialog.findViewById(R.id.dialog_info)).setText(getString(R.string.save_app));
		((TextView) dialog.findViewById(R.id.dialog_infoo)).setText(getString(R.string.save_this_app));
		((TextView) dialog.findViewById(R.id.dialog_infooo)).setText(getString(R.string.save_app_offline));
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/MolhimBold.ttf");
		((TextView) dialog.findViewById(R.id.dialog_info)).setTypeface(typeface);
		((TextView) dialog.findViewById(R.id.dialog_infoo)).setTypeface(typeface);
		((TextView) dialog.findViewById(R.id.dialog_infooo)).setTypeface(typeface);
		((ImageButton)dialog.findViewById(R.id.dialog_ok)).setImageResource(R.drawable.popup_download);
		dialog.findViewById(R.id.dialog_ok).setOnClickListener(v -> {
			Function.saveFromText(this, "update", Function.setTime());
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName()));
			startActivity(browserIntent);
			dialog.dismiss();
			finishAffinity();
			finishAndRemoveTask();
		});
		((ImageButton)dialog.findViewById(R.id.dialog_cancel)).setImageResource(R.drawable.popup_download_off);
		dialog.findViewById(R.id.dialog_cancel).setOnClickListener(v -> {
			Function.saveFromText(this, "update", Function.setTime());
			dialog.dismiss();
			boolUpdate = true;
		});
		if(!this.isFinishing()){
			Function.saveFromText(this, "update", Function.setTime());
			dialog.show();
		}
	}
	@SuppressLint("NewApi")
	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		if (boolExit) {
			if(Function.isNetworkConnected(this)) updateReference.removeEventListener(valueEventListener);
			finish();
			finishAffinity();
			finishAndRemoveTask();
		} else {
			Function.showToastMessage(this, getString(R.string.re_exit));
			boolExit = true;
		}
	}
	
	private void startSongs(MediaPlayer songs)
    {
        if(Function.getBoolean(this, "song")){
            songs.start();
        }
    }
}
