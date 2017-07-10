package com.example.chenminyao.hootsuiteassignment.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenminyao on 2017-07-03.
 */

public class Images implements Serializable {
    @SerializedName("base_url") public String baseUrl;
    @SerializedName("backdrop_sizes") public List<String> backdropSizes;
}
