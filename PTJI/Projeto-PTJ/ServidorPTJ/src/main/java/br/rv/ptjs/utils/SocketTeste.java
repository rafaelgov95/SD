package br.rv.ptjs.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTeste {
    public static boolean available(int port) {
        boolean portTaken = false;
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            portTaken = true;
        } finally {
            if (socket != null)
                try {
                    socket.close();
                } catch (IOException e) { /* e.printStackTrace(); */ }
        }
        return false;
    }

}
