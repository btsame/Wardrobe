package com.cy.yangbo.wardrobe.bean;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Administrator on 2016/2/29.
 */
public class Costume extends RealmObject {

    @PrimaryKey
    private long id;
    private String sort_part;
    @Required
    private String pic;
    private String remark;
    private Date create_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSort_part() {
        return sort_part;
    }

    public void setSort_part(String sort_part) {
        this.sort_part = sort_part;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
