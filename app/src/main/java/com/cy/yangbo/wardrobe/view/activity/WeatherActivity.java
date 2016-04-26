package com.cy.yangbo.wardrobe.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cy.yangbo.wardrobe.R;
import com.cy.yangbo.wardrobe.bean.weather.WeatherForecastInfo;
import com.cy.yangbo.wardrobe.bean.weather.WeatherIndex;
import com.cy.yangbo.wardrobe.bean.weather.WeatherResponse;
import com.cy.yangbo.wardrobe.comm.BaseActivity;
import com.cy.yangbo.wardrobe.presenter.WeatherPresenter;
import com.cy.yangbo.wardrobe.view.inter.WeatherView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/18.
 */
public class WeatherActivity extends BaseActivity implements WeatherView {

    private WeatherPresenter weatherPresenter;
    @Bind(R.id.sdv_weather_icon)
    SimpleDraweeView mIconSDV;
    @Bind(R.id.rv_weather_forecast)
    RecyclerView mForeCastRV;
    @Bind(R.id.tv_weather_date)
    TextView mDateTV;
    @Bind(R.id.tv_weather_weather)
    TextView mWeatherTV;
    @Bind(R.id.tv_weather_wind)
    TextView mWindTV;
    @Bind(R.id.tv_weather_temperature)
    TextView mTemperatureTV;
    @Bind(R.id.tv_weather_pm25)
    TextView mPm25TV;
    @Bind(R.id.ll_weather_bottomsheet)
    View mBottomSheetView;
    @Bind(R.id.rv_weather_detail)
    RecyclerView mDetailRV;
    @Bind(R.id.iv_weather_refresh)
    ImageView mRefresh;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.icon_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        queryWeather();
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void setListener() {
        super.setListener();

        RxView.clicks(mRefresh).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                queryWeather();
            }
        });
    }

    /**
     * 查询最新天气
     */
    private void queryWeather(){
        startRefreshAnimator();

        //缓存实现，暂缓实现

        weatherPresenter = new WeatherPresenter();
        weatherPresenter.queryWeather("北京")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeatherResponse>() {
                    @Override
                    public void call(WeatherResponse weatherResponse) {
                        stopRefreshAnimator();
                        if (weatherResponse.status.equals(WeatherResponse.SUCCESS)) {
                            WeatherForecastInfo today = weatherResponse.results[0].weather_data[0];
                            mDateTV.setText(today.date);
                            mIconSDV.setImageURI(Uri.parse(today.dayPictureUrl));
                            mWeatherTV.setText("天气：" + today.weather);
                            mWindTV.setText("风力：" + today.wind);
                            mTemperatureTV.setText("温度：" + today.temperature);
                            mPm25TV.setText("PM2.5：" + weatherResponse.results[0].pm25);

                            List<WeatherForecastInfo> forecastInfos = new ArrayList<WeatherForecastInfo>();
                            for (int i = 1; i < weatherResponse.results[0].weather_data.length; i++) {
                                forecastInfos.add(weatherResponse.results[0].weather_data[i]);
                            }
                            WeatherForecastAdapter forecastAdapter = new WeatherForecastAdapter(forecastInfos);
                            mForeCastRV.setLayoutManager(new LinearLayoutManager(context,
                                    LinearLayoutManager.HORIZONTAL, false));
                            mForeCastRV.setAdapter(forecastAdapter);

                            List<WeatherIndex> indexes = new ArrayList<WeatherIndex>();
                            for (int i = 1; i < weatherResponse.results[0].index.length; i++) {
                                indexes.add(weatherResponse.results[0].index[i]);
                            }
                            WeatherIndexAdapter indexAdapter = new WeatherIndexAdapter(indexes);
                            mDetailRV.setLayoutManager(new LinearLayoutManager(context,
                                    LinearLayoutManager.VERTICAL, false));
                            mDetailRV.setAdapter(indexAdapter);
                        } else {
                            Toast.makeText(context, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private Animation animation;
    private void startRefreshAnimator(){
        if(animation == null){
            animation = AnimationUtils.loadAnimation(context, R.anim.refresh_rotate);
        }
        mRefresh.startAnimation(animation);
    }
    private void stopRefreshAnimator(){
        if(animation != null){
            mRefresh.clearAnimation();
        }
    }


    /**
     *
     */
    public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder>{
        private List<WeatherForecastInfo> mDatas = new ArrayList<WeatherForecastInfo>();

        public WeatherForecastAdapter(List<WeatherForecastInfo> mDatas) {
            this.mDatas = mDatas;
        }

        @Override
        public WeatherForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WeatherForecastViewHolder(LayoutInflater.from(context).inflate(R.layout.item_weather_forecast, null));
        }

        @Override
        public void onBindViewHolder(WeatherForecastViewHolder holder, int position) {
            holder.mIconSDV.setImageURI(Uri.parse(mDatas.get(position).dayPictureUrl));
            holder.mTemperatureTV.setText("温度：" + mDatas.get(position).temperature);
            holder.mWeatherTV.setText("天气：" + mDatas.get(position).weather);
            holder.mDateTV.setText(mDatas.get(position).date);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public class WeatherForecastViewHolder extends RecyclerView.ViewHolder{
            @Bind(R.id.sdv_item_forecast_icon)
            public SimpleDraweeView mIconSDV;
            @Bind(R.id.tv_item_forecast_temperature)
            public TextView mTemperatureTV;
            @Bind(R.id.tv_item_forecast_weather)
            public TextView mWeatherTV;
            @Bind(R.id.tv_item_forecast_date)
            public TextView mDateTV;


            public WeatherForecastViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    /**
     *
     */
    public class WeatherIndexAdapter extends RecyclerView.Adapter<WeatherIndexAdapter.WeatherIndexViewHolder>{
        private List<WeatherIndex> mDatas = new ArrayList<WeatherIndex>();

        public WeatherIndexAdapter(List<WeatherIndex> mDatas) {
            this.mDatas = mDatas;
        }

        @Override
        public WeatherIndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WeatherIndexViewHolder(LayoutInflater.from(context).inflate(R.layout.item_weather_index, null));
        }

        @Override
        public void onBindViewHolder(WeatherIndexViewHolder holder, int position) {
            holder.mTitleTV.setText(mDatas.get(position).title + "："
                    + mDatas.get(position).zs);
            holder.mContentTV.setText(mDatas.get(position).tipt + "："
                    + mDatas.get(position).des);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public class WeatherIndexViewHolder extends RecyclerView.ViewHolder{
            @Bind(R.id.tv_weather_index_title)
            public TextView mTitleTV;
            @Bind(R.id.tv_weather_index_content)
            public TextView mContentTV;


            public WeatherIndexViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
