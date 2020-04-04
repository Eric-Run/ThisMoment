package com.dgut.moment.View;

import android.content.Context;
import android.graphics.Canvas;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

public class PlanMonthView extends MonthView {

    public PlanMonthView(Context context) {
        super(context);
    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {

    }
}
