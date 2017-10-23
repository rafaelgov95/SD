package br.rv.ptjs.servicos;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.model.Impressora;
import br.rv.ptjs.utils.SocketTeste;

import java.io.IOException;


/**
 *
 * @author rafael
 */

public class SaidaDocumento implements Runnable {
    public SaidaDocumento() {

    }
    static void trasmitir() throws IOException, InterruptedException {
        String nome = Thread.currentThread().getName();
        if (!Buffer.isImpressoras() && !Buffer.isDocumentos()) {
            Impressora imp;
                imp = Buffer.getImpressora();
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
