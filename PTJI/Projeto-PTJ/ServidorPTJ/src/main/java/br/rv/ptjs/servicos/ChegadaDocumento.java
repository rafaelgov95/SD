package br.rv.ptjs.servicos;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.model.Documento;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ChegadaDocumento implements Runnable {
    private Documento cliente;

    public ChegadaDocumento(Socket novo, String mensagem, PrintStream toast) {
        this.cliente= new Documento(novo, mensagem, toast );
    }

    public void run() {
        try {
            Buffer.addDocumento(cliente);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
