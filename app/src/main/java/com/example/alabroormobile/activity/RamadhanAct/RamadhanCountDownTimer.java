package com.example.alabroormobile.activity.RamadhanAct;

import android.text.format.Time;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */
public class RamadhanCountDownTimer {

    private long intervalMillis;

    public RamadhanCountDownTimer(int second, int minute, int hour, int monthDay, int month, int year) {

        Time futureTime = new Time();

        // Set date to future time
        futureTime.set(second, minute, hour, monthDay, month, year);
        futureTime.normalize(true);
        long futureMillis = futureTime.toMillis(true);

        Time timeNow = new Time();

        // Set date to current time
        timeNow.setToNow();
        timeNow.normalize(true);
        long nowMillis = timeNow.toMillis(true);

        // Subtract current milliseconds time from future milliseconds time to retrieve interval
        intervalMillis = futureMillis - nowMillis;
    }

    public long getIntervalMillis() {
        return intervalMillis;
    }
}
