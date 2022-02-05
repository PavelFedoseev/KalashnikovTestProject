package com.example.kalashnikovconcerntest.data.db;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class CalendarTypeConverter {

    @TypeConverter
    public static Calendar toCalendar(Long l) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);
        return c;
    }

    @TypeConverter
    public static Long fromCalendar(Calendar c){
        return c == null ? null : c.getTime().getTime();
    }

}
