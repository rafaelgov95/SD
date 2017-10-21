package br.rv.ptjs;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.servicos.AdicionarMensagemNoBuffer;
import br.rv.ptjs.servicos.EnviarMensagemParaImpressora;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorClienteBuffer {
    private static Buffer buffer;
    private int porta;
    public static void main(String[] args) throws IOException, InterruptedException {
        // inicia o servidor
        new ServidorClienteBuffer(2222).executa();



    }


    public ServidorClienteBuffer(int porta) {
        this.porta = porta;
        buffer = new Buffer();
    }

    public void executa() throws IOException {
        EnviarMensagemParaImpressora t1 = new EnviarMensagemParaImpressora(buffer);
        EnviarMensagemParaImpressora t2 = new EnviarMensagemParaImpressora(buffer);
        new Thread(t1,"IMPRE-1").start();
        new Thread(t2,"IMPRE-2").start();
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Porta 222 aberta!");

        while (true) {
            Socket cliente = servidor.accept();
            AdicionarMensagemNoBuffer tc = new AdicionarMensagemNoBuffer(buffer,cliente.getInputStream(),new PrintStream(cliente.getOutputStream()));
            new Thread(tc).start();
        }

    }
}
