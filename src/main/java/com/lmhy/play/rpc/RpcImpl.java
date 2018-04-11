package com.lmhy.play.rpc;

public class RpcImpl implements RpcInterface {
    @Override
    public String handle(String name, String doing, Integer num) {
        return String.format("%s is %s ,num is %d", name, doing, num);
    }
}
