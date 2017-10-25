package br.rv.ptjs;

import br.rv.ptjs.buffer.Buffer;
import br.rv.ptjs.model.Impressora;
import br.rv.ptjs.servicos.ChegadaDocumento;
import br.rv.ptjs.servicos.SaidaDocumento;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author rafael
 */

public class PoolDeImpressao {
    private int porta;
    public static void main(String[] args) throws IOException, InterruptedException {
        new PoolDeImpressao(3000).executa();
    }


    public PoolDeImpressao(int porta) {
        this.porta = porta;
    }

    public void executa() throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("#### BEM VINDO AO POOL DE IMPRESSÂO RAFAEL VIANA ####\n" +
                "Exemplos de valores de entrada\n"+
                "R = (0 a 100), 0 representando 0% e 100 represetando 100% de probabiliadde de erro de impressão.\n" +
                "M = (1 a N>1) Valor inteiro  do Tamanho Buffer do Servidor.\n" +
                "T = (1 a 3) Valor inteiro\n" +
                "Informe em sequência R M T , separados por espaço entre os valores!");

        String[] linha = rd.readLine().split(" ");
        Buffer b = new Buffer(Integer.parseInt(linha[0]),Integer.parseInt(linha[1]),Integer.parseInt(linha[2]));
        System.out.println("Servidor Rodando na Porta "+ porta+ "!");
        b.addImpressora(new Impressora("sdufms2017.ddns.net",4200,"4200"));
        b.addImpressora(new Impressora("sdufms2017.ddns.net",4300,"4300"));
        SaidaDocumento escalonador = new SaidaDocumento(b);
        Thread esc = new Thread(escalonador);
        esc.start();
        ServerSocket servidor = new ServerSocket(this.porta);
        while (true) {
            Socket cliente = servidor.accept();
            ChegadaDocumento tc = new ChegadaDocumento(b, cliente, new Scanner(cliente.getInputStream()).nextLine(),
                    new PrintStream(cliente.getOutputStream()));
            new Thread(tc).start();
        }

    }
}


