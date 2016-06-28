package TimeTest;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by xxsy on 2016/4/7.
 */
public class TimeTest {

    public static void print(Object obj) {
        System.out.println(obj.toString());
    }

    /**
     * 将int转为低字节在前，高字节在后的byte数组
     *
     * @param n
     * int
     * @return byte[]
     */
    public static byte[] toLH(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * 将int转为高字节在前，低字节在后的byte数组
     *
     * @param n
     * int
     * @return byte[]
     */
    public static byte[] toHH(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }
    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        print(time);
        String a = "123";
        print(a.equals("123"));
        ByteBuffer bb = ByteBuffer.wrap(new byte[4]);
        bb.asIntBuffer().put(3);
        System.out.println(Arrays.toString(bb.array()));
        print(Arrays.toString(toLH(3)));
        print(Arrays.toString(toHH(3)));
    }

}
