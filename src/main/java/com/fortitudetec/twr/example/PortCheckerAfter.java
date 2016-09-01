package com.fortitudetec.twr.example;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Port checker class that uses try-with-resources (TWR).
 * <p>
 * Accompanies blog:
 * <a href="http://www.sleberknight.com/blog/sleberkn/entry/reduce_java_boilerplate_using_try">Reduce Java boilerplate using try-with-resources</a>
 */
public class PortCheckerAfter {

    private boolean silent;

    public PortCheckerAfter(boolean silent) {
        this.silent = silent;
    }

    public boolean isPortAvailable(final int port) {
        try (ServerSocket serverSocket = new ServerSocket(port);
             DatagramSocket dataSocket = new DatagramSocket(port)) {
            serverSocket.setReuseAddress(true);
            dataSocket.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            System.err.printf("Error occurred checking port %d availability: %s, %s%n",
                    port, e.getClass(), e.getMessage());
            return false;
        }
    }

    public boolean isPortAvailableSilent(final int port) {
        try (ServerSocket serverSocket = new ServerSocket(port); DatagramSocket dataSocket = new DatagramSocket(port)) {
            serverSocket.setReuseAddress(true);
            dataSocket.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void checkPortAndReport(int port) {
        Function<Integer, Boolean> portCheckFunction = portCheckFunction(silent);
        boolean available = portCheckFunction.apply(port);
        System.out.printf("Port %d available? %b%n", port, available);
    }

    private Function<Integer, Boolean> portCheckFunction(boolean silent) {
        if (silent) {
            return this::isPortAvailableSilent;
        }
        return this::isPortAvailable;
    }

    public static void main(String[] args) {
        PortCheckerAfter portChecker = new PortCheckerAfter(true);
        IntStream.rangeClosed(1000, 1125).forEach(portChecker::checkPortAndReport);
    }

}
