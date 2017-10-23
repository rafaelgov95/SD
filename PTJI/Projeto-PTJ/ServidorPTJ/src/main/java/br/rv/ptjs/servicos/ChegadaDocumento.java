package br.rv.ptjs.servicos;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.model.Documento;
import br.rv.ptjs.utils.Arquivos;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Random;
import java.util.Scanner;

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
        Buffer.addDocumento(cliente);
        cliente.toast.println("Mensagem adicionada a Fila.");
        String log = "|Cliente       " + cliente.socket.getPort() +"     | Data: " + LocalTime.now().toString() +"| ";
        Arquivos.CriarArquioX("logs", "./Logs", log);
        System.out.println(log);
    }

    public  void run() {
        try {
            Random r = new Random();
            while(true) {
                if (r.nextInt(10)<6) {
                    salva();
                    return;
                }else{
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
