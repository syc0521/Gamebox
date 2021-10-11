package com.example.gamebox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.gamebox.R;
import com.example.gamebox.fragment.GameFragment;
import com.example.gamebox.fragment.NewsFragment;
import com.example.gamebox.fragment.UserFragment;

public class MainActivity extends FragmentActivity
{
    private TabHost mTabHost;
    private String[] tags = {"page1", "page2", "page3"};
    private String[] titles = {"新闻", "游戏库", "个人中心"};
    private int[] picId = {R.drawable.news, R.drawable.game, R.drawable.user};
    private NewsFragment newsFragment;
    private GameFragment gameFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            WindowInsetsController insetsController = getWindow().getInsetsController();
            insetsController.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
        }
        else
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        mTabHost = findViewById(R.id.mTabHost);
        mTabHost.setup();
        for (int i = 0; i < titles.length; i++)
        {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tags[i]);
            View view = getLayoutInflater().inflate(R.layout.tab, null);
            ImageView imageView = view.findViewById(R.id.tabImage);
            TextView tv1 = view.findViewById(R.id.tabText);
            imageView.setBackgroundResource(picId[i]);
            tv1.setText(titles[i]);
            tabSpec.setIndicator(view);
            tabSpec.setContent(R.id.content);
            mTabHost.addTab(tabSpec);
        }
        mTabHost.setOnTabChangedListener(new MyTabChangedListener());
        mTabHost.setCurrentTab(1);
        mTabHost.setCurrentTab(0);
    }

    private class MyTabChangedListener implements TabHost.OnTabChangeListener
    {

        @Override
        public void onTabChanged(String tabId)
        {
            switch (tabId)
            {
                case "page1":
                    initNewsFragment();
                    break;
                case "page2":
                    initGameFragment();
                    break;
                case "page3":
                    initUserFragment();
                    break;
            }
        }

        private void hideFragment(FragmentTransaction transaction)
        {
            if (newsFragment != null)
            {
                transaction.hide(newsFragment);
            }
            if (gameFragment != null)
            {
                transaction.hide(gameFragment);
            }
            if (userFragment != null)
            {
                transaction.hide(userFragment);
            }
        }

        private void initGameFragment()
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (gameFragment == null)
            {
                gameFragment = new GameFragment();
                transaction.add(R.id.content, gameFragment);
            }
            hideFragment(transaction);
            transaction.show(gameFragment);
            transaction.commit();
        }

        private void initUserFragment()
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (userFragment == null)
            {
                userFragment = new UserFragment();
                userFragment.setArguments(getIntent().getBundleExtra("bundle"));
                transaction.add(R.id.content, userFragment);
            }
            hideFragment(transaction);
            transaction.show(userFragment);
            transaction.commit();
        }

        private void initNewsFragment()
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (newsFragment == null)
            {
                newsFragment = new NewsFragment();
                transaction.add(R.id.content, newsFragment);
            }
            hideFragment(transaction);
            transaction.show(newsFragment);
            transaction.commit();
        }

    }
}