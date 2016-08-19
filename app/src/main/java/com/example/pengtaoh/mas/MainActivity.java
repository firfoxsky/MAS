package com.example.pengtaoh.mas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uhf.api.UHF;

public class MainActivity extends BaseActivity {

    @BindView(R.id.scanner)
    Button scanner;
    @BindView(R.id.edit)
    Button edit;
    @BindView(R.id.setting)
    Button setting;


    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    void initViews() {
        settingToolbar(R.id.toolbar, R.id.toolbar_title, false);


    }

    @OnClick({R.id.edit, R.id.scanner, R.id.setting})
    void clickViews(View view) {
        switch (view.getId()) {
            case R.id.edit:
                startActivity(new Intent(this, EditActivity.class));
                break;
            case R.id.scanner:
                startActivity(new Intent(this, ScannerActivity.class));
                break;
            case R.id.setting:
                startActivity(new Intent(this, ScannerActivity.class));
                break;
        }
    }

}
