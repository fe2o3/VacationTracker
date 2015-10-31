package com.example.rusty.myapplication.services;

import android.content.Context;
import android.text.TextUtils;

import com.example.rusty.myapplication.model.TimeOffEntry;
import com.example.rusty.myapplication.model.TimeOffLog;
import com.example.rusty.myapplication.util.FileUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by rusty on 9/25/2015.
 */
public class TimeOffDaoService {
    private static final String FILENAME = "timeoff_log";
    private final Gson gson;

    private final FileUtil fileUtil;

    public TimeOffDaoService(Context context) {
        GsonBuilder builder=new GsonBuilder();
        gson=builder.create();
        fileUtil = new FileUtil(context);
    }

    public void save(TimeOffLog log) {

        log.setUpdated(Calendar.getInstance());
        String data=gson.toJson(log);

        fileUtil.writeToFile(FILENAME, data);

    }

    public TimeOffLog loadData() {

        //   context.deleteFile(FILENAME);


        String data = fileUtil.loadFile(FILENAME);

        TimeOffLog deserialized=(TimeOffLog)gson.fromJson(data,TimeOffLog.class);
        if(deserialized==null){
            deserialized=new TimeOffLog();
        }
        return deserialized;
//
//        String[] stringEntries = TextUtils.split(data, ":");
//        List<TimeOffEntry> entries = new ArrayList<TimeOffEntry>();
//        for (String s : stringEntries) {
//            TimeOffEntry toe = new TimeOffEntry();
//            toe.setHoursUsed(Integer.parseInt(s));
//            entries.add(toe);
//
//        }
//        return entries;


    }
    public void clear(){
        TimeOffLog log=new TimeOffLog();

        save(log);

    }
}
