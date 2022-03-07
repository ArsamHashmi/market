package com.example.market.formatters;

import android.text.format.DateFormat;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.Calendar;
import java.util.Locale;

public class MinDateValueFormatter implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // Simple version. You should use a DateFormatter to specify how you want to textually represent your date.


        return getDatehour(new Float(value).longValue());
    }
    // ...

    private String getDatehour(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("HH:mm", cal).toString();
        return date;
    }

}