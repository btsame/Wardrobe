package com.cy.yangbo.wardrobe.bean.weather;

/**
 * Created by Administrator on 2016/3/18.
 */
public class WeatherResponse {

    public static final String SUCCESS = "success";

    public int error;
    public String status;
    public String date;
    public WeatherInfo[] results;
}
