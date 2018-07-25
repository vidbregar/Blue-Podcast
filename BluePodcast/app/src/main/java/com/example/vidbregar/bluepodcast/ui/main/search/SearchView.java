package com.example.vidbregar.bluepodcast.ui.main.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.example.vidbregar.bluepodcast.ui.main.search.listener.OnKeyDownListener;

public class SearchView extends android.support.v7.widget.AppCompatEditText {

    private OnKeyDownListener onKeyDownListener;

    public SearchView(Context context) {
        super(context);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            onKeyDownListener.onKeyDown();
        }
        return super.dispatchKeyEvent(event);
    }

    public void setOnKeyDownListener(OnKeyDownListener onKeyDownListener) {
        this.onKeyDownListener = onKeyDownListener;
    }
}
