package br.rv.ptjs.utils;

import br.rv.ptjs.model.Impressora;

import java.io.IOException;
import java.net.Socket;

public class SocketTeste {
    public static boolean available(Impressora imp) {
        boolean portTaken = false;
        Socket socket = null;
        try {
            socket = new Socket(imp.getIp(), imp.getPorta());
        } catch (IOException e) {
            return false;
        } finally {
            if (socket != null)
                try {
                    socket.close();
                    return true;
                } catch (IOException e) {
                    return false;
                }
        }
        return false;
    }

}
