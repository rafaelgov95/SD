package br.rv.ptjs.servicos;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.model.Documento;
import br.rv.ptjs.utils.Arquivos;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Random;

/**
 * @author rafael
 */

public class ChegadaDocumento implements Runnable {
    private Documento documento;

    public ChegadaDocumento(Socket novo, String mensagem, PrintStream toast) {
        this.documento = new Documento(novo, mensagem, toast);
    }


    public synchronized void salva() throws IOException, InterruptedException {
        if (Buffer.getM() > Buffer.getDocumentosSize()) {
            Buffer.addDocumento(documento);
            documento.toast.println("Mensagem adicionada a Fila.");
            String log = "|Cliente       " + documento.socket.getPort() + "     | Data: " + LocalTime.now().toString() + "| ";
            Arquivos.CriarArquioX("logs", "./Logs", log);
            System.out.println(log);
        } else {
            System.out.println("Buffer Lotado Esperando para reenviar!");
            Thread.sleep(200);
            salva();
        }
    }

    public synchronized void salvaP() throws IOException, InterruptedException {
        if (Buffer.getM() < Buffer.getDocumentosSize()) {
            Buffer.addLevelPDocumento(documento);
            documento.toast.println("Mensagem adicionada a Fila com prioridade");
            String log = "|Cliente       " + documento.socket.getPort() + "     | Data: " + LocalTime.now().toString() + "| P";
            Arquivos.CriarArquioX("logs", "./Logs", log);
            System.out.println(log);
        } else {
            System.out.println("Buffer Lotado Esperando para reenviar!");
            Thread.sleep(200);
            salvaP();

        }

    }

    public void run() {
        try {
            synchronized (this) {
                String priori = documento.getMensagem().split("#")[0];
                Random r = new Random();
                if (!priori.equals("PD")) {
                    while (true) {
                        if (r.nextInt(10) < Buffer.getR()) {
                            salva();
                            return;
                        } else {
                            Thread.sleep(1000);
                        }
                    }
                } else {
                    while (true) {
                        if (r.nextInt(10) < 7) {
                            salvaP();
                            return;
                        } else {
                            Thread.sleep(1000);
                        }
                    }
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
