package com.lmhy.play.rpc;

import jdk.net.Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public SocketServer() {
        try {
            serverSocket = new ServerSocket(9991);

            while (true) {
                socket = serverSocket.accept();
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = in.readLine();

                System.out.println("line:"+line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
