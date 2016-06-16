package com.ghosthawk.salard.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tacademy on 2016-05-25.
 */
public class Message implements Serializable {

    public String msg_content;
    //    public String msg_partnername;
//    public String msg_partnerid;
//    public String msg_partnerpicture;
//    public int msg_partner_sellcount;
    //member로 통일
    public Member member= new Member();
    public String msg_date;
    public int msg_read;
    public int type;
    public int msg_packagenum;

    public int getMsg_packagenum() {
        return msg_packagenum;
    }

    public void setMsg_packagenum(int msg_packagenum) {
        this.msg_packagenum = msg_packagenum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg_date() {
        return msg_date;
    }

    public void setMsg_date(String msg_date) {
        this.msg_date = msg_date;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }



    public int getMsg_read() {
        return msg_read;
    }

    public void setMsg_read(int msg_read) {
        this.msg_read = msg_read;
    }


    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

}