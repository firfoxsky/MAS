package com.example.pengtaoh.mas;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pengtaoh.mas.uicomp.SmoothProgressBar.SmoothProgressBar;

import butterknife.BindView;
import butterknife.OnClick;
import jni.Linuxc;
import uhf.api.CommandType;
import uhf.api.Power;
import uhf.api.UHF;

public class ScannerActivity extends BaseActivity {

    @BindView(R.id.scanner_btn)
    Button scannerBtn;
    @BindView(R.id.progress_wheel)
    SmoothProgressBar progressWheel;

    private UHF mUHF;

    @Override
    int getContentViewId() {
        return R.layout.activity_scanner;
    }

    @Override
    void initViews() {
        settingToolbar(R.id.toolbar, R.id.toolbar_title, true);
        scannerBtn.setTag(Constants.FLAG_NONE_SCANNER);
    }

    @OnClick(R.id.scanner_btn)
    public void scanner(View view) {
        if (scannerBtn.getTag().equals(Constants.FLAG_NONE_SCANNER)) { //开始->结束
            scannerBtn.setTag(Constants.FLAG_SCANNER);
            scannerBtn.setText(R.string.end_scanner);
            progressWheel.setVisibility(View.VISIBLE);
            openTransfer();
        } else { //结束到开始
            scannerBtn.setTag(Constants.FLAG_SCANNER);
            scannerBtn.setText(R.string.start_scanner);
            progressWheel.setVisibility(View.GONE);
            closeTransfer();
        }
    }

    public void openTransfer() {
        mUHF = new UHF("102", Linuxc.BAUD_RATE_115200, 1, 0);
        mUHF.transfer_open(mUHF);
        Power mPower = new Power();
        mPower.com_type = CommandType.SET_POWER;
        mPower.loop = 0;
        mPower.read = 23;
        mPower.write = 30;
        boolean flag = mUHF.command(CommandType.SET_POWER, mPower);

        Log.e("pengtaoH", "  mdzz  " + flag);
    }

    public void closeTransfer() {
        try {
            if (mUHF != null)
                mUHF.transfer_close(mUHF);
        } catch (Exception e) {
            Toast.makeText(this, "please choose com_fd with sure !", Toast.LENGTH_SHORT).show();
        }
    }
}
