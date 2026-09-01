package com.myassistant;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.myassistant.ui.BaiduFragment;
import com.myassistant.ui.DiscoverFragment;
import com.myassistant.ui.NovelFragment;
import com.myassistant.ui.MeFragment;
import com.myassistant.ui.WeixinFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Android 15 (API 35) / targetSdk 35 强制 edge-to-edge，系统会忽略
        // android:statusBarColor，状态栏永远透明。这里的做法是：让状态栏透出
        // activity_main.xml 根布局的黑色背景（"黑底"），并把系统图标设为浅色（"白字"）。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    getWindow().getDecorView().getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        bottomNav = findViewById(R.id.bottom_nav);

        MainPagerAdapter adapter = new MainPagerAdapter(this);
        adapter.addFragment(new WeixinFragment());
        adapter.addFragment(new BaiduFragment());
        adapter.addFragment(new NovelFragment());
        adapter.addFragment(new DiscoverFragment());
        adapter.addFragment(new MeFragment());
        viewPager.setAdapter(adapter);

        // 禁止 ViewPager2 滑动切换（更接近微信体验）
        viewPager.setUserInputEnabled(false);

        // ViewPager2 切换时同步底部导航
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (bottomNav.getSelectedItemId() != bottomNav.getMenu().getItem(position).getItemId()) {
                    bottomNav.setSelectedItemId(bottomNav.getMenu().getItem(position).getItemId());
                }
            }
        });

        // 底部菜单点击时切换 ViewPager2
        bottomNav.setOnItemSelectedListener(item -> {
            int position = getPositionById(item.getItemId());
            if (position >= 0) {
                viewPager.setCurrentItem(position, false);
            }
            return true;
        });
    }

    private int getPositionById(int itemId) {
        if (itemId == R.id.nav_weixin) return 0;
        if (itemId == R.id.nav_baidu) return 1;
        if (itemId == R.id.nav_novel) return 2;
        if (itemId == R.id.nav_explore) return 3;
        if (itemId == R.id.nav_me) return 4;
        return -1;
    }
}