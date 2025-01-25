package com.mohamed.barki.asl.lite;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class AutoCompleteEditText extends androidx.appcompat.widget.AppCompatAutoCompleteTextView {
    public AutoCompleteEditText(Context context, AttributeSet attrs) {super(context, attrs);}
    private KeyImeChange keyImeChangeListener;
    public void setKeyImeChangeListener(KeyImeChange listener) {keyImeChangeListener = listener;}

    public interface KeyImeChange { void onKeyIme(int keyCode, KeyEvent event);}

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event)
    {
        if (keyImeChangeListener != null)
            keyImeChangeListener.onKeyIme(keyCode, event);
        return false;
    }
}