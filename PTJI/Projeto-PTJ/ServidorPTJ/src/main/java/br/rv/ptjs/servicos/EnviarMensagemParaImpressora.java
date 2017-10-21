package br.rv.ptjs.servicos;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.utils.SocketTeste;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Vector;

public class EnviarMensagemParaImpressora implements Runnable {
    private Buffer buffer;
    private Collection<String> ports;

    public EnviarMensagemParaImpressora(Buffer buffer) {
        this.buffer = buffer;
        this.ports = new Vector<String>();
        this.ports.add("2324");
        this.ports.add("2325");
        this.ports.add("2326");
    }


    public void run() {
        while (true) {
            try {
                int porta=0;
                for (String a:ports) {
                    if(SocketTeste.available(Integer.parseInt(a))){
                        porta = Integer.parseInt(a);
                        return;
                    }
                }
                ServerSocket servidor = new ServerSocket(porta);
                Buffer.getMensagem();
                Socket cliente = servidor.accept();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
