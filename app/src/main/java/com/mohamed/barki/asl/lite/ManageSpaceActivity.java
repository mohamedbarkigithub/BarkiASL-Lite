package com.mohamed.barki.asl.lite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ManageSpaceActivity extends AppCompatActivity {
    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Function.showToastMessage(this, this.getString(R.string.dont_clear_data));
        finish();
    }
}