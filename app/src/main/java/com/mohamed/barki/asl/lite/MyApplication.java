package com.mohamed.barki.asl.lite;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.util.Log;

@SuppressWarnings({"deprecation", "RedundantSuppression", "SpellCheckingInspection", "DuplicateExpressions", "unused", "CallToPrintStackTrace", "ReassignedVariable", "ResultOfMethodCallIgnored", "SwitchStatementWithTooFewBranches", "LocalVariableUsedAndDeclaredInDifferentSwitchBranches", "EnhancedSwitchBackwardMigration", "StatementWithEmptyBody", "rawtypes", "StatementWithEmptyBody", "ConstantConditions", "EqualsBetweenInconvertibleTypes", "SuspiciousIndentAfterControlStatement"})
@SuppressLint({"NonConstantResourceId", "SuspiciousIndentation", "SetTextI18n", "StaticFieldLeak", "InflateParams", "MissingInflatedId", "MissingSuperCall", "NewApi", "NotifyDataSetChanged", "UseCompatTextViewDrawableApis", "SuspiciousIndentation", "ClickableViewAccessibility", "UseCompatTextViewDrawableApis", "ResourceAsColor", "CheckResult", "SetJavaScriptEnabled"})
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            Log.e("BarkiASL", "Uncaught exception is: ", ex);
            Intent myIntent = new Intent(getApplicationContext(), UpdateActivity.class);
            myIntent.putExtra("error", logError(ex));
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(myIntent);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        });
    }
    private String logError(final Throwable paramThrowable){
        try {
            StringBuilder stackTrace = new StringBuilder();
            for (int i = 0; i < paramThrowable.getStackTrace().length-1; i++) {
                stackTrace.append(paramThrowable.getStackTrace()[i].toString()).append("\n at");
            }
            stackTrace.append(paramThrowable.getStackTrace()[paramThrowable.getStackTrace().length-1].toString()).append("\n");

            Throwable tmp = paramThrowable;
            int j = 0;
            while ((tmp = tmp.getCause()) != null && j < 5) {
                j++;
                stackTrace.append("Coused by:\n");
                for (int i = 0; i < tmp.getStackTrace().length-1; i++) {
                    stackTrace.append(tmp.getStackTrace()[i].toString()).append("\n at");
                }
                stackTrace.append(tmp.getStackTrace()[tmp.getStackTrace().length-1].toString()).append(".");
            }
            return paramThrowable.getMessage()+"\n"+ stackTrace;
        }catch(Exception e){
            e.printStackTrace();
            return paramThrowable.getMessage();
        }
    }
}