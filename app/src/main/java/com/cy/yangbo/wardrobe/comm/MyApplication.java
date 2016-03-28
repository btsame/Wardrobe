package com.cy.yangbo.wardrobe.comm;

import android.app.Application;

import com.cy.yangbo.wardrobe.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 2016/2/29.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 初始化Fresco
         */
        initFresco();
        initRealm();
    }

    private void initFresco(){
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);
    }

    private void initRealm(){
        byte[] key = new byte[64];
        new SecureRandom().nextBytes(key);
        RealmConfiguration configuration = new RealmConfiguration.Builder(this)
                .name("wardrobe.realm")
                .schemaVersion(1)
               //.encryptionKey(key)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

    public GenericDraweeHierarchy getDefaultDraweeHierarchy(){
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder.setFadeDuration(300)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setPlaceholderImage(getResources().getDrawable(R.drawable.pic_default), ScalingUtils.ScaleType.CENTER_CROP)
                .build();
        return hierarchy;
    }
}
