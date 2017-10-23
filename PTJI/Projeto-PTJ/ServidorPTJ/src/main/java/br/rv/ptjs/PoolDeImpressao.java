package br.rv.ptjs;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.model.Impressora;
import br.rv.ptjs.servicos.ChegadaDocumento;
import br.rv.ptjs.servicos.SaidaDocumento;

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
        new PoolDeImpressao(2222).executa();
    }


    public PoolDeImpressao(int porta) {
        this.porta = porta;
    }

    public void executa() throws IOException {
        Buffer.addImpressora(new Impressora("127.0.0.1",2323,"Impressora-0"));
        Buffer.addImpressora(new Impressora("127.0.0.1",2324,"Impressora-1"));
        SaidaDocumento escalonador = new SaidaDocumento();
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
