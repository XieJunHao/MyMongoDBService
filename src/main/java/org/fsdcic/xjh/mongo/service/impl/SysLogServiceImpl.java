package org.fsdcic.xjh.mongo.service.impl;

import org.fsdcic.xjh.mongo.dao.MongoBaseDao;
import org.springframework.stereotype.Service;

/**
 * Created by ben on 2017-9-19.
 */
@Service("sysLogServiceImpl")
public class SysLogServiceImpl extends MongoBaseServiceImpl{

    public SysLogServiceImpl(MongoBaseDao mongoBaseDao) {
        super(mongoBaseDao);
    }

//    public ServiceResult<SysLog> save(SysLog sysLog, String collectionName){
//        return super.save(SysLog.class,sysLog,collectionName);
//    }

}
