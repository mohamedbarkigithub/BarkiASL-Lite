package com.mohamed.barki.asl.lite;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings({"deprecation", "RedundantSuppression", "SpellCheckingInspection", "DuplicateExpressions", "unused", "CallToPrintStackTrace", "ReassignedVariable", "ResultOfMethodCallIgnored", "SwitchStatementWithTooFewBranches", "LocalVariableUsedAndDeclaredInDifferentSwitchBranches", "EnhancedSwitchBackwardMigration", "StatementWithEmptyBody", "rawtypes", "StatementWithEmptyBody", "ConstantConditions", "EqualsBetweenInconvertibleTypes", "SuspiciousIndentAfterControlStatement"})
@SuppressLint({"NonConstantResourceId", "SuspiciousIndentation", "SetTextI18n", "StaticFieldLeak", "InflateParams", "MissingInflatedId", "MissingSuperCall", "NewApi", "NotifyDataSetChanged", "UseCompatTextViewDrawableApis", "SuspiciousIndentation", "ClickableViewAccessibility", "UseCompatTextViewDrawableApis", "ResourceAsColor", "CheckResult", "SetJavaScriptEnabled"})
public class UpdateActivity extends AppCompatActivity {
    String error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Function.showToastMessage(this, getString(R.string.app_crashed));
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                error = "null";
            }else{
                error = extras.getString("error");
            }
        }else{
            error = (String) savedInstanceState.getSerializable("error");
        }
        String name = "user";
        if(!Function.getValue(this, "name").isEmpty())
            name = Function.getValue(this, "name");
        Map<String, Object> map = new HashMap<>();
        map.put(name+Function.setTime(), error);

        openDialog(map);
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    private void openDialog(Map<String, Object> map) {
        Function.startSongs(this, MediaPlayer.create(this, R.raw.update));
        final Dialog dialog = new Dialog(UpdateActivity.this, R.style.DialogStyle);
        dialog.setContentView(R.layout.dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        ((TextView) dialog.findViewById(R.id.dialog_info)).setText(getString(R.string.save_app));
        ((TextView) dialog.findViewById(R.id.dialog_infoo)).setText(getString(R.string.update_this_app));
        ((TextView) dialog.findViewById(R.id.dialog_infooo)).setText(getString(R.string.update_app_offline));
        Typeface typeface = Typeface.createFromAsset(UpdateActivity.this.getAssets(),
                (Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ?
                        "fonts/naskh.ttf" : "fonts/casual.ttf"
        );
        ((TextView) dialog.findViewById(R.id.dialog_info)).setTypeface(typeface);
        ((TextView) dialog.findViewById(R.id.dialog_infoo)).setTypeface(typeface);
        ((TextView) dialog.findViewById(R.id.dialog_infooo)).setTypeface(typeface);
        ((ImageButton)dialog.findViewById(R.id.dialog_ok)).setImageResource(R.drawable.popup_download);
        dialog.findViewById(R.id.dialog_ok).setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName()));
            startActivity(browserIntent);
            dialog.dismiss();
            finishAffinity();
            finishAndRemoveTask();
        });
        dialog.findViewById(R.id.dialog_cancel).setVisibility(View.GONE);
        dialog.show();

        FirebaseDatabase.getInstance().getReference().child("app-crash").setValue(map)
                .addOnSuccessListener(unused -> Function.showToastMessage(this, getString(R.string.error_upload_successfull)))
                .addOnFailureListener(e ->Function.showToastMessage(this, getString(R.string.error_upload_failed)));
    }
    @Override
    public void onBackPressed() {}
}