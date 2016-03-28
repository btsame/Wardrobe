package com.cy.yangbo.wardrobe.biz.weather;

import com.cy.yangbo.wardrobe.bean.weather.WeatherResponse;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2016/3/18.
 */
public interface IWeatherBiz {

    @GET("telematics/v3/weather")
    Observable<WeatherResponse> queryWeather(@QueryMap Map<String, String> queryParams);
}
