package com.example.pc.blog;

/**
 * Created by pc on 7/7/2015.
 */
public class AlertItem {
    private int _id;
    private String _AlertTitle;
    private int _AlertDay;
    private int _AlertMonth;
    private int _AlertYear;
    private int _AlertHour;
    private int _AlertMinute;
    private String _AlertIsActive;


    public String get_AlertIsActive() {
        return _AlertIsActive;
    }

    public void set_AlertIsActive(String _AlertIsActive) {
        this._AlertIsActive = _AlertIsActive;
    }

    public AlertItem(String _AlertTitle, int _AlertDay, int _AlertMonth, int _AlertYear, int _AlertHour, int _AlertMinute, String _AlertIsActive) {
        this._AlertTitle = _AlertTitle;
        this._AlertDay = _AlertDay;
        this._AlertMonth = _AlertMonth;
        this._AlertYear = _AlertYear;
        this._AlertHour = _AlertHour;
        this._AlertMinute = _AlertMinute;
        this._AlertIsActive=_AlertIsActive;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_AlertTitle() {
        return _AlertTitle;
    }

    public void set_AlertTitle(String _AlertTitle) {
        this._AlertTitle = _AlertTitle;
    }

    public int get_AlertDay() {
        return _AlertDay;
    }

    public void set_AlertDay(int _AlertDay) {
        this._AlertDay = _AlertDay;
    }

    public int get_AlertMonth() {
        return _AlertMonth;
    }

    public void set_AlertMonth(int _AlertMonth) {
        this._AlertMonth = _AlertMonth;
    }

    public int get_AlertYear() {
        return _AlertYear;
    }

    public void set_AlertYear(int _AlertYear) {
        this._AlertYear = _AlertYear;
    }

    public int get_AlertHour() {
        return _AlertHour;
    }

    public void set_AlertHour(int _AlertHour) {
        this._AlertHour = _AlertHour;
    }

    public int get_AlertMinute() {
        return _AlertMinute;
    }

    public void set_AlertMinute(int _AlertMinute) {
        this._AlertMinute = _AlertMinute;
    }
}
