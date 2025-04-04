package com.mohamed.barki.asl.lite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings({"deprecation", "RedundantSuppression", "SpellCheckingInspection", "DuplicateExpressions", "unused", "CallToPrintStackTrace", "ReassignedVariable", "ResultOfMethodCallIgnored", "SwitchStatementWithTooFewBranches", "LocalVariableUsedAndDeclaredInDifferentSwitchBranches", "EnhancedSwitchBackwardMigration", "StatementWithEmptyBody", "rawtypes", "StatementWithEmptyBody", "ConstantConditions", "EqualsBetweenInconvertibleTypes", "SuspiciousIndentAfterControlStatement"})
@SuppressLint({"NonConstantResourceId", "SuspiciousIndentation", "SetTextI18n", "StaticFieldLeak", "InflateParams", "MissingInflatedId", "MissingSuperCall", "NewApi", "NotifyDataSetChanged", "UseCompatTextViewDrawableApis", "SuspiciousIndentation", "ClickableViewAccessibility", "UseCompatTextViewDrawableApis", "ResourceAsColor", "CheckResult", "SetJavaScriptEnabled"})
public class ManageSpaceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Function.showToastMessage(this, this.getString(R.string.dont_clear_data));
        finish();
    }
}