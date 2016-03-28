package com.cy.yangbo.wardrobe.dagger.module;

import android.app.Application;

import com.cy.yangbo.wardrobe.comm.MyApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/3/16.
 */
@Module
public class ApplicationModule {

    final Application application;

    public ApplicationModule(MyApplication myApplication) {
        application = myApplication;
    }

    @Provides Application provideApplication(){
        return application;
    }

}
