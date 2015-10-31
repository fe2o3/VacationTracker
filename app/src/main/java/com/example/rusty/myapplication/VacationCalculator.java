package com.example.rusty.myapplication;

import android.content.Context;

import com.example.rusty.myapplication.model.TimeOffEntry;
import com.example.rusty.myapplication.model.TimeOffLog;
import com.example.rusty.myapplication.services.TimeOffDaoService;
import com.example.rusty.myapplication.services.VacationYearService;

import java.util.Calendar;
import java.util.List;

/**
 * Created by rusty on 9/25/2015.
 */
public class VacationCalculator {


    private final int usedHoursThisYear;

    public VacationCalculator(Context context) {
        TimeOffDaoService log = new TimeOffDaoService(context);

        VacationYearService yearService = new VacationYearService();

        int used = 0;
        try {
            TimeOffLog data = log.loadData();
            List<TimeOffEntry> vacations = data.getVacationEntries();
            used = getUsedHours(vacations, yearService.getVacationYearStart());
        } catch (Exception e) {

        }
        usedHoursThisYear = used;
    }

    public int getRemainingHours() {


        int total = getTotalHours();


        return total - usedHoursThisYear;
    }


    public int getUsedHours() {
        return usedHoursThisYear;
    }


    public int getTotalHours() {
        return 22 * 7;
    }

    private int getUsedHours(List<TimeOffEntry> vacations, Calendar sinceDate) {
        int runningTotal = 0;

        for (TimeOffEntry entry : vacations) {
            if (!entry.getDate().before(sinceDate)) {
                int hoursUsedThisEntry = entry.getHoursUsed();

                runningTotal = runningTotal + hoursUsedThisEntry;
            }
        }
        return runningTotal;
    }

}
