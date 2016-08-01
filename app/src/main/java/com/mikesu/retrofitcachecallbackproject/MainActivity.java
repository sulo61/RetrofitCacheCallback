package com.mikesu.retrofitcachecallbackproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.mikesu.retrofitcachecallback.CacheCallback;
import com.mikesu.retrofitcachecallbackproject.model.RandomNumber;
import com.mikesu.retrofitcachecallbackproject.service.RandomService;
import java.util.Date;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MikeSu on 01/08/16.
 * www.michalsulek.pl
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private Retrofit retrofit;
  private Button btn;
  private TextView value;
  private boolean lock;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    lock = false;

    btn = (Button) findViewById(R.id.btn);
    value = (TextView) findViewById(R.id.value);

    btn.setOnClickListener(this);

    retrofit = new Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://qrng.anu.edu.au/API/")
        .build();
  }

  @Override
  public void onClick(View view) {
    if (!lock) {
      btn.setEnabled(false);

      retrofit.create(RandomService.class).getRandomNumber()
          .enqueue(new CacheCallback<RandomNumber>(MainActivity.this, RandomNumber.class, true) {
            @Override
            public void success(RandomNumber body, boolean fromCache) {
              btn.setEnabled(true);
              if (body != null && body.getData().length > 0) {
                value.setText(new Date().toString() + "\n" + String.valueOf(body.getData()[0]));
              } else {
                value.setText("Error, empty response");
              }
            }

            @Override
            public void failure(Call<RandomNumber> call, Throwable throwable) {
              btn.setEnabled(true);
              value.setText("Error:\n" + throwable.getMessage());
            }
          });
    }
  }
}
