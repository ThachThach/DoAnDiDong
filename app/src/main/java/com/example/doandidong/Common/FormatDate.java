package com.example.doandidong.Common;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatDate {

    public FormatDate(){}

    public int truThoiGian(String ngayBatDau, String ngayKetThuc){
        Date date1 = new Date();
        Date date2 = new Date();
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(ngayBatDau);
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(ngayKetThuc);
        }catch (ParseException e){
            return 0;
        }
        //int days = Days.daysBetween(date1, date2).getDays();
        int days = (int)( (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
        return days;
    }

    public String getDate(Calendar calendar, int amount){
        String dinhDang = "dd/MM/yyyy";

        Long toDay = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(toDay);
        Calendar cal = calendar;
        cal.add(Calendar.DAY_OF_YEAR, amount);
        Date date = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(dinhDang);
        String startDate = formatter.format(date);
        return startDate;
    }

}
