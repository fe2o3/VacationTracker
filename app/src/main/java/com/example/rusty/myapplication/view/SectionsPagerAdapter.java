package com.example.rusty.myapplication.view;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);


    }
    @Override public int getItemPosition(Object object){

if(object instanceof SummaryFragment) {
    //notifyDataSetChanged();
    ((SummaryFragment)object).refreshData();

}
        return POSITION_UNCHANGED;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SummaryFragment();
            case 1:
                return new VacationHistoryFragment();
            case 2:
                return new SickHistoryFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Summary";
            case 1:
                return "Vacation";
            case 2:
                return "Sick Leave";
        }
        return null;
    }
}
