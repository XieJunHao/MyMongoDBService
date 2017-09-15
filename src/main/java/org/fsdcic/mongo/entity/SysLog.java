package org.fsdcic.mongo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ben on 2017-9-15.
 */
//mongodb的实体基类注释
@Document
public class SysLog implements Serializable {

    private Integer id;

    private String userName;

    private String ip;

    private String content;

    //表单集合信息
    private List<String> tableMessage;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTableMessage() {
        return tableMessage;
    }

    public void setTableMessage(List<String> tableMessage) {
        this.tableMessage = tableMessage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
