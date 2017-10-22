package br.rv.ptji;


import br.rv.ptji.servicos.ProcessaMensagem;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorImpressora {
    private int porta;

    public static void main(String[] args) throws IOException, InterruptedException {

        new ServidorImpressora(2323).executa();

    }

    public ServidorImpressora(int porta) {
        this.porta = porta;
    }

    public void executa() throws IOException {

        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println(this.porta);

        while (true) {
            Socket cliente = servidor.accept();
            PrintStream s = new PrintStream(cliente.getOutputStream());
            s.println("OK");
//            cliente.notify();
        }

    }

}