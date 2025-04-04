package com.mohamed.barki.asl.lite.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.mohamed.barki.asl.lite.DataBase.DatabaseSupport;
import com.mohamed.barki.asl.lite.Function;
import com.mohamed.barki.asl.lite.Model.Message;
import com.mohamed.barki.asl.lite.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings({"deprecation", "RedundantSuppression", "SpellCheckingInspection", "DuplicateExpressions", "unused", "CallToPrintStackTrace", "ReassignedVariable", "ResultOfMethodCallIgnored", "SwitchStatementWithTooFewBranches", "LocalVariableUsedAndDeclaredInDifferentSwitchBranches", "EnhancedSwitchBackwardMigration", "StatementWithEmptyBody", "rawtypes", "StatementWithEmptyBody", "ConstantConditions", "EqualsBetweenInconvertibleTypes", "SuspiciousIndentAfterControlStatement"})
@SuppressLint({"NonConstantResourceId", "SuspiciousIndentation", "SetTextI18n", "StaticFieldLeak", "InflateParams", "MissingInflatedId", "MissingSuperCall", "NewApi", "NotifyDataSetChanged", "UseCompatTextViewDrawableApis", "SuspiciousIndentation", "ClickableViewAccessibility", "UseCompatTextViewDrawableApis", "ResourceAsColor", "CheckResult", "SetJavaScriptEnabled"})
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.myViewHolder> {
    Context context;
    ArrayList<Message> messages;
    Activity getActivity;
    TextView tvTime;
    String idSender;
    public MessageAdapter(Context context, ArrayList<Message> messages, Activity getActivity, TextView tvTime, String idSender) {
        this.context = context;
        this.messages = messages;
        this.getActivity = getActivity;
        this.tvTime = tvTime;
        this.idSender = idSender;
    }
    private static final int ITEM_LEFT = 1;
    private static final int ITEM_RIGHT = 2;

    @Override
    public int getItemViewType(int position) {
        if(messages == null) return 0;

        if(Function.isAdmin(getActivity)){
            if("BarkiASL".equals(messages.get(position).getName()))
                return 2;
            else
                return 1;
        }else{
            if("BarkiASL".contains(messages.get(position).getName()))
                return 1;
            else
                return 2;
        }
    }
    @NonNull
    @Override
    public MessageAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_RIGHT){
            return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_right, parent, false));
        }if(viewType == ITEM_LEFT){
            return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_left, parent, false));
        }else return null;
    }
    private int viewTypeInt;
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, final int position) {
        Function.saveFromText(getActivity, "message", Function.setTime());
        if(messages != null){
            //retrieving message
            Message message = messages.get(position);
            //building view from XML
            if(message.getName().equals("BarkiASL") && !Function.isAdmin(getActivity)){
                //recive admin message
                viewTypeInt = ITEM_LEFT;
                String[] time = Function.getTime(message.getTime());
                String[] timeNow = Function.getTime(Function.setTime());
                if((time[0]+time[1]+time[2]).equals(timeNow[0]+timeNow[1]+timeNow[2])) tvTime.setText(time[3]+":"+time[4]);
                else tvTime.setText(time[0]+"/"+time[1]+"/"+time[2]+" "+time[3]+":"+time[4]);
            }
            if(!message.getName().equals("BarkiASL") && Function.isAdmin(getActivity)){
                //recive user message
                viewTypeInt = ITEM_LEFT;
                holder.imgMessage.setImageResource(R.drawable.ic_name);
                String[] time = Function.getTime(message.getTime());
                String[] timeNow = Function.getTime(Function.setTime());
                if((time[0]+time[1]+time[2]).equals(timeNow[0]+timeNow[1]+timeNow[2])) tvTime.setText(time[3]+":"+time[4]);
                else tvTime.setText(time[0]+"/"+time[1]+"/"+time[2]+" "+time[3]+":"+time[4]);
            }
            if(!message.getName().equals("BarkiASL") && !Function.isAdmin(getActivity)){
                //user sent message
                viewTypeInt = ITEM_RIGHT;
                holder.imgMessage.setImageResource(R.drawable.ic_name);
            }

            holder.lnyLayout.setOnLongClickListener(view -> {
                Function.startSongs(context, MediaPlayer.create(context, R.raw.click));
                openDialog(message);
                return false;
            });
            holder.lnyLayout.setOnClickListener(v -> {
                Function.startSongs(context, MediaPlayer.create(context, R.raw.click));
                FrameLayout.LayoutParams params;
                if(boolLarge){
                    params =  new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, Math.min(Function.dpToPx(200), FrameLayout.LayoutParams.WRAP_CONTENT));
                }else{
                    params =  new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                }
                params.setMargins(Function.dpToPx(10), Function.dpToPx(10), Function.dpToPx(10), Function.dpToPx(10));
                holder.tvMessage.setLayoutParams(params);
                boolLarge = !boolLarge;
            });
            holder.tvMessage.setText(message.getMessage());
            animation(holder.itemView, viewTypeInt);
        }

    }
    public static boolean boolAnim = true;
    private void animation(View view, int viewType){
        if(boolAnim){
            Animation slideIn;
            if(viewType == ITEM_LEFT)
                slideIn = AnimationUtils.loadAnimation(getActivity, android.R.anim.slide_out_right);
            else
                slideIn = AnimationUtils.loadAnimation(getActivity, android.R.anim.slide_in_left);
            view.startAnimation(slideIn);
        }
    }
    private void openDialog(Message message) {
        boolAnim = false;
        final Dialog dialog = new Dialog(getActivity, R.style.DialogStyle);
        dialog.setContentView(R.layout.dialog_edit_or_remove);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        if(Function.isAdmin(getActivity)){
            dialog.findViewById(R.id.dialog_ok).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.dialog_cancel).setVisibility(View.VISIBLE);
        }
        dialog.findViewById(R.id.dialog_close).setOnClickListener(v -> dialog.dismiss());

        ((TextView) dialog.findViewById(R.id.dialog_info)).setText(getActivity.getString(R.string.edit_or_remove_message));

        Typeface typeface = Typeface.createFromAsset(getActivity.getAssets(),
                (Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ?
                        "fonts/ArefRuqaa.ttf" : "fonts/casual.ttf"
        );
        ((TextView) dialog.findViewById(R.id.dialog_info)).setTypeface(typeface);
        dialog.findViewById(R.id.dialog_ok).setOnClickListener(v -> {
            Function.startSongs(context, MediaPlayer.create(context, R.raw.click));
            editFile(message);
            dialog.dismiss();
        });
        dialog.findViewById(R.id.dialog_copy).setOnClickListener(v -> {
            Function.startSongs(context, MediaPlayer.create(context, R.raw.click));
            Function.doCopy(getActivity, message.getMessage(), getActivity.getString(R.string.copy_message_to_clip));
            dialog.dismiss();
        });
        dialog.findViewById(R.id.dialog_cancel).setOnClickListener(v -> {
            Function.startSongs(context, MediaPlayer.create(context, R.raw.click));
            openDialogRemove(message);
            dialog.dismiss();
        });
        dialog.show();
    }
    private void openDialogRemove(Message message) {
        boolAnim = false;
        final Dialog dialog = new Dialog(getActivity, R.style.DialogStyle);
        dialog.setContentView(R.layout.dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        ((TextView) dialog.findViewById(R.id.dialog_info)).setText(getActivity.getString(R.string.remove_message));
        ((TextView) dialog.findViewById(R.id.dialog_infoo)).setText(getActivity.getString(R.string.remove_this_message));
        Typeface typeface  = Typeface.createFromAsset(getActivity.getAssets(),
                (Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ?
                        "fonts/ArefRuqaa.ttf" : "fonts/casual.ttf"
        );
        ((TextView) dialog.findViewById(R.id.dialog_info)).setTypeface(typeface);
        ((TextView) dialog.findViewById(R.id.dialog_infoo)).setTypeface(typeface);
        ((ImageButton)dialog.findViewById(R.id.dialog_ok)).setImageResource(R.drawable.popup_remove);
        dialog.findViewById(R.id.dialog_ok).setOnClickListener(v -> saveRemovefile(dialog, message.getKey()));
        ((ImageButton)dialog.findViewById(R.id.dialog_cancel)).setImageResource(R.drawable.popup_remove_off);
        dialog.findViewById(R.id.dialog_cancel).setOnClickListener(v -> {
            Function.startSongs(context, MediaPlayer.create(context, R.raw.click));
            dialog.dismiss();
        });
        if(!getActivity.isFinishing()) dialog.show();
    }
    private void saveRemovefile(Dialog dialog, String refKey){
        int[] intString = new int[]{R.string.support_remove_succes, R.string.support_remove_failde};
        FirebaseDatabase SupportDatabase = FirebaseDatabase.getInstance(DatabaseSupport.getInstance(getActivity));

        SupportDatabase.getReference().child("support").child(idSender).child(refKey).removeValue()
                .addOnSuccessListener(unused -> {
                    Function.startSongs(context, MediaPlayer.create(context, R.raw.click));
                    notifyDataSetChanged();
                    Function.showToastMessage(getActivity, getActivity.getString(intString[0]));
                    dialog.dismiss();
                })
                .addOnFailureListener(e -> {
                    Function.startSongs(context, MediaPlayer.create(context, R.raw.error));
                    Function.showToastMessage(getActivity, getActivity.getString(intString[1]));
                });
    }
    private void editFile(Message message) {
        boolAnim = false;
        final Dialog dialog = new Dialog(getActivity, R.style.DialogStyle);
        dialog.setContentView(R.layout.dialog_edit_message);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Typeface typeface = Typeface.createFromAsset(getActivity.getAssets(),
                (Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي")) ?
                        "fonts/ArefRuqaa.ttf" : "fonts/casual.ttf"
        );
        ((TextView) dialog.findViewById(R.id.dialog_info)).setTypeface(typeface);

        final EditText edtMessage = dialog.findViewById(R.id.maindEditText2);

        edtMessage.setText(message.getMessage());

        dialog.findViewById(R.id.maindButtonPast).setOnClickListener(v -> Function.doPast(getActivity, edtMessage));

        dialog.findViewById(R.id.dialog_ok).setOnClickListener(v -> {
            String new_message = edtMessage.getText().toString();
            if(!new_message.isEmpty() && new_message.length()<200){
                saveEditFile(dialog, message.getKey(), message.getName(), message.getTime(), new_message);
            }else{
                Function.startSongs(context, MediaPlayer.create(context, R.raw.error));
                edtMessage.setError(Html.fromHtml("<font color='red'>" + getActivity.getString(R.string.error) + "</font>"));
            }
        });
        dialog.findViewById(R.id.dialog_cancel).setOnClickListener(v -> {
            Function.startSongs(context, MediaPlayer.create(context, R.raw.click));
            dialog.dismiss();
        });
        dialog.show();
    }
    private void saveEditFile(Dialog dialog, String refKey, String namee, String time, String newmessage){
        int[] intString = new int[]{R.string.support_edit_succes, R.string.support_edit_failde};

        Map<String, Object> map = new HashMap<>();
        map.put("time", time);
        map.put("name", namee);
        map.put("message", newmessage);

        FirebaseDatabase SupportDatabase = FirebaseDatabase.getInstance(DatabaseSupport.getInstance(getActivity));

        SupportDatabase.getReference().child("support").child(idSender).child(refKey).updateChildren(map)
                .addOnSuccessListener(unused -> {
                    Function.startSongs(context, MediaPlayer.create(context, R.raw.click));
                    dialog.dismiss();
                    notifyDataSetChanged();
                    Function.showToastMessage(getActivity, getActivity.getString(intString[0]));
                })
                .addOnFailureListener(e -> {
                    Function.startSongs(context, MediaPlayer.create(context, R.raw.click));
                    Function.showToastMessage(getActivity, getActivity.getString(intString[1]));
                });
    }
    private boolean boolLarge = false;
    @Override
    public int getItemCount() {return messages.size();}
    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView tvMessage;
        ImageView imgMessage;
        CardView cardView;
        LinearLayout lnyLayout;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);
            imgMessage = itemView.findViewById(R.id.img_message);
            lnyLayout = itemView.findViewById(R.id.lnyLayout);
            cardView = itemView.findViewById(R.id.cardViewSubTitle);
        }
    }
}