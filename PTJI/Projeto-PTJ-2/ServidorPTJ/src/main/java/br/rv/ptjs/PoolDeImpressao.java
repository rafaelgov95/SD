package br.rv.ptjs;

import br.rv.ptjs.servicos.ChegadaDocumento;
import br.rv.ptjs.servicos.impressao.EscalonadorServidorImpressao;
import br.rv.ptjs.servicos.impressao.SaidaDocumento;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author rafael
 */

public class PoolDeImpressao {
    private int porta;

    public static void main(String[] args) throws IOException, InterruptedException {
        new PoolDeImpressao(3000).executa();
    }


    public PoolDeImpressao(int porta) {
        this.porta = porta;
    }

    public void executa() throws IOException {
        EscalonadorServidorImpressao escalonador = new EscalonadorServidorImpressao(2223);
        new Thread(escalonador).start();
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Porta "+porta+" aberta!");
        while (true) {
            Socket cliente = servidor.accept();
            ChegadaDocumento tc = new ChegadaDocumento(cliente, new Scanner(cliente.getInputStream()).nextLine(),
                    new PrintStream(cliente.getOutputStream()));
            new Thread(tc).start();
        }

    }
}
