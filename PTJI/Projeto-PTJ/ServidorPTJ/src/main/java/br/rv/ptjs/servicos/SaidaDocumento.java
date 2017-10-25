package br.rv.ptjs.servicos;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.model.Impressora;
import br.rv.ptjs.utils.Arquivos;
import br.rv.ptjs.utils.SocketTeste;

import java.io.IOException;
import java.util.Random;


/**
 * @author rafael
 */

public class SaidaDocumento implements Runnable {
    private Buffer b;
    private Random r;

    public SaidaDocumento(Buffer b) {
        this.b = b;
        this.r = new Random();
    }

    void trasmitir() throws IOException, InterruptedException {
        Impressora imp;
        imp = b.getImpressora();
        if (r.nextInt(100) < 10) {
        } else {
            if (SocketTeste.available(imp)) {
                Trasmitir tf = new Trasmitir(imp, b);
                Thread enviar = new Thread(tf);
                enviar.start();

            } else {
                String log = "Impressora " + imp.getName() + " indisponível no momento";
                Arquivos.CriarArquioX("logs", "./Logs", log);
                System.out.println(log);
                trasmitir();

            }
        }

    }


    public void run() {
        while (true) {
            try {
                b.EscalonadorDormi();
                trasmitir();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
