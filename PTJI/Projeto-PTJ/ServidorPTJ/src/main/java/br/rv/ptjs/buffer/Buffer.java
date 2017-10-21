package br.rv.ptjs.buffer;

import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.*;

public class Buffer {


    private static Collection<String> buffer = new Vector<String>();

    public Buffer() {

    }

    public static void addMensagem(InputStream is, PrintStream ps) {
        synchronized (buffer) {
            String nome = Thread.currentThread().getName();
            Scanner s = new Scanner(is);
            String mensagem = s.nextLine();
            System.out.println("|Cliente   " + nome + "| Data: " + LocalTime.now().toString() + "| Mensagem: " +mensagem+ " |");
            buffer.add(mensagem);
            s.close();
        }

    }

    public static void getMensagem() throws InterruptedException {
        String nome = Thread.currentThread().getName();
        synchronized (buffer) {
            if (!buffer.isEmpty()) {
                Thread.sleep(10000);
                System.out.println("|Impressora " + nome + "| Data: " + LocalTime.now().toString() + "| Mensagem: " + buffer.toArray()[0] + " |");
                buffer.remove(buffer.toArray()[0]);
            } else {
            }
        }

    }

}
