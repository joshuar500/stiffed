package com.stiffedapp.stiffed.beans;

import java.util.HashMap;
import java.util.Map;

public class Tip {
    public String tipAmount;

    public Tip() {

    }

    public Tip(String tipAmount) {
        this.tipAmount = tipAmount;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tipAmount", tipAmount);
        return result;
    }

}
