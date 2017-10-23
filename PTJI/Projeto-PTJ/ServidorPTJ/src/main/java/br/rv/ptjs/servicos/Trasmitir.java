package br.rv.ptjs.servicos;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.model.Documento;
import br.rv.ptjs.model.Impressora;
import br.rv.ptjs.utils.SocketTeste;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

public class Trasmitir implements Runnable {
    private  Impressora imp;
    private  Documento doc;

    public Trasmitir(Impressora imp, Documento doc) {
        this.imp = imp;
        this.doc = new Documento(doc);
    }

    private static void enviar(Impressora imp ,Documento doc) throws IOException {
        Socket servidor = new Socket(imp.getIp(), imp.getPorta());
        Scanner s = new Scanner(servidor.getInputStream());
        String confirma = s.nextLine(); // parece qe a thread fica aqui :(
        if (confirma.equals("OK")) {
            doc.toast.println("Impressão OK ");
        }
        System.out.println("|Impressora " + imp.getName() + "| Data: " + LocalTime.now().toString() + "| Mensagem: " + doc.getMensagem() + " |");
        doc.socket.close();
        s.close();
        servidor.close();
    }


    @Override
    public void run() {
        try {
            enviar(imp,doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (Buffer.Impressoras) {
            Buffer.Impressoras.add(imp);
        }

    }
}
