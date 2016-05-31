package com.ghosthawk.salard.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tacademy on 2016-05-25.
 */
public class FollowerResult {
    @SerializedName("follower")
    public List<Member> member;
}
