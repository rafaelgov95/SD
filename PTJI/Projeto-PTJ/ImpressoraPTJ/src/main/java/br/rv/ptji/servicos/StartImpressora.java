package br.rv.ptji.servicos;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class StartImpressora implements Runnable {

    int porta;
    public StartImpressora(int porta){
        this.porta=porta;
    }

    public void run() {
        ServerSocket servidor = null;
        try {
            servidor = new ServerSocket(this.porta);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(this.porta);
        while (true) {
            Socket cliente = null;
            try {
                cliente = servidor.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintStream s = null;
            try {
                s = new PrintStream(cliente.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            s.println("OK");
        }
    }
}
