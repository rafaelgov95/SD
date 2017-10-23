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
    private int porta;

    public StartImpressora(int porta) {
        this.porta = porta;
    }


    public static synchronized String Port(Socket cliente) throws IOException {
        Scanner leia = new Scanner(cliente.getInputStream());
        if(leia.hasNextLine()) {
            return leia.nextLine();
        }
        return "Estralho";
    }

    public void FakeImpressoras() throws InterruptedException, IOException {
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println(this.porta);
        int i=0;
        while (true) {
            Socket cliente = servidor.accept();
            PrintStream ps = new PrintStream(cliente.getOutputStream());
            String localPort = Port(cliente);
            Random r = new Random();
            Thread.sleep(1000);
            int proba = r.nextInt(10);
            String status ;
            if (proba < 6) {
                status="  ERRO";
                ps.println("ERRO");
            } else {
                status="  OK";
                ps.println("OK");
            }
            if(!localPort.equals("Estralho")) {
                String log = "Impressão: " +(i++)+" Cliente " + localPort + " Data: " + LocalTime.now().toString()+status;
                Arquivos.CriarArquioX("logs_" + porta, "./Logs", log);
            }


            ps.close();
            cliente.close();
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
