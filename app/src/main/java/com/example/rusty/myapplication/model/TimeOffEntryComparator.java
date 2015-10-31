package com.example.rusty.myapplication.model;

import java.util.Comparator;

/**
 * Created by rusty on 10/3/2015.
 */
public class TimeOffEntryComparator implements Comparator<TimeOffEntry> {
    @Override
    public int compare(TimeOffEntry lhs, TimeOffEntry rhs) {
        if(lhs==null || lhs.getDate()==null){
            return -1;
        }
        if(rhs==null||rhs.getDate()==null){
            return 1;
        }
        return lhs.getDate().compareTo(rhs.getDate());

    }
}
