package br.rv.ptji;

import br.rv.ptji.servicos.StartImpressora;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorImpressora {
    private int porta;

    public static void main(String[] args) throws IOException, InterruptedException {

        new ServidorImpressora(2324).executa();

    }

    public ServidorImpressora(int porta) {
        this.porta = porta;
    }

    public void executa() throws IOException, InterruptedException {
        StartImpressora imp1 = new StartImpressora(2323);
        StartImpressora imp2 = new StartImpressora(2324);
        Thread t1 = new Thread(imp1);
        t1.start();
        Thread t2 = new Thread(imp2);
        t2.start();
        t1.join();
        t2.join();
//        ServerSocket servidor = new ServerSocket(this.porta);
//        System.out.println(this.porta);

//        while (true) {
//            Socket cliente = servidor.accept();
//            PrintStream s = new PrintStream(cliente.getOutputStream());
//            Thread.sleep(10000);
//            s.println("OK");
//        }

    }

}