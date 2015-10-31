package com.example.rusty.myapplication;

import android.content.Context;

import com.example.rusty.myapplication.model.TimeOffEntry;
import com.example.rusty.myapplication.model.TimeOffLog;
import com.example.rusty.myapplication.services.TimeOffDaoService;

import java.util.Calendar;
import java.util.List;

/**
 * Created by rusty on 9/25/2015.
 */
public class SickCalculator {

    private final Context context;
    public SickCalculator(Context context){
        this.context=context;
    }


    public int calculate(){
        TimeOffDaoService log=new TimeOffDaoService(context);

        TimeOffLog data = log.loadData();
        if(data==null){
            return 0;
        }
        List<TimeOffEntry> sickEntries = data.getSickEntries();
        Calendar hireDate=data.getHireDate();
if(hireDate==null){
    return 0;
}
        Integer startHours=data.getStartingSickHours();
        if(startHours==null){
            startHours=0;
        }
        int total=getTotalHours(hireDate,startHours);
        int used = sumEntriesHours(sickEntries);

       
        return getRemainingHours(total,used);
    }


    private int getRemainingHours(int totalHours,int usedHours){
        return totalHours-usedHours;
    }

    private int getTotalHours(Calendar start,Integer startHours){
        Calendar now=Calendar.getInstance();
        int startMonths=start.get(Calendar.YEAR)*12 + start.get(Calendar.MONTH);
        int currentMonths=now.get(Calendar.YEAR)*12+now.get(Calendar.MONTH);
        int elapsedMonths=currentMonths-startMonths +1;
        return startHours+(elapsedMonths*7);
    }

    private int sumEntriesHours(List<TimeOffEntry> entries){
        int runningTotal=0;

        for (TimeOffEntry entry:entries) {
            int hoursUsedThisEntry=entry.getHoursUsed();

            runningTotal= runningTotal+hoursUsedThisEntry;

        }
        return runningTotal;
    }

}
