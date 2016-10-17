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

    @SerializedName("last_month")
    @Expose
    private List<Tip> lastMonth;

    @SerializedName("two_month")
    @Expose
    private List<Tip> twoMonth;

    @SerializedName("three_month")
    @Expose
    private List<Tip> threeMonth;

    @SerializedName("four_month")
    @Expose
    private List<Tip> fourMonth;

    @SerializedName("five_month")
    @Expose
    private List<Tip> fiveMonth;

    @SerializedName("six_month")
    @Expose
    private List<Tip> sixMonth;

    public List<Tip> getThisWeek() {
        return thisWeek;
    }

    public List<Tip> getLastWeek() {
        return lastWeek;
    }

    public List<Tip> getLastMonth() {
        return lastMonth;
    }

    public List<Tip> getTwoMonth() {
        return twoMonth;
    }

    public List<Tip> getThreeMonth() {
        return threeMonth;
    }

    public List<Tip> getFourMonth() {
        return fourMonth;
    }

    public List<Tip> getFiveMonth() {
        return fiveMonth;
    }

    public List<Tip> getSixMonth() {
        return sixMonth;
    }
}
