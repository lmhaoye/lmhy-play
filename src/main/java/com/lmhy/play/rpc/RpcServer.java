package com.lmhy.play.rpc;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcServer {

    private RpcInterface rpcInterface = new RpcImpl();

    private ServerSocket serverSocket;
    private Socket socket;

    @Test
    public void start() {
        try {
            serverSocket = new ServerSocket(1234);
            while (true) {
                socket = serverSocket.accept();
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    Object object = objectInputStream.readObject();

                    System.out.println("obj is " + object);

                    String result = "";
                    if(object instanceof RpcRequest) {
                        RpcRequest rpcRequest = (RpcRequest) object;
                        if("handle".equals(rpcRequest.getMethod())){
                            result = rpcInterface.handle(rpcRequest.getName(),rpcRequest.getDoing(),rpcRequest.getNum());
                        }
                    }

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
