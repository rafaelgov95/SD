package br.rv.ptjs.buffer;

import br.rv.ptjs.model.Documento;
import br.rv.ptjs.model.Impressora;

import javax.print.Doc;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.*;

public class Buffer {


    public final static List<Documento> Documentos = new ArrayList<Documento>();
    public final static List<Impressora> Impressoras = new ArrayList<Impressora>();

    public Buffer() {

    }
    public synchronized static boolean isDocumentos(){
        return Documentos.isEmpty();
    }

    public synchronized static boolean isImpressoras(){
        return Impressoras.isEmpty();
    }
    public static void addDocumento(Documento novo) throws InterruptedException, IOException {
        synchronized (Documentos) {
            Documentos.add(novo);
            String nome = Thread.currentThread().getName();
            novo.toast.println("Mensagem adicionada a Fila.");
            System.out.println("|Cliente   " + nome  + "| Data: " + LocalTime.now().toString() + "| Mensagem: " + novo.getMensagem()+ " |");
        }

    }

    public static synchronized Documento getDocumento() throws InterruptedException {
            if (!Documentos.isEmpty()) {
                return Documentos.remove(0);
            }
        return null;
    }

}
