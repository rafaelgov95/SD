package br.rv.ptji;

import br.rv.ptji.servicos.StartImpressora;
import br.rv.ptji.utils.Recebedor;

import java.io.*;
import java.net.*;
import java.util.Random;

public class ClienteImpressora {
    public static void main(String[] args)
            throws UnknownHostException, IOException, InterruptedException {
        new ClienteImpressora("127.0.0.1", 2223).executa();
    }

    private String host;
    private int porta;

    public ClienteImpressora(String host, int porta) {
        this.host = host;
        this.porta = porta;
    }

    public void executa() throws UnknownHostException, IOException, InterruptedException {

            Thread.sleep(4000);
            Socket cliente = new Socket(this.host, this.porta);
            System.out.println("O cliente se conectou ao servidor!");
            StartImpressora start = new StartImpressora(cliente);
            Thread t1 = new Thread(start);
            t1.start();
             t1.join();
            System.out.println("Thread Finalizada");


    }
}
