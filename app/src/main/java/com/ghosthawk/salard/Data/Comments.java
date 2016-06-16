package com.ghosthawk.salard.Data;

/**
 * Created by Tacademy on 2016-05-24.
 */
public class Comments {
    public String comment_text;
    public float comment_grade;
    public String comment_sendid;
    public String comment_giveid;
    public String comment_name;

    public String getComment_name() {
        return comment_name;
    }

    public void setComment_name(String comment_name) {
        this.comment_name = comment_name;
    }

    public void setComment_giveid(String comment_giveid) {
        this.comment_giveid = comment_giveid;
    }

    public String getComment_giveid() {
        return comment_giveid;
    }

    public void setComment_grade(float comment_grade) {
        this.comment_grade = comment_grade;
    }

    public float getComment_grade() {
        return comment_grade;
    }

    public void setComment_sendid(String comment_sendid) {
        this.comment_sendid = comment_sendid;
    }

    public String getComment_sendid() {
        return comment_sendid;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public String getComment_text() {
        return comment_text;
    }
}
