package br.rv.ptjs.utils;

import br.rv.ptjs.model.Impressora;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author rafael
 */

public class SocketTeste {
    public synchronized static boolean available(Impressora imp) {
        boolean portTaken = false;
        Socket socket = null;
        try {
            if(imp==null){
                System.out.println("impressora null");
            }
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
