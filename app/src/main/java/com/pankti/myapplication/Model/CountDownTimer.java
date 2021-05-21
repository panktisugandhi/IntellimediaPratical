package com.pankti.myapplication.Model;

public class CountDownTimer {
    public static final int STATE_START = 0;
    public static final int STATE_PAUSE = 1;
    public static final int STATE_RESUME = 2;

    public long secondsInMillis = 0;
    public int buttonCurrentState = STATE_START;
    public android.os.CountDownTimer timer;
}
