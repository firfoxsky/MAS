package com.example.pengtaoh.mas.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.pengtaoh.mas.R;
import com.example.pengtaoh.mas.utils.ShellUtils;
import com.example.pengtaoh.mas.utils.UHFClient;

import butterknife.BindView;
import butterknife.OnClick;
import uhf.api.CommandType;
import uhf.api.Ware;

public class MainActivity extends BaseActivity {

    @BindView(R.id.scanner)
    Button scanner;
    @BindView(R.id.edit)
    Button edit;
    @BindView(R.id.setting)
    Button setting;
    //2016.4.1 改  （新版P6300U）
    private String cmd_open = "echo 1 > /sys/devices/soc.0/xt_dev.68/xt_dc_in_en";
    private String cmd_close = "echo 0 > /sys/devices/soc.0/xt_dev.68/xt_dc_in_en";

    private String cmd_open1 = "echo on > /proc/uart3_3v3_en";
    private String cmd_close1 = "echo off > /proc/uart3_3v3_en";

    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    void initViews() {
        settingToolbar(R.id.toolbar, R.id.toolbar_title, false);

        //打开GPIO
        Boolean ret = ShellUtils.checkRootPermission();
        Log.e("TAG", "ret=" + ret);
        ShellUtils.CommandResult tt = ShellUtils.execCommand(cmd_open, ret);
        ShellUtils.execCommand(cmd_open1, ret);
        if (tt.result == 0) {

        }

        Ware mWare = new Ware(CommandType.GET_FIRMWARE_VERSION, 0, 0, 0);
        int count = 0;
        while (true) {
            UHFClient info = UHFClient.getInstance();
            if (info != null) {
                Boolean rett = UHFClient.mUHF.command(CommandType.GET_FIRMWARE_VERSION, mWare);
                if (rett) {
                    Log.e("TAG", "Ver." + mWare.major_version + "." + mWare.minor_version + "." + mWare.revision_version);
                    if (mWare.major_version == 1
                            && (mWare.minor_version == 0 || mWare.minor_version == 1
                            || mWare.minor_version == 2 || mWare.minor_version == 3
                    )
                            && (mWare.revision_version == 0 || mWare.revision_version == 1
                            || mWare.revision_version == 2 || mWare.revision_version == 3 ||
                            mWare.revision_version == 4 || mWare.revision_version == 5 ||
                            mWare.revision_version == 6 || mWare.revision_version == 7 ||
                            mWare.revision_version == 8 || mWare.revision_version == 9)
                            ) {
                        setTitle("设备获取成功");
//                        minfos.setText("Ver." + mWare.major_version + "." + mWare.minor_version + "." + mWare.revision_version);
                        break;
                    }
                }
            }

            count++;
            if (count > 5) {
                //setTitle(count);

                setTitle("连接失败，请重启设备");
                break;
            }
        }
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
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }

    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        finish();
        //关闭GPIO
        //打开GPIO
        Boolean ret = ShellUtils.checkRootPermission();
        Log.e("TAG", "ret=" + ret);
        ShellUtils.CommandResult tt = ShellUtils.execCommand(cmd_close, ret);
        ShellUtils.execCommand(cmd_close1, ret);
        if (tt.result == 0) {

        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    // 按键捕获
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 退出系统提示
    private void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                MainActivity.this);
        builder.setTitle("提示").setMessage("是否退出系统?").setCancelable(true)
                .setIcon(R.mipmap.ic_launcher).setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }).setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
