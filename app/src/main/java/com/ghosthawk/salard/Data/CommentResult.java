package com.ghosthawk.salard.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tacademy on 2016-05-26.
 */
public class CommentResult {
    @SerializedName("comment")
    public List<Comments> comments;
}
