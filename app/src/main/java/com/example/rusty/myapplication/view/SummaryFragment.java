package com.example.rusty.myapplication.view;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rusty.myapplication.R;
import com.example.rusty.myapplication.SickCalculator;
import com.example.rusty.myapplication.VacationCalculator;
import com.example.rusty.myapplication.services.TimeOffDaoService;
import com.example.rusty.myapplication.services.VacationYearService;

/**
 * A placeholder fragment containing a simple view.
 */
public class SummaryFragment extends Fragment {

    private View view;

    public SummaryFragment() {


    }
    public void refreshData(){

        showVacationRemaining();
        showYearRemaining();
        showSickRemaining();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.summary_fragment, container, false);



        showVacationRemaining();
        showYearRemaining();
        showSickRemaining();


        return view;
    }

    private void showVacationRemaining() {


        VacationCalculator vc = new VacationCalculator(view.getContext());
        Integer used= vc.getUsedHours();
        int total=vc.getTotalHours();


        float vacationFillFactor=((float)used/(float)total);


        LayerDrawable vacationRect=(LayerDrawable)getContext().getResources().getDrawable(R.drawable.progress_rectangle);
        ClipDrawable clip=(ClipDrawable)vacationRect.findDrawableByLayerId(R.id.clip_layer_id);
        clip.setLevel((int) Math.ceil(vacationFillFactor * 10000));


        TextView barView=(TextView)view.findViewById(R.id.vacationProgressRectangle);
        setBackgroundWithoutMessingPadding(barView, vacationRect);

        barView.setText(String.format("%d vacation hours left", total-used));
    }
    private void showYearRemaining( ) {

        VacationYearService yearService=new VacationYearService();
        int total=(int)yearService.getDaysInThisVacationYear();
        int remaining=(int)yearService.getDaysRemainingThisVacationYear();

        float yearFillFactor=1-((float)remaining/(float)total);


        LayerDrawable yearRect=(LayerDrawable)getContext().getResources().getDrawable(R.drawable.progress_rectangle);
        ClipDrawable clip=(ClipDrawable)yearRect.findDrawableByLayerId(R.id.clip_layer_id);
        clip.setLevel((int)Math.ceil(yearFillFactor*10000));

        TextView barView=(TextView)view.findViewById(R.id.yearProgressRectangle);
        setBackgroundWithoutMessingPadding(barView, yearRect);

        barView.setText(String.format("%d days left",remaining));
    }
    private void showSickRemaining() {
        TextView textView = (TextView) view.findViewById(R.id.sickProgressRectangle);

        SickCalculator vc = new SickCalculator(view.getContext());
        Integer remaining = vc.calculate();
        textView.setText(String.format("%d sick hours left", remaining));

    }
    private void setBackgroundWithoutMessingPadding(View v,Drawable d){
        int bottom=v.getPaddingBottom();
        int top=v.getPaddingTop();
        int left=v.getPaddingLeft();
        int right=v.getPaddingRight();

        v.setBackground(d);

        v.setPadding(left,top,right,bottom);
    }

}
