package com.cy.yangbo.wardrobe.presenter;

import com.cy.yangbo.wardrobe.bean.weather.WeatherResponse;
import com.cy.yangbo.wardrobe.biz.weather.IWeatherBiz;
import com.cy.yangbo.wardrobe.comm.AppConfig;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Administrator on 2016/3/18.
 */
public class WeatherPresenter {

    public Observable<WeatherResponse> queryWeather(String city) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BAIDU_WEATHER_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        IWeatherBiz weatherBiz = retrofit.create(IWeatherBiz.class);

        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("location", city);
        queryParams.put("output", "json");
        queryParams.put("ak", AppConfig.BAIDU_APP_KEY);
        return weatherBiz.queryWeather(queryParams);
    }
}
