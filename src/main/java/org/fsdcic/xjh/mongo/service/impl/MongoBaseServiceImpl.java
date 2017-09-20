package org.fsdcic.xjh.mongo.service.impl;

import com.mongodb.WriteResult;
import org.fsdcic.xjh.mongo.dao.MongoBaseDao;
import org.fsdcic.xjh.mongo.entity.BaseEntity;
import org.fsdcic.xjh.mongo.entity.ServiceResult;
import org.fsdcic.xjh.mongo.service.MongoBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by ben on 2017-9-19.
 */
public class MongoBaseServiceImpl<T extends BaseEntity> implements MongoBaseService {

    private final MongoBaseDao mongoBaseDao;

    @Autowired
    public MongoBaseServiceImpl(MongoBaseDao mongoBaseDao) {
        this.mongoBaseDao = mongoBaseDao;
    }

    /**
     * 保存信息
     * @param entity 需要保存的对象
     * @param collectionName 对应的集合名称（当不存在时会自动创建）
     * @return
     */
    public ServiceResult<T> save(BaseEntity entity, String collectionName) {
        try{
            T t = (T) this.mongoBaseDao.getDao().insert(entity,collectionName);
            String id = t.getClass().getMethod("getId",null).invoke(t).toString();
            return new ServiceResult<T>(0,"插入成功，id:"+id);
        }catch (Exception e){
            return new ServiceResult<T>(1000,"发生异常："+e.getMessage());
        }
    }


    /**
     * 使用id查询信息
     * @param id 指定信息主键id
     * @param aClass 所属类的class（注意，当按id查找出来的对象，与class不符，则不能反序列化，不会报错）
     * @param collectionName 集合名称
     * @param ignoreClass 是否忽略对应的class查询
     * @return
     * 问题：当传入的class与目标保存的class不一致时，对象转换不会报错，但会返回空对象。
     */
    public ServiceResult<T> findOne(String id, Class aClass, String collectionName,Boolean ignoreClass) {

        try {
            T entity = (T)this.mongoBaseDao.findOne(id,aClass,collectionName);
            if(entity!=null){
                if(ignoreClass){
                    return new ServiceResult<T>(0,"查找成功",entity);
                }else {
//                  判断取出的对象类型是否与查找所需的对象类型一致
                    if(entity.get_class().equals(aClass.getName())){
                        return new ServiceResult<T>(0,"查找成功",entity);
                    }else {
                        return new ServiceResult<T>(ServiceResult.FAILURE,"Class类型匹配失败");
                    }
                }
            }else {
                return new ServiceResult<T>(ServiceResult.FAILURE,"查找不到对应ID的对象");
            }
        }catch (Exception e){
            return new ServiceResult<T>(ServiceResult.EXCEPTION,e.getMessage());
        }
    }


    /**
     * 使用id删除信息
     * @param ids id列表
     * @param aClass 用于封装返回的数据
     * @param collectionName 对应的集合名称
     * @return
     */
    public ServiceResult<List<T>> deleteByids(List ids, Class aClass, String collectionName) {
        try{
            if(ids.size()>0){
                List tList = this.mongoBaseDao.removeByIds(ids,aClass,collectionName);
                if(tList!=null && tList.size()>0){
                    return new ServiceResult<List<T>>(ServiceResult.SUCCESS,"删除成功",tList);
                }else {
                    return new ServiceResult<List<T>>(ServiceResult.FAILURE,"删除失败");
                }
            }else {
                return new ServiceResult<List<T>>(ServiceResult.FAILURE,"传入的id列表无值");
            }
        }catch (Exception e){
            return new ServiceResult<List<T>>(ServiceResult.EXCEPTION,e.getMessage());
        }
    }


    /**
     * 根据id进行更新
     * @param id 信息id
     * @param update 更新设置
     * @param aClass 对应的class
     * @param collectionName 集合名称
     * @return
     */
    public ServiceResult updateById(String id, Update update, Class aClass, String collectionName) {
        try{
            WriteResult writeResult = this.mongoBaseDao.updateById(id,update,aClass,collectionName);
            if(writeResult.isUpdateOfExisting()){
                return new ServiceResult(ServiceResult.SUCCESS,"更新成功");
            }else {
                return new ServiceResult(ServiceResult.FAILURE,"更新失败,请检查更新设置是否合法");
            }
        }catch (Exception e){
            return new ServiceResult(ServiceResult.EXCEPTION,e.getMessage());
        }
    }


    /**
     * 查询分页
     * @param query 查询条件
     * @param pageable 分页对象
     * @param aClass 查询的对象类型
     * @param collectionName 集合名称
     * @return
     */
    public ServiceResult<PageImpl<T>> findPageData(Query query, Pageable pageable, Class aClass, String collectionName) {
        try{
            if(query==null){
                query = new Query();
            }

            PageImpl page = this.mongoBaseDao.findPageByQuery(query,pageable,aClass,collectionName);
            if(page!=null){
               return new ServiceResult<PageImpl<T>>(ServiceResult.SUCCESS,"查询成功",page);
            }else {
                return new ServiceResult<PageImpl<T>>(ServiceResult.FAILURE,"查询失败");
            }
        }catch (Exception e){
            return new ServiceResult<PageImpl<T>>(ServiceResult.EXCEPTION,"查询异常");
        }
    }
}
