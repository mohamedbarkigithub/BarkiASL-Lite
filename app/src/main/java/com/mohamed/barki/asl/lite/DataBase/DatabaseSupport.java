package com.mohamed.barki.asl.lite.DataBase;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SuppressWarnings("SpellCheckingInspection")
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
                .setApiKey("AIzaSyB1CKpxlrG8Y9AIkv3QH8otkpv3iSyMudM")
                .setApplicationId("1:702211601811:android:4a8aa9f7091d1e65e36466")
                .setProjectId("supportasl-33e46")
                .setDatabaseUrl("https://supportasl-33e46-default-rtdb.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(context, options1, "admin");
        return FirebaseApp.getInstance("admin");
    }
}
