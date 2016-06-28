package RedisTest;

import com.csdsx.redis.RedisUtil;

/**
 * Created by xxsy on 2016/3/15.
 */
public class RedisTest {
    public static void main(String[] args) throws InterruptedException {
        RedisUtil.init();
        Thread.sleep(4000);
        RedisUtil.set("a", "你好");
        System.out.println(RedisUtil.get("a"));
    }
}
