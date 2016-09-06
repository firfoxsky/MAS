package com.example.pengtaoh.mas.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pengtaoh.mas.MasApplication;
import com.example.pengtaoh.mas.R;
import com.pengtaoh.mas.dao.DictBrandEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by firefox on 2016/9/6.
 */
public class DetailActivity extends BaseActivity {


    @BindView(R.id.card_number)
    TextView cardNumber;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.attr)
    TextView attr;
    @BindView(R.id.category)
    TextView category;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.number)
    TextView number;

    private int count;
    private DictBrandEntity entity;

    @Override
    int getContentViewId() {
        return R.layout.activity_detail;
    }

    @Override
    void initViews() {
        settingToolbar(R.id.toolbar, R.id.toolbar_title, true);

        entity = (DictBrandEntity) getIntent().getSerializableExtra("item");
        count = getIntent().getIntExtra("count", 1);
        if (entity != null) {
            area.setText(entity.getArea());
            attr.setText(entity.getAttr());
            category.setText(entity.getType());
            desc.setText(entity.getDesc());
            name.setText(entity.getName());
            number.setText("" + count);

            delete.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.delete)
    public void deleteFromDB(View view) {

        MasApplication.getDaoSession().getDictBrandEntityDao().delete(entity);
        finish();
    }


}
