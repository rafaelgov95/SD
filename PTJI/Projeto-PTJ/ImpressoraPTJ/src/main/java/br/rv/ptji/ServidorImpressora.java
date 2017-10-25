package br.rv.ptji;

import br.rv.ptji.servicos.StartImpressora;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorImpressora {
    private int porta;

    public static void main(String[] args) throws IOException, InterruptedException {

        new ServidorImpressora(2324).executa();

    }

    public ServidorImpressora(int porta) {
        this.porta = porta;
    }

    public void executa() throws IOException, InterruptedException {
        StartImpressora imp1 = new StartImpressora(3000);
        StartImpressora imp2 = new StartImpressora(3001);
        Thread t1 = new Thread(imp1);
        t1.start();
        Thread t2 = new Thread(imp2);
        t2.start();
        t1.join();
        t2.join();

    }

}