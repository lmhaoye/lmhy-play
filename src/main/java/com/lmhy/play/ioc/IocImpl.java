package com.lmhy.play.ioc;

public class IocImpl implements IocInterface {
    @Override
    public String say(String name) {
        return "Hello " + name;
    }
}
