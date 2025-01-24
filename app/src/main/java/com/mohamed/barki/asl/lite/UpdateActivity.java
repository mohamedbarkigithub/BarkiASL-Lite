package com.mohamed.barki.asl.lite;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings({"deprecation", "RedundantSuppression"})
public class UpdateActivity extends AppCompatActivity {
    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Function.showToastMessage(this, getString(R.string.app_crashed));
        openDialog();
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    private void openDialog() {
        final Dialog dialog = new Dialog(UpdateActivity.this, R.style.DialogStyle);
        dialog.setContentView(R.layout.dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        ((TextView) dialog.findViewById(R.id.dialog_info)).setText(getString(R.string.save_app));
        ((TextView) dialog.findViewById(R.id.dialog_infoo)).setText(getString(R.string.update_this_app));
        ((TextView) dialog.findViewById(R.id.dialog_infooo)).setText(getString(R.string.update_app_offline));
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/MolhimBold.ttf");
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
    }
    @Override
    public void onBackPressed() {}
}