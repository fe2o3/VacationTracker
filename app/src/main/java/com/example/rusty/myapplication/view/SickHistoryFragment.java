package com.example.rusty.myapplication.view;




import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rusty.myapplication.R;
import com.example.rusty.myapplication.model.TimeOffEntry;
import com.example.rusty.myapplication.model.TimeOffEntryComparator;
import com.example.rusty.myapplication.model.TimeOffLog;
import com.example.rusty.myapplication.services.TimeOffDaoService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public  class SickHistoryFragment extends Fragment {
    private View view;
    private ArrayAdapter<String> adapter;
    private List<String> backingData;
    public SickHistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sick_history_fragment, container, false);



        backingData = getData();
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, backingData);


        ListView listView=(ListView)view.findViewById(R.id.sick_history_list);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
        return view;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v, ContextMenu.ContextMenuInfo menuInfo){

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.entry_context_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(getUserVisibleHint()==false){
            return false;
        }
        switch (item.getItemId()) {
            case R.id.menu_delete_entry:
                deleteSick(info.position);
                refresh();
                return true;
            case R.id.menu_hide_entry:
                hideOldSick(info.position);
                refresh();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void refresh(){
        backingData.clear();
        backingData.addAll(getData());
        adapter.notifyDataSetChanged();

    }
    private List<String> getData(){
        TimeOffDaoService log=new TimeOffDaoService(getContext());

        TimeOffLog data = log.loadData();
        List<TimeOffEntry> entries=data.getSickEntries();

        Collections.sort(entries, new TimeOffEntryComparator());
        List<String> list=new ArrayList<String>();
        for(TimeOffEntry toe:entries){
            if(!toe.isHidden()) {
                list.add(formatEntry(toe));
            }

        }
        return list;
    }
    private final String template="%d hours on %s (entered %s)";

    private String formatEntry(TimeOffEntry toe){


        StringBuilder sb=new StringBuilder();
        Formatter formatter=new Formatter(sb, Locale.US);


        SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yy");
        formatter.format(template,toe.getHoursUsed(),fmt.format(toe.getDate().getTime()),fmt.format(toe.getEntered().getTime()));

        return sb.toString();
    }

    private void hideOldSick(int position) {
        TimeOffDaoService dao = new TimeOffDaoService(getContext());
        TimeOffLog log = dao.loadData();
        List<TimeOffEntry> sick = log.getSickEntries();     Collections.sort(sick, new TimeOffEntryComparator());
        for(int i=0;i<position;i++){
            TimeOffEntry toe=sick.get(i);
            toe.setHidden(true);
        }

        dao.save(log);
    }
    private void deleteSick(int position){
        TimeOffDaoService dao=new TimeOffDaoService(getContext());
        TimeOffLog log=dao.loadData();
        List<TimeOffEntry> entries=log.getSickEntries();
        Collections.sort(entries,new TimeOffEntryComparator());
        entries.remove(position);
        dao.save(log);
    }

}
