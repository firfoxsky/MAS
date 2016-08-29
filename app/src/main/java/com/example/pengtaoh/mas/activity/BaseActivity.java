package com.example.pengtaoh.mas.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.pengtaoh.mas.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {


    protected Toolbar mToolbar;

    protected TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 只有3.0系统以上的手机才开始硬件加速功能
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        initViews();
    }

    protected void settingToolbar(int toolbarId, int titleId, boolean isBack) {
        View toolbarView = findViewById(toolbarId);
        if (toolbarView == null) {
            return;
        }
        mToolbar = (Toolbar) toolbarView;
        mToolbar.setTitle("");
        if (isBack)
            mToolbar.setNavigationIcon(R.mipmap.icon_back);
        setSupportActionBar(mToolbar);
        View titleView = findViewById(titleId);
        if (titleView == null) {
            return;
        }
        mTitle = (TextView) titleView;
        mTitle.setText(getTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else {
            return getToolbarRight(item);
        }
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }


    abstract int getContentViewId();

    abstract void initViews();

    protected boolean getToolbarRight(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
