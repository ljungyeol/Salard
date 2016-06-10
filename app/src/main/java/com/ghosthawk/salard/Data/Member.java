package com.ghosthawk.salard.Data;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-05-16.
 */
public class Member implements Serializable {
    public String mem_id;
    public String mem_name;
    public String mem_picture;
    public Number mem_PicutureNum;
    public Number mem_NotifyNewCount;
    public Number mem_Phone;
    public double mem_xloca;
    public double mem_yloca;
    public String mem_statemsg; //상태메세지
    public String mem_RecentLoca; //최근 위치
    public String mem_Email;
    public int mem_followercount;
    public int mem_followingcount;
    public String mem_loca;
    public int mem_sellcount;

    public int getMem_sellcount() {
        return mem_sellcount;
    }

    public void setMem_sellcount(int mem_sellcount) {
        this.mem_sellcount = mem_sellcount;
    }

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    public String getMem_Location() {
        return mem_loca;
    }

    public void setMem_Location(String mem_loca) {
        this.mem_loca = mem_loca;
    }

    public String getMem_Name() {
        return mem_name;
    }

    public void setMem_Name(String mem_name) {
        this.mem_name = mem_name;
    }

    public String getMem_Picture() {
        return mem_picture;
    }

    public void setMem_Picture(String mem_picture) {
        this.mem_picture = mem_picture;
    }

    public String getMem_StatMsg() {
        return mem_statemsg;
    }

    public void setMem_StatMsg(String mem_statemsg) {
        this.mem_statemsg = mem_statemsg;
    }

    public int getMem_followingcount() {
        return mem_followingcount;
    }

    public void setMem_followingcount(int mem_followingcount) {
        this.mem_followingcount = mem_followingcount;
    }

    public int getMem_followercount() {
        return mem_followercount;
    }

    public void setMem_followercount(int mem_followercount) {
        this.mem_followercount = mem_followercount;
    }

    public double getMem_xloca() {
        return mem_xloca;
    }

    public void setMem_xloca(double mem_xloca) {
        this.mem_xloca = mem_xloca;
    }

    public double getMem_yloca() {
        return mem_yloca;
    }

    public void setMem_yloca(double mem_yloca) {
        this.mem_yloca = mem_yloca;
    }
}
