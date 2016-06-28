package JsonTest;

import java.util.List;
import java.util.Map;

/**
 * Created by xxsy on 2016/3/14.
 */
public class P {
    private String name;

    private Map<String, Object> data;

    private List<Object> lis;

    public P() {

    }

    public P(String name, Map<String, Object> data, List<Object> lis) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public List<Object> getLis() {
        return lis;
    }

    public void setLis(List<Object> lis) {
        this.lis = lis;
    }
}
