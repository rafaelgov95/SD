package br.rv.ptjs.servicos;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.model.Documento;
import br.rv.ptjs.model.Impressora;
import br.rv.ptjs.utils.Arquivos;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * @author rafael
 */

public class Trasmitir implements Runnable {
    private Impressora imp;
    private Documento doc;

    public Trasmitir(Impressora imp, Documento doc) {
        this.imp = imp;
        this.doc = new Documento(doc);
    }

    private static void enviar(Impressora imp, Documento doc) throws IOException {
        Socket servidor = new Socket(imp.getIp(), imp.getPorta());
        Scanner s = new Scanner(servidor.getInputStream());
        String confirma = s.nextLine();
        if (confirma.equals("OK")) {
            doc.toast.println("Impressão OK ");
        }
        String log = "|Impressora " + imp.getName() + "| Data: " + LocalTime.now().toString() + "| Mensagem: " + doc.getMensagem() + " |";
        Arquivos.CriarArquioX("logs", "./Logs", log);
        System.out.println(log);
        doc.socket.close();
        s.close();
        servidor.close();
    }


    @Override
    public void run() {
        try {
            enviar(imp, doc);
            synchronized (this) {
                Buffer.addImpressora(imp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
