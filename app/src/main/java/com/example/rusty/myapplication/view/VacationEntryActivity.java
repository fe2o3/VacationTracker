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

public class VacationEntryActivity extends AppCompatActivity implements DateEntryActivity {
    private Calendar selectedDate=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_entry);
        selectedDate=null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vacation_entry, menu);
        return true;
    }

@Override
    public void setDateChosen(Calendar cal){

        if(cal!=null) {

            TextView tv = (TextView) findViewById(R.id.date_entered);
            SimpleDateFormat fmt = new SimpleDateFormat("MMM/dd/yyyy");

            tv.setText(fmt.format(cal.getTime()));
            selectedDate=cal;
        }

    }
    public void createVacationEntry(View view){


        EditText editText= (EditText) findViewById(R.id.edit_hourss);
        if(editText.getText()!=null && selectedDate!=null) {
            String hours = editText.getText().toString();


            TimeOffDaoService timeOffDao = new TimeOffDaoService(view.getContext());
            TimeOffLog log = timeOffDao.loadData();

            TimeOffEntry toe = new TimeOffEntry();
            toe.setHoursUsed(Integer.parseInt(hours));
            toe.setDate(selectedDate);
            log.getVacationEntries().add(toe);
            timeOffDao.save(log);
            NavUtils.navigateUpFromSameTask(this);
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
        newFragment.show(getFragmentManager(), "vacationDate");
    }
}
