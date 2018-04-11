package com.lmhy.play.ioc;

import java.util.HashMap;

public class IocCore {
    private static HashMap<String,Class> map = new HashMap<>();

    public static void init(){
        load("ioc",IocImpl.class);
        load("main",IocMain.class);
    }
    public static void load(String name,Class cl){
        map.put(name,cl);
    }
    public static  <T> T get(String name){
        Class cl = map.get(name);
        try {
            return (T) cl.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
