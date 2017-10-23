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
        StartImpressora imp1 = new StartImpressora();
        StartImpressora imp2 = new StartImpressora();
        Thread t1 = new Thread(imp1);
        t1.start();
        Thread t2 = new Thread(imp1);
        t2.start();

    }

}