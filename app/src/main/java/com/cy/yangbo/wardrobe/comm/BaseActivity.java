package com.cy.yangbo.wardrobe.comm;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/2/19.
 */
public class BaseActivity extends AppCompatActivity{

    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        initContentView();
        initView();
        setListener();

    }

    protected void initContentView(){
    }

    protected void initView(){

    }

    protected void setListener(){

    }
}
