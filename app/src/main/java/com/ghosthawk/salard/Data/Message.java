package com.ghosthawk.salard.Data;

import java.util.Date;

/**
 * Created by Tacademy on 2016-05-25.
 */
public class Message {
    public int msg_state;
    public String msg_content;
    public String msg_partnerid;
    public String msg_partnerpicture;
    public String msg_memid;
    public Date msg_date;
    public int msg_read;
    public String msg_picture;


    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getMsg_memid() {
        return msg_memid;
    }

    public void setMsg_memid(String msg_memid) {
        this.msg_memid = msg_memid;
    }

    public String getMsg_partnerid() {
        return msg_partnerid;
    }

    public void setMsg_partnerid(String msg_partnerid) {
        this.msg_partnerid = msg_partnerid;
    }

    public String getMsg_picture() {
        return msg_picture;
    }

    public void setMsg_picture(String msg_picture) {
        this.msg_picture = msg_picture;
    }

    public String getMsg_partnerpicture() {
        return msg_partnerpicture;
    }

    public void setMsg_partnerpicture(String msg_partnerpicture) {
        this.msg_partnerpicture = msg_partnerpicture;
    }
}
