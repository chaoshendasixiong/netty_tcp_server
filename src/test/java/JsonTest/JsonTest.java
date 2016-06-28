package JsonTest;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xxsy on 2016/3/14.
 */
public class JsonTest {
    public static void main(String[] args) {
        Gson g = new Gson();


        List<Func> list = new ArrayList<Func>();
        for (int i = 0; i < 5; i++) {
            Func f = new Func();
            f.setId(i);
            f.setClasses("cls"+i);
            f.setMethod("method"+i);
            list.add(f);
        }
        System.out.println(g.toJson(list));
    }
    public static void main1(String[] args) {
        //String user_j = "{\"name\":\"jack\",\"aaa\":10}";
        //Gson gson = new Gson();
        //User user = gson.fromJson(user_j, User.class);
        //int b = 0;
        Gson g = new Gson();
        P p1 = new P();
        p1.setName("ONE");
        p1.setData(getPojoMap()); //设置map
        p1.setLis(getPojoList()); //设置list
        System.out.println(g.toJson(p1)); //输出

    }
    private static List<Object> getPojoList() {
        List<Object> list = new ArrayList<Object>();
        P p = new P();
        p.setName("POJOLIST-P");
        list.add(p);
        return list;
    }

    private static Map<String, Object> getPojoMap() {
        Map<String, Object> m = new HashMap<String, Object>();
        P p = new P();
        p.setName("POJOMAP-P");
        m.put("POJOMAP-P", p);
        return m;
    }
}

class Func{
    private int id;
    private String method;
    private String classes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }
}
