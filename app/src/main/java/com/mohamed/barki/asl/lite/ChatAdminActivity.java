package com.mohamed.barki.asl.lite;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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

@SuppressWarnings({"deprecation", "RedundantSuppression"})
public class ChatAdminActivity extends AppCompatActivity {
    private String baa;

    @Override
    protected void onStop() {
        super.onStop();
        messageReference.child("support").child(nameReceiver).removeEventListener(valueEventListenerMessage);
        Log.i(TAG, "ValueEventListener: onStop");
    }
    private EditText edit_message;
    ImageButton bt_send;
    RecyclerView listView;
    private ArrayList<Message> messages;
    private MessageAdapter adapter;
    DatabaseReference messageReference;
    ValueEventListener valueEventListenerMessage;
    //data receiver
    String nameReceiver;
    private String nameSender;
    private String activity;
    private static final String TAG = "ChatAdminActivity";
    @SuppressLint("NotifyDataSetChanged")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Function.getBoolean(this, "dark")) Function.setThemeDark(this, R.layout.chat);
        else Function.setThemeLight(this, R.layout.chat);
        baa = "exitt";

        messageReference = FirebaseDatabase.getInstance(DatabaseSupport.getInstance(ChatAdminActivity.this)).getReference();

        edit_message = findViewById(R.id.edit_message);
        bt_send = findViewById(R.id.bt_send);
        listView = findViewById(R.id.lv_chats);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                nameReceiver = null;
                activity = "ScreenActivity";
            } else {
                nameReceiver = extras.getString("name");
                activity = extras.getString("activity");
            }
        } else {
            nameReceiver = (String) savedInstanceState.getSerializable("name");
            activity = (String) savedInstanceState.getSerializable("activity");
        }

        nameSender = "BarkiASL";

        ((TextView)findViewById(R.id.tvName_toolbar)).setText(nameReceiver);
        if(!nameReceiver.equals(Function.getApplicationName(this))) ((ImageView)findViewById(R.id.img_toolbar)).setImageResource(R.drawable.ic_name);

        TextView tvTime = (Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ? findViewById(R.id.tvTime_toolbarR) : findViewById(R.id.tvTime_toolbarL);
        tvTime.setVisibility(View.VISIBLE);

        messages = new ArrayList<>();
        adapter = new MessageAdapter(ChatAdminActivity.this, messages, ChatAdminActivity.this, tvTime, nameReceiver);
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

        messageReference.child("support").child(nameReceiver).addValueEventListener(valueEventListenerMessage);

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
                saveMessage(nameReceiver, message);
            }
        });
        pullToRefresh = findViewById(R.id.pullToRefresh);
    }
    SwipeRefreshLayout pullToRefresh;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
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
    private void saveMessage(String nameReceiver, Map<String, Object> message){
        try{
            String key = messageReference.child("support").child(nameSender).push().getKey();
            messageReference.child("support").child(nameReceiver)
                    .child(Objects.requireNonNull(key))
                    .setValue(message)
                    .addOnSuccessListener(unused -> edit_message.setText(""))
                    .addOnFailureListener(e ->Function.showToastMessage(ChatAdminActivity.this, getString(R.string.send_failed)));
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
            Function.showToastMessage(this, getString(R.string.re_home));
            baa = "exit";
        }
    }
    private void goToHome(){
        messageReference.removeEventListener(valueEventListenerMessage);
        if(activity.equals("ScreenActivity")){
            Intent intent = new Intent(ChatAdminActivity.this, ScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else
            finish();
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("activity", activity);
        outState.putString("name", nameReceiver);
        outState.putString("admin", nameSender);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        activity = savedInstanceState.getString("activity");
        nameReceiver = savedInstanceState.getString("name");
        nameSender = savedInstanceState.getString("admin");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
