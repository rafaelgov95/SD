package br.rv.ptjc;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Cliente {
    public static void main(String[] args)
            throws UnknownHostException, IOException, InterruptedException {
        new Cliente("127.0.0.1", 2222).executa();
    }

    private String host;
    private int porta;

    public Cliente(String host, int porta) {
        this.host = host;
        this.porta = porta;
    }

    public void executa() throws UnknownHostException, IOException, InterruptedException {
        System.out.println("Bem vindo digite algo para ser impresso!");
        BufferedReader rf = new BufferedReader(new InputStreamReader(System.in));
        String mensagem = rf.readLine();
        Socket cliente = new Socket(this.host, this.porta);
        System.out.println("O cliente de numero "+ cliente.getLocalPort() + " se conectou ao servidor "+ cliente.getPort()+"!");

        Recebedor r = new Recebedor(cliente.getInputStream());
        Thread t1 = new Thread(r);
        t1.start();
        PrintStream saida = new PrintStream(cliente.getOutputStream());

        saida.println(mensagem);

        t1.join();
        saida.close();
        cliente.close();
    }
}

