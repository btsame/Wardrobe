package com.cy.yangbo.wardrobe.presenter;

import com.cy.yangbo.wardrobe.bean.CostumeCategory;
import com.cy.yangbo.wardrobe.biz.CostumeCategoryBiz;
import com.cy.yangbo.wardrobe.biz.ICostumeCategoryBiz;
import com.cy.yangbo.wardrobe.view.inter.AddCostumeView;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Administrator on 2016/3/11.
 */
public class CostumeCategoryPresenter {

    private AddCostumeView mAddCostumeView;
    private ICostumeCategoryBiz mCostumeCategoryBiz;
    private Realm mRealm;

    public CostumeCategoryPresenter(AddCostumeView mAddCostumeView) {
        this.mAddCostumeView = mAddCostumeView;
        mRealm = Realm.getDefaultInstance();
        mCostumeCategoryBiz = new CostumeCategoryBiz(mRealm);
    }

    public RealmResults<CostumeCategory> findAllCostumeCategory(){
        return mCostumeCategoryBiz.findAll();
    }

    public void swapOrder(CostumeCategory from, CostumeCategory to){
        mCostumeCategoryBiz.swapOrder(from , to);
    }

    public void onDestory(){
        if(mRealm != null){
            mRealm.close();
        }
    }


}
