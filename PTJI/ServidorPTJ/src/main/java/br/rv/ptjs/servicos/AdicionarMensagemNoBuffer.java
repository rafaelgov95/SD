package br.rv.ptjs.servicos;

import br.rv.ptjs.buffer.Buffer;

import java.io.InputStream;
import java.io.PrintStream;

public class AdicionarMensagemNoBuffer implements Runnable {
    private Buffer buffer;
    private InputStream is;
    private PrintStream ps;

    public AdicionarMensagemNoBuffer(Buffer buffer, InputStream is, PrintStream ps) {
        this.buffer = buffer;
        this.is = is;
        this.ps = ps;
    }

    public void run() {
            this.buffer.addMensagem(is, ps);
    }
}
