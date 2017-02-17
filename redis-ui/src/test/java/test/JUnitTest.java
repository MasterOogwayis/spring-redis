package test;

import java.net.SocketException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.redis.persistence.cache.BaseUserCache;
import com.redis.persistence.cache.BaseValueCache;
import com.redis.persistence.dao.UserDao;
import com.redis.persistence.domain.User;
import com.redis.service.ticket.TicketServlce;
import com.redis.service.user.UserService;
import com.redis.service.utils.Log4jUtils;



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
@ContextConfiguration(locations={"classpath:config/spring/app-context-*.xml"})  
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class JUnitTest {
	
    private final Log log = Log4jUtils.getLog(this.getClass());
    
	@Resource(name = "baseRedisCache")
	private BaseValueCache<String, Object> baseCache;
	
	@Resource(name = "baseRedisUserCache")
	private BaseUserCache userCache;
	
	@Resource(name = TicketServlce.SERVICE)
	private TicketServlce ticketService;
	
	@Resource(name = "userHibernateDao")
	private UserDao<User> userDao;
	
	@Resource(name = UserService.SERVICE_NAME)
	private UserService userService;
	
	
	@Test
//	@Repeat(5)
	public void test() throws Exception{


//	    this.log.warn("票务缓存失败", new SocketException());

//	    for(int i = 0;i<10;i++){
//	        System.out.println(this.accessFrequency("key"));;
//	        Thread.sleep(200);
//	    }
//	    
//	    Thread.sleep(200);
	    
//	    Long time = System.currentTimeMillis();
//	    User user = this.userService.get(10L);
//	    System.out.println(user);
//	    System.err.println(System.currentTimeMillis()-time);
	    
	    
	    
//	    List<String> list = this.userService.testList("list");

        List<User> users = this.userService.findAll();
        for(User user : users){
			System.err.println(user);
		}
		
		
//		this.userCache.put(key, hashKey, value);
		
//		this.ticketService.validateTicket("ticket", 5);
		
//		List<User> users = this.userDao.findAll();
//		for(User user : users){
//			System.err.println(user);
//		}
//		this.userCache.rightPushAll("list", users);
		
		
//		List<User> users = this.userCache.range("list", 0, -1);
//		System.out.println(users.size());
//		for(User user : users){
//			System.err.println(user);
//		}
		
		
		
//		Map<String,Object> map = new HashMap<>();
//		map.put("name", "张三");
//		map.put("age", 18);
//		map.put("gender", "男");
//		this.baseCache.hmset("map", map);
		
//		System.err.println(this.userCache.exists("user_list"));
//		List<User> users = this.userCache.range("user_list", 0, 10);
//		for(User user : users){
//			System.out.println(user);
//		}
		
//		List<User> users = new ArrayList<>();
//		users.add(new User(1l, "张三", 18, "男", "Earth"));
//		users.add(new User(2l, "李四", 19, "男", "Earth"));
//		users.add(new User(3l, "王五", 20, "男", "Earth"));
//		users.add(new User(4l, "刘华", 17, "男", "Earth"));
//		users.add(new User(5l, "小明", 16, "男", "Earth"));
//		users.add(new User(6l, "小芳", 15, "男", "Earth"));
//		this.userCache.rightPushAll("user_list", users);
		
		
//		this.userCache.save(new User(1l, "张三", 18, "男", "Earth"));
		
	}
	
	private static final Long MAX_ACCESS_TIMES = 2L;
    
    private static final Long MAX_ACCESS_TIME_UNIT = 10L;
	
	public Boolean accessFrequency(String key) {
        Boolean access = true;
        //现在访问的时间戳
        Long now = System.currentTimeMillis();
        if (this.baseCache.size(key) == MAX_ACCESS_TIMES) {
            //往前第10次访问的时间戳
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
	
//	private void testRedis()throws Exception{
//		User u = new User(null, "李四", 22, 0, "月球");
//		//存数据库
//		this.userService.saveWithHibernate(u);
//		
//		//取缓存
//		User user = this.userService.get(u.getId());
//		System.err.println("读取缓存:" + user.toString());
//	}
	
}
