package com.mikesu.retrofitcachecallbackproject.service;

import com.mikesu.retrofitcachecallbackproject.model.RandomNumber;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by MikeSu on 01/08/16.
 * www.michalsulek.pl
 */
public interface RandomService {

  @GET("jsonI.php?length=1&type=uint8")
  Call<RandomNumber> getRandomNumber();

}
