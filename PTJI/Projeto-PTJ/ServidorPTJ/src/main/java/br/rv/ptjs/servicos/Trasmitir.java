package br.rv.ptjs.servicos;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.model.Documento;
import br.rv.ptjs.model.Impressora;
import br.rv.ptjs.utils.Arquivos;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Random;
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
        PrintStream ps = new PrintStream(servidor.getOutputStream());
        ps.println(doc.socket.getPort());
        String confirma = s.nextLine();
        if (confirma.equals("OK")) {

            doc.toast.println("Impressão Concluida pela Impressora " +imp.getName() +" OK  ");
            String log = "|Impressora " + imp.getName() + "| Cliente "+doc.socket.getPort()+"| Data: " + LocalTime.now().toString() + "| OK";
            Arquivos.CriarArquioX("logs", "./Logs", log);
            System.out.println(log);
            doc.socket.close();
            s.close();
            servidor.close();
        }else{
            doc.toast.println("Ocorreu um erro de impressão, na impressora "+imp.getName()+" o documento será reenviado!!!! ");
            String log = "|Impressora " + imp.getName() + "| Cliente "+doc.socket.getPort()+"| Data: " + LocalTime.now().toString() + "| ERRO";
            Arquivos.CriarArquioX("logs", "./Logs", log);
            System.out.println(log);
            s.close();
            servidor.close();
            enviar(imp,doc);
        }
    }

    public void probabilidadeDeEnvio() throws IOException, InterruptedException {
        Random r = new Random();
        int proba = Buffer.getR()-1;
        if (proba > r.nextInt(Buffer.getR())) {
            enviar(imp, doc);
        } else {
            Thread.sleep(100);
            probabilidadeDeEnvio();
        }
    }

    @Override
    public void run() {
        try {
            probabilidadeDeEnvio();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            Buffer.addImpressora(imp);
        }
    }
}
