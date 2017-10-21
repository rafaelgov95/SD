package socket;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Random;

public class Impressora implements Runnable {
    private int id_processo;
    private LocalTime data;
    private Collection<String> mensagens;

    public Impressora(int id_processo ) {
        this.id_processo = id_processo;
        this.data = LocalTime.now();
    }

    Random gerador = new Random();
    public synchronized void run() {
                System.out.println(id_processo+" : "+gerador.nextInt());

                System.out.println(id_processo+" : "+gerador.nextInt());

    }
}