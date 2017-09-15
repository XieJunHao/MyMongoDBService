package org.fsdcic.mongo.dao.impl;

import org.fsdcic.mongo.dao.SysLogDao;
import org.fsdcic.mongo.entity.SysLog;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by ben on 2017-9-15.
 */
@Repository("sysLogDaoImpl")
public class SysLogDaoImpl implements SysLogDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public void insert(SysLog object, String collectionName) {
        mongoTemplate.insert(object, collectionName);
    }

    public SysLog findOne(Map<String, Object> params, String collectionName) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(params.get("id"))), SysLog.class,collectionName);
    }

    public List<SysLog> findAll(Map<String, Object> params, String collectionName) {
        List<SysLog> SysLogList = mongoTemplate.find(new Query(Criteria.where("id").lt(params.get("id"))), SysLog.class,collectionName);
        return SysLogList;
    }

    public void update(Map<String, Object> params, String collectionName) {
//      先查询，后update（可加入Criteria条件筛选：Criteria这个类也提供了where 、and、lt等）
        mongoTemplate.upsert(new Query(Criteria.where("id").is(params.get("id"))), new Update().set("name", params.get("name")), SysLog.class,collectionName);
    }

    public void createCollection(String collectionName) {
        mongoTemplate.createCollection(collectionName);
    }

    public void remove(Map<String, Object> params, String collectionName) {
        mongoTemplate.remove(new Query(Criteria.where("id").is(params.get("id"))),SysLog.class,collectionName);
    }
}
