package com.fortitudetec.java8.ex03.randomports;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

@SuppressWarnings("Duplicates")
public class PortChecker {

    public boolean isAvailable(final int port) {
        try (ServerSocket serverSocket = new ServerSocket(port);
             DatagramSocket dataSocket = new DatagramSocket(port)) {
            serverSocket.setReuseAddress(true);
            dataSocket.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
