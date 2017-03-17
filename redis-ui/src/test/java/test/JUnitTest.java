package test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.redis.api.request.OrderRequest;
import com.redis.api.request.PassengerRequest;
import com.redis.api.request.ProductRequest;
import com.redis.api.request.SaleOrderIntercityRequest;
import com.redis.persistence.beans.BaseBean;
import com.redis.persistence.cache.BaseValueCache;
import com.redis.persistence.domain.User;
import com.redis.service.user.TestUserService;
import com.redis.service.utils.Log4jUtils;
import com.redis.utils.DesBase64For3;
import com.thread.service.ThreadPoolService;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.convert.converter.Converter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * 描述：jUnit4 测试类
 *
 * @author zhangshaowei
 * @version v1.0
 * @since v1.0
 */
@RunWith(JUnit4ClassRunner.class)//SpringJUnit4ClassRunner.class log4j会随tomcat启动加载，测试则不会 这里需要手动加载
@Rollback(false)//本机不回滚
@Transactional(transactionManager = "transactionManager")
@ContextConfiguration(locations = {"classpath:config/spring/app-context-*.xml"})
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class JUnitTest {

    private final Log log = Log4jUtils.getLog(this.getClass());

    /**
     * 初始化mapper参数
     */
    private ObjectMapper mapper = new ObjectMapper();

    public JUnitTest() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //忽略没有的属性
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); //允许没有双引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true); //允许转义字符
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true); //允许单引号
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    @Resource(name = "baseRedisCache")
    private BaseValueCache<String, Object> baseCache;

//    @Resource(name = "baseRedisUserCache")
//    private BaseUserCache userCache;

//    @Resource(name = TicketServlce.SERVICE)
//    private TicketServlce ticketService;

//    @Resource(name = "userHibernateDao")
//    private UserDao<User> userDao;

//    @Resource(name = UserService.SERVICE_NAME)
//    private UserService userService;

    @Resource(name = "testUserService")
    private TestUserService userService;

    @Resource(name = "threadPool")
    private ThreadPoolService threadPool;


    @Test
//    @Repeat(5)
    public void test() throws Exception {

        for (int i = 1; i < 50; i++) {
            this.threadPool.execute(this::printl, new User(Long.valueOf(i)));
        }


//        Converter<String, Integer> converter = Integer::valueOf;

//        Consumer<BaseBean> consumer = JUnitTest::printl;

//        this.threadPool.execute(this::printl, new User());


//        this.userService.test();

//        List<User> users = this.userService.findAll();
//
//        users.forEach(user -> System.out.println(user));


    }


    public void printl(BaseBean bean) {
        try {
            System.out.println("提交第" + bean.getId() + "个任务!");
            Thread.sleep(2000);
            System.out.println("running=====" + bean);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void des() throws Exception {
        Date now = new Date();
        ProductRequest product = new ProductRequest();
        product.setLineCode("0001");
        product.setProductCode("NC0001");
        product.setProductName("测试线路");
        product.setOperationStartDate(now);
        product.setOperationEndDate(DateUtils.addDays(now, 5));
        product.setAheadDay(5);
        product.setVehicleLevel("大型高一");
        product.setVehicleType("客车");
        product.setSeats(29);
        product.setPrice(1000);
        product.setInsurance(800000);
        SaleOrderIntercityRequest data = new SaleOrderIntercityRequest();
        data.setSupplierNumber("10000005");
        data.setPurchaseNumber("ASDJAIHJ989987");
        data.setOpeartor("vdc");
        data.setProduct(product);
        data.setVehicleQuantity(2);
        data.setPassengerQuantity(13);
        data.setUseDate(now);
        data.setTimeScope("9:00");
        data.setAmount(1000);
        String json = mapper.writeValueAsString(data);
        System.out.println(json);
        System.err.println(DesBase64For3.encrypt(json));
    }

    private void des1() throws Exception {
        Date now = new Date();
        OrderRequest data = new OrderRequest();
        data.setOrderNumber("PI201703151740420011");
        data.setSupplierNumber("10000005");
        String json = mapper.writeValueAsString(data);
        System.out.println(json);
        System.err.println(DesBase64For3.encrypt(json));
    }

    private void des2() throws Exception {
        Date now = new Date();
        PassengerRequest data = new PassengerRequest();
        data.setOrderNumber("PI201703132032570008");
        data.setSupplierNumber("10000005");
        data.setOpeartor("vdc");
        data.setRemark("test");
        data.setPassengers(10);
        String json = mapper.writeValueAsString(data);
        System.out.println(json);
        System.err.println(DesBase64For3.encrypt(json));
    }


    private static final Long MAX_ACCESS_TIMES = 2L;

    private static final Long MAX_ACCESS_TIME_UNIT = 10L;

    public Boolean accessFrequency(String key) {
        Boolean access = true;
        //现在访问的时间戳
        Long now = System.currentTimeMillis();
        if (this.baseCache.size(key) == MAX_ACCESS_TIMES) {
            Long first = (Long) this.baseCache.get(key, 0L);
            access = now - first > MAX_ACCESS_TIME_UNIT * 1000L;
            if (access) { //时间间隔大于允许范围 移除第一个 并将当前时间戳插入到队尾
                this.baseCache.leftPop(key);
                this.baseCache.rightPush(key, now);
            }
        } else { //访问次数未达到指定次数则不验证 ，仅将当前时间戳放入队尾
            this.baseCache.rightPush(key, now);
        }
        //每次访问都刷新该队列的生存时间
        this.baseCache.expire(key, MAX_ACCESS_TIME_UNIT, TimeUnit.SECONDS);
        return access;
    }

//    private void testRedis()throws Exception{
//        User u = new User(null, "李四", 22, 0, "月球");
//        //存数据库
//        this.userService.saveWithHibernate(u);
//
//        //取缓存
//        User user = this.userService.get(u.getId());
//        System.err.println("读取缓存:" + user.toString());
//    }


}
