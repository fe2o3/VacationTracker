package com.example.rusty.myapplication.model;

import java.util.Calendar;

/**
 * Created by rusty on 9/25/2015.
 */

public class TimeOffEntry {
    private int hoursUsed;
    private Calendar date;
    private Calendar entered=Calendar.getInstance();

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    private boolean hidden=false;




    public Calendar getEntered() {
        return entered;
    }

    public void setEntered(Calendar entered) {
        this.entered = entered;
    }

    public int getHoursUsed() {
        return hoursUsed;
    }

    public void setHoursUsed(int hoursUsed) {
        this.hoursUsed = hoursUsed;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
