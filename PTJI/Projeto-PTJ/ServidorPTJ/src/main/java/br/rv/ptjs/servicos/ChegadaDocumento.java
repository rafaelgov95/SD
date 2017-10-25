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
    private Buffer b;

    public ChegadaDocumento(Buffer b, Socket novo, String mensagem, PrintStream toast) {
        this.documento = new Documento(novo, mensagem, toast);
        this.b = b;
    }


    public synchronized void salva() throws IOException, InterruptedException {

        if (b.getM() > b.getDocumentosSize()) {
            b.addDocumento(documento);
            documento.getToast().println("Adicionada á Fila.");
            String log = "|Cliente       " + documento.getSocket().getPort() + "     | Data: " + LocalTime.now().toString() + "| ";
            Arquivos.CriarArquioX("logs", "./Logs", log);
            System.out.println(log);
            b.EscalonadorAcordar();
        } else {
            System.out.println("Buffer Lotado Esperando para reenviar!");
            Thread.sleep(200);
            salva();
        }
    }

    public synchronized void salvaP() throws IOException, InterruptedException {

        if (b.getM() > b.getDocumentosSize()) {
            b.addLevelPDocumento(documento);
            documento.getToast().println("Adicionada á Fila com prioridade");
            String log = "|Cliente       " + documento.getSocket().getPort() + "     | Data: " + LocalTime.now().toString() + "| P";
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
                LocalTime t = LocalTime.now();
                String[] priori = documento.getMensagem().split("#");
                documento.setTempo(LocalTime.now());
                if (priori.length> 2 && priori[1].equals("P")) {
                    salvaP();
                } else {
                    salva();
                }

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
