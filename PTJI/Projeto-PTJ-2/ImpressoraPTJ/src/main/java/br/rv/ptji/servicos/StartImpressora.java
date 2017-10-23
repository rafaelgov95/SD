package br.rv.ptji.servicos;

import br.rv.ptji.utils.Arquivos;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class StartImpressora implements Runnable {
    private Socket cliente;

    public StartImpressora(Socket cliente) {
        this.cliente = cliente;
    }


    public synchronized void GetDocumento(Socket cliente) throws IOException, InterruptedException {
        PrintStream ps = new PrintStream(cliente.getOutputStream());
        Scanner leia = new Scanner(cliente.getInputStream());
        String temp = "";
        while (true) {

            ps.println("OK");
            if (leia.hasNextLine()) {
                temp = leia.nextLine();
            }
            System.out.println(temp);
            if (temp.equals("OK")) {
                Thread.sleep(100);
                ps.println("IMPRESSO COM SUCESSO");
            }

            Thread.sleep(5000);
        }

    }

//    public String Get() throws IOException {
//        Scanner leia = new Scanner(cliente.getInputStream());
//        while (leia.hasNextLine()) {
//            return leia.nextLine();
//        }
//        return "Estralho";
//    }

    public void run() {
        try {
            GetDocumento(cliente);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
