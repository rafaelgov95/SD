package br.rv.ptjs.servicos.impressao;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EscalonadorServidorImpressao implements Runnable {
    public static int porta;

    public EscalonadorServidorImpressao(int porta) {
        this.porta = porta;
    }

    public static void iniciar() throws IOException {
        ServerSocket servidor = new ServerSocket(porta);
        System.out.println("Escalonador: "+porta);
        int i = 0;
        while (true) {
            Socket cliente = servidor.accept();
            SaidaDocumento saida = new SaidaDocumento(cliente);
            new Thread(saida).start();
        }
    }
    @Override
    public void run() {
        try {
            iniciar();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
