package com.ghosthawk.salard.Data;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-05-16.
 */
public class Member implements Serializable {
    public String mem_id;
    public String mem_Name;
    public int mem_Picture;
    public Number mem_PicutureNum;
    public Number mem_NotifyNewCount;
    public Number mem_Phone;
    public Number mem_Xloca;
    public Number mem_Yloca;
    public String mem_StatMsg; //상태메세지
    public String mem_RecentLoca; //최근 위치
    public String mem_Email;
    public Number mem_follow;


    public String getMem_Name() {
        return mem_Name;
    }

    public void setMem_Name(String mem_Name) {
        this.mem_Name = mem_Name;
    }

    public int getMem_Picture() {
        return mem_Picture;
    }

    public void setMem_Picture(int mem_Picture) {
        this.mem_Picture = mem_Picture;
    }

    public String getMem_StatMsg() {
        return mem_StatMsg;
    }

    public void setMem_StatMsg(String mem_StatMsg) {
        this.mem_StatMsg = mem_StatMsg;
    }

    public void setMem_follow(Number mem_follow) {
        this.mem_follow = mem_follow;
    }

    public Number getMem_follow() {
        return mem_follow;
    }
}
