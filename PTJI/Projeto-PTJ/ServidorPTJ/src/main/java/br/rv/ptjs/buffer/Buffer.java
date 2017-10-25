package br.rv.ptjs.buffer;

import br.rv.ptjs.model.Documento;
import br.rv.ptjs.model.Impressora;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.Semaphore;

/**
 *
 * @author rafael
 */

public class Buffer {


    private  int r;
    private  int t;
    private  int m;

    private boolean available = false;
    private final static List<Documento> Documentos = new ArrayList<Documento>();
    private final static List<Impressora> Impressoras = new ArrayList<Impressora>();

    public Buffer(int r,int t,int m) {
        this.r = r;
        this.t =t;
        this.m =m;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public synchronized void EscalonadorDormi() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        available = false;
        notifyAll();
    }

    public synchronized void EscalonadorAcordar( ) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        available = true;
        notifyAll();
    }

    public  synchronized void addImpressora(Impressora imp){
        Impressoras.add(imp);
    }

    public  void  addDocumento(Documento novo) throws InterruptedException, IOException {
        synchronized (Documentos) {
            Documentos.add(novo);

        }

    }
    public  void addLevelPDocumento(Documento novo) throws InterruptedException, IOException {
        synchronized (Documentos) {
            Documentos.add(0,novo);

        }

    }
    public  synchronized Documento getDocumento() throws InterruptedException {
        if (!Documentos.isEmpty()) {
            return Documentos.remove(0);
        }
        return null;
    }
    public  synchronized int getDocumentosSize() throws InterruptedException {
      return Documentos.size();
    }

    public  synchronized Impressora getImpressora() {
        if (!Impressoras.isEmpty()) {
            return Impressoras.remove(0);
        }
        return null;
    }


}
