package com.example.pengtaoh.mas.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pengtaoh.mas.R;
import com.pengtaoh.mas.dao.DictBrandEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by firefox on 2016/8/29.
 */
public class ScannerAdapter extends BaseAdapter {

    private Context mContext;

    private List<DictBrandEntity> entityList;

    public ScannerAdapter(Context mContext, List<DictBrandEntity> entityList) {
        this.mContext = mContext;
        this.entityList = entityList;
    }

    @Override
    public int getCount() {
        return entityList == null ? 0 : entityList.size();
    }

    @Override
    public DictBrandEntity getItem(int position) {
        return entityList == null ? null : entityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BrandHolder hold;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_scanner_adapter, null);
            hold = new BrandHolder(convertView);
            convertView.setTag(hold);
        } else
            hold = (BrandHolder) convertView.getTag();

        DictBrandEntity entity = getItem(position);

        hold.name.setText(entity.getName());
        hold.number.setText(entity.getSerialNumber());
        hold.time.setText(longToString(entity.getModelCreateTime()));

        return convertView;
    }


    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public String longToString(long currentTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(new Date(currentTime));
    }

    public class BrandHolder {
        private TextView name;
        private TextView number;
        private TextView time;


        public BrandHolder(View view) {
            name = (TextView) view.findViewById(R.id.name);
            number = (TextView) view.findViewById(R.id.serial_number);
            time = (TextView) view.findViewById(R.id.time);
        }
    }
}
