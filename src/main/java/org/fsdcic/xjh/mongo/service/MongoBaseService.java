package org.fsdcic.xjh.mongo.service;

import org.fsdcic.xjh.mongo.entity.BaseEntity;
import org.fsdcic.xjh.mongo.entity.ServiceResult;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by ben on 2017-9-19.
 */
public interface MongoBaseService<T extends BaseEntity> {
    public ServiceResult<T> save( T entity, String collectionName);
    public ServiceResult<T> updateById(String id, Update update, Class<T> aClass, String collectionName);
    public ServiceResult<List<T>> deleteByids(List<String> ids, Class<T> tClass, String collectionName);
    public ServiceResult<PageImpl<T>> findPageData(Query query, Pageable pageable, Class<T> tClass, String collectionName);
    public ServiceResult<T> findOne(String id,Class<T> tClass, String collectionName, Boolean ignoreClass);
}
