package br.rv.ptjs.servicos;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.model.Documento;
import br.rv.ptjs.utils.Arquivos;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalTime;

/**
 *
 * @author rafael
 */

public class ChegadaDocumento implements Runnable {
    private Documento cliente;

    public  ChegadaDocumento(Socket novo, String mensagem, PrintStream toast) {
        this.cliente= new Documento(novo, mensagem, toast );
    }

    public synchronized void salva() throws IOException, InterruptedException {
        String nome = Thread.currentThread().getName();
        Buffer.addDocumento(cliente);
        cliente.toast.println("Mensagem adicionada a Fila.");
        String log = "|Cliente   " + nome + "| Data: " + LocalTime.now().toString() + "| Mensagem: " + cliente.getMensagem() + " |";
        Arquivos.CriarArquioX("logs", "./Logs", log);
        System.out.println(log);
    }

    public  void run() {
        try {
            salva();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
