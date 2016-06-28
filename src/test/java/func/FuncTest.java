package func;

import com.csdsx.func.CallFunc;

import java.io.File;

/**
 * Created by xxsy on 2016/3/14.
 */
public class FuncTest {
    static {
        System.setProperty("log4j.configurationFile", System.getProperty("user.dir") + File.separator + "conf" + File.separator
                + "log4j2.xml");
    }
    public static void main(String[] args) throws ClassNotFoundException {
        CallFunc.init();
        for(short i = 0; i < 4; i++) {
            CallFunc.invoke(i,null);
        }

    }
}
