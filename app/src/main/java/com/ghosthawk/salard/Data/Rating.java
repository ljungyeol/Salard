package com.ghosthawk.salard.Data;

import java.util.Date;

/**
 * Created by Tacademy on 2016-05-23.
 */
public class Rating {
    public String name;
    public String msg;
    public int ratingNum;
    public Date ratingTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }


    public int getRatingNum() {
        return ratingNum;
    }

    public void setRatingNum(int ratingNum) {
        this.ratingNum = ratingNum;
    }

    public Date getRatingTime() {
        return ratingTime;
    }

    public void setRatingTime(Date ratingTime) {
        this.ratingTime = ratingTime;
    }
}
