package TroveTest;

import gnu.trove.list.array.TIntArrayList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxsy on 2016/4/11.
 */
public class TroveTest {
    public static void main(String[] args) throws InterruptedException {
        TIntArrayList list1 = new TIntArrayList();
        List<Integer> list2 = new ArrayList<Integer>();
        long begin = System.currentTimeMillis();
        for(int i = 0; i < 100; i++) {
            Test t = new Test();
            t.list =  list2;
            Thread thread = new Thread(t);
            thread.start();
        }
        Thread.sleep(2000);
        long end = System.currentTimeMillis();
        System.out.println(list2.size());
        System.out.println(end- begin);
    }


}

class Test implements Runnable{
    public List<Integer> list;
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        list.add(1);
        list.add(1);
        list.add(1);
    }
}
