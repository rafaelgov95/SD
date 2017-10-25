package br.rv.ptjs.model;

import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalTime;

/**
 *
 * @author rafael
 */

public class Documento {
    private Socket socket;
    private String mensagem;
    private PrintStream toast;
    private LocalTime tempo;
    public Documento(Documento doc){
        this.socket =doc.socket;
        this.mensagem = doc.mensagem;
        this.toast = doc.toast;
    }
    public Documento(Socket socket, String mensagem, PrintStream toast) {
        this.socket = socket;
        this.mensagem = mensagem;
        this.toast = toast;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public PrintStream getToast() {
        return toast;
    }

    public void setToast(PrintStream toast) {
        this.toast = toast;
    }

    public LocalTime getTempo() {
        return tempo;
    }

    public void setTempo(LocalTime tempo) {
        this.tempo = tempo;
    }
}
