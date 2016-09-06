package com.example.pengtaoh.mas.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pengtaoh.mas.Constants;
import com.example.pengtaoh.mas.MasApplication;
import com.example.pengtaoh.mas.R;
import com.example.pengtaoh.mas.uicomp.SmoothProgressBar.SmoothProgressBar;
import com.example.pengtaoh.mas.utils.CommandUtil;
import com.example.pengtaoh.mas.utils.UHFClient;
import com.pengtaoh.mas.dao.DictBrandEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uhf.api.CommandType;
import uhf.api.Query_epc;
import uhf.api.ShareData;

public class EditActivity extends BaseActivity {


    @BindView(R.id.scanner_btn)
    Button scannerBtn;
    @BindView(R.id.card_number)
    TextView cardNumber;
    @BindView(R.id.progress_wheel)
    SmoothProgressBar progressWheel;
    @BindView(R.id.area)
    EditText area;
    @BindView(R.id.attr)
    EditText attr;
    @BindView(R.id.category)
    EditText category;
    @BindView(R.id.desc)
    EditText desc;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.name)
    EditText name;

    private DictBrandEntity currentEntity;


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
        if (item.getItemId() == R.id.action_add) {
            insertDB();
            return true;
        }
        return super.getToolbarRight(item);
    }

    @OnClick(R.id.scanner_btn)
    public void scanner(View view) {
        if (scannerBtn.getTag().equals(Constants.FLAG_NONE_SCANNER)) { //开始->结束
            startScanner();
        } else { //结束到开始
            closeScanner();
        }

    }

    @OnClick(R.id.delete)
    public void deleteFromDB(View view) {

        MasApplication.getDaoSession().getDictBrandEntityDao().delete(currentEntity);
        clearContent();
    }

    public void insertDB() {
        if (cardNumber.getText() == null || TextUtils.isEmpty(cardNumber.getText().toString())) {
            Toast.makeText(this, "no has serial number !!!", Toast.LENGTH_SHORT);
            return;
        }
        if (name.getText() == null || TextUtils.isEmpty(name.getText().toString())) {
            Toast.makeText(this, "no has product name !!!", Toast.LENGTH_SHORT);
            return;
        }
        if (currentEntity != null) {
            MasApplication.getDaoSession().getDictBrandEntityDao().delete(currentEntity);
        }
        DictBrandEntity entity = new DictBrandEntity();
        entity.setModelCreateTime(System.currentTimeMillis());
        if (area.getText() == null || TextUtils.isEmpty(area.getText().toString())) {
            entity.setArea("");
        } else
            entity.setArea(area.getText().toString());
        if (attr.getText() == null || TextUtils.isEmpty(attr.getText().toString())) {
            entity.setAttr("");
        } else
            entity.setAttr(attr.getText().toString());
        if (desc.getText() == null || TextUtils.isEmpty(desc.getText().toString())) {
            entity.setDesc("");
        } else
            entity.setDesc(desc.getText().toString());
        if (category.getText() == null || TextUtils.isEmpty(category.getText().toString())) {
            entity.setType("");
        } else
            entity.setType(category.getText().toString());
        entity.setName(name.getText().toString());
        entity.setSerialNumber(cardNumber.getText().toString());
        MasApplication.getDaoSession().getDictBrandEntityDao().insert(entity);
        finish();
    }

    public void startScanner() {
        scannerBtn.setTag(Constants.FLAG_SCANNER);
        scannerBtn.setText(R.string.end_scanner);
        progressWheel.setVisibility(View.VISIBLE);
        clearContent();

        Query_epc mQuery_epc = new Query_epc();
        UHFClient info = UHFClient.getInstance();
        if (info != null) {
            Boolean ret = UHFClient.mUHF.command(CommandType.SINGLE_QUERY_TAGS_EPC, mQuery_epc);
            if (ret) {
                String str_tmp = ShareData.CharToString(mQuery_epc.epc.epc, mQuery_epc.epc.epc.length);
                str_tmp = str_tmp.replace(" ", "");
                String str_rssi = "" +
                        CommandUtil.rssi_calculate((char) mQuery_epc.rssi_msb, (char) mQuery_epc.rssi_lsb);
                cardNumber.setText(str_tmp);
                showMessage(str_tmp, str_rssi);
                closeScanner();
            } else {
            }
        }
    }

    public void closeScanner() {
        scannerBtn.setTag(Constants.FLAG_NONE_SCANNER);
        scannerBtn.setText(R.string.start_scanner);
        progressWheel.setVisibility(View.GONE);

//        UHFClient info = UHFClient.getInstance();
//        if (info != null)
//            UHFClient.mUHF.command(CommandType.SINGLE_QUERY_TAGS_EPC, null);

    }

    public void showMessage(String str_tmp, String str_rssi) {
        List<DictBrandEntity> list = MasApplication.getDaoSession().getDictBrandEntityDao()
                .queryRaw(" where SERIAL_NUMBER = ?", new String[]{str_tmp});


        if (list != null && !list.isEmpty()) {
            currentEntity = list.get(0);
            area.setText(currentEntity.getArea());
            attr.setText(currentEntity.getAttr());
            category.setText(currentEntity.getType());
            desc.setText(currentEntity.getDesc());
            name.setText(currentEntity.getName());

            delete.setVisibility(View.VISIBLE);
        }

    }

    public void clearContent() {
        currentEntity = null;
        cardNumber.setText("");
        name.setText("");
        area.setText("");
        attr.setText("");
        category.setText("");
        desc.setText("");
    }

}
