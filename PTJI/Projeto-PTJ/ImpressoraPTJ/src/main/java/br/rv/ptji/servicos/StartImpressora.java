package br.rv.ptji.servicos;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class StartImpressora implements Runnable {


    public void run() {
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println(this.porta);
        while (true) {
            Socket cliente = servidor.accept();
            PrintStream s = new PrintStream(cliente.getOutputStream());
            Thread.sleep(10000);
            s.println("OK");
        }
    }
}
