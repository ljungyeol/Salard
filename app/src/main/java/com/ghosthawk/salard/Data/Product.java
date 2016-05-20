package com.ghosthawk.salard.Data;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-05-18.
 */
public class Product implements Serializable {
    public String product_Name;
    public Number product_Price;
    public int product_Picture;
    public int product_Picture2;
    public Number product_Count;
    public String product_Detail;
    public String product_Detail2;
    public String product_Recipe;
    public boolean product_Soldout;
    public int product_Mem_Id;

    public int getProduct_Mem_Id() {
        return product_Mem_Id;
    }

    public void setProduct_Mem_Id(int product_Mem_Id) {
        this.product_Mem_Id = product_Mem_Id;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Picture(int product_Picture) {
        this.product_Picture = product_Picture;
    }

    public int getProduct_Picture() {
        return product_Picture;
    }

    public void setProduct_Price(Number product_Price) {
        this.product_Price = product_Price;
    }

    public Number getProduct_Price() {
        return product_Price;
    }

    public void setProduct_Count(Number product_Count) {
        this.product_Count = product_Count;
    }

    public Number getProduct_Count() {
        return product_Count;
    }

    public int getProduct_Picture2() {
        return product_Picture2;
    }

    public void setProduct_Picture2(int product_Picture2) {
        this.product_Picture2 = product_Picture2;
    }


    public String getProduct_Detail() {
        return product_Detail;
    }

    public void setProduct_Detail(String product_Detail) {
        this.product_Detail = product_Detail;
    }

    public String getProduct_Recipe() {
        return product_Recipe;
    }

    public void setProduct_Recipe(String product_Recipe) {
        this.product_Recipe = product_Recipe;
    }

    public void setProduct_Detail2(String product_Detail2) {
        this.product_Detail2 = product_Detail2;
    }

    public String getProduct_Detail2() {
        return product_Detail2;
    }
}
