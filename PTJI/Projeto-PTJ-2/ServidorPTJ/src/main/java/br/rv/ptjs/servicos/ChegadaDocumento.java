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
 *
 * @author rafael
 */

public class ChegadaDocumento implements Runnable {
    private Documento documento;

    public  ChegadaDocumento(Socket novo, String mensagem, PrintStream toast) {
        this.documento = new Documento(novo, mensagem, toast );
    }


    public synchronized void salva() throws IOException, InterruptedException {
        Buffer.addDocumento(documento);
        documento.toast.println("Mensagem adicionada a Fila.");
        String log = "|Cliente       " + documento.socket.getPort() +"     | Data: " + LocalTime.now().toString() +"| ";
        Arquivos.CriarArquioX("logs", "./Logs", log);
        System.out.println(log);
    }

    public synchronized void salvaP() throws IOException, InterruptedException {
        Buffer.addLevelPDocumento(documento);
        documento.toast.println("Mensagem adicionada a Fila com prioridade");
        String log = "|Cliente       " + documento.socket.getPort() +"     | Data: " + LocalTime.now().toString() +"| P";
        Arquivos.CriarArquioX("logs", "./Logs", log);
        System.out.println(log);
    }
    public  void run() {
        try {
            synchronized (this){
               String priori = documento.getMensagem().split("#")[0];
                Random r = new Random();
                if(!priori.equals("PD")){
                    while(true) {
                        if (r.nextInt(10)<6) {
                            salva();
                            return;
                        }else{
                            Thread.sleep(1000);
                        }
                    }
                }else{
                    while(true) {
                        if (r.nextInt(10)<7) {
                            salvaP();
                            return;
                        }else{
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
