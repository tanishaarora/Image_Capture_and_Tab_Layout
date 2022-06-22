package com.example.application2022.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.application2022.fragment.CompanyFragment;
import com.example.application2022.fragment.SchoolFragment;
import com.example.application2022.fragment.SportsFragment;

public class TabAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;

    public TabAdapter(Context cont, FragmentManager fragmentManager, int totalTab){
        super(fragmentManager);
        context = cont;
        this.totalTabs = totalTab;

    }

    public Fragment getItem(int position){
        switch (position){
            case 0:
                SportsFragment sportsFragment = new SportsFragment();
                return sportsFragment;
            case 1:
                SchoolFragment schoolFragment = new SchoolFragment();
                return schoolFragment;
            case 2:
                CompanyFragment companyFragment = new CompanyFragment();
                return companyFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
