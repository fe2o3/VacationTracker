package com.example.rusty.myapplication.services;

import java.util.Calendar;

/**
 * Created by rusty on 9/25/2015.
 */
public class VacationYearService {
    private final int currentMonth;
    private final int currentDay;
    private final int currentYear;
    private static final int START_MONTH=6;
    private static final int START_DAY=1;
    public VacationYearService(){
        Calendar now=Calendar.getInstance();
        currentMonth =now.get(Calendar.MONTH);
        currentYear =now.get(Calendar.YEAR);
        currentDay =now.get(Calendar.DAY_OF_MONTH);
    }

    public Calendar getVacationYearStart(){
        Calendar cal=Calendar.getInstance();
       cal.clear();
        int startYear=currentYear;
        if(currentMonth<START_MONTH){
            startYear--;
        }
        cal.set(Calendar.YEAR,startYear);
        cal.set(Calendar.MONTH,START_MONTH);
        cal.set(Calendar.DAY_OF_MONTH,START_DAY);
        return cal;
    }
    public Calendar getVacationYearEnd(){
        Calendar cal=getVacationYearStart();
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal;
    }

    public long getDaysRemainingThisVacationYear(){
        Calendar now=Calendar.getInstance();
        now.clear();
        now.set(Calendar.YEAR, currentYear);
        now.set(Calendar.MONTH,currentMonth);
        now.set(Calendar.DAY_OF_MONTH,currentDay);

        Calendar end=getVacationYearEnd();

        long days=(end.getTimeInMillis()-now.getTimeInMillis())/(24*60*60*1000);
        return days+1;
    }
    public long getDaysInThisVacationYear(){
        Calendar start=getVacationYearStart();
        Calendar end= getVacationYearEnd();
        long days=(end.getTimeInMillis()-start.getTimeInMillis())/ (24 * 60 * 60 * 1000);
        return days;
    }
}
