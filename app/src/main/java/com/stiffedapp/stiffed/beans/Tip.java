package com.stiffedapp.stiffed.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Tip {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("tip_date")
    @Expose
    private String tipDate;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("tip_out_amount")
    @Expose
    private Double tipOutAmount;

    /**
     *
     * @return
     * The uid
     */
    public String getUid() {
        return uid;
    }

    /**
     *
     * @param uid
     * The uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     *
     * @return
     * The tipDate
     */
    public String getTipDate() {
        return tipDate;
    }

    /**
     *
     * @param tipDate
     * The tip_date
     */
    public void setTipDate(String tipDate) {
        this.tipDate = tipDate;
    }

    /**
     *
     * @return
     * The amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     * The amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     * The tipOutAmount
     */
    public Double getTipOutAmount() {
        return tipOutAmount;
    }

    /**
     *
     * @param tipOutAmount
     * The tip_out_amount
     */
    public void setTipOutAmount(Double tipOutAmount) {
        this.tipOutAmount = tipOutAmount;
    }

}
