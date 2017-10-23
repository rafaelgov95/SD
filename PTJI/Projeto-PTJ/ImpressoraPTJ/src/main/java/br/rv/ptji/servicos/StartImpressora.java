package br.rv.ptji.servicos;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class StartImpressora implements Runnable {
    private int porta;
    public StartImpressora(int porta){
        this.porta=porta;
    }

    public void FakeImpressoras() throws InterruptedException, IOException {
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println(this.porta);
        while (true) {
            Socket cliente = servidor.accept();
            PrintStream s = new PrintStream(cliente.getOutputStream());
            Thread.sleep(10000);
            s.println("OK");
        }
    }

    public void run() {
        try {
            FakeImpressoras();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
