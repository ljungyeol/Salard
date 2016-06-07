package com.ghosthawk.salard.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tacademy on 2016-06-07.
 */
public class MyResult {
    @SerializedName("success")
    public int success_code;

    public Member member;

    public int getSuccess_code() {
        return success_code;
    }

    public void setSuccess_code(int success_code) {
        this.success_code = success_code;
    }

    public Member getResult() {
        return member;
    }

    public void setResult(Member member) {
        this.member = member;
    }
}
