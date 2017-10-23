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
    private int cont;
    public StartImpressora(int porta) {
        this.cont=0;
        this.porta = porta;
    }


    public static synchronized String Port(Socket cliente) throws IOException {
        Scanner leia = new Scanner(cliente.getInputStream());
        if(leia.hasNextLine()) {
            return leia.nextLine();
        }
        return "Erro";
    }

    public void FakeImpressoras() throws InterruptedException, IOException {
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println(this.porta);
        Random r = new Random();
        while (true) {

            Thread.sleep(r.nextInt(2000));
            Socket cliente = servidor.accept();
            PrintStream ps = new PrintStream(cliente.getOutputStream());
            String localPort = Port(cliente);
            Thread.sleep(r.nextInt(100));
            int proba = 6;
            String status ="  OK" ;
            if (proba > r.nextInt(10)) {
                status="  ERRO";
                ps.println("ERRO");
            } else {
                status="  OK";
                ps.println("OK");
            }
            if(!localPort.equals("Estralho")) {
                String log = "Impressão: " +(cont++)+" Cliente " + localPort + " Data: " + LocalTime.now().toString()+status;
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
