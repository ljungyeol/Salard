package com.ghosthawk.salard.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tacademy on 2016-05-27.
 */
public class SuccessCode {
    @SerializedName("success")
    public int success_code;

    public int getSuccess_code() {
        return success_code;
    }

    public void setSuccess_code(int success_code) {
        this.success_code = success_code;
    }
}
