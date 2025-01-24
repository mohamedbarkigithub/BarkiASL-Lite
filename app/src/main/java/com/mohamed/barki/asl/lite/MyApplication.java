package com.mohamed.barki.asl.lite;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            Log.e("BarkiASL Lite", "Uncaught exception is: ", ex);
            Function.doCopy(getApplicationContext(), logError(ex));
            Intent myIntent = new Intent(getApplicationContext(), UpdateActivity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(myIntent);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        });
    }
    /** @noinspection CallToPrintStackTrace*/
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