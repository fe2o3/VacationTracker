package com.example.rusty.myapplication.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by rusty on 9/26/2015.
 */
public class TimeOffLog {
    private List<TimeOffEntry> vacationEntries =new ArrayList<TimeOffEntry>();
    private List<TimeOffEntry> sickEntries=new ArrayList<TimeOffEntry>();
    private Calendar updated;
    private Calendar hireDate;

    private Integer startingSickHours;

    public Integer getStartingSickHours() {
        return startingSickHours;
    }

    public void setStartingSickHours(Integer startingSickHours) {
        this.startingSickHours = startingSickHours;
    }


    public Calendar getHireDate() {
        return hireDate;
    }

    public void setHireDate(Calendar hireDate) {
        this.hireDate = hireDate;
    }

    public Calendar getUpdated() {
        return updated;
    }

    public void setUpdated(Calendar updated) {
        this.updated = updated;
    }

    public List<TimeOffEntry> getSickEntries() {
        return sickEntries;
    }

    public void setSickEntries(List<TimeOffEntry> sickEntries) {
        this.sickEntries = sickEntries;
    }

    public List<TimeOffEntry> getVacationEntries() {

        return vacationEntries;
    }

    public void setVacationEntries(List<TimeOffEntry> vacationEntries) {
        this.vacationEntries = vacationEntries;
    }
}
