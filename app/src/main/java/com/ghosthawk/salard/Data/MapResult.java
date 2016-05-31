package com.ghosthawk.salard.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tacademy on 2016-05-25.
 */
public class MapResult {
    @SerializedName("member")
    public List<Member> member;
    @SerializedName("package")
    public List<PackageProduct> packageproduct;
}
