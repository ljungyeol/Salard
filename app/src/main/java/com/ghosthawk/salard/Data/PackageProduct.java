package com.ghosthawk.salard.Data;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tacademy on 2016-05-23.
 */
public class PackageProduct implements Serializable {
    public int _id;
    public String package_name;
    public String package_mainpicture;
    public String package_subpicture;
    public String package_personpicture;
    public String package_personid;
    public Date  package_date;
    public int package_price;
    public String package_recipeinfo;
    public String package_detailinfo;
    public String package_subdetailinfo;
    public int package_state;
    public double package_xloca;
    public double package_yloca;
    public int package_count;
    public String package_loca;
    public int person_sellcount;

    public int getPerson_sellcount() {
        return person_sellcount;
    }

    public void setPerson_sellcount(int person_sellcount) {
        this.person_sellcount = person_sellcount;
    }

    public Bitmap bm;

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public Bitmap getBm() {
        return bm;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_mainpicture() {
        return package_mainpicture;
    }

    public void setPackage_mainpicture(String package_mainpicture) {
        this.package_mainpicture = package_mainpicture;
    }

    public String getPackage_subpicture() {
        return package_subpicture;
    }

    public void setPackage_subdetailinfo(String package_subdetailinfo) {
        this.package_subdetailinfo = package_subdetailinfo;
    }

    public String getPackage_personpicture() {
        return package_personpicture;
    }

    public void setPackage_personpicture(String package_personpicture) {
        this.package_personpicture = package_personpicture;
    }

    public int getPackage_price() {
        return package_price;
    }

    public void setPackage_price(int package_price) {
        this.package_price = package_price;
    }

    public double getPackage_xloca() {
        return package_xloca;
    }

    public void setPackage_xloca(double package_xloca) {
        this.package_xloca = package_xloca;
    }


    public double getPackage_yloca() {
        return package_yloca;
    }


    public void setPackage_yloca(double package_yloca) {
        this.package_yloca = package_yloca;
    }

    public String getPackage_detailinfo() {
        return package_detailinfo;
    }

    public void setPackage_detailinfo(String package_detailinfo) {
        this.package_detailinfo = package_detailinfo;
    }

    public int getPackage_count() {
        return package_count;
    }

    public void setPackage_count(int package_count) {
        this.package_count = package_count;
    }

    public String getPackage_subdetailinfo() {
        return package_subdetailinfo;
    }

    public void setPackage_subpicture(String package_subpicture) {
        this.package_subpicture = package_subpicture;
    }

    public String getPackage_personid() {
        return package_personid;
    }

    public void setPackage_personid(String package_personid) {
        this.package_personid = package_personid;
    }

    public String getPackage_recipeinfo() {
        return package_recipeinfo;
    }

    public void setPackage_recipeinfo(String package_recipeinfo) {
        this.package_recipeinfo = package_recipeinfo;
    }

    public int getPackage_state() {
        return package_state;
    }

    public void setPackage_state(int package_state) {
        this.package_state = package_state;
    }

    public String getPackage_loca() {
        return package_loca;
    }

    public void setPackage_loca(String package_loca) {
        this.package_loca = package_loca;
    }


    public Date getPackage_date() {
        return package_date;
    }

    public void setPackage_date(Date package_date) {
        this.package_date = package_date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
