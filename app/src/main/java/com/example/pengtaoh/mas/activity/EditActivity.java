package com.example.pengtaoh.mas.activity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pengtaoh.mas.Constants;
import com.example.pengtaoh.mas.MasApplication;
import com.example.pengtaoh.mas.R;
import com.example.pengtaoh.mas.uicomp.SmoothProgressBar.SmoothProgressBar;
import com.example.pengtaoh.mas.utils.CommandUtil;
import com.example.pengtaoh.mas.utils.UHFClient;
import com.pengtaoh.mas.dao.DictBrandEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import uhf.api.CommandType;
import uhf.api.MultiLableCallBack;
import uhf.api.Query_epc;
import uhf.api.ShareData;

public class EditActivity extends BaseActivity implements MultiLableCallBack {


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
        Query_epc mQuery_epc = new Query_epc();
        UHFClient info = UHFClient.getInstance();
        if (info != null) {

            Boolean ret = UHFClient.mUHF.command(CommandType.SINGLE_QUERY_TAGS_EPC, mQuery_epc);
            if (ret) {
                String str_tmp = ShareData.CharToString(mQuery_epc.epc.epc, mQuery_epc.epc.epc.length);
                str_tmp = str_tmp.replace(" ", "");
                String str_rssi = "" + CommandUtil.rssi_calculate((char) mQuery_epc.rssi_msb, (char) mQuery_epc.rssi_lsb);

//                showMessage(str_tmp,str_rssi,false);
                String serialNumber = "";
                List<DictBrandEntity> list = MasApplication.getDaoSession().getDictBrandEntityDao()
                        .queryRaw(" where SerialNumber = ?", new String[]{serialNumber});
                if (list == null || list.isEmpty()) {
                    cardNumber.setText(getString(R.string.serial_number, serialNumber));
                }

            }
        }
    }

    @Override
    public void method(char[] data) {
        char msb = data[0];
        char lsb = data[1];
        int pc = (msb & 0x00ff) << 8 | (lsb & 0x00ff);
        pc = (pc & 0xf800) >> 11; //算出PC
        char[] tmp = new char[pc * 2];
        String str_tmp = ShareData.CharToString(tmp, tmp.length);
        Log.e("pengtaoH", "扫描结果=" + str_tmp);
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
