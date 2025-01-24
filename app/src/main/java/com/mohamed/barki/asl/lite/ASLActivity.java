package com.mohamed.barki.asl.lite;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.InputDevice;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.ImageViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mohamed.barki.asl.lite.resactivity.ResActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

@SuppressWarnings({"deprecation", "RedundantSuppression"})
public class ASLActivity extends AppCompatActivity implements OnClickListener, OnLongClickListener{
	final String[][] resList = ResActivity.resList();
	private AutoCompleteTextView edt;
	private boolean autoDecrement = false, autoIncrement = false, autoLeftcrement = false, autoRightcrement = false, bool, boolPause = true, boolShow = true, boolVideo;
	private Dialog dialog;
	private EditText edtTime;
	private Handler handler;
	private final Handler repeatLeftRightHandler = new Handler(), repeatUpdateHandler = new Handler();
	private ImageButton btnShow, btnImage, btnVideo, btnLeft, btnRight, dimenDeafNegatif, dimenDeafPositif;
	private ImageView imageView, photoView;
	private int fSize, position = 0, intShow, intBut, startPhoto, endPhoto, scor, scor10, randomOfTest, nbrFalse;
	private final int nbrLenght = resList.length;
	private LinearLayout lnyBig, lnyBigText, lnyTextView, lnyTop, lnyBottom, lnyTextViewView, lnyEditText, lnyScor;
	private LinearLayout.LayoutParams paramsM, paramsMS, paramsMW, paramsM0;
	private MediaPlayer clear, timerepond, redo_undo, nonono, failText, succText, clicUp, clicDown, click, skip, tasfiq, refresh, aide, clickSpinner;
	private Runnable r;
	private Spinner s1;
	private String[] nameArFr, nameDomaine, nameDomaineAr, nameDomaineFr;
	private String search, searchPhoto, intent = "2", scortxt;
	private String[] nameArFrNoPal;
	private TextView tv, tvv, tvW, tvvW, tvScor;
	private WebView webView;
	private boolean boolSelect = true;
	private boolean boolExit;
	public ASLActivity() {}
	@Override
	public void onClick(View p1)
	{
		if(Function.validatePackageName(this)){return;
		}else if(Function.validateApplicationName(this)){return;}
		boolExit = false;
		fSize = Integer.parseInt(Function.getValue(ASLActivity.this, "dimenPhoto"));
		switch (p1.getId()) {
			case (R.id.maindButton2):
				if(intent.equals("1")){
					Function.startSongs(this, skip);
					gameButtonFun(1);
					break;
				}
				//عرض صورة إشارية
				Function.startSongs(this, click);
				btnVideo.setImageResource(R.drawable.ic_video);
				btnVideo.setBackgroundResource(R.drawable.button_f);
				if(Function.getValue(this, "screen").equals("false")){
					Function.doCopy(this, searchPhoto);
					Function.showToastMessage(this, searchPhoto);
				}
				Function.saveFromText(this, "type", "image");
				searchFun(search, "image");
				break;
			case (R.id.maindButton3):
				if(intent.equals("1")){
					Function.startSongs(this, aide);
					gameButtonFun(2);
					break;
				}
				//عرض فيديو إشاري
				Function.startSongs(this, click);
				if(Function.getValue(this, "type").equals("video")){
					webView.evaluateJavascript("AndroidApp.SimulateScreenTap();", null);
				}else{
					if(Function.getValue(this, "screen").equals("false")){
						Function.doCopy(this, searchPhoto);
						Function.showToastMessage(this, searchPhoto);
					}
					Function.saveFromText(this, "type", "video");
					searchFun(search, "video");
				}
				break;
			case (R.id.maindButton1):
				if(intent.equals("1")){
					Function.startSongs(this, refresh);
					gameButtonFun(3);
					break;
				}
				break;
			case (R.id.maindLinearLayout3): Function.startSongs(this, click);
				Function.doCopy(this, tv.getText().toString()+" / "+tvv.getText().toString());
				break;
			case (R.id.maindButtonN): Function.startSongs(this, clicDown);
				decrement(10);
				break;
			case (R.id.maindButtonP): Function.startSongs(this, clicUp);
				increment(10);
				break;
			case (R.id.maindButtonLeft): Function.startSongs(this, redo_undo);
				slideFun(1);
				break;
			case (R.id.maindButtonRight): Function.startSongs(this, redo_undo);
				slideFun(2);
				break;
		}
	}
	class RepetetiveUpdater implements Runnable {
		public void run() {
			long REPEAT_DELAY = 50;
			if( autoIncrement ){
				increment(5);
				repeatUpdateHandler.postDelayed( new RepetetiveUpdater(), REPEAT_DELAY);
			}else if( autoDecrement ){
				decrement(5);
				repeatUpdateHandler.postDelayed( new RepetetiveUpdater(), REPEAT_DELAY);
			}
		}
	}
	public void increment(int addSize){
		if(fSize <= Function.getScreenWidth()){
			fSize = fSize + addSize;
			Function.saveFromText(ASLActivity.this, "dimenPhoto", String.valueOf(fSize));
			paramDeaf(fSize, fSize+20*(fSize/100));
		}
	}
	public void decrement(int addSize){
		if(fSize >= Function.dpToPx(100)){
			fSize = fSize - addSize;
			Function.saveFromText(ASLActivity.this, "dimenPhoto", String.valueOf(fSize));
			paramDeaf(fSize, fSize+20*(fSize/100));
		}
	}
	@Override
	public boolean onLongClick(View p1) {
		fSize = Integer.parseInt(Function.getValue(ASLActivity.this, "dimenPhoto"));
		switch (p1.getId()) {
			case (R.id.maindButtonP):
				Function.startSongs(this, clicUp);
				autoIncrement = true;
				repeatUpdateHandler.post( new RepetetiveUpdater() );
				break;
			case (R.id.maindButtonN):
				Function.startSongs(this, clicDown);
				autoDecrement = true;
				repeatUpdateHandler.post( new RepetetiveUpdater() );
				break;
			case (R.id.maindButtonLeft):
				Function.startSongs(this, redo_undo);
				autoLeftcrement = true;
				repeatLeftRightHandler.post( new repetiveLeftRight() );
				break;
			case (R.id.maindButtonRight):
				Function.startSongs(this, redo_undo);
				autoRightcrement = true;
				repeatLeftRightHandler.post( new repetiveLeftRight() );
				break;
		}
		return false;
	}
	@SuppressLint("ClickableViewAccessibility")
    private void searchFun(String text, String type)
	{
		if(bool){bool = false; if(Function.getValue(this, "screen").equals("true")){Function.showToastMessage(this, getString(R.string.welcom)+" 😊😊");}else{Function.showToastMessage(this, String.valueOf(resList.length));} return;}
		Function.hideKeyboard(ASLActivity.this, edt);
		int nbr = resList.length;
		if(!Function.validate(ASLActivity.this, text, edt)){return;}
		boolVideo = false;
		if(intent.equals("2")) btnShow.setVisibility(View.GONE); else btnShow.setVisibility(ImageButton.VISIBLE);
		if(type.equals("image")){
			imageView = findViewById(R.id.maindImageView);
			imageView.setLayoutParams(paramsM);
			imageView.setVisibility(View.VISIBLE);
			if(webView!=null) webView.stopLoading();
			findViewById(R.id.maindWebView).setVisibility(View.GONE);
			if(intent.equals("1")) btnImage.setEnabled(true);
			btnVideo.setEnabled(true);
			lnyBig.setBackgroundColor(Color.BLACK);
		}else{
			webView = findViewById(R.id.maindWebView);
			webView.setLayoutParams(paramsM);
			webView.setVisibility(View.VISIBLE);
			findViewById(R.id.maindImageView).setVisibility(View.GONE);
			btnImage.setEnabled(true);
			btnVideo.setEnabled(true);
			lnyBig.setBackgroundColor(Color.WHITE);
		}
		if(intent.equals("2")) btnVideo.setImageResource(R.drawable.ic_video);
		btnVideo.setBackgroundResource(R.drawable.button_f);
		int colorInt = ContextCompat.getColor(this, R.color.button_f);
		ImageViewCompat.setImageTintList(btnVideo, ColorStateList.valueOf(colorInt));
		lnyBig.setPadding(Function.dpToPx(5), Function.dpToPx(5), Function.dpToPx(5), Function.dpToPx(5));
		int j=0;
		while(j<nbr){if(text.equals(nameArFr[j])){break;}else{j++;}}
		if(j<nbr){
			searchPhoto = resList[j][0];
			if(Function.isAdmin(this))
				if(Function.getBoolean(this, "notify"))
					Function.showToastMessage(this, searchPhoto);
			position = j;

			if(type.equals("image")) setPhotoToButtonPhoto(searchPhoto, imageView);
			else setVideoToVideoView(j);

			if(Function.getValue(this, "screen").equals("false")){Function.doCopy(this, searchPhoto);}
			if(intent.equals("2")){
				String txt=searchPhoto+"s";
				if(resList[j][2].endsWith("2") || resList[j][2].endsWith("3")){
					int k=0;
					while(k<j){
						if((resList[k][2].replace("1", "")).equals(resList[j][2].replace("2",""))){
							break;
						}else{
							if((resList[k][2].replace("1", "")).equals(resList[j][2].replace("3",""))){
								break;
							}else{k++;}
						}
					}
					txt=resList[k][0]+"s";
				}
				setPhotoToButtonImage(txt, photoView);
				btnLeft.setVisibility(View.VISIBLE);
				btnRight.setVisibility(View.VISIBLE);
			}else{startHandler();}
			tv.setText(resList[j][3]);
			tvv.setText(resList[j][2]);
			edt.setText("");
			edt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_menu_search, 0);
			dimenDeafPositif.setEnabled(true);
			dimenDeafNegatif.setEnabled(true);
			lnyTextViewView.setVisibility(View.VISIBLE);
			lnyTextView.setVisibility(View.VISIBLE);
			lnyBigText.setVisibility(View.VISIBLE);
			lnyTop.setVisibility(View.VISIBLE);
			lnyBottom.setVisibility(View.VISIBLE);
			btnImage.setVisibility(View.VISIBLE);
			btnVideo.setVisibility(View.VISIBLE);
			dimenDeafPositif.setVisibility(View.VISIBLE);
			dimenDeafNegatif.setVisibility(View.VISIBLE);
		}else {
			if (type.equals("image")) {imageView.setBackgroundResource(R.drawable.question_mark);}
			lnyTextView.setVisibility(View.INVISIBLE);
		}
		findViewById(R.id.maindButtonImage).setOnClickListener(v -> {
			if(photoView.getVisibility()==View.GONE){
				photoView.setVisibility(View.VISIBLE);
				findViewById(R.id.maindButtonImageInvisible).setVisibility(View.INVISIBLE);
				((ImageButton)findViewById(R.id.maindButtonImage)).setImageResource(R.drawable.ic_smalle);
			}else{
				photoView.setVisibility(View.GONE);
				findViewById(R.id.maindButtonImageInvisible).setVisibility(View.GONE);
				((ImageButton)findViewById(R.id.maindButtonImage)).setImageResource(R.drawable.ic_large);
			}
		});
		lnyBig.setVisibility(View.VISIBLE);
	}
	private void setPhotoToButtonPhoto(String path, ImageView imagView) {
		Glide.with(this)
				.load(getString(R.string.url_asset)+Function.serchFolder(path)+"/"+path+".br")
				.apply(new RequestOptions()
						.error(R.drawable.question_mark)
				).listener(new RequestListener<>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        new Handler().postDelayed(() -> {
                            if(intent.equals("2")){
								Function.saveFromText(ASLActivity.this, "type", "video");
								searchFun(search, "video");
							}
                        }, 1000);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(imagView);
	}
	private void setPhotoToButtonImage(String path, ImageView photoButton) {
		Glide.with(this)
				.load(getString(R.string.url_asset)+Function.serchFolder(path)+"/"+path+".br")
				.apply(new RequestOptions()
						.error(R.drawable.question_mark)
				).listener(new RequestListener<>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        photoButton.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.textPrimaryInverse, null)));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        photoButton.setBackgroundTintList(null);
                        return false;
                    }
                }).into(photoButton);
	}
	private String VIDEO_ID, VIDEO_NAME;
	private void setVideoToVideoView(int videoIndex) {
		VIDEO_ID = resList[videoIndex][1];
		VIDEO_NAME = resList[videoIndex][0] + ".MPG" ;

		if(VIDEO_ID.equals("url")){
			btnVideo.setImageResource(R.drawable.ic_video);
			btnVideo.setBackgroundResource(R.drawable.button_f);
			Function.saveFromText(this, "type", "image");
			searchFun(search, "image");
			if(Function.getValue(this, "screen").equals("true")){Function.showToastMessage(this, getString(R.string.no_video_fond));}
			return;
		}
		if(!Function.isNetworkConnected(this)){
			btnVideo.setImageResource(R.drawable.ic_video);
			btnVideo.setBackgroundResource(R.drawable.button_f);
			Function.saveFromText(this, "type", "image");
			searchFun(search, "image");
			if(Function.getValue(this, "screen").equals("true")){Function.showToastMessage(this, getString(R.string.not_connected_to_a_network));}
			return;
		}
		setWebView();
		if(intent.equals("2")){
			btnVideo.setImageResource(R.drawable.ic_video_play);
			int colorInt = ContextCompat.getColor(this, R.color.play);
			ImageViewCompat.setImageTintList(btnVideo, ColorStateList.valueOf(colorInt));
			btnVideo.setBackgroundResource(R.drawable.button_p);
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		if(webView!=null) webView.stopLoading();
	}
	/** @noinspection CallToPrintStackTrace*/
    @Override
	protected void onDestroy() {
		super.onDestroy();
		if(webView!=null) webView.destroy();
		try {
			Function.trimCache(ASLActivity.this);
		} catch (Exception e) {
            e.printStackTrace();
		}
	}
	@SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
	private void setWebView() {
		boolFinish = false;
		String colorFram = "black";
		if(Function.getBoolean(this, "dark"))
			colorFram = "white";

		String urlVideo = "<html><body style=\"background-color:"+colorFram+"\">"+
				"<iframe style=\"background-color:"+colorFram+"\"; title=\"" + VIDEO_NAME + "\"" +
				"id=\"drive-viewer-video-player-object-0\" src=\"https://drive.google.com/file/d/" + VIDEO_ID + "/preview?embedded=false\"" +
				"frameborder=\"0\" width=\"100%\" height=\"100%\" allow=\"autoplay\" title=\""+VIDEO_NAME+"\"></iframe>"+
				"</body></html>";

		WebSettings webSettings = webView.getSettings();
		webSettings.setPluginState(WebSettings.PluginState.ON);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setDatabaseEnabled(true);

		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDisplayZoomControls(false);
		webSettings.setEnableSmoothTransition(true);

		webView.setScrollbarFadingEnabled(true);
		webView.setVerticalScrollBarEnabled(true);
		webView.setHorizontalScrollBarEnabled(true);
		webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		webView.onCancelPendingInputEvents();


		webSettings.setSupportMultipleWindows(false);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
		webSettings.setMediaPlaybackRequiresUserGesture(false);


		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

		webSettings.setMixedContentMode(0);
		webSettings.setUserAgentString(System.getProperty("http.agent") + getString(R.string.app_name));


		CookieManager.getInstance().setAcceptCookie(true);
		CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);


		webView.setWebViewClient(new AutoPlayVideoWebViewClient());
		webView.setWebChromeClient(new WebChromeClient());

		webView.addJavascriptInterface(new JSInterface(), "AndroidApp");

		webView.loadData(urlVideo,  "text/html", "utf-8");

		webView.requestFocus();
		webView.setEnabled(false);
		webView.setFocusable(false);
		webView.setClickable(false);
		webView.setSelected(false);

		webView.setTouchscreenBlocksFocus(false);
		webView.setFilterTouchesWhenObscured(false);
		webView.setFocusableInTouchMode(false);
		webView.setOnTouchListener((v, event) -> false);
		if(intent.equals("2")){
			btnVideo.setImageResource(R.drawable.ic_video_play);
			btnVideo.setBackgroundResource(R.drawable.button_p);
		}
	}
	class JSInterface{
		@JavascriptInterface
		public void SimulateScreenTap(){
			ASLActivity.this.runOnUiThread(() -> {
				long delta = 100;
				long downTime = SystemClock.uptimeMillis();
				float x = (float) webView.getWidth() /2;
				float y = (float) webView.getHeight() /2;

				MotionEvent tapDownEvent = MotionEvent.obtain(downTime, downTime + delta, MotionEvent.ACTION_DOWN, x, y, 0);
				tapDownEvent.setSource(InputDevice.SOURCE_CLASS_POINTER);

				MotionEvent tapUpEvent = MotionEvent.obtain(downTime, downTime + delta + 2, MotionEvent.ACTION_UP, x, y, 0);
				tapUpEvent.setSource(InputDevice.SOURCE_CLASS_POINTER);

				webView.dispatchTouchEvent(tapDownEvent);
				webView.dispatchTouchEvent(tapUpEvent);
			});
		}
	}
	private boolean boolFinish = false;
	class AutoPlayVideoWebViewClient extends WebViewClient {
		@Nullable
		@Override
		public WebResourceResponse shouldInterceptRequest(WebView view, String url) {return super.shouldInterceptRequest(view, url);}
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {return true;}
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {super.onPageStarted(view, url, favicon);}
		@Override
		public void onPageFinished(final WebView view, String url) {
			super.onPageFinished(view, url);
			webView.evaluateJavascript("AndroidApp.SimulateScreenTap();", null);
			boolFinish = true;
		}
		@Override
		public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
			super.onReceivedError(view, request, error);
			if(!boolFinish)
				errorVideo();
		}
		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			super.onReceivedSslError(view, handler, error);
			if(!boolFinish)
				errorVideo();
		}
		@Override
		public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
			super.onReceivedHttpError(view, request, errorResponse);
			if(!boolFinish)
				errorVideo();
		}
	}
	private void errorVideo(){
		final Dialog dialog = new Dialog(this, R.style.DialogStyle);
		dialog.setContentView(R.layout.dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		((TextView) dialog.findViewById(R.id.dialog_info)).setText(getString(R.string.h1));
		((TextView) dialog.findViewById(R.id.dialog_infoo)).setText(getString(R.string.h2));
		((TextView) dialog.findViewById(R.id.dialog_infooo)).setText(getString(R.string.h4));
		dialog.findViewById(R.id.dialog_ok).setOnClickListener(v -> {
			Function.startSongs(this, click);
			setWebView();
			dialog.dismiss();
		});
		dialog.findViewById(R.id.dialog_cancel).setOnClickListener(v -> {
			Function.startSongs(this, click);
			dialog.dismiss();
		});
		dialog.show();
	}
	private void paramDeaf(int p0, int p1)
	{
		paramsM = new LinearLayout.LayoutParams(p0, p1);
		String type = Function.getValue(this, "type");
		if(type.equals("image")){if(imageView!=null){imageView.setLayoutParams(paramsM);}
		}else if(webView!=null){webView.setLayoutParams(paramsM);}
		paramsMS = new LinearLayout.LayoutParams(p1/2, p1/2);
		photoView.setLayoutParams(paramsMS);
	}
	@SuppressLint({"ClickableViewAccessibility", "SuspiciousIndentation"})
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

		intent = Function.getValue(this, "intent");
		if(Function.getBoolean(this, "dark"))
			Function.setThemeDark(this, R.layout.asl);
		else
			Function.setThemeLight(this, R.layout.asl);

		if(intent.equals("1")) (findViewById(R.id.gradientlayout)).setBackgroundResource(R.drawable.gradient_game);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		bool = true;
		boolExit = false;
		paramsMW = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		paramsM0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
		lnyTextViewView = findViewById(R.id.maindLinearLayout33);
		lnyTextViewView.setVisibility(View.INVISIBLE);
		lnyTextViewView.setLayoutParams(paramsM0);
		lnyEditText = findViewById(R.id.maindLinearLayoutEditText);
		lnyEditText.setVisibility(View.VISIBLE);
		lnyEditText.setLayoutParams(paramsMW);
		lnyBig = findViewById(R.id.maindLinearLayout1);
		lnyBigText = findViewById(R.id.maindLinearLayout4);
		lnyTextView = findViewById(R.id.maindLinearLayout3);
		lnyBig.setVisibility(View.INVISIBLE);
		lnyBigText.setVisibility(View.INVISIBLE);
		lnyTextView.setVisibility(View.INVISIBLE);
		lnyTop = findViewById(R.id.maindLinearLayoutTop);
		lnyBottom = findViewById(R.id.maindLinearLayoutBottom);
		lnyTop.setVisibility(View.INVISIBLE);
		lnyBottom.setVisibility(View.INVISIBLE);
		LinearLayout lnyText = findViewById(R.id.maindTextViewL1);
		LinearLayout lnyText2 = findViewById(R.id.maindTextViewL2);
		Typeface faceAr = Typeface.createFromAsset(getAssets(), "fonts/amiri.ttf");
		Typeface faceFr = Typeface.createFromAsset(getAssets(), "fonts/Noteworthy.ttf");
		tv = findViewById(R.id.maindTextView1);
		tv.setTypeface(faceAr);
		lnyText.setEnabled(false);
		tvv = findViewById(R.id.maindTextView2);
		tvv.setTypeface(faceFr);
		lnyText2.setEnabled(false);
		tvW = findViewById(R.id.maindTextView11);
		tvW.setTypeface(faceAr);
		tvW.setEnabled(false);
		tvvW = findViewById(R.id.maindTextView22);
		tvvW.setTypeface(faceFr);
		tvvW.setEnabled(false);
		lnyTextView.setOnClickListener(this);
		btnShow = findViewById(R.id.maindButton1);
		btnShow.setOnClickListener(this);
		if(intent.equals("2")) btnShow.setVisibility(View.GONE);
		btnImage = findViewById(R.id.maindButton2);
		btnImage.setOnClickListener(this);
		btnVideo = findViewById(R.id.maindButton3);
		btnVideo.setOnClickListener(this);
		btnImage.setEnabled(false);
		btnVideo.setEnabled(true);
		nameDomaine = ResActivity.nameDomaine;
		nameDomaineAr = ResActivity.nameDomaineAr;
		nameDomaineFr = ResActivity.nameDomaineFr;
		addStringArray();
		autoSpinnerAdapter();
		autoCompleteAdapter(nameArFr, nameArFrNoPal);
		search = "";
		Function.saveFromText(this, "type", "image");
		dimenDeafPositif = findViewById(R.id.maindButtonP);
		dimenDeafPositif.setOnClickListener(this);
		dimenDeafPositif.setOnLongClickListener(this);
		dimenDeafPositif.setOnTouchListener((v, event) -> {
			if( event.getAction() == MotionEvent.ACTION_UP && autoIncrement ){autoIncrement = false;}
			return false;
		});
		dimenDeafNegatif = findViewById(R.id.maindButtonN);
		dimenDeafNegatif.setOnClickListener(this);
		dimenDeafNegatif.setOnLongClickListener(this);
		dimenDeafNegatif.setOnTouchListener((v, event) -> {
			if( event.getAction() == MotionEvent.ACTION_UP && autoDecrement ){autoDecrement = false;}
			return false;
		});
		photoView = findViewById(R.id.maindButtonView);
		photoView.setEnabled(false);
		if(Function.getValue(ASLActivity.this, "dimenPhoto").isEmpty()){
			paramDeaf(500, 600);
			Function.saveFromText(ASLActivity.this, "dimenPhoto", "500");
		}else{
			int p = Integer.parseInt(Function.getValue(ASLActivity.this, "dimenPhoto"));
			paramDeaf(p, p+20*(p/100));
		}
		int pSize = Integer.parseInt(Function.getValue(ASLActivity.this, "dimenPhoto"));
		paramsM = new LinearLayout.LayoutParams(pSize, pSize +20*(pSize /100));
		paramsMS = new LinearLayout.LayoutParams(pSize /2,(pSize +20*(pSize /100))/2);
		btnLeft = findViewById(R.id.maindButtonLeft);
		btnLeft.setOnClickListener(this);
		btnLeft.setOnLongClickListener(this);
		btnLeft.setOnTouchListener((v, event) -> {
			if( event.getAction() == MotionEvent.ACTION_UP && autoLeftcrement ){autoLeftcrement = false;}
			return false;
		});
		btnRight = findViewById(R.id.maindButtonRight);
		btnRight.setOnClickListener(this);
		btnRight.setOnLongClickListener(this);
		btnRight.setOnTouchListener((v, event) -> {
			if( event.getAction() == MotionEvent.ACTION_UP && autoRightcrement ){autoRightcrement = false;}
			return false;
		});
		lnyScor = findViewById(R.id.mainLinearLayoutScor);
		lnyScor.setLayoutParams(paramsM0);
		initMediaPlayer();

		findViewById(R.id.imgSpinner1).setOnClickListener(view -> {
			if(intent.equals("2")) {
				edt.clearFocus();
				Function.hideKeyboard(ASLActivity.this, edt);
				Function.startSongs(this, click);
				boolSelect = false;
				while (position != s1.getSelectedItemPosition()) {
					s1.setSelection(position);
				}
			}
			boolSelect = true;
			s1.requestFocus();
			s1.performClick();
		});
		Typeface face = Typeface.createFromAsset(getAssets(), "fonts/casual.ttf");
		if(Locale.getDefault().getDisplayLanguage().contains("rab") || Locale.getDefault().getDisplayLanguage().contains("عربي"))
			face = Typeface.createFromAsset(getAssets(), "fonts/naskh.ttf");
		edt.setTypeface(face);
		if(intent.equals("1")){
			gameFun();
		}
	}
	@Override
	public void onUserInteraction() {
		super.onUserInteraction();
		if(Function.getValue(ASLActivity.this, "intent").equals("1")){
			stopHandler();
			Function.stopSongs(ASLActivity.this, timerepond);
			startHandler();
		}
	}
	public void stopHandler() {handler.removeCallbacks(r);}
	public void startHandler() {
		long REPEAT_USER_DELAY = 12;
		handler.postDelayed(r, REPEAT_USER_DELAY *1000);
	}
	private void slideFun(int k) {
		switch(k){
			case 2:
				if(position<nbrLenght-2){search = nameArFr[position+1];
				}else{search = nameArFr[1];}
				break;
			case 1:
				if(position>1){search = nameArFr[position-1];
				}else{search = nameArFr[nbrLenght-2];}
				break;
		}
		searchFun(search, Function.getValue(this, "type"));
	}
	/** @noinspection SpellCheckingInspection*/
    class repetiveLeftRight implements Runnable {
		public void run() {
			long REPEAT_LR_DELAY = 100;
			if( autoLeftcrement ){
				slideFun(1);
				repeatLeftRightHandler.postDelayed( new repetiveLeftRight(), REPEAT_LR_DELAY);
			}else if( autoRightcrement ){
				slideFun(2);
				repeatLeftRightHandler.postDelayed( new repetiveLeftRight(), REPEAT_LR_DELAY);
			}
		}
	}
	private void initMediaPlayer() {
		nonono = MediaPlayer.create(this, R.raw.nonono);
		clear = MediaPlayer.create(this, R.raw.clear);
		failText = MediaPlayer.create(this, R.raw.faill_text);
		succText = MediaPlayer.create(this, R.raw.succ_text);
		clicUp = MediaPlayer.create(this, R.raw.click_up);
		clicDown = MediaPlayer.create(this, R.raw.click_down);
		skip = MediaPlayer.create(this, R.raw.skip);
		redo_undo = MediaPlayer.create(this, R.raw.redo_undo);
		click = MediaPlayer.create(this, R.raw.click);
		tasfiq = MediaPlayer.create(this, R.raw.tasfiq);
		refresh = MediaPlayer.create(this, R.raw.refresh);
		aide = MediaPlayer.create(this, R.raw.aide);
		timerepond = MediaPlayer.create(this, R.raw.timerepond);
		clickSpinner = MediaPlayer.create(this, R.raw.click_spinner);
	}
	@SuppressLint("ResourceAsColor")
	private void gameFun() {
		photoView.setVisibility(View.GONE);
		findViewById(R.id.maindButtonImage).setVisibility(View.GONE);
		findViewById(R.id.maindButtonImageInvisible).setVisibility(View.GONE);
		edt.setHint(getString(R.string.enter_a_repons_term));
		lnyBig.setVisibility(View.VISIBLE);
		lnyBigText.setVisibility(View.VISIBLE);
		lnyTextView.setVisibility(View.VISIBLE);
		lnyTop.setVisibility(View.VISIBLE);
		lnyBottom.setVisibility(View.VISIBLE);
		lnyScor.setLayoutParams(paramsMW);
		lnyTextView.setLayoutParams(paramsM0);
		btnImage.setImageResource(R.drawable.ic_skip);
		btnImage.setEnabled(true);
		btnVideo.setImageResource(R.drawable.ic_aide);
		btnVideo.setEnabled(true);
		btnShow.setImageResource(R.drawable.ic_refresh);
		btnShow.setEnabled(true);
		btnLeft.setVisibility(View.GONE);
		btnRight.setVisibility(View.GONE);
		tvScor = findViewById(R.id.mainTextViewScor);
		if(Function.getValue(this, "scor").isEmpty()){Function.saveFromText(this, "scor", "0");}
		scor = Integer.parseInt(Function.getValue(this, "scor"));
		scor10 = 0;
		nbrFalse = 0;
		String scortxt = String.valueOf(scor);
		if (scor > 0) {scortxt = "+" + scortxt;}
		tvScor.setText(scortxt);
		bool = false;
		handler = new Handler();
		r = () -> {
			Function.startSongs(ASLActivity.this, timerepond);
			startHandler();
		};
		randomDeaf();
	}
	private void gameButtonFun(int key) {
		switch(key){
			case 1://skip
				randomDeaf();
				break;
			case 2://aide
				lnyTextView.setLayoutParams(paramsMW);
				scor = Integer.parseInt(Function.getValue(this, "scor"))-5;
				scor10 = 0;
				Function.saveFromText(this, "scor", String.valueOf(scor));
				scortxt = String.valueOf(scor);
				if(scor>0){scortxt = "+"+scortxt;}
				tvScor.setText(scortxt);
				new android.os.Handler().postDelayed(() -> lnyTextView.setLayoutParams(paramsM0), 500);
				break;
			case 3://refresh
				scor = 0;
				scor10 = 0;
				Function.saveFromText(this, "scor", "0");
				scortxt = String.valueOf(scor);
				tvScor.setText(scortxt);
				randomDeaf();
				break;
		}
	}
	@SuppressLint("SuspiciousIndentation")
    private void randomDeaf() {
		int min = 0;
		int max = resList.length - 2;
        do randomOfTest = randomGenerator(min, max);
        while (Objects.equals(resList[randomOfTest][1], "url") || resList[randomOfTest][1].isEmpty());
		search = nameArFr[randomOfTest];
		new android.os.Handler().postDelayed(() -> searchFun(nameArFr[randomOfTest], "image"), 100);
	}
	private int randomGenerator(int min, int max) {
		Random random = new Random();
		return random.nextInt((max-min)+1) + min;
	}
	private void testSameWord(String selection) {
		if(selection.equals(nameArFr[randomOfTest])){
			scorFun("1");
			new android.os.Handler().postDelayed(
					this::randomDeaf, 500);
		}else{
			edt.setText("");
			scorFun("0");
			if((nbrFalse>=10) && (nbrFalse%5==0)){
				scor = scor+scor10/2;
				scortxt = String.valueOf(scor);
				if(scor>0){scortxt = "+"+scortxt;}
				if(Function.getValue(this, "notify").equals("true")){
					Function.startSongs(this, nonono);
					final Dialog dialog = new Dialog(ASLActivity.this, R.style.DialogStyle);
					dialog.setContentView(R.layout.dialog_nonono);
					dialog.setCanceledOnTouchOutside(false);
					dialog.setCancelable(false);
					dialog.findViewById(R.id.dialog_close).setOnClickListener(v -> {
						Function.startSongs(ASLActivity.this, click);
						dialog.dismiss();
					});
					((TextView) dialog.findViewById(R.id.dialog_infoo)).setText(this.getString(R.string.more_error));
					((TextView) dialog.findViewById(R.id.dialog_nonono)).setText(this.getString(R.string.ask_if_you_want_learn));
					dialog.findViewById(R.id.dialog_yes).setOnClickListener(v -> {
						Function.startSongs(ASLActivity.this, click);
						Function.saveFromText(ASLActivity.this, "intent", "2");
						Function.startActivityFun(ASLActivity.this, ASLActivity.class);
						dialog.dismiss();
					});
					dialog.show();
				}
			}
		}
	}
	@SuppressLint({"SetTextI18n", "ResourceAsColor"})
	private void scorFun(String p0) {
		photoView.setVisibility(View.VISIBLE);
		switch(p0){
			case "0": Function.startSongs(this, failText);
				scor--;
				scor10 =0;
				nbrFalse++;
				photoView.setBackgroundResource(R.drawable.ic_false);
				break;
			case "1": Function.startSongs(this, succText);
				scor++;
				scor10++;
				nbrFalse=0;
				if(Function.getBoolean(this, "dark")){
					photoView.setBackgroundResource(R.drawable.ic_true);
				} else {
					photoView.setBackgroundResource(R.drawable.ic_true_light);
				}
				break;
		}
		scortxt = String.valueOf(scor);
		if(scor>0){scortxt = "+"+scortxt;}
		tvScor.setText(scortxt);
		Function.saveFromText(this, "scor", String.valueOf(scor));
		if(scor10!=0 && scor10%10 == 0){
			scor = scor+scor10/2;
			scortxt = String.valueOf(scor);
			if(scor>0){scortxt = "+"+scortxt;}
			if(Function.getValue(this, "notify").equals("true")){
				Function.startSongs(this, tasfiq);
				final Dialog dialog = new Dialog(ASLActivity.this, R.style.DialogStyle);
				dialog.setContentView(R.layout.dialog_congratulations);
				dialog.setCanceledOnTouchOutside(false);
				dialog.setCancelable(false);
				((TextView) dialog.findViewById(R.id.dialog_cong)).setText(getString(R.string.congrulation_0)+repoFun()+noteFun());
				dialog.show();
				new android.os.Handler().postDelayed(() -> {
							Function.stopSongs(ASLActivity.this, tasfiq);
							photoView.setVisibility(View.INVISIBLE);
							tvScor.setText(scortxt);
							dialog.dismiss();
						}, 4500);
			}else{
				new android.os.Handler().postDelayed(() -> {
							tvScor.setText(scortxt);
							photoView.setVisibility(View.INVISIBLE);
						}, 500);
			}
		}else{new android.os.Handler().postDelayed(() -> photoView.setVisibility(View.INVISIBLE), 500);}
	}
	private String repoFun() {
		if(scor10==10){return " 10 "+this.getString(R.string.congrulation_2);}
		else{return " "+ scor10 +" "+this.getString(R.string.congrulation_2);}
	}
	private String noteFun() {
		if(scor10==10){return " 5 "+this.getString(R.string.congrulation_3);}
		else if(scor10==20){return " 10 "+this.getString(R.string.congrulation_4);}
		else{return " "+ scor10 / 2 +" "+this.getString(R.string.congrulation_5);}
	}
	private void addStringArray(){
		nameArFr = new String[resList.length];
		nameArFr[0] = "";
		nameArFrNoPal = new String[resList.length];
		nameArFrNoPal[0] = "*";
		for(int i=1; i<resList.length; i++){
			nameArFr[i] = resList[i][3] + " / " + resList[i][2];
			nameArFrNoPal[i] = Function.toNoPalWord(nameArFr[i]);
		}
	}
	@SuppressLint("NewApi")
	private void autoSpinnerAdapter(){
		s1 = findViewById(R.id.maindSpinner1);
		s1.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
					if(boolSelect){
						Function.startSongs(ASLActivity.this, clickSpinner);
						String selection = (String)arg0.getItemAtPosition(arg2);
						if(intent.equals("1")){if(!selection.isEmpty()){testSameWord(selection);}
						}else{
							search = selection;
							searchFun(selection, Function.getValue(ASLActivity.this, "type"));
						}
					}
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {s1.setSelection(position);}
			});
		s1.setAdapter(new SpinnerAdapter(this, R.layout.custom_title_spinner, R.layout.custom_drop_down_spinner, nameArFr));
		s1.callOnClick();
	}
	public static class SpinnerAdapter extends ArrayAdapter<String> {
		private final String[] objects;
		private final int textViewTitleId, textViewResourceId;
		public SpinnerAdapter(Context context, int textViewTitleId, int textViewResourceId, String[] objects) {
			super(context, textViewResourceId, objects);
			this.objects=objects;
			this.textViewTitleId=textViewTitleId;
			this.textViewResourceId=textViewResourceId;
		}
		@Override
		public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
			View row = LayoutInflater.from(parent.getContext()).inflate(textViewResourceId, parent, false);
			final TextView Title= row.findViewById(R.id.custom_spinner);
			if(Function.getValue(getContext(), "intent").equals("1")){
				Title.setBackgroundResource(R.drawable.celll_game);
				Title.setTextColor(getContext().getColor(R.color.black));
			}
			Title.setGravity(Gravity.CENTER);
			Title.setText(objects[position]);
			Title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
			Title.setSingleLine(true);
			Title.setHorizontallyScrolling(true);
			Title.setHorizontalFadingEdgeEnabled(true);
			Title.setMarqueeRepeatLimit(-1);
			Title.setSelected(true);
			return row;
		}
		@NonNull
		@Override
		public View getView(int position, View convertView, @NonNull ViewGroup parent) {
			return getCustomViewUp(position, parent);
		}
		private View getCustomViewUp(int position, ViewGroup parent) {
			View row = LayoutInflater.from(parent.getContext()).inflate(textViewTitleId, parent, false);
			final TextView Title= row.findViewById(R.id.custom_spinner);
			Title.setText(objects[position]);
			row.setVisibility(View.INVISIBLE);
			return row;
		}
	}
	private Boolean boolClose = true;
	private void autoCompleteAdapter(String[] nam, String[] namNoPal){
		ArrayList<String> stringList = new ArrayList<>(Arrays.asList(nam));
		ArrayList<String> stringListNoPal = new ArrayList<>(Arrays.asList(namNoPal));
		AutoSuggestAdapter adapter = new AutoSuggestAdapter(ASLActivity.this, R.layout.list_item, stringList, stringListNoPal);
		edt = findViewById(R.id.maindEditText1);
		edt.setOnItemClickListener((parent, view, position, rowId) -> {
			Function.startSongs(ASLActivity.this, clickSpinner);
			String selection = (String)parent.getItemAtPosition(position);
			if(intent.equals("1")){if(!selection.isEmpty()){testSameWord(selection);}
			}else{
				search = selection;
				searchFun(selection, Function.getValue(ASLActivity.this, "type"));
			}
		});
		edt.setAdapter(adapter);
	    edt.setThreshold(2);
		TextWatcher textWatcher = new TextWatcher(){
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@SuppressLint("UseCompatTextViewDrawableApis")
			@Override
			public void afterTextChanged(Editable e) {
				int d, colorInt;
				if(edt.getText().toString().isEmpty()){
					d = R.drawable.ic_menu_search;
					colorInt = ContextCompat.getColor(ASLActivity.this, R.color.textPrimaryInverse);
					boolClose = false;
				}else{
					d = R.drawable.ic_delete;
					colorInt = ContextCompat.getColor(ASLActivity.this, R.color.button_f);
					boolClose = true;
				}
				edt.setCompoundDrawablesWithIntrinsicBounds(0, 0, d, 0);
				edt.setCompoundDrawableTintList(ColorStateList.valueOf(colorInt));
			}
		};
		edt.addTextChangedListener(textWatcher);
		View btnClose = findViewById(R.id.maindButtonClose);
		btnClose.setOnClickListener(v -> {
			if(boolClose){
				Function.startSongs(ASLActivity.this, clear);
				String txt = edt.getText().toString();
				if(!txt.isEmpty()){
					edt.setText(txt.substring(0, txt.length()-1));
					edt.setSelection(txt.length()-1);
				}else{
					edt.setSelection(0);
					Function.showKeyboard(ASLActivity.this, edt);
				}
			}else{
				if (Function.isSoftKeyboardShown(this, edt)) {
					Function.hideKeyboard(this, edt);
				} else {
					Function.showKeyboard(this, edt);
				}
			}
		});
		btnClose.setOnLongClickListener(p1 -> {
			Function.startSongs(ASLActivity.this, clear);
			edt.setText("");
			return false;
		});
	}
	/** @noinspection CallToPrintStackTrace*/
    @SuppressWarnings("rawtypes")
	public class AutoSuggestAdapter extends ArrayAdapter
	{
		private final Context      context;
		private final int          resource;
		private final ArrayList<String> items, tempItems, tempItemsNoPl, suggestions;
		@SuppressWarnings("unchecked")
		public AutoSuggestAdapter(Context context, int resource, ArrayList<String> items, ArrayList<String> itemsNoPal)
		{
			super(context, resource, items);
			this.context = context;
			this.resource = resource;
			this.items = items;
			tempItems = new ArrayList<>(items);
			tempItemsNoPl = new ArrayList<>(itemsNoPal);
			suggestions = new ArrayList<>();
		}
		@NonNull
		@SuppressLint({"ClickableViewAccessibility", "SuspiciousIndentation"})
		@Override
		public View getView(int position, View convertView, @NonNull ViewGroup parent)
		{
			View view = convertView;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(resource, parent, false);
			}
			view.setOnTouchListener((v, event) -> {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {Function.hideKeyboard(ASLActivity.this, edt);}
				return false;
			});
			String item = items.get(position);
			if(item != null){
				TextView Title = view.findViewById(R.id.listitemTextView);
				if(Function.getValue(getContext(), "intent").equals("1")){
					Title.setBackgroundResource(R.drawable.celll_game);
					Title.setTextColor(getContext().getColor(R.color.black));
				}
			    Title.setText(item);

				Title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
				Title.setSingleLine(true);
				Title.setHorizontallyScrolling(true);
				Title.setHorizontalFadingEdgeEnabled(true);
				Title.setMarqueeRepeatLimit(-1);
				Title.setSelected(true);
			}
			return view;
		}
		@NonNull
		@Override
		public Filter getFilter() {return nameFilter;}
		Filter nameFilter = new Filter() {
			@Override
			public CharSequence convertResultToString(Object resultValue) {return (String) resultValue;}
			@Override
			protected FilterResults performFiltering(CharSequence constraint)
			{
				suggestions.clear();
				FilterResults filterResults = new FilterResults();
				try {
					if (constraint != null && !constraint.equals("ال")) {
						try {
							int index = 0;
							for (String names : tempItemsNoPl)
							{
								if (names.contains(Function.toNoPalWord(constraint.toString())))
								{
									suggestions.add(tempItems.get(index));
								}
								index++;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else {
						notifyDataSetChanged();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				filterResults.values = suggestions;
				filterResults.count = suggestions.size();
				return filterResults;
			}
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				if (results != null && results.count > 0) {
					clear();
					addAll((ArrayList<String>) results.values);
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
	}
	@SuppressLint("NewApi")
	@Override
	public void onBackPressed()
	{
		if (boolExit) {
			if (intent.equals("1")) {
				stopHandler();
				r = null;
			}
			if(webView!=null) webView.stopLoading();
			Function.startActivityFun(this, ScreenActivity.class);
		} else {
			Function.showToastMessage(this, getString(R.string.re_exit));
			boolExit = true;
		}
	}
}


