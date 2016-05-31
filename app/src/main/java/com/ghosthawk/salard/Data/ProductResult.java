package com.ghosthawk.salard.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tacademy on 2016-05-24.
 */
public class ProductResult {
    @SerializedName("person")
    public Member member;

    @SerializedName("package")
    public PackageProduct _package;

    @SerializedName("mywish")
    public MyWishList mywish;

}
