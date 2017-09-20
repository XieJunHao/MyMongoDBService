package org.fsdcic.xjh.mongo.entity;

import java.io.Serializable;

/**
 * Created by ben on 2017-9-20.
 */
public class BaseEntity implements Serializable {

//   主键
    private String id;

//  用于判断查找与实际的对象类型是否一致
    private String _class;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }
}
