package org.fsdcic.xjh.mongo.dao;

import com.mongodb.WriteResult;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by ben on 2017-9-15.
 * MongoDB操作基础接口
 */
public interface MongoBaseDao<T> {
    //添加单个
    public T insert(T object,String collectionName);
//    添加多个
    public void insertList(List<T> tList, String collectionName);
    //根据id查找
    public T findOne(String id, Class aclass, String collectionName);
    //查找所有
    public PageImpl<T> findPageByQuery(Query query, Pageable pageable, Class<T> aclass, String collectionName);
    //修改
    public WriteResult updateById(String id, Update update, Class<T> aclass, String collectionName);
    //创建集合
    public void createCollection(String collectionName);
    //根据id列表删除
    public List<T> removeByIds(List<String> ids, Class<T> aclass, String collectionName);

    public MongoBaseDao getDao();

}
