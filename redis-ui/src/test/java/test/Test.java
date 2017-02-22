package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redis.persistence.domain.User;

import redis.clients.jedis.Jedis;

public class Test {

//    private static final Logger log = LoggerFactory.getLogger(TicketServlce.class);

//    private static final ThreadLocalRandom R = ThreadLocalRandom.current();
    
    public static void main(String[] args) {

        Integer i = 127;
        Integer j = 127;
        System.err.println(i);
        System.err.println(j);
        System.out.println(i == j);

//        Long time = System.currentTimeMillis();
//        for (int i = 0;i < 100000000;i++){
//            ThreadLocalRandom.current().nextInt(10000);
//            RandomUtils.nextInt(10000);
//        }
//        System.err.println(System.currentTimeMillis() - time);


//        Jedis jedis =  new Jedis("127.0.0.1",6379);
//        System.err.println(jedis.incr("number"));
//        jedis.close();

    }


    @SuppressWarnings("unused")
    private static void testSet(){
        Jedis jedis =  new Jedis("127.0.0.1",6379);
//        jedis.auth(password);



        //User user = new User(1l, "Shaowei Zhang");
        //jedis.set("user:1".getBytes(), SerializeUtil.serialize(user));

        byte[] u = jedis.get("user:1".getBytes());


        User user = (User) SerializeUtil.unserialize(u);

        System.err.println(user.getId() + " - " + user.getName());
        jedis.close();
    }


    @SuppressWarnings("unused")
    private static void setList(){
        Jedis jedis =  new Jedis("127.0.0.1",6379);
        List<String> list = Arrays.asList("123","456","789","asd","fgh","jkl");
        for(String s : list){
            jedis.lpush("list", s);
        }

        List<String> data = jedis.lrange("list", 0, 10);

        System.err.println(data.toString());
        jedis.close();
    }


    @SuppressWarnings("unused")
    private static void setMap(){
        Jedis jedis =  new Jedis("127.0.0.1",6379);

        Map<String,String> map = new HashMap<String,String>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value2");
        String status = jedis.hmset("map", map);
        System.err.println(status);
        jedis.close();
    }


    @SuppressWarnings("unused")
    private static void getMap(){
        Jedis jedis =  new Jedis("127.0.0.1",6379);

        Map<String,String>map = jedis.hgetAll("map");

        System.err.println(map.toString());
        jedis.close();
    }

}
