package com.cy.yangbo.wardrobe.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.cy.yangbo.wardrobe.bean.CostumeCategory;
import com.cy.yangbo.wardrobe.comm.BaseActivity;
import com.cy.yangbo.wardrobe.util.SpUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Administrator on 2016/3/11.
 */
public class CoverActivity extends BaseActivity {

    public static final String IS_FIRST_LAUNCH = "isFirstLaunch";
    public final String[] initCostumeCategory = {"外套", "衬衫", "T Shirt", "裤子", "帽子", "围巾", "鞋子"};

    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(SpUtil.getFromLocal(context, SpUtil.SP_MAIN, IS_FIRST_LAUNCH, false)){

        }else{
            SpUtil.saveToLocal(context, SpUtil.SP_MAIN, IS_FIRST_LAUNCH, true);
            realm = Realm.getDefaultInstance();
            RealmResults<CostumeCategory> realts = realm.allObjects(CostumeCategory.class);
            if(realts.size() == 0){
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        List<CostumeCategory> datas = new ArrayList<CostumeCategory>();
                        int id = 0;
                        String time = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss").format(new Date());
                        for(String strCategory : initCostumeCategory){
                            CostumeCategory category = new CostumeCategory();
                            category.setId(id++);
                            category.setName(strCategory);
                            category.setOrder(id);
                            category.setUpdate_time(time);
                            datas.add(category);
                        }
                        realm.copyToRealmOrUpdate(datas);
                    }
                });
            }
        }

        startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void initContentView() {
        super.initContentView();
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(realm != null){
            realm.close();
        }
    }
}
