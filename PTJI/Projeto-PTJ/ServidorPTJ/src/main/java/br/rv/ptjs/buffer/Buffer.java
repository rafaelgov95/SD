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


    private static int r;
    private static int t;
    private static int m;
    private final static List<Documento> Documentos = new ArrayList<Documento>();
    private final static List<Impressora> Impressoras = new ArrayList<Impressora>();

    public Buffer(int r, int t, int m) {
        this.r = r;
        this.t = t;
        this.m = m;

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
    public static void addLevelPDocumento(Documento novo) throws InterruptedException, IOException {
        synchronized (Documentos) {
            Documentos.add(0,novo);

        }

    }
    public static synchronized Documento getDocumento() throws InterruptedException {
        if (!Documentos.isEmpty()) {
            return Documentos.remove(0);
        }
        return null;
    }
    public static synchronized int getDocumentosSize() throws InterruptedException {
      return Documentos.size();
    }

    public static synchronized Impressora getImpressora() {
        if (!Impressoras.isEmpty()) {
            return Impressoras.remove(0);
        }
        return null;
    }

    public static int getR() {
        return r;
    }

    public static void setR(int r) {
        Buffer.r = r;
    }

    public static int getT() {
        return t;
    }

    public static void setT(int t) {
        Buffer.t = t;
    }

    public static int getM() {
        return m;
    }

    public static void setM(int m) {
        Buffer.m = m;
    }

}
