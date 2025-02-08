package com.mohamed.barki.asl.lite;

import static com.mohamed.barki.asl.lite.Function.setThemeDark;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohamed.barki.asl.lite.Adapter.MessageAdapter;
import com.mohamed.barki.asl.lite.DataBase.DatabaseSupport;
import com.mohamed.barki.asl.lite.Model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/** @noinspection rawtypes*/
@SuppressWarnings({"deprecation", "RedundantSuppression"})
public class ChatActivity extends AppCompatActivity {
    private String baa;

    @Override
    protected void onStop() {
        super.onStop();
        messageReference.removeEventListener(valueEventListenerMessage);
        Log.i(TAG, "ValueEventListener: onStop");
    }

    //layout
    private EditText edit_message;
    ImageButton bt_send;

    //lists
    RecyclerView listView;
    private ArrayList<Message> messages;
    private MessageAdapter adapter;

    //firebase
    DatabaseReference messageReference;
    private ValueEventListener valueEventListenerMessage;

    //data receiver
    String nameReceiver;

    //data sender
    private String nameSender;

    private String activity;
    //constants
    private static final String TAG = "ChatActivity";
    @SuppressLint("NotifyDataSetChanged")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!Function.isAdmin(this))
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        if(Function.getBoolean(this, "dark")) setThemeDark(this, R.layout.chat);
        else Function.setThemeLight(this, R.layout.chat);
        baa = "exitt";
        messageReference = FirebaseDatabase.getInstance(DatabaseSupport.getInstance(ChatActivity.this)).getReference();

        edit_message = findViewById(R.id.edit_message);
        bt_send = findViewById(R.id.bt_send);
        listView = findViewById(R.id.lv_chats);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);

        nameReceiver = "BarkiASL";

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                nameSender = null;
                activity = "ScreenActivity";
            } else {
                nameSender = extras.getString("name");
                activity = extras.getString("activity");
            }
        } else {
            nameSender = (String) savedInstanceState.getSerializable("name");
            activity = (String) savedInstanceState.getSerializable("activity");
        }

        ((TextView)findViewById(R.id.tvName_toolbar)).setText(nameReceiver);
        ((ImageView)findViewById(R.id.img_toolbar)).setImageResource(R.drawable.ic_admin);

        TextView tvTime = (Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ? findViewById(R.id.tvTime_toolbarR) : findViewById(R.id.tvTime_toolbarL);
        tvTime.setVisibility(View.VISIBLE);
        //setting listview and adapter
        messages = new ArrayList<>();
        adapter = new MessageAdapter(ChatActivity.this, messages, ChatActivity.this, tvTime, nameSender);
        listView.setAdapter(adapter);
        Typeface typeface = Typeface.createFromAsset(getAssets(),
                (Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ?
                        "fonts/naskh.ttf" : "fonts/casual.ttf"
        );
        ((TextView)findViewById(R.id.lastseen_toolbar)).setTypeface(typeface);
        tvTime.setText("");
        valueEventListenerMessage = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int l = messages.size();
                messages.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Message message = new Message(data.getKey(), data.child("time").getValue(String.class), data.child("name").getValue(String.class), data.child("message").getValue(String.class));
                    messages.add(message);
                }
                adapter.notifyDataSetChanged();
                if(l<messages.size() && messages.size()>1) listView.smoothScrollToPosition(messages.size()-1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };

        messageReference.child("support").child(nameSender).addValueEventListener(valueEventListenerMessage);

        //send message
        bt_send.setOnClickListener(v -> {
            MessageAdapter.boolAnim = false;
            baa = "exitt";
            String messageText = edit_message.getText().toString();
            if(messageText.isEmpty()){

            }else{
                Map<String, Object> message = new HashMap<>();
                message.put("time", Function.setTime());
                message.put("name", nameSender);
                message.put("message", messageText);

                //saving message for sender
                saveMessage(nameSender, message);
            }
        });
        pullToRefresh = findViewById(R.id.pullToRefresh);
    }
    SwipeRefreshLayout pullToRefresh;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        if (((int) event.getY() <= (getWindowManager().getDefaultDisplay().getHeight() / 2))){
            pullToRefresh.setEnabled(true);
            pullToRefresh.setOnRefreshListener(() -> {
                adapter.notifyDataSetChanged();
                pullToRefresh.setRefreshing(false);
            });
        } else {
            pullToRefresh.setEnabled(false);
            pullToRefresh.setOnRefreshListener(null);
            pullToRefresh.setRefreshing(false);
        }
        MessageAdapter.boolAnim = true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }
        return false;
    }
    /** @noinspection CallToPrintStackTrace*/
    private void saveMessage(String nameSender, Map<String, Object> message){
        try{
            String key = messageReference.child("support").child(nameSender).push().getKey();
            messageReference.child("support").child(nameSender)
                    .child(Objects.requireNonNull(key))
                    .setValue(message)
                    .addOnSuccessListener(unused -> edit_message.setText(""))
                    .addOnFailureListener(e ->Function.showToastMessage(ChatActivity.this, getString(R.string.send_failed)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (baa.equals("exit")) goToHome();
        else {
            Function.saveFromBoolean(this, "screen", false);
            Function.showToastMessage(this, activity.equals("ScreenActivity") ? getString(R.string.re_home) : getString(R.string.re_activity));
            baa = "exit";
        }
    }
    private void goToHome(){
        Function.saveFromText(this, "message", Function.setTime());
        messageReference.child("support").child(nameSender).removeEventListener(valueEventListenerMessage);
        if(activity.equals("ScreenActivity")){
            Intent intent = new Intent(ChatActivity.this, ScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else{
            Function.saveFromText(this, "message", Function.setTime());
            finish();
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("activity", activity);
        outState.putString("name", nameSender);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        activity = savedInstanceState.getString("activity");
        nameSender = savedInstanceState.getString("name");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /** @noinspection CallToPrintStackTrace*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            Function.trimCache(ChatActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {super.onResume();}
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {super.onPostCreate(savedInstanceState);}
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {super.onConfigurationChanged(newConfig);}
}
