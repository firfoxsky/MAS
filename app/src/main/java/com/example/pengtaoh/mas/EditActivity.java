package com.example.pengtaoh.mas;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pengtaoh.mas.uicomp.SmoothProgressBar.SmoothProgressBar;

import butterknife.BindView;
import butterknife.OnClick;

public class EditActivity extends BaseActivity {


    @BindView(R.id.scanner_btn)
    Button scannerBtn;
    @BindView(R.id.card_number)
    TextView cardNumber;
    @BindView(R.id.progress_wheel)
    SmoothProgressBar progressWheel;

    @Override
    int getContentViewId() {
        return R.layout.activity_edit;
    }

    @Override
    void initViews() {
        settingToolbar(R.id.toolbar, R.id.toolbar_title, true);
        scannerBtn.setTag(Constants.FLAG_NONE_SCANNER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    protected boolean getToolbarRight(MenuItem item) {
        if (item.getItemId() == R.menu.menu_add) {
            // TODO: 2016/8/17
            finish();
            return true;
        }
        return super.getToolbarRight(item);
    }

    @OnClick(R.id.scanner_btn)
    public void scanner(View view) {
        if (scannerBtn.getTag().equals(Constants.FLAG_NONE_SCANNER)) { //开始->结束
            scannerBtn.setTag(Constants.FLAG_SCANNER);
            scannerBtn.setText(R.string.end_scanner);
            progressWheel.setVisibility(View.VISIBLE);
        } else { //结束到开始
            scannerBtn.setTag(Constants.FLAG_SCANNER);
            scannerBtn.setText(R.string.start_scanner);
            progressWheel.setVisibility(View.GONE);
        }

    }
}
