package com.example.alabroormobile.api;

import com.example.alabroormobile.model.ModelJadwal;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fahmi on 14/02/2019.
 */

public interface ApiService {
    @GET("bandung.json")
    Call<ModelJadwal> getJadwal();
}
