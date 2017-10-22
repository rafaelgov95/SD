package br.rv.ptjc;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args)
            throws UnknownHostException, IOException {
        new Cliente("127.0.0.1", 2222).executa();
    }

    private String host;
    private int porta;

    public Cliente(String host, int porta) {
        this.host = host;
        this.porta = porta;
    }

    public void executa() throws UnknownHostException, IOException {
        System.out.println("Vem vindo ao impressora magica!");
        BufferedReader rf = new BufferedReader(new InputStreamReader(System.in));
        String mensagem = rf.readLine();
        Socket cliente = new Socket(this.host, this.porta);
        System.out.println("O cliente se conectou ao servidor!");

        Recebedor r = new Recebedor(cliente.getInputStream());
        new Thread(r).start();

        PrintStream saida = new PrintStream(cliente.getOutputStream());
        saida.println(mensagem);
        System.out.println("Mensagem enviada para impressao.....");
        saida.close();
        cliente.close();
    }
}

