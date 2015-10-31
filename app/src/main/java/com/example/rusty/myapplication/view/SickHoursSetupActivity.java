package com.example.rusty.myapplication.view;

import android.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rusty.myapplication.DateEntryActivity;
import com.example.rusty.myapplication.R;
import com.example.rusty.myapplication.model.TimeOffEntry;
import com.example.rusty.myapplication.model.TimeOffLog;
import com.example.rusty.myapplication.services.TimeOffDaoService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SickHoursSetupActivity extends AppCompatActivity  implements DateEntryActivity {
    private Calendar selectedDate=null;
    private SimpleDateFormat fmt = new SimpleDateFormat("MMM/dd/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sick_hours_setup);
        EditText editHoursText=(EditText)findViewById(R.id.edit_hours);
        TextView textDateView=(TextView)findViewById(R.id.date_entered);

        TimeOffDaoService timeOffDao = new TimeOffDaoService(getApplicationContext());
        TimeOffLog log = timeOffDao.loadData();
        if(log.getStartingSickHours()!=null) {
            editHoursText.setText(log.getStartingSickHours().toString());
        }
        if(log.getHireDate()!=null){
            selectedDate=log.getHireDate();
            textDateView.setText(fmt.format(log.getHireDate().getTime()));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sick_hours_setup, menu);
        return true;
    }

    @Override
    public void setDateChosen(Calendar cal){

        if(cal!=null) {

            TextView tv = (TextView) findViewById(R.id.date_entered);


            tv.setText(fmt.format(cal.getTime()));
            selectedDate=cal;
        }

    }
    public void showDatePickerDialog(View v) {
        Bundle b=new Bundle();

        Calendar now=Calendar.getInstance();
        b.putInt("year",now.get(Calendar.YEAR) );
        b.putInt("month",now.get(Calendar.MONTH) );
        b.putInt("day", now.get(Calendar.DAY_OF_MONTH));


        DialogFragment newFragment = new PickDateDialogFragment();
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "hireDate");
    }
    public void setStartingInfo(View view){


        if( selectedDate!=null) {
            Integer hours = parseHours(view);

            TimeOffDaoService timeOffDao = new TimeOffDaoService(view.getContext());
            TimeOffLog log = timeOffDao.loadData();
            log.setUpdated(Calendar.getInstance());
            log.setHireDate(selectedDate);
            log.setStartingSickHours(hours);
            timeOffDao.save(log);

            NavUtils.navigateUpFromSameTask(this);
        }

    }
    private Integer parseHours(View view){
        EditText editText= (EditText) view.findViewById(R.id.edit_hours);
        Integer val=null;
        if(editText!=null && editText.getText()!=null){
            try {
                val = Integer.parseInt(editText.getText().toString());
            }catch(NumberFormatException e){
                val=null;
            }
        }
        return val;
    }
}
