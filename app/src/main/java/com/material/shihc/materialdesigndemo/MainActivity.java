package com.material.shihc.materialdesigndemo;

import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;

    private Toolbar mToolbar;

    private ActionBarDrawerToggle mDrawerToggle;

    private NavigationView mNavigationView;

    private View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        parentLayout = findViewById(R.id.parentLayout);

        //初始化toobar
        initToolbar();

        //初始化侧边栏
        initNavigation();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        initViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CategoryListFragment(), "Category 1");
        adapter.addFragment(new CategoryListFragment(), "Category 2");
        adapter.addFragment(new CategoryListFragment(), "Category 3");
        adapter.addFragment(new ScrollFragment(), "Category 4");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();

    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mToolbar.setTitle("首页");// 标题的文字需在setSupportActionBar之前，不然会无效
        // toolbar.setSubtitle("副标题");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /* 这些通过ActionBar来设置也是一样的，注意要在setSupportActionBar(toolbar);之后，不然就报错了 */
        // getSupportActionBar().setTitle("标题");
        // getSupportActionBar().setSubtitle("副标题");
        // getSupportActionBar().setLogo(R.drawable.ic_launcher);

        /* 菜单的监听可以在toolbar里设置，也可以像ActionBar那样，通过Activity的onOptionsItemSelected回调方法来处理
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });*/
    }

    private void initNavigation() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, mToolbar, 0, 0);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mNavigationView = (NavigationView) findViewById(R.id.nv_main_navigation);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //切换相应 Fragment 等操作
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawer(mNavigationView);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, "action_settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_share:
                Toast.makeText(MainActivity.this, "action_share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_show_snackbar:
                showSnackbar();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (mDrawerLayout.isDrawerOpen(mNavigationView)){
                mDrawerLayout.closeDrawer(mNavigationView);
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.snackbar_action:
                Toast.makeText(this, "click Snackbar action", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showSnackbar() {
        Snackbar
                .make(parentLayout, R.string.snackbar_text, Snackbar.LENGTH_LONG)
                .setAction(R.string.snackbar_action, this)
                .show(); // Don’t forget to show!
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
