package com.csdsx.func;

import com.csdsx.Msg.MsgHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xxsy on 2016/3/14.
 */
public final class CallFunc {
    public static Map<Short, Method> methodMap = new HashMap<Short, Method>();

    public static void RegMethod(short type, Method method) {
        methodMap.put(type, method);
    }

    private CallFunc() {}

    public static void init() {
        //
        File file = new File("conf/func.json");
        BufferedReader reader = null;
        StringBuffer data = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tmp = null;
            while ((tmp = reader.readLine())!=null) {
                data.append(tmp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Gson g = new Gson();
        TypeToken<List<FunConf>> list = new TypeToken<List<FunConf>>() {};
        Type t = list.getType();
        List<FunConf> func_list = g.fromJson(data.toString(), t);
        for(int i = 0; i < func_list.size(); i++) {
            FunConf funConf = func_list.get(i);
            Class<?> threadClazz = null;
            try {
                threadClazz = Class.forName(funConf.getClasses());
                Method method = threadClazz.getMethod(funConf.getMethod(), MsgHandler.class);
                RegMethod(funConf.getId(), method);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public static void invoke(short type, MsgHandler req) {
        Method m = methodMap.get(type);
        if(m == null) {
            return;
        }
        try {
            m.invoke(null, req);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}

class FunConf{
    private short id;
    private String classes;
    private String method;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
