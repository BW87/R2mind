package com.example.r2mind;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater = null;
    private TextView setName;
    private Switch onOffSwitch;
    private ArrayList<String> mData = new ArrayList<>();
    private int count = mData.size();

    @Override
    public int getCount(){
        return count;
    }
    @Override
    public Object getItem(int position){
        return mData.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final Context context = parent.getContext();
        final int pos = position;

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        convertView = inflater.inflate(R.layout.listitem, parent, false);

        setName = (TextView)convertView.findViewById(R.id.setName);
        onOffSwitch = (Switch)convertView.findViewById(R.id.onOffSwitch);

        setName.setText(mData.get(position));

        setName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateActivity.class);
                context.startActivity(intent);
            }
        });
        onOffSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked){
                if(isChecked) {
                    Toast.makeText(context, (pos + 1) + "번째 세트 on", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(context, (pos +1) + "번째 세트 off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }
    public void add(String item){
        mData.add(item);
        count++;
    }
}
