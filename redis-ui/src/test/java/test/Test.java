package test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.redis.persistence.domain.User;
import com.redis.utils.DesBase64For3;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {

//    private static final Logger log = LoggerFactory.getLogger(TicketServlce.class);

//    private static final ThreadLocalRandom R = ThreadLocalRandom.current();

    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        /**
         * 初始化mapper参数
         */
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //忽略没有的属性
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); //允许没有双引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true); //允许转义字符
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true); //允许单引号
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        Map<String, String> map = new HashMap<>();
        map.put("supplierNumber", "0023");
        System.err.println(DesBase64For3.encrypt(mapper.writeValueAsString(map)));


        Set<String> set = new HashSet<>();
        set.add("String");
//        Timer timer = new Timer("hello");
//        timer.schedule(new PrintTimer(), 1000L, 2000L);


//        Jedis jedis =  new Jedis("127.0.0.1",6379);
//        System.err.println(jedis.incr("number"));
//        jedis.close();

    }


    static class PrintTimer extends TimerTask {

        @Override
        public void run() {
            System.out.println(new Date());
        }
    }

    public static void printl(Serializable customer){
        System.out.println(((Customer) customer).getName());

    }


    @SuppressWarnings("unused")
    private static void testSet() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
//        jedis.auth(password);


        //User user = new User(1l, "Shaowei Zhang");
        //jedis.set("user:1".getBytes(), SerializeUtil.serialize(user));

        byte[] u = jedis.get("user:1".getBytes());


        User user = (User) SerializeUtil.unserialize(u);

        System.err.println(user.getId() + " - " + user.getName());
        jedis.close();
    }


    @SuppressWarnings("unused")
    private static void setList() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        List<String> list = Arrays.asList("123", "456", "789", "asd", "fgh", "jkl");
        for (String s : list) {
            jedis.lpush("list", s);
        }

        List<String> data = jedis.lrange("list", 0, 10);

        System.err.println(data.toString());
        jedis.close();
    }


    @SuppressWarnings("unused")
    private static void setMap() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value2");
        String status = jedis.hmset("map", map);
        System.err.println(status);
        jedis.close();
    }


    @SuppressWarnings("unused")
    private static void getMap() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        Map<String, String> map = jedis.hgetAll("map");

        System.err.println(map.toString());
        jedis.close();
    }

}
