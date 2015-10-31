package com.example.rusty.myapplication.view;



import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rusty.myapplication.R;
import com.example.rusty.myapplication.model.TimeOffEntry;
import com.example.rusty.myapplication.model.TimeOffLog;
import com.example.rusty.myapplication.services.TimeOffDaoService;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener{
    public static final String HOURS_INTENT="main.hours_intent.key";
    public static final String HOURS_INTENT_VACATION="main.purpose.hours_intent.vacation";
    public static final String HOURS_INTENT_SICK="main.purpose.hours_intent.sick";


    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //mViewPager.setOffscreenPageLimit(0);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                if(position==0) {
                    mSectionsPagerAdapter.notifyDataSetChanged();
                }

            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.action_new_vacation){
gotoNewVacation();
            return true;
        }
        if(id==R.id.action_new_sick){
            gotoNewSick();
            return true;
        }
if(id==R.id.dialog_set_sick_start_date){
            openHireDatePicker();
    return true;
        }

        if(id==R.id.menu_unhide_entries){
            unhideAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void gotoNewVacation(){
        Intent intent=new Intent(this,VacationEntryActivity.class);
        startActivity(intent);
    }
    private void unhideAll(){
        TimeOffDaoService dao=new TimeOffDaoService(getApplicationContext());
        TimeOffLog log=dao.loadData();
        for(TimeOffEntry toe:log.getVacationEntries()){
            toe.setHidden(false);
        }
        for(TimeOffEntry toe:log.getSickEntries()){
            toe.setHidden(false);
        }
        dao.save(log);
    }
private void openHireDatePicker(){
    Intent intent=new Intent(this,SickHoursSetupActivity.class);
    startActivity(intent);
//    android.app.DialogFragment newFragment = new HireDateDialogFragment();
//
//    newFragment.show(getFragmentManager(), "hireDate");
}

    private void gotoNewSick(){
        Intent intent=new Intent(this,SickEntryActivity.class);
        startActivity(intent);
    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
