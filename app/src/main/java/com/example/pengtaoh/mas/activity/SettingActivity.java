package com.example.pengtaoh.mas.activity;

import com.example.pengtaoh.mas.R;
import com.example.pengtaoh.mas.activity.BaseActivity;

public class SettingActivity extends BaseActivity {


    @Override
    int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    void initViews() {
        settingToolbar(R.id.toolbar, R.id.toolbar_title, true);
    }
}
