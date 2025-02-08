package com.mohamed.barki.asl.lite.DataBase;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class DatabaseSupport {
    private static FirebaseApp INSTANCE;

    public static FirebaseApp getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = getSecondProject(context);
        }
        return INSTANCE;
    }
    private static FirebaseApp getSecondProject(Context context) {
        FirebaseOptions options1 = new FirebaseOptions.Builder()
                .setApiKey("AIzaSyAQeSk8xcPipQq2s15AdG_8M8ZL8rSy_Lc")
                .setApplicationId("1:555783393132:android:0f84559266aab92958ed49")
                .setProjectId("barkiasl")
                .setDatabaseUrl("https://barkiasl-default-rtdb.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(context, options1, "admin");
        return FirebaseApp.getInstance("admin");
    }
}
