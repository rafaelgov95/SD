package br.rv.ptji.servicos;

import br.rv.ptji.utils.Arquivos;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Random;
import java.util.Scanner;

public class StartImpressora implements Runnable {
    private int porta;
    private int cont;

    public StartImpressora(int porta) {
        this.cont = 0;
        this.porta = porta;
    }


    public static synchronized String Port(Socket cliente) throws IOException {
        Scanner leia = new Scanner(cliente.getInputStream());
        if (leia.hasNextLine()) {
            return leia.nextLine();
        }
        return "Erro";
    }

    public void FakeImpressoras() throws InterruptedException, IOException {


        ServerSocket servidor = new ServerSocket(this.porta);
        Random r = new Random();
        System.out.println("Entro: " + this.porta);
        while (true) {
            if (r.nextInt(100) < 9) {
                String log = "Impressora " + this.porta + " está Off-line | Data: "+LocalTime.now();
                Arquivos.CriarArquioX("logs_" + porta, "./Logs", log);
                Thread.sleep(r.nextInt(2000) + 1000);
                log = "Impressora " + this.porta + " está On-line | Data: "+LocalTime.now();
                Arquivos.CriarArquioX("logs_" + porta, "./Logs", log);
                System.out.println("Volto: " + this.porta);
                if(servidor.isClosed()){
                    servidor.close();
                    servidor = new ServerSocket(this.porta);
                }
            }
            Socket cliente = servidor.accept();
            PrintStream ps = new PrintStream(cliente.getOutputStream());
            String localPort = Port(cliente);
            String status;
            if (r.nextInt(100) < 15) {
                status = "  ERRO";
                ps.println("ERRO");
            } else {
                status = "  OK";
                ps.println("OK");
            }
            if (!localPort.equals("Estralho")) {
                String log = "Impressão: " + (cont++) + " POOL TCP:" + cliente.getPort() + " Data: " + LocalTime.now().toString() + status;
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
