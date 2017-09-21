package org.fsdcic.xjh.mongo.dao.impl;

import com.mongodb.WriteResult;
import org.fsdcic.xjh.mongo.dao.MongoBaseDao;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ben on 2017-9-15.
 */
@Repository("mongoBaseDaoImpl")
public class MongoBaseDaoImpl<T> implements MongoBaseDao {

    private MongoOperations mongoTemplate;

    public MongoBaseDaoImpl(MongoOperations mongoOperations) {
        this.mongoTemplate = mongoOperations;
    }

    public MongoBaseDao getDao() {
        return this;
    }

    /**
     * 插入一条信息
     * @param object 插入的对象
     * @param collectionName 对应的集合名称
     */
    public T insert(Object object, String collectionName) {
        mongoTemplate.insert(object, collectionName);
        return  (T) object;
    }

    /**
     * 插入多条信息
     * @param list 对象list
     * @param collectionName 对应的集合名称
     */
    public void insertList(List list, String collectionName) {
        mongoTemplate.insert(list,collectionName);
    }

    /**
     * 按照id查询一条信息
     * @param id 主键id
     * @param collectionName 对应的集合名称
     * @return
     */
    public Object findOne(String id, Class aclass, String collectionName) {
        Object oid = id;
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(oid)),aclass,collectionName);
    }

    /**
     * 根据id更新
     * @param id 对应的id
     * @param update 封装好的更新对象
     * @param aclass 对象的class
     * @param collectionName 对应的集合名称
     */
    public WriteResult updateById(String id, Update update, Class aclass, String collectionName) {
//        mongoTemplate.upsert(new Query(Criteria.where("id").is(params.get("id"))), new Update().set("name", params.get("name")), SysLog.class,collectionName);
         Object oid = id;
//      先查询，后update（可加入Criteria条件筛选：Criteria这个类也提供了where 、and、lt等）
         WriteResult writeResult = mongoTemplate.updateFirst(new Query(Criteria.where("id").is(oid)),update,aclass,collectionName);
         return writeResult;
    }

    /**
     * 查询分页
     * @param query 封装好的查询条件
     * @param pageable 封装好的page对象（sort、一页显示的数量等）
     * @param aclass 需要查询的对象的class
     * @param collectionName 对应的集合名称
     * @return
     */
    public PageImpl findPageByQuery(Query query, Pageable pageable, Class aclass, String collectionName) {
//      查询条数
        long count = mongoTemplate.count(query,aclass,collectionName);
//      查询当前页
        List objList = mongoTemplate.find(query.with(pageable), aclass,collectionName);
//      返回page数据
        return new PageImpl<T>(objList, pageable, count);
    }


    /**
     * 手动创建集合
     * @param collectionName
     */
    public void createCollection(String collectionName) {
        mongoTemplate.createCollection(collectionName);
    }


    /**
     * 根据id列表删除
     * @param ids idlist
     * @param collectionName 存储的集合名称
     * @param aclass 对象类型
     */
    public List removeByIds(List ids,Class aclass, String collectionName) {
        try{
            return mongoTemplate.findAllAndRemove(new Query(Criteria.where("id").in(ids)),aclass,collectionName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }




//    /**
//     * 查找一条日志记录
//     * @param params 参数map：至少一个参数
//     * @param collectionName 要查询的mongodb中的集合名称
//     * @return 日志对象
//     */
//    public SysLog findOne(Map<String, Object> params, String collectionName) {
//        Set<String> keySet = params.keySet();
//        BasicDBList basicDBList = new BasicDBList();
//        for (String key : keySet) {
//            basicDBList.add(new BasicDBObject(key,params.get(key)));
//        };
//        DBObject object = new BasicDBObject();
//        object.put("$and",basicDBList);
//        return mongoTemplate.findOne(new BasicQuery(object), SysLog.class,collectionName);
//    }
//
//    public List<SysLog> findAll(Map<String, Map<String,Object>> params, String collectionName) {
//        Set<String> conditionSet = params.keySet();
//        DBObject object = new BasicDBObject();
//        for (String condition : conditionSet) {
//            Map<String,Object> contentMap = params.get(condition);
//            BasicDBList basicDBList = new BasicDBList();
//            for (String key : contentMap.keySet()) {
//                basicDBList.add(new BasicDBObject(key,contentMap.get(key)));
//            }
//            object.put(condition,basicDBList);
//        };
//        List<SysLog> SysLogList = mongoTemplate.find(new BasicQuery(object), SysLog.class,collectionName);
//        return SysLogList;
//    }

//    public void update(Map<String, Object> params, String collectionName) {
////      先查询，后update（可加入Criteria条件筛选：Criteria这个类也提供了where 、and、lt等）
//        mongoTemplate.upsert(new Query(Criteria.where("id").is(params.get("id"))), new Update().set("name", params.get("name")), SysLog.class,collectionName);
//    }

}
