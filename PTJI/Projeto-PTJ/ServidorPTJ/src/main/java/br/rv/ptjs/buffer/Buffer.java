package br.rv.ptjs.buffer;

import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.*;

public class Buffer {


    public static Collection<String> buffer = new Vector<String>();
    public static Collection<String> ports = new Vector<String>();

    public Buffer() {
        this.ports = new Vector<String>();
        this.ports.add("2324");
        this.ports.add("2323");
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

    public static String getMensagem() throws InterruptedException {
        String nome = Thread.currentThread().getName();
        synchronized (buffer) {
            if (!buffer.isEmpty()) {
                Thread.sleep(10000);
               String  m = buffer.toArray()[0].toString();
                buffer.remove(buffer.toArray()[0]);
                return "|Impressora " + nome + "| Data: " + LocalTime.now().toString() + "| Mensagem: " + m + " |";

            } else {
            }
        }
    return "Error";
    }

}
