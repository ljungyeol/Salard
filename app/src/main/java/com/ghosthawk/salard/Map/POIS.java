package com.ghosthawk.salard.Map;

import com.ghosthawk.salard.Data.PackageProduct;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tacademy on 2016-05-18.
 */
public class POIS {
    @SerializedName("poi")
    List<PackageProduct> packageProducts;
}
