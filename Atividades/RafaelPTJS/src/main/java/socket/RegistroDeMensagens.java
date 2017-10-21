package socket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

public class RegistroDeMensagens {

    public static void main(String[] args) throws InterruptedException {
        Collection<String> mensagens = new Vector<String>();

        Thread t1 = new Thread(new Impressora(0));
        Thread t2 = new Thread(new Impressora(10000));
        Thread t3 = new Thread(new Impressora(20000));

        t1.start();
        t2.start();
        t3.start();

        // faz com que a thread que roda o main aguarde o fim dessas
        t1.join();
        t2.join();
        t3.join();

        System.out.println("Threads produtoras de mensagens finalizadas!");

        System.out.println("Fim da execucao com sucesso");
    }
}