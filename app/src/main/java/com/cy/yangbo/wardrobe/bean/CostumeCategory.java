package com.cy.yangbo.wardrobe.bean;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Administrator on 2016/3/10.
 */
public class CostumeCategory extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String name;
    private int order;
    private String update_time;

    /**
     * 被忽略的属性，在查询出的对象中属性修改不好使
     */
    @Ignore
    private int isSelected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int isSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }
}
