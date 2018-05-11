package com.lmhy.play.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RpcRemoteImpl implements RpcInterface {
    public static final int PORT = 1234;

    @Override
    public String handle(String name, String doing, Integer num) {
        List<String> addressList = lookupProviders("rpc.handler");
        String address = chooseTarget(addressList);
        try {
            Socket socket = new Socket(address, PORT);
            RpcRequest rpcRequest = generateRequest("rpc", "request", 1);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            objectOutputStream.writeObject(rpcRequest);

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object response = objectInputStream.readObject();

            if (response instanceof String) {
                String result = (String) response;
                return result;
            } else {
                System.out.println("error");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public RpcRequest generateRequest(String name, String doing, int num) {
        return new RpcRequest("handle", name, doing, num);
    }

    private String chooseTarget(List<String> providers) {
        if (null == providers || providers.size() == 0) {
            throw new IllegalArgumentException();
        }
        return providers.get(0);
    }

    public static List<String> lookupProviders(String name) {
        List<String> strings = new ArrayList();
        strings.add("127.0.0.1");
        return strings;
    }
}
