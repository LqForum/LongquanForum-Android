/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package com.mobcent.discuz.activity;

import com.appbyme.dev.R;
import com.mobcent.discuz.base.constant.BaseIntentConstant;
import com.mobcent.discuz.base.constant.LocationProvider;
import com.mobcent.discuz.fragments.Discovery1Fragment;
import com.mobcent.discuz.fragments.Discovery2Fragment;
import com.mobcent.discuz.fragments.Discovery3Fragment;
import com.mobcent.discuz.fragments.Discuz1Fragment;
import com.mobcent.discuz.fragments.Discuz2Fragment;
import com.mobcent.discuz.fragments.Discuz3Fragment;
import com.mobcent.discuz.fragments.DiscuzFragment;
import com.mobcent.discuz.fragments.HomeFragment;
import com.mobcent.discuz.fragments.IWantKnowFragment;
import com.mobcent.discuz.fragments.Me1Fragment;
import com.mobcent.discuz.fragments.MeFragment;
import com.mobcent.discuz.fragments.ZhidaoFragment;
import com.mobcent.lowest.android.ui.module.plaza.constant.PlazaConstant;
import com.mobcent.discuz.android.constant.ConfigConstant;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class HomeActivity extends FragmentActivity implements BaseIntentConstant, PlazaConstant, ConfigConstant, View.OnClickListener {
    private String TAG;
    private Fragment[] fragment = new Fragment[4];
    private Fragment currentFragment;
    private Button mStateButton1;
    private Button mStateButton2;
    private Button mStateButton3;
    private Button mStateButton4;
    private int mState = 0;

    public HomeActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);
        LinearLayout tv = (LinearLayout) findViewById(R.id.bottomBox);
        mStateButton1 = (Button)tv.findViewById(R.id.first);
        mStateButton1.setOnClickListener(this);
        mStateButton1.setSelected(true);
        mStateButton2 = (Button)tv.findViewById(R.id.second);
        mStateButton2.setOnClickListener(this);
        mStateButton3 = (Button)tv.findViewById(R.id.third);
        mStateButton3.setOnClickListener(this);
        mStateButton4 = (Button)tv.findViewById(R.id.fourth);
        mStateButton4.setOnClickListener(this);
        tv.findViewById(R.id.nav_btn).setOnClickListener(this);
        LoginUtils.getInstance().init(this);
        LocationProvider.getInstance().init(this);
        fragment[0] = new HomeFragment();

        fragment[1] = new DiscuzFragment();
        String[] f1 = {"版块", "最新", "精华"};
        Fragment[] fg1 = {new Discuz1Fragment(), new Discuz2Fragment(), new Discuz3Fragment()};
        ((DiscuzFragment)fragment[1]).setTitles(f1);
        ((DiscuzFragment)fragment[1]).setFragments(fg1);

        String[] f2 = {"视界", "慈善", "动漫"};
        Fragment[] fg2 = {new Discovery1Fragment(), new Discovery2Fragment(), new Discovery3Fragment()};
        fragment[2] = new DiscuzFragment();
        ((DiscuzFragment)fragment[2]).setTitles(f2);
        ((DiscuzFragment)fragment[2]).setFragments(fg2);

        fragment[3] = new MeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment[0]).commit();
        currentFragment = fragment[0];
        switchState(0);
    }

    private void switchState(int state) {
        if (mState == state) {
            return;
        }

        mState = state;
        mStateButton1.setSelected(false);
        mStateButton2.setSelected(false);
        mStateButton3.setSelected(false);
        mStateButton4.setSelected(false);

        switch (mState) {
            case 0:
                mStateButton1.setSelected(true);
                break;

            case 1:
                mStateButton2.setSelected(true);
                break;

            case 2:
                mStateButton3.setSelected(true);
                break;

            case 3:
                mStateButton4.setSelected(true);
                break;

            default:
                break;
        }
        onTabChange(mState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.first:
                switchState(0);
                break;

            case R.id.second:
                switchState(1);
                break;

            case R.id.third:
                switchState(2);
                break;

            case R.id.fourth:
                switchState(3);
                break;

            case R.id.nav_btn:
                onPublic();
                break;

            default:
                break;
        }
    }

    private void onTabChange(int position) {
        if (currentFragment == fragment[position]) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (!fragment[position].isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.container, fragment[position]).commit();
        } else {
            transaction.hide(currentFragment).show(fragment[position]).commit();
        }
        currentFragment = fragment[position];
    }

    private void onPublic() {
        if (!LoginUtils.getInstance().isLogin()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }
        final Dialog dialog = new Dialog(this, R.style.mc_forum_home_publish_dialog);
        final LayoutInflater in = LayoutInflater.from(this);
        View view = in.inflate(R.layout.publish_dialog, null);
        view.findViewById(R.id.mc_forum_cancle_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.mc_forum_publish_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(HomeActivity.this, PublishTopicActivity.class);
                intent.putExtra("Type", "0");
                startActivity(intent);
            }
        });

        view.findViewById(R.id.mc_forum_pic_topic_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(HomeActivity.this, PublishTopicActivity.class);
                intent.putExtra("Type", "1");
                startActivity(intent);
            }
        });

        view.findViewById(R.id.mc_forum_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(HomeActivity.this, PublishTopicActivity.class);
                intent.putExtra("Type", "2");
                startActivity(intent);
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }
}
