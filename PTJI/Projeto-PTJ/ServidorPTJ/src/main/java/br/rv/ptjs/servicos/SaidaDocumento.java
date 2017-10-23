package br.rv.ptjs.servicos;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.model.Documento;
import br.rv.ptjs.model.Impressora;
import br.rv.ptjs.utils.SocketTeste;

import java.io.IOException;
import java.time.LocalTime;


public class SaidaDocumento implements Runnable {
    public static Documento doc;
    public SaidaDocumento() {


    }

    static synchronized void LogSaida (Impressora imp , Documento doc){

            System.out.println("|Impressora " + imp.getName() + "| Data: " + LocalTime.now().toString() + "| Mensagem: " + doc.getMensagem() + " |");

    }
    static void trasmitir() throws IOException, InterruptedException {
        String nome = Thread.currentThread().getName();
        if (!Buffer.isImpressoras() && !Buffer.isDocumentos()) {
            Impressora imp;
            synchronized (Buffer.Impressoras) {
                imp = Buffer.Impressoras.remove(0);
            }
            if (SocketTeste.available(imp)) {
                Trasmitir tf = new Trasmitir(imp,Buffer.getDocumento());
                Thread enviar = new Thread(tf);
                enviar.start();
            }



        }


    }


    public void run() {


        while (true) {
            try {
                trasmitir();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
