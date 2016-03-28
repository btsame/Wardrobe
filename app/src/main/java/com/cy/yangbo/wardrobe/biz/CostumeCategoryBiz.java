package com.cy.yangbo.wardrobe.biz;

import android.support.annotation.NonNull;

import com.cy.yangbo.wardrobe.bean.CostumeCategory;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Administrator on 2016/3/11.
 */
public class CostumeCategoryBiz implements ICostumeCategoryBiz{
    private Realm mRealm;

    public CostumeCategoryBiz(@NonNull Realm mRealm) {
        this.mRealm = mRealm;
    }

    public void setmRealm(Realm mRealm) {
        this.mRealm = mRealm;
    }

    @Override
    public RealmResults<CostumeCategory> findAll() {
        RealmResults<CostumeCategory> realmResults = mRealm.allObjectsSorted(CostumeCategory.class, "order", Sort.ASCENDING);

        return realmResults;
    }

    public void swapOrder(final CostumeCategory from, final CostumeCategory to){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int temp = from.getOrder();
                from.setOrder(to.getOrder());
                to.setOrder(temp);
            }
        });
    }

    @Override
    public boolean add() {
        return false;
    }

    @Override
    public boolean remove() {
        return false;
    }
}
