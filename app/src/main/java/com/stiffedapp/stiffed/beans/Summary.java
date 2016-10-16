package com.stiffedapp.stiffed.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Summary {

    @SerializedName("this_week")
    @Expose
    private List<Tip> thisWeek;

    @SerializedName("last_week")
    @Expose
    private List<Tip> lastWeek;

    public List<Tip> getThisWeek() {
        return thisWeek;
    }

    public List<Tip> getLastWeek() {
        return lastWeek;
    }
}
