package br.rv.ptjs.servicos;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.utils.SocketTeste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Scanner;
import java.util.Vector;

public class EnviarMensagemParaImpressora implements Runnable {
    private Buffer buffer;
    private int Myport;

    public EnviarMensagemParaImpressora(Buffer buffer) {
        this.buffer = buffer;

    }

    static synchronized void trasmitir() throws IOException, InterruptedException {


        if (!Buffer.ports.isEmpty() && !Buffer.buffer.isEmpty()) {
            int porta = Integer.parseInt(Buffer.ports.toArray()[0].toString());
            String confirma = "";
            Socket servidor = new Socket("127.0.0.1", porta);
            String mensagem = Buffer.getMensagem();
            Buffer.ports.remove(Buffer.ports.toArray()[0]);
            Scanner s = new Scanner(servidor.getInputStream());
            confirma += s.nextLine();
            if (!confirma.equals("OK")) {
                confirma += "   Error";
            }

            System.out.println(mensagem + " " + confirma);
            Buffer.ports.add(String.valueOf(porta));
            s.close();
        }


    }


    public void run() {
        while (true) {
            synchronized (this) {
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
}
