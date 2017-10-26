package br.rv.ptji;

import br.rv.ptji.servicos.StartImpressora;
import java.io.*;

public class ServidorImpressora {

    public static void main(String[] args) throws IOException, InterruptedException {

        new ServidorImpressora().executa();

    }

    public ServidorImpressora() {

    }

    public void executa() throws IOException, InterruptedException {
        StartImpressora imp1 = new StartImpressora(4200);
        StartImpressora imp2 = new StartImpressora(4300);
        Thread t1 = new Thread(imp1);
        t1.start();
        Thread t2 = new Thread(imp2);
        t2.start();
        t1.join();
        t2.join();

    }

}