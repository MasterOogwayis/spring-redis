package test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import com.redis.persistence.domain.User;

import redis.clients.jedis.Jedis;

public class Test {

//    private static final Logger log = LoggerFactory.getLogger(TicketServlce.class);

//    private static final ThreadLocalRandom R = ThreadLocalRandom.current();

    public static void main(String[] args) {



//        File file = new File("C:\\Users\\ZhangShaowei\\Desktop\\I must know.txt");
//        final Path path = file.toPath();
//        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
//
//            lines.onClose(() -> System.out.println("Done!")).forEach(line -> System.out.println(line));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        List<Integer> list = new ArrayList<>();
//        for(int i = 0;i<1000000;i++) {
//            list.add(i);
//        }
//
//        Long time = System.currentTimeMillis();
//        int sum = 0;
//        for(int i : list){
//            sum += i;
//        }
//        System.out.println(System.currentTimeMillis() - time);
//        int s = list.stream().mapToInt(i -> i).sum();
//        System.err.println(System.currentTimeMillis() - time);

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        list.forEach(System.out::print);

        int sum = list.stream().filter(i -> i%3 == 0).mapToInt(i -> i).sum();
        System.err.println(sum);

//        Map<Integer, String> map = new HashMap<>();
//        for (int i = 0; i < 10; i++) {
//            map.putIfAbsent(i, "val_" + i);
//        }
//
//        map.forEach((id, value) -> System.out.println(value));
//
//        map.computeIfPresent(3, (num, value) -> value + num);
//        System.out.println(map.get(3));




//        Car car = Car.create(Car::new);
//        List<Car> cars = Arrays.asList(car);
//        cars.forEach(Car::collide);
//        cars.forEach(Car::repair);
//        cars.forEach(car::follow);



//        Map<String, String> map = new HashMap<>();
//        map.put("name", "Y2K");
//        map.put("age", "20");
//        map.put("gender", "未知生物");
//        map.put("address", "Mars");
//
//        List<String> list = new ArrayList<>();
//
//
//        map.forEach((key, value) -> list.add(value));
//        list.sort((a, b) -> a.compareTo(b));
//        System.out.println(list.toString());



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
