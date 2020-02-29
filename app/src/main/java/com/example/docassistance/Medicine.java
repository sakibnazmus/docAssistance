package com.example.docassistance;

public class Medicine {
    String name;
    int days;
    boolean morning;
    boolean lunch;
    boolean dinner;
    int interval;
    String note;

    public Medicine(String name, int days, String note) {
        this.name = name;
        this.days = days;
        this.note = note;
    }

    public Medicine(String name, int days, boolean morning, boolean lunch, boolean dinner, String note) {
        this(name, days, note);
        this.morning = morning;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public Medicine(String name, int days, int interval, String note) {
        this(name, days, note);
        this.interval = interval;
    }

    public String getName() {
        return name;
    }

    public int getDays() {
        return days;
    }

    public boolean isMorning() {
        return morning;
    }

    public boolean isLunch() {
        return lunch;
    }

    public boolean isDinner() {
        return dinner;
    }

    public int getInterval() {
        return interval;
    }

    public String getNote() {
        return note;
    }
}
