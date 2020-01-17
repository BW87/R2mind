package com.example.r2mind;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ListView customListView;
    private Button createButton;
    private CustomListViewAdapter customListViewAdapter = new CustomListViewAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ////////// 탭호스트 떄문에 추가한것들 나중에 삭제해야됨
        SectionPagerAdapter pagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        /////////////////////////////////////////
        hideNavigationBar();
        init();
        initListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);

        return true;
    }

    private void init() {
        customListView = (ListView) findViewById(R.id.listView);
        createButton = (Button) findViewById(R.id.createButton);
        for (int i = 1; i < 10; i++) {
            customListViewAdapter.add(i + "번째 Setting");
        }
        customListView.setAdapter(customListViewAdapter);
    }

    private void initListener() {
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.createButton:
                        Intent intent1 = new Intent(getApplicationContext(), CreateActivity.class);
                        startActivity(intent1);
                        break;
                }
            }
        };
        createButton.setOnClickListener(onClickListener);
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
    //탭호스트 때문에 만든 Inner 클래스 나중에 삭제해야됨~~~
    private class SectionPagerAdapter extends FragmentPagerAdapter{
        public SectionPagerAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public int getCount(){
            return 2;
        }
        @Override
        public Fragment getItem(int position){
            switch(position){
                case 0 :
                    return new fragment_top();
                case 1:
                    return new fragment_end();
            }
            return null;
        }
        @Override
        public CharSequence getPageTitle(int position){
            switch(position){
                case 0 :
                    return "test1";
                case 1:
                    return "test2";
            }
            return null;
        }
    }
}
