package com.cy.yangbo.wardrobe.presenter;

import com.cy.yangbo.wardrobe.biz.CostumeBiz;
import com.cy.yangbo.wardrobe.biz.ICostumeBiz;
import com.cy.yangbo.wardrobe.view.inter.AddCostumeView;

/**
 * Created by Administrator on 2016/3/11.
 */
public class CostumePresenter {

    private AddCostumeView mAddCostumeView;
    private ICostumeBiz mCostumeBiz;

    public CostumePresenter(AddCostumeView mAddCostumeView) {
        this.mAddCostumeView = mAddCostumeView;
        mCostumeBiz = new CostumeBiz();
    }

}
