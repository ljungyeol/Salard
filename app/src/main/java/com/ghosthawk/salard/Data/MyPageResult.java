package com.ghosthawk.salard.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tacademy on 2016-05-24.
 */
public class MyPageResult {
   @SerializedName("member")
    public Member member;

    @SerializedName("comments")
    public List<Comments> comments;

}
