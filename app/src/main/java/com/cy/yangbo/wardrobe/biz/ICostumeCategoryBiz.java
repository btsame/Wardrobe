package com.cy.yangbo.wardrobe.biz;

import com.cy.yangbo.wardrobe.bean.CostumeCategory;

import io.realm.RealmResults;

/**
 * Created by Administrator on 2016/3/11.
 */
public interface ICostumeCategoryBiz {
    RealmResults<CostumeCategory> findAll();
    void swapOrder(CostumeCategory from, CostumeCategory to);
    boolean add();
    boolean remove();
}
