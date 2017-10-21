package br.rv.ptjs.utils;

import java.io.IOException;
import java.net.Socket;

public class SocketTeste {
    public static boolean available(int port) {
        try (Socket ignored = new Socket("localhost", port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }
}
