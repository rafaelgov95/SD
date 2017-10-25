package br.rv.ptjs.servicos;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.model.Documento;
import br.rv.ptjs.model.Impressora;
import br.rv.ptjs.utils.Arquivos;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;
import java.util.Scanner;

/**
 * @author rafael
 */

public class Trasmitir implements Runnable {
    private Impressora imp;
    private Documento doc;
    private Buffer b;
    public Trasmitir(Impressora imp, Buffer b) throws InterruptedException {
        this.imp = imp;
        this.b = b;
        this.doc = this.b.getDocumento();

    }

    private void enviar(Impressora imp, Documento doc) throws IOException {
        Socket servidor = new Socket(imp.getIp(), imp.getPorta());
        Scanner s = new Scanner(servidor.getInputStream());
        PrintStream ps = new PrintStream(servidor.getOutputStream());
        ps.println(doc.getSocket().getPort());
        LocalTime t = LocalTime.now();
        Duration duracao = Duration.between(doc.getTempo(), t);
        BigDecimal minutosEmBigDecimal = new BigDecimal(duracao.toMillis());

        String confirma = s.nextLine();
        if (confirma.equals("OK")) {
            doc.getToast().println("Impressão Concluida pela Impressora " + imp.getName() + "em "+minutosEmBigDecimal+" OK  " );
            String log = "A Impressora " + imp.getName() + " concluiu, a impressão do  Cliente " + doc.getSocket().getPort() + "| Data: " + t.toString() + "| OK | Duração em Milissegundos: " + minutosEmBigDecimal;
            Arquivos.CriarArquioX("logs", "./Logs", log);
            System.out.println(log);
            doc.getToast().close();
            s.close();
            servidor.close();
        } else {
            doc.getToast().println("Ocorreu um erro, ao encaminha para impressora " + imp.getName() + ", uma nova tentativa será realizada !!!! ");
            String log = "|Impressora " + imp.getName() + "| Cliente " + doc.getSocket().getPort() + "| Data: " + LocalTime.now().toString() + "| ERRO AO ENCAMINHAR";
            Arquivos.CriarArquioX("logs", "./Logs", log);
            System.out.println(log);
            s.close();
            servidor.close();
            enviar(imp, doc);
        }
    }

    public void probabilidadeDeEnvio() throws IOException, InterruptedException {
        Random r = new Random();
        if (r.nextInt(100) >= b.getR()) {
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
            b.addImpressora(imp);
        }
    }
}
