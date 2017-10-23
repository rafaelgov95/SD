package br.rv.ptjs.buffer;

import br.rv.ptjs.model.Documento;
import br.rv.ptjs.model.Impressora;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

/**
 *
 * @author rafael
 */

public class Buffer {


    private final static List<Documento> Documentos = new ArrayList<Documento>();
    private final static List<Impressora> Impressoras = new ArrayList<Impressora>();

    public Buffer() {

    }

    public synchronized static boolean isDocumentos() {
        return Documentos.isEmpty();
    }

    public synchronized static boolean isImpressoras() {
        return Impressoras.isEmpty();
    }

    public static synchronized void addImpressora(Impressora imp){
        Impressoras.add(imp);
    }

    public static void addDocumento(Documento novo) throws InterruptedException, IOException {
        synchronized (Documentos) {
            Documentos.add(novo);

        }

    }

    public static synchronized Documento getDocumento() throws InterruptedException {
        if (!Documentos.isEmpty()) {
            return Documentos.remove(0);
        }
        return null;
    }

    public static synchronized Impressora getImpressora() {
        if (!Impressoras.isEmpty()) {
            return Impressoras.remove(0);
        }
        return null;
    }


}
