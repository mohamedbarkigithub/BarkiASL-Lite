package com.mohamed.barki.asl.lite;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

import androidx.annotation.NonNull;

@SuppressWarnings({"deprecation", "RedundantSuppression", "SpellCheckingInspection", "DuplicateExpressions", "unused", "CallToPrintStackTrace", "ReassignedVariable", "ResultOfMethodCallIgnored", "SwitchStatementWithTooFewBranches", "LocalVariableUsedAndDeclaredInDifferentSwitchBranches", "EnhancedSwitchBackwardMigration", "StatementWithEmptyBody", "rawtypes", "StatementWithEmptyBody", "ConstantConditions", "EqualsBetweenInconvertibleTypes", "SuspiciousIndentAfterControlStatement"})
@SuppressLint({"NonConstantResourceId", "SuspiciousIndentation", "SetTextI18n", "StaticFieldLeak", "InflateParams", "MissingInflatedId", "MissingSuperCall", "NewApi", "NotifyDataSetChanged", "UseCompatTextViewDrawableApis", "SuspiciousIndentation", "ClickableViewAccessibility", "UseCompatTextViewDrawableApis", "ResourceAsColor", "CheckResult", "SetJavaScriptEnabled"})
public class CustomTypefaceSpan extends TypefaceSpan {
    private final Typeface newType;
    public CustomTypefaceSpan(String family, Typeface type) {
        super(family);
        newType = type;
    }
    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        applyCustomTypeFace(ds, newType);
    }
    @Override
    public void updateMeasureState(@NonNull TextPaint paint) {
        applyCustomTypeFace(paint, newType);
    }
    private static void applyCustomTypeFace(Paint paint, Typeface tf) {
        int oldStyle;

        Typeface old = paint.getTypeface();

        if (old == null) oldStyle = 0; else oldStyle = old.getStyle();

        int fake = oldStyle & ~tf.getStyle();

        if ((fake & Typeface.BOLD) != 0) paint.setFakeBoldText(true);

        if ((fake & Typeface.ITALIC) != 0) paint.setTextSkewX(-0.25f);

        paint.setTypeface(tf);
    }
}
