package com.example.pengtaoh.mas.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.pengtaoh.mas.Constants;
import com.example.pengtaoh.mas.MasApplication;
import com.example.pengtaoh.mas.R;
import com.example.pengtaoh.mas.adapter.ScannerAdapter;
import com.example.pengtaoh.mas.uicomp.SmoothProgressBar.SmoothProgressBar;
import com.example.pengtaoh.mas.utils.CommandUtil;
import com.example.pengtaoh.mas.utils.UHFClient;
import com.pengtaoh.mas.dao.DictBrandEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uhf.api.CommandType;
import uhf.api.MultiLableCallBack;
import uhf.api.Multi_query_epc;
import uhf.api.ShareData;

public class ScannerActivity extends BaseActivity implements MultiLableCallBack {

    @BindView(R.id.scanner_btn)
    Button scannerBtn;
    @BindView(R.id.progress_wheel)
    SmoothProgressBar progressWheel;
    private static Boolean isStart = false;
    @BindView(R.id.listView)
    ListView listView;
    private ArrayList<DictBrandEntity> receptionArrayList = new ArrayList<>();

    private ScannerAdapter mAdapter;


    @Override
    int getContentViewId() {
        return R.layout.activity_scanner;
    }

    @Override
    void initViews() {
        settingToolbar(R.id.toolbar, R.id.toolbar_title, true);
        scannerBtn.setTag(Constants.FLAG_NONE_SCANNER);
        mAdapter = new ScannerAdapter(this, receptionArrayList);
        listView.setAdapter(mAdapter);
    }

    @OnClick(R.id.scanner_btn)
    public void scanner(View view) {
        if (scannerBtn.getTag().equals(Constants.FLAG_NONE_SCANNER)) { //开始->结束
            scannerBtn.setTag(Constants.FLAG_SCANNER);
            scannerBtn.setText(R.string.end_scanner);
            progressWheel.setVisibility(View.VISIBLE);
            openTransfer();
        } else { //结束到开始
            scannerBtn.setTag(Constants.FLAG_NONE_SCANNER);
            scannerBtn.setText(R.string.start_scanner);
            progressWheel.setVisibility(View.GONE);
            closeTransfer();
        }
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();

        UHFClient info = UHFClient.getInstance();
        if (isStart && info != null) {
            Boolean ret = UHFClient.mUHF.command(CommandType.STOP_MULTI_QUERY_TAGS_EPC, null);
            if (ret) {
                setTitle("Stop Ok");
                isStart = false;
            } else {
                setTitle("Stop Fail");
            }
        }
        if (info != null) {
            UHFClient.mUHF.setCallBack(null);
            isStart = false;
        }
        //mUHF.transfer_close(mUHF);
    }

    @Override
    public void method(char[] data) {
        if (data.length <= 0) {
            return;
        }
        //把EPC拷贝出来显示
        char msb = data[0];
        char lsb = data[1];
        int pc = (msb & 0x00ff) << 8 | (lsb & 0x00ff);
        pc = (pc & 0xf800) >> 11;

        char[] tmp = new char[pc * 2];
        System.arraycopy(data, 2, tmp, 0, tmp.length);
        String str_tmp = ShareData.CharToString(tmp, tmp.length);
        str_tmp = str_tmp.replace(" ", "");
        Log.e("pengtaoH", "扫描结果=" + str_tmp);

        String str_rssi = "" +
                CommandUtil.rssi_calculate(data[2 + pc * 2], data[2 + pc * 2 + 1]);
        showMessage(str_tmp, str_rssi, false);
    }

    private void showMessage(final String str_tmp, final String rssi, final Boolean ishead) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boolean isContains = false;
                for (int i = 0; i < receptionArrayList.size(); i++) {
                    if (str_tmp.equals(receptionArrayList.get(i).getSerialNumber())) {
                        isContains = true;
                        return;

                    }
                }
                if (!isContains) {
                    List<DictBrandEntity> list = MasApplication.getDaoSession().getDictBrandEntityDao()
                            .queryRaw(" where SERIAL_NUMBER = ?", new String[]{str_tmp});
                    if (list == null || list.isEmpty()) {
                        return;
                    }

                    receptionArrayList.addAll(list);
                    mAdapter.notifyDataSetChanged();

                }

            }
        });
    }

    public void openTransfer() {


        Multi_query_epc mMulti_query_epc = new Multi_query_epc();
        mMulti_query_epc.query_total = 0;

        UHFClient info = UHFClient.getInstance();
        if (info != null) {
            UHFClient.mUHF.setCallBack(this);
            UHFClient.mUHF.command(CommandType.MULTI_QUERY_TAGS_EPC, mMulti_query_epc);
            isStart = true;
        }
    }

    public void closeTransfer() {
        UHFClient info = UHFClient.getInstance();
        if (info != null) {
            Boolean ret = UHFClient.mUHF.command(CommandType.STOP_MULTI_QUERY_TAGS_EPC, null);
            if (ret) {
                isStart = false;
            } else {
            }
        }
    }

}
