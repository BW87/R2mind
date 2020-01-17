package com.example.r2mind;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> listItem;
    private ArrayAdapter adapter;
    private Button acceptButton;
    private Button denyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hideNavigationBar();
        init();
        initListener();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);
        return true;
    }
    private void init(){
        listView =(ListView)findViewById(R.id.createListView);
        listItem = new ArrayList<String>();
        acceptButton = (Button)findViewById(R.id.accept);
        denyButton = (Button)findViewById(R.id.deny);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice,listItem);
        listView.setAdapter(adapter);

        listItem.add("지갑");
        listItem.add("가방");
        listItem.add("노트북");
        listItem.add("개념");
        listItem.add("우산");

        adapter.notifyDataSetChanged();
    }
    private void initListener(){
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.accept :
                        Toast.makeText(getApplicationContext(), "확인버튼 눌럿음", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.deny :
                        Toast.makeText(getApplicationContext(), "취소버튼 눌렀음", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        acceptButton.setOnClickListener(onClickListener);
        denyButton.setOnClickListener(onClickListener);
    }
    private void hideNavigationBar() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.d("is on?", "Turning immersive mode mode off. ");
        } else {
            Log.d("is on?", "Turning immersive mode mode on.");
        }
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }
}
