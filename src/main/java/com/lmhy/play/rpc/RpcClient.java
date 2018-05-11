package com.lmhy.play.rpc;

import org.junit.Test;

public class RpcClient {
    @Test
    public void start(){
        RpcInterface rpcInterface = new RpcRemoteImpl();
        for (int i = 0; i < 20; i++) {
            String result = rpcInterface.handle("app","do",1);
            System.out.println(result);
        }

    }
}
