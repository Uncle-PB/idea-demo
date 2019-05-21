package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.Set;


@RestController
public class controller {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @PostMapping("test")
    public String demo1(User user){
//        String name = userService.getName();
        return "hello nice to meet you!"+user;
    }

    @PostMapping("test2")
    public String demo2(User user){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("name",user.getName());
        ops.set("age",user.getAge().toString());
        return "name="+ops.get("name")+"  age="+ops.get("age");
    }
    @GetMapping("test3")
    public String demo3(){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        return "name="+ops.get("name")+"  age="+ops.get("age");
    }

    @PostMapping("redis")
    public void redis1(@RequestParam("values")String[] values){
        String key="com:dragon:";
        RedisConnection connection = jedisConnectionFactory.getConnection();
        Jedis jedis=(Jedis)connection.getNativeConnection();

        for(String s:values){
            jedis.set(s,s);
        }
        connection.close();
    }
    @GetMapping("getRedis")
    public String redis2(){
        String key="com:dragon:";
        RedisConnection connection = jedisConnectionFactory.getConnection();
        Jedis jedis=(Jedis)connection.getNativeConnection();
        Set<String> keys = jedis.keys(key+"*");
        Set<String> all = jedis.keys("*");

        connection.close();
        return keys.toString()+"==========all========"+all.toString();
    }

    @PostMapping("111")
    public String  one(String name){
        RedisConnection connection = jedisConnectionFactory.getConnection();
        Jedis jedis=(Jedis)connection.getNativeConnection();
        jedis.set(name,"qwer");
        return "ok";
    }

    @PostMapping("222")
    public String  two(String name){
        RedisConnection connection = jedisConnectionFactory.getConnection();
        Jedis jedis=(Jedis)connection.getNativeConnection();
        jedis.set(name,"123");
        return "two ok";
    }
}
