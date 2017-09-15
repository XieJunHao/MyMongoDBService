import com.mongodb.DB;
import com.mongodb.Mongo;
import org.fsdcic.mongo.dao.SysLogDao;
import org.fsdcic.mongo.entity.SysLog;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * Created by ben on 2017-9-15.
 */
public class MongoSpringTest {


//  测试连接mongodb数据库
    @Test
    public void testMongodb() {
        try {
            // 连接到 mongodb 服务
            Mongo mongo = new Mongo("192.168.1.30", 27017);
            //根据mongodb数据库的名称获取mongodb对象 ,
            DB db = mongo.getDB("test");
            Set<String> collectionNames = db.getCollectionNames();
            // 打印出test中的集合
            for (String name : collectionNames) {
                System.out.println("collectionName===" + name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static  SysLogDao sysLogDaoImpl;
    private static  ClassPathXmlApplicationContext  app;
    private static String collectionName;

//    执行每一个测试方法前，都要跑一次
//    @Before
    //针对所有测试方法，只执行一次，且必须为static void
    @BeforeClass
    public static void initSpring() {
        try {
            app = new ClassPathXmlApplicationContext(new String[] { "classpath:application.xml"});

            //从配置文件中获取实例（包扫描），实例名称要与@Repository("sysLogDaoImpl")中的名字一致
            sysLogDaoImpl = (SysLogDao) app.getBean("sysLogDaoImpl");
            collectionName ="SysLog";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAdd()
    {

        //添加一百个user
//        for(int i=0;i<100;i++){
//            SysLog sysLog =new SysLog();
//            sysLog.setId(i);
//            sysLog.setIp("192.168.0."+i);
//            sysLog.setUserName("测试"+i);
//            sysLog.setContent("测试内容"+i);
//            List<String> tables = new ArrayList<String>();
//            tables.add("这是第一张表单");
//            tables.add("这是第二张表单");
//            sysLog.setTableMessage(tables);
//            sysLogDaoImpl.insert(sysLog,collectionName);
//        }
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("id", 50);
        List<SysLog> list=sysLogDaoImpl.findAll(params,collectionName);
        System.out.println("查询到的列表"+list.size());
    }



//    @Autowired
//    private MongoTemplate mongoTemplate;
//    @Before
//    public void  BeforeTest(){
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
//        mongoTemplate= (MongoTemplate) context.getBean("mongoTemplate");
//    }
//    /**
//     * 插入日志信息
//     */
//    @Test
//    public void testAddUser() {
//        SysLog sysLog = new SysLog();
//        sysLog.setUserName("太极");
//        sysLog.setIp("192.168.1.1");
//        sysLog.setContent("法人一门式业务受理");
//        List<String> tables = new ArrayList<String>();
//        tables.add("这是第一张表单");
//        tables.add("这是第二张表单");
//        sysLog.setTableMessage(tables);
//
//        // 插入数据
//        mongoTemplate.insert(sysLog);
//    }
//
//    @Test
//    public void testFind3() {
//        Criteria cri = Criteria.where("$gt").is(90).and("$lt").is(100);
//        Query query = new Query(Criteria.where("score").elemMatch(cri));
//        query.fields().include("score");
//        List<SysLog> sysLogs = mongoTemplate.find(query, SysLog.class);
//        for(SysLog sysLoglist : sysLogs) {
//            System.out.println(sysLogs.toString());
//        }
//    }

}
