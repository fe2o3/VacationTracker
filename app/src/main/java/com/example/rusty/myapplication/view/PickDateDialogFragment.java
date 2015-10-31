package com.example.rusty.myapplication.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import android.widget.DatePicker;

import com.example.rusty.myapplication.DateEntryActivity;

import java.util.Calendar;

/**
 * Created by rusty on 9/27/2015.
 */
public class PickDateDialogFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle b = getArguments();


        int year = (Integer) b.get("year");
        int month = (Integer) b.get("month");
        int day = (Integer) b.get("day");

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);

        ((DateEntryActivity)getActivity()).setDateChosen(cal);


    }
}
