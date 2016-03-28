package com.cy.yangbo.wardrobe.dagger.component;

import android.app.Activity;

import com.cy.yangbo.wardrobe.dagger.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2016/3/16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(Activity activity);
}
