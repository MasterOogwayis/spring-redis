package com.redis.service.ticket;

import java.net.SocketException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.redis.persistence.cache.BaseValueCache;
import com.redis.persistence.domain.User;
import com.redis.service.user.UserService;


@Service(TicketServlce.SERVICE)
public class TicketServlce {
	
	public static final String SERVICE = "ticketService";
	
	private final long LOCK_TIMEOUT = 10 * 1000L;//加锁超时时间 ms
	
	private final long WAIT_TIMEOUT = 10 * 1000L;//等待超时时间
	
	private final ThreadLocalRandom R = ThreadLocalRandom.current();
	
	@Resource(name = "baseRedisCache")
	private BaseValueCache<String, Object> baseCache;
	
	@Resource(name = UserService.SERVICE_NAME)
	private UserService userService;
	
	private final Logger log = LoggerFactory.getLogger(TicketServlce.class);
	
	
	/**
	 * 描述：分布式锁的强一致性
	 * 	    redis事物受spring管理 
	 * 		这里不能使用事物 否则指令集不能立即提交
	 * 		所以下单阶段如果出错 需要手动将消耗的票数恢复 increment
	 * 		
	 * 		若抛出异常则请在外层捕获 使用悲观锁
	 * 		
	 *
	 * @param ticketKey
	 * @param number
	 * @return
	 * @throws Exception
	 * @author 	: zhangshaowei
	 * @version	: v1.0
	 * @since 	: v1.0
	 * @date 	: 2017年1月11日 下午5:22:03
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Boolean validateTicket(String ticketKey, Integer number)throws Exception{
		if(!this.baseCache.exists(ticketKey)){//缓存票务不存在则需读取数据库加载到缓存
			this.updateTicketCache(ticketKey);
		}
		
		long leftNum = this.baseCache.decrement(ticketKey, number);//cache的原子性操作 修改库存 数量-number并返回
		//leftNum < 0则代表票数不足    将锁定的票数返还
		if(leftNum < 0){
			this.baseCache.increment(ticketKey, number);
			return false;
		}
		return true;
	}
	
	
	
	/**
	 * 描述：多实例更新缓存 使用setNX分布式锁   更新缓存不需要事务控制
	 * 
	 *  互斥，不管任何时候，只有一个客户端能持有同一个锁。
	 *  不会死锁，最终一定会得到锁，即使持有锁的客户端对应的master节点宕掉。
	 *  容错，只要大多数Redis节点正常工作，客户端应该都能获取和释放锁。
	 *
	 * @param lock
	 * @author 	: zhangshaowei
	 * @version	: v1.0
	 * @since 	: v1.0
	 * @date 	: 2017年1月11日 下午5:25:31
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateTicketCache(String ticketKey)throws Exception{
		boolean locked = false;
		String lock = ticketKey + "_lock";
		try {
			long timer = WAIT_TIMEOUT;
			while (timer > 0) {
				//加锁
				locked = this.baseCache.setNX(lock, "locked");
				//并不关心值是什么 主要关心是否成功上锁
				if(locked){
					//加锁成功后设置过期时间防止 任何意外出现的死锁
					this.baseCache.expire(lock, LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
					//分布式加锁成功后需尝试 使用下单同一个悲观锁锁定数据库  防止下单进行时 缓存恢复 读取库存出错
					
					User user = this.userService.get(9l);
					this.baseCache.set(ticketKey, user.getAge(), 12, TimeUnit.HOURS);
					break;
				}
				
				//使用随机的等待时间可以一定程度上保证公平性   20ms - 80ms
				Long waitTime = Long.valueOf(R.nextLong(80) + 20L);
				Thread.sleep(waitTime);
				if (this.baseCache.exists(ticketKey)) {//等待后已有更新则跳出
					break;
				}
				timer -= waitTime;
			}
			if(timer <= 0){//超出等待时间 抛出异常 走悲观锁
				this.log.error("更新缓存 " + ticketKey + "超时，等待时间" + (WAIT_TIMEOUT - timer));
				throw new SocketException("尝试更新缓存超时：未能读取缓存 且 无法获取锁   key = " + ticketKey);
			}
		} catch (Exception e) {
			throw e;
		}finally{
			if(locked){//成功加锁 在最后需解锁
				this.baseCache.del(lock);
			}
		}
	}
	
	
	
}
